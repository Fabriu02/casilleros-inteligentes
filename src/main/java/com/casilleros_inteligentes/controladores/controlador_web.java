package com.casilleros_inteligentes.controladores;

import com.casilleros_inteligentes.modelos.Casillero;
import com.casilleros_inteligentes.modelos.Reserva;
import com.casilleros_inteligentes.modelos.Usuario;
import com.casilleros_inteligentes.repositorio.repocasillero;
import com.casilleros_inteligentes.repositorio.reporeserva;
import com.casilleros_inteligentes.repositorio.usuariorepo;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class controlador_web {

    @Autowired private repocasillero repository;
    @Autowired private usuariorepo usuarioRepository;
    @Autowired private reporeserva reservaRepository;

    @PostConstruct
    public void inicializarCasilleros() {
        if (repository.count() == 0) {
            // 0 = CASILLERO LIBRE
            repository.save(new Casillero(1, 0));
            repository.save(new Casillero(2, 0));
            repository.save(new Casillero(3, 0));
            repository.save(new Casillero(4, 0));
        }
    }

    @GetMapping("/")
    public String mostrarInicio(HttpSession session, Model model, @RequestParam(required = false) String error) {
        Long usuarioId = (Long) session.getAttribute("usuario_id");
        if (usuarioId == null) return "redirect:/login"; 

        List<Casillero> listaCasilleros = repository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Casillero c : listaCasilleros) {
            // 1 = CASILLERO OCUPADO
            if (c.getEstado() == 1) {
                // 0 = RESERVA ACTIVA
                Optional<Reserva> resOpt = reservaRepository.findByCasilleroIdAndEstado(c.getId(), 0);
                if (resOpt.isPresent()) {
                    c.setOcupadoDesde(resOpt.get().getFechaInicio().format(formatter));
                    c.setOcupadoPor(resOpt.get().getUsuario().getNombre());
                    c.setOcupadoPorId(resOpt.get().getUsuario().getId());
                }
            }
        }

        model.addAttribute("error", error); 
        model.addAttribute("titulo", "Panel de Casilleros");
        model.addAttribute("casilleros", listaCasilleros);
        model.addAttribute("nombreUsuario", session.getAttribute("usuario_nombre"));
        model.addAttribute("rolUsuario", session.getAttribute("usuario_rol"));
        model.addAttribute("usuarioIdLogueado", usuarioId); 

        return "gestion-casilleros";
    }

    @PostMapping("/reservar")
    public String hacerReserva(@RequestParam Long casilleroId, @RequestParam String pin, HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("usuario_id");
        String rol = (String) session.getAttribute("usuario_rol");
        if (usuarioId == null) return "redirect:/login";

        // 0 = RESERVA ACTIVA
        if ("ALUMNO".equals(rol) && reservaRepository.existsByUsuarioIdAndEstado(usuarioId, 0)) {
            return "redirect:/?error=¡Solo puedes reservar 1 casillero a la vez!";
        }

        Casillero casillero = repository.findById(casilleroId).orElse(null);
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        // 0 = CASILLERO LIBRE
        if (casillero != null && usuario != null && casillero.getEstado() == 0) {
            // 1 = CASILLERO OCUPADO
            casillero.setEstado(1);
            repository.save(casillero);
            
            // 0 = RESERVA ACTIVA
            Reserva nuevaReserva = new Reserva(usuario, casillero, pin, 0, LocalDateTime.now());
            reservaRepository.save(nuevaReserva);
        }

        return "redirect:/";
    }

    @PostMapping("/liberar")
    public String liberarCasillero(@RequestParam Long casilleroId, HttpSession session) {
        Long uId = (Long) session.getAttribute("usuario_id");
        String rol = (String) session.getAttribute("usuario_rol");
        if (uId == null) return "redirect:/login"; 

        Casillero casillero = repository.findById(casilleroId).orElse(null);
        
        // 1 = CASILLERO OCUPADO
        if (casillero != null && casillero.getEstado() == 1) {
            // 0 = RESERVA ACTIVA
            Optional<Reserva> resOpt = reservaRepository.findByCasilleroIdAndEstado(casilleroId, 0);
            
            if (resOpt.isPresent()) {
                Reserva reserva = resOpt.get();
                if ("ADMIN".equals(rol) || reserva.getUsuario().getId().equals(uId)) {
                    // 0 = CASILLERO LIBRE
                    casillero.setEstado(0);
                    repository.save(casillero);

                    // 1 = RESERVA FINALIZADA
                    reserva.setEstado(1);
                    reservaRepository.save(reserva);
                }
            }
        }
        return "redirect:/";
    }
}