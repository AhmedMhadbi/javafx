package entities;

import java.time.LocalDate;

public class Cartebancaire {

    int id, compte_id, is_frozen;
    String num_carte, cvv;
    LocalDate date_exp;

    public Cartebancaire() {
    }

    public Cartebancaire(int compte_id, int is_frozen, String num_carte, String cvv, LocalDate date_exp) {
        this.compte_id = compte_id;
        this.is_frozen = is_frozen;
        this.num_carte = num_carte;
        this.cvv = cvv;
        this.date_exp = date_exp;
    }
    public Cartebancaire(int id, int compte_id, int is_frozen, String num_carte, String cvv, LocalDate date_exp) {
        this.id = id;
        this.compte_id = compte_id;
        this.is_frozen = is_frozen;
        this.num_carte = num_carte;
        this.cvv = cvv;
        this.date_exp = date_exp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompte_id() {
        return compte_id;
    }

    public void setCompte_id(int compte_id) {
        this.compte_id = compte_id;
    }

    public int getIs_frozen() {
        return is_frozen;
    }

    public void setIs_frozen(int is_frozen) {
        this.is_frozen = is_frozen;
    }

    public String getNum_carte() {
        return num_carte;
    }

    public void setNum_carte(String num_carte) {
        this.num_carte = num_carte;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getDate_exp() {
        return date_exp;
    }

    public void setDate_exp(LocalDate date_exp) {
        this.date_exp = date_exp;
    }


    @Override
    public String toString() {
        return "Cartebancaire{" +
                "id=" + id +
                ", compte_id=" + compte_id +
                ", is_frozen=" + is_frozen +
                ", num_carte='" + num_carte + '\'' +
                ", cvv='" + cvv + '\'' +
                ", date_exp=" + date_exp +
                '}';
    }
    public java.sql.Date getSqlDate() {
        return java.sql.Date.valueOf(date_exp);
    }

}
