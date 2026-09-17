package com.casilleros_inteligentes.controladores;

import com.casilleros_inteligentes.modelos.Reserva;
import com.casilleros_inteligentes.repositorio.reporeserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController // Fundamental: Responde JSON (datos puros)
@RequestMapping("/api/hardware") // Todas las rutas empezarán con esto
public class controlador_hardware {

    @Autowired
    private reporeserva reservaRepository;

    // Ruta que el módulo Wi-Fi (ESP-01) del Arduino consultará
    @GetMapping("/verificar")
    public Map<String, Object> verificarPin(@RequestParam Long casilleroId, @RequestParam String pin) {

        // Usamos un Map para construir la respuesta JSON
        Map<String, Object> respuesta = new HashMap<>();

        // Buscamos si hay una reserva activa en ese casillero específico
        Optional<Reserva> reservaActiva = reservaRepository.findByCasilleroIdAndEstado(casilleroId, "ACTIVA");

        if (reservaActiva.isPresent()) {
            // Comparamos el PIN que envió el hardware con el de la base de datos
            if (reservaActiva.get().getPin().equals(pin)) {
                respuesta.put("abrir", true);
                respuesta.put("mensaje", "PIN Aceptado. Abriendo puerta...");
            } else {
                respuesta.put("abrir", false);
                respuesta.put("mensaje", "PIN Incorrecto.");
            }
        } else {
            respuesta.put("abrir", false);
            respuesta.put("mensaje", "El casillero no está reservado.");
        }

        // Spring Boot convierte este Map a formato JSON automáticamente
        return respuesta;
    }
}