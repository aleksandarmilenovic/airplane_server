package org.plu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "karta")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Karta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int userid;

    @NotBlank
    private String korisnik;

    @NotBlank
    private String card;

    @NotBlank
    private String jmbg;

    @Column(nullable = false)
    private int idleta;

    @Column(nullable = false)
    private int sediste;

    @NotBlank
    private String status;

    @Column(nullable = false)
    private long vremerezervacije;

    @Column(nullable = false)
    private long veremeleta;

    public Karta(){}

    public Karta(int userid, String korisnik, String card, String jmbg, int idleta, int sediste, String status, long vremerezervacije, long veremeleta) {
        this.userid = userid;
        this.korisnik = korisnik;
        this.card = card;
        this.jmbg = jmbg;
        this.idleta = idleta;
        this.sediste = sediste;
        this.status = status;
        this.vremerezervacije = vremerezervacije;
        this.veremeleta = veremeleta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public int getIdleta() {
        return idleta;
    }

    public void setIdleta(int idleta) {
        this.idleta = idleta;
    }

    public int getSediste() {
        return sediste;
    }

    public void setSediste(int sediste) {
        this.sediste = sediste;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getVremerezervacije() {
        return vremerezervacije;
    }

    public void setVremerezervacije(int vremerezervacije) {
        this.vremerezervacije = vremerezervacije;
    }

    public long getVeremeleta() {
        return veremeleta;
    }

    public void setVeremeleta(int veremeleta) {
        this.veremeleta = veremeleta;
    }
}
