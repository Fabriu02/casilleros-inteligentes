package com.casilleros_inteligentes.modelos;
import jakarta.persistence.*;

@Entity
@Table(name = "tarjetas_rfid")
public class TarjetaRFID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String uid;

    @Column(nullable = false)
    private Integer estado; 

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public TarjetaRFID() {}

    public TarjetaRFID(String uid, Integer estado, Usuario usuario) {
        this.uid = uid;
        this.estado = estado;
        this.usuario = usuario;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}