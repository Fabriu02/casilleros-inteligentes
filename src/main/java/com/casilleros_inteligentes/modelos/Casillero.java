package com.casilleros_inteligentes.modelos;
import jakarta.persistence.*;

@Entity
@Table(name = "casilleros")
public class Casillero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private String estado;

    @Transient
    private String ocupadoDesde;

    @Transient
    private String ocupadoPor;

    @Transient
    private Long ocupadoPorId; // Para verificar si el usuario actual es el dueño

    public Casillero() {}

    public Casillero(Integer numero, String estado) {
        this.numero = numero;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getOcupadoDesde() { return ocupadoDesde; }
    public void setOcupadoDesde(String ocupadoDesde) { this.ocupadoDesde = ocupadoDesde; }
    public String getOcupadoPor() { return ocupadoPor; }
    public void setOcupadoPor(String ocupadoPor) { this.ocupadoPor = ocupadoPor; }
    public Long getOcupadoPorId() { return ocupadoPorId; }
    public void setOcupadoPorId(Long ocupadoPorId) { this.ocupadoPorId = ocupadoPorId; }
}