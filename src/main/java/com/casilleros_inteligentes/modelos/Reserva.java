package com.casilleros_inteligentes.modelos;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "casillero_id", nullable = false)
    private Casillero casillero;

    @Column(nullable = false)
    private String pin;

    @Column(nullable = false)
    private String estado;

    // AHORA ES FECHA DE INICIO
    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    public Reserva() {}

    public Reserva(Usuario usuario, Casillero casillero, String pin, String estado, LocalDateTime fechaInicio) {
        this.usuario = usuario;
        this.casillero = casillero;
        this.pin = pin;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Casillero getCasillero() { return casillero; }
    public void setCasillero(Casillero casillero) { this.casillero = casillero; }
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
}