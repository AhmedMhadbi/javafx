package entities;

import java.time.LocalDate;

public class Rembourssement {
    int id, credit_id;
        Double montant ;
        LocalDate date_remboursement;


    public Rembourssement() {
    }

    public Rembourssement( int credit_id, Double montant, LocalDate date_remboursement) {
        this.credit_id = credit_id;
        this.montant = montant;
        this.date_remboursement = date_remboursement;
    }
    public Rembourssement(int id, int credit_id, Double montant, LocalDate date_remboursement) {
        this.id = id;
        this.credit_id = credit_id;
        this.montant = montant;
        this.date_remboursement = date_remboursement;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(int credit_id) {
        this.credit_id = credit_id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDate getDate_remboursement() {
        return date_remboursement;
    }

    public void setDate_remboursement(LocalDate date_remboursement) {
        this.date_remboursement = date_remboursement;
    }


    @Override
    public String toString() {
        return "Rembourssement{" +
                "id=" + id +
                ", credit_id=" + credit_id +
                ", montant=" + montant +
                ", date_remboursement=" + date_remboursement +
                '}';
    }
}
