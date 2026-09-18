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
    private Integer estado;

    @Transient private String ocupadoDesde;
    @Transient private String ocupadoPor;
    @Transient private Long ocupadoPorId;

    public Casillero() {}

    public Casillero(Integer numero, Integer estado) {
        this.numero = numero;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }
    
    public String getOcupadoDesde() { return ocupadoDesde; }
    public void setOcupadoDesde(String ocupadoDesde) { this.ocupadoDesde = ocupadoDesde; }
    public String getOcupadoPor() { return ocupadoPor; }
    public void setOcupadoPor(String ocupadoPor) { this.ocupadoPor = ocupadoPor; }
    public Long getOcupadoPorId() { return ocupadoPorId; }
    public void setOcupadoPorId(Long ocupadoPorId) { this.ocupadoPorId = ocupadoPorId; }
}