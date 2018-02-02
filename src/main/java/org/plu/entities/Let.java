package org.plu.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "letovi")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Let {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int brojLeta;

    @Column(nullable = false)
    private int idAviona;

    @Column(nullable = false)
    private int polaziste; // idGrada

    @Column(nullable = false)
    private int odrediste; // idGrada

    @Column(nullable = false)
    private long vremePolaska;

    @Column(nullable = false)
    private long vremeDolaska;

    @Column(nullable = false)
    private int vremePutovanja;

    @Column(nullable = false)
    private int razdaljinaDestinacije;

    @Column(nullable = false)
    private int gate; // polazak

    public Let(){}

    // operator unosi za let
//    public Let(int brojLeta,int idAviona,int polaziste, int odrediste, int vremePolaska, int razdaljinaDestinacije) {
//        this.brojLeta = brojLeta; // op
//        this.idAviona = idAviona; // op
//        this.polaziste = polaziste; // op
//        this.odrediste = odrediste; // op
//        this.vremePolaska = vremePolaska; // op
//        this.razdaljinaDestinacije = razdaljinaDestinacije; // op
//    }

    // sva polja
    public Let(int brojLeta, int idAviona, int polaziste, int odrediste, long vremePolaska, long vremeDolaska, int vremePutovanja, int razdaljinaDestinacije, int gate) {
        this.brojLeta = brojLeta;
        this.idAviona = idAviona;
        this.polaziste = polaziste;
        this.odrediste = odrediste;
        this.vremePolaska = vremePolaska;
        this.vremeDolaska = vremeDolaska;
        this.vremePutovanja = vremePutovanja;
        this.razdaljinaDestinacije = razdaljinaDestinacije;
        this.gate = gate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getBrojLeta() {
        return brojLeta;
    }

    public void setBrojLeta(int brojLeta) {
        this.brojLeta = brojLeta;
    }

    public int getIdAviona() {
        return idAviona;
    }

    public void setIdAviona(int idAviona) {
        this.idAviona = idAviona;
    }

    public int getPolaziste() {
        return polaziste;
    }

    public void setPolaziste(int polaziste) {
        this.polaziste = polaziste;
    }

    public int getOdrediste() {
        return odrediste;
    }

    public void setOdrediste(int odrediste) {
        this.odrediste = odrediste;
    }

    public long getVremePolaska() {
        return vremePolaska;
    }

    public void setVremePolaska(long vremePolaska) {
        this.vremePolaska = vremePolaska;
    }

    public long getVremeDolaska() {
        return vremeDolaska;
    }

    public void setVremeDolaska(long vremeDolaska) {
        this.vremeDolaska = vremeDolaska;
    }

    public int getVremePutovanja() {
        return vremePutovanja;
    }

    public void setVremePutovanja(int vremePutovanja) {
        this.vremePutovanja = vremePutovanja;
    }

    public int getRazdaljinaDestinacije() {
        return razdaljinaDestinacije;
    }

    public void setRazdaljinaDestinacije(int razdaljinaDestinacije) {
        this.razdaljinaDestinacije = razdaljinaDestinacije;
    }

    public int getGate() {
        return gate;
    }

    public void setGate(int gate) {
        this.gate = gate;
    }

}
