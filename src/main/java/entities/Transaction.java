package entities;

import java.time.LocalDate;

public class Transaction {
int id , client_id,client2_id;
String nom_destinataire,prenom_destinataire,iban_destinataire,iban;
    Double montant;
            LocalDate date_transaction;


    public Transaction() {
    }

    public Transaction( int client_id, int client2_id, String nom_destinataire, String prenom_destinataire, String iban_destinataire, String iban, Double montant, LocalDate date_transaction) {
        this.client_id = client_id;
        this.client2_id = client2_id;
        this.nom_destinataire = nom_destinataire;
        this.prenom_destinataire = prenom_destinataire;
        this.iban_destinataire = iban_destinataire;
        this.iban = iban;
        this.montant = montant;
        this.date_transaction = date_transaction;
    }
    public Transaction(int id, int client_id, int client2_id, String nom_destinataire, String prenom_destinataire, String iban_destinataire, String iban, Double montant, LocalDate date_transaction) {
        this.id = id;
        this.client_id = client_id;
        this.client2_id = client2_id;
        this.nom_destinataire = nom_destinataire;
        this.prenom_destinataire = prenom_destinataire;
        this.iban_destinataire = iban_destinataire;
        this.iban = iban;
        this.montant = montant;
        this.date_transaction = date_transaction;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getClient2_id() {
        return client2_id;
    }

    public void setClient2_id(int client2_id) {
        this.client2_id = client2_id;
    }

    public String getNom_destinataire() {
        return nom_destinataire;
    }

    public void setNom_destinataire(String nom_destinataire) {
        this.nom_destinataire = nom_destinataire;
    }

    public String getPrenom_destinataire() {
        return prenom_destinataire;
    }

    public void setPrenom_destinataire(String prenom_destinataire) {
        this.prenom_destinataire = prenom_destinataire;
    }

    public String getIban_destinataire() {
        return iban_destinataire;
    }

    public void setIban_destinataire(String iban_destinataire) {
        this.iban_destinataire = iban_destinataire;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDate getDate_transaction() {
        return date_transaction;
    }

    public void setDate_transaction(LocalDate date_transaction) {
        this.date_transaction = date_transaction;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", client2_id=" + client2_id +
                ", nom_destinataire='" + nom_destinataire + '\'' +
                ", prenom_destinataire='" + prenom_destinataire + '\'' +
                ", iban_destinataire='" + iban_destinataire + '\'' +
                ", iban='" + iban + '\'' +
                ", montant=" + montant +
                ", date_transaction=" + date_transaction +
                '}';
    }
}
