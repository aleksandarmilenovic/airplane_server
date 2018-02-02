package org.plu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "aerodromi")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Aerodrom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    private String naziv;

    @Column(nullable = false)
    private int vremenskaZona;

    @Column(nullable = false)
    private int maxGate;

    @NotBlank
    private String zauzetiGateovi;

    public Aerodrom(){}

    public Aerodrom(String naziv, int vremenskaZona, int maxGate, String zauzetiGateovi) {
        this.naziv = naziv;
        this.vremenskaZona = vremenskaZona;
        this.maxGate = maxGate;
        this.zauzetiGateovi = zauzetiGateovi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getVremenskaZona() {
        return vremenskaZona;
    }

    public void setVremenskaZona(int vremenskaZona) {
        this.vremenskaZona = vremenskaZona;
    }

    public int getMaxGate() {
        return maxGate;
    }

    public void setMaxGate(int maxGate) {
        this.maxGate = maxGate;
    }

    public String getZauzetiGateovi() {
        return zauzetiGateovi;
    }

    public void setZauzetiGateovi(String zauzetiGateovi) {
        this.zauzetiGateovi = zauzetiGateovi;
    }


}
