package org.plu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "avioni")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Avion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    private String marka;

    @NotBlank
    private String model;

    @Column(nullable = false)
    private int brojSedista;

    @Column(nullable = false)
    private int brojKolona;

    @NotBlank
    private String redovi;

    @Column(nullable = false)
    private int maksimalniDolet;

    @Column(nullable = false)
    private int brzinaLetenja; //

    public Avion(){}

    public Avion(String marka, String model, int brojSedista, int brojKolona, String redovi, int maksimalniDolet, int brzinaLetenja) {
        this.marka = marka;
        this.model = model;
        this.brojSedista = brojSedista;
        this.brojKolona = brojKolona;
        this.redovi = redovi;
        this.maksimalniDolet = maksimalniDolet;
        this.brzinaLetenja = brzinaLetenja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getBrojSedista() {
        return brojSedista;
    }

    public void setBrojSedista(int brojSedista) {
        this.brojSedista = brojSedista;
    }

    public int getBrojKolona() {
        return brojKolona;
    }

    public void setBrojKolona(int brojKolona) {
        this.brojKolona = brojKolona;
    }

    public String getRedovi() {
        return redovi;
    }

    public void setRedovi(String redovi) {
        this.redovi = redovi;
    }

    public int getMaksimalniDolet() {
        return maksimalniDolet;
    }

    public void setMaksimalniDolet(int maksimalniDolet) {
        this.maksimalniDolet = maksimalniDolet;
    }

    public int getBrzinaLetenja() {
        return brzinaLetenja;
    }

    public void setBrzinaLetenja(int brzinaLetenja) {
        this.brzinaLetenja = brzinaLetenja;
    }
}
