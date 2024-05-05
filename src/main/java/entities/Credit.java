package entities;

import java.time.LocalDate;

public class Credit {

    int id, nb_mois;
    Double montant,montant_emprunte,montant_paye,mensualite,taux_interet;
    String type,status,description,image_name;
    LocalDate date_demande,date_emission, date_echeance;


    public Credit() {
    }

    public Credit(int nb_mois, Double montant, Double montant_emprunte, Double montant_paye, Double mensualite, Double taux_interet, String type, String status, String description, String image_name, LocalDate date_demande, LocalDate date_emission, LocalDate date_echeance) {

        this.nb_mois = nb_mois;
        this.montant = montant;
        this.montant_emprunte = montant_emprunte;
        this.montant_paye = montant_paye;
        this.mensualite = mensualite;
        this.taux_interet = taux_interet;
        this.type = type;
        this.status = status;
        this.description = description;
        this.image_name = image_name;
        this.date_demande = date_demande;
        this.date_emission = date_emission;
        this.date_echeance = date_echeance;
    }

    public Credit(int id, int nb_mois, Double montant, Double montant_emprunte, Double montant_paye, Double mensualite, Double taux_interet, String type, String status, String description, String image_name, LocalDate date_demande, LocalDate date_emission, LocalDate date_echeance) {
        this.id = id;
        this.nb_mois = nb_mois;
        this.montant = montant;
        this.montant_emprunte = montant_emprunte;
        this.montant_paye = montant_paye;
        this.mensualite = mensualite;
        this.taux_interet = taux_interet;
        this.type = type;
        this.status = status;
        this.description = description;
        this.image_name = image_name;
        this.date_demande = date_demande;
        this.date_emission = date_emission;
        this.date_echeance = date_echeance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNb_mois() {
        return nb_mois;
    }

    public void setNb_mois(int nb_mois) {
        this.nb_mois = nb_mois;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getMontant_emprunte() {
        return montant_emprunte;
    }

    public void setMontant_emprunte(Double montant_emprunte) {
        this.montant_emprunte = montant_emprunte;
    }

    public Double getMontant_paye() {
        return montant_paye;
    }

    public void setMontant_paye(Double montant_paye) {
        this.montant_paye = montant_paye;
    }

    public Double getMensualite() {
        return mensualite;
    }

    public void setMensualite(Double mensualite) {
        this.mensualite = mensualite;
    }

    public Double getTaux_interet() {
        return taux_interet;
    }

    public void setTaux_interet(Double taux_interet) {
        this.taux_interet = taux_interet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public LocalDate getDate_demande() {
        return date_demande;
    }

    public void setDate_demande(LocalDate date_demande) {
        this.date_demande = date_demande;
    }

    public LocalDate getDate_emission() {
        return date_emission;
    }

    public void setDate_emission(LocalDate date_emission) {
        this.date_emission = date_emission;
    }

    public LocalDate getDate_echeance() {
        return date_echeance;
    }

    public void setDate_echeance(LocalDate date_echeance) {
        this.date_echeance = date_echeance;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", nb_mois=" + nb_mois +
                ", montant=" + montant +
                ", montant_emprunte=" + montant_emprunte +
                ", montant_paye=" + montant_paye +
                ", mensualite=" + mensualite +
                ", taux_interet=" + taux_interet +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", image_name='" + image_name + '\'' +
                ", date_demande=" + date_demande +
                ", date_emission=" + date_emission +
                ", date_echeance=" + date_echeance +
                '}';
    }
    public java.sql.Date getSqlDate() {
        return java.sql.Date.valueOf(date_demande);
    }
    public java.sql.Date getSqlDatee() {
        return java.sql.Date.valueOf(date_emission);
    }
    public java.sql.Date getSqlDateee() {
        return java.sql.Date.valueOf(date_echeance);
    }


}
