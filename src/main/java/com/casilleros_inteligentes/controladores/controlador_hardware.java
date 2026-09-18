package com.casilleros_inteligentes.controladores;

import com.casilleros_inteligentes.modelos.Reserva;
import com.casilleros_inteligentes.modelos.TarjetaRFID;
import com.casilleros_inteligentes.modelos.Usuario;
import com.casilleros_inteligentes.repositorio.RepoTarjetaRFID;
import com.casilleros_inteligentes.repositorio.reporeserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController 
@RequestMapping("/api/hardware")
public class controlador_hardware {

    @Autowired private reporeserva reservaRepository;
    @Autowired private RepoTarjetaRFID tarjetaRepository; 

    @GetMapping("/verificar")
    public Map<String, Object> verificarAcceso(@RequestParam Long casilleroId, 
                                               @RequestParam(required = false) String pin, 
                                               @RequestParam(required = false) String rfid) {
        
        Map<String, Object> respuesta = new HashMap<>();
        
        // 0 = RESERVA ACTIVA
        Optional<Reserva> reservaActiva = reservaRepository.findByCasilleroIdAndEstado(casilleroId, 0);

        if (reservaActiva.isPresent()) {
            Reserva reserva = reservaActiva.get();

            if (pin != null && !pin.isEmpty()) {
                if (reserva.getPin().equals(pin)) {
                    respuesta.put("abrir", true);
                    respuesta.put("mensaje", "PIN Aceptado.");
                    return respuesta;
                }
            }
            
            if (rfid != null && !rfid.isEmpty()) {
                // 0 = TARJETA ACTIVA
                Optional<TarjetaRFID> tarjetaOpt = tarjetaRepository.findByUidAndEstado(rfid, 0);
                
                if (tarjetaOpt.isPresent()) {
                    Usuario duenioTarjeta = tarjetaOpt.get().getUsuario();
                    if (reserva.getUsuario().getId().equals(duenioTarjeta.getId())) {
                        respuesta.put("abrir", true);
                        respuesta.put("mensaje", "Tarjeta RFID Aceptada. Autorizado.");
                        return respuesta;
                    } else {
                        respuesta.put("abrir", false);
                        respuesta.put("mensaje", "Acceso denegado: Esta tarjeta no pertenece al usuario que reservó el casillero.");
                        return respuesta;
                    }
                } else {
                    respuesta.put("abrir", false);
                    respuesta.put("mensaje", "Tarjeta no registrada, extraviada o bloqueada.");
                    return respuesta;
                }
            }

            respuesta.put("abrir", false);
            respuesta.put("mensaje", "Credenciales incorrectas.");

        } else {
            respuesta.put("abrir", false);
            respuesta.put("mensaje", "El casillero no está reservado.");
        }

        return respuesta; 
    }
}