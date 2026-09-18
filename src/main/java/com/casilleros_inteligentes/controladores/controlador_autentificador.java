package com.casilleros_inteligentes.controladores;

import com.casilleros_inteligentes.modelos.TarjetaRFID;
import com.casilleros_inteligentes.modelos.Usuario;
import com.casilleros_inteligentes.repositorio.RepoTarjetaRFID;
import com.casilleros_inteligentes.repositorio.usuariorepo;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class controlador_autentificador {

    @Autowired private usuariorepo usuarioRepository;
    @Autowired private RepoTarjetaRFID tarjetaRepository;

    @PostConstruct
    public void crearUsuariosPrueba() {
        if (usuarioRepository.count() == 0) {
            Usuario alumno = new Usuario("Estudiante UGD", "alumno@ugd.edu.ar", "1234", "ALUMNO");
            usuarioRepository.save(alumno);

            // 0 = TARJETA ACTIVA
            TarjetaRFID tarjetaAlumno = new TarjetaRFID("A1B2C3D4", 0, alumno);
            tarjetaRepository.save(tarjetaAlumno);

            usuarioRepository.save(new Usuario("Administrador", "admin@ugd.edu.ar", "admin123", "ADMIN"));
        }
    }

    @GetMapping("/login")
    public String mostrarLogin() { return "login"; }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(password)) {
            session.setAttribute("usuario_id", usuarioOpt.get().getId());
            session.setAttribute("usuario_nombre", usuarioOpt.get().getNombre());
            session.setAttribute("usuario_rol", usuarioOpt.get().getRol());
            return "redirect:/"; 
        }
        model.addAttribute("error", "Correo o contraseña incorrectos");
        return "login";
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login";
    }
}