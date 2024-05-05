package entities;

public class Etablissement {

int id, compte_id,num_contact;
String nom,adresse;

    public Etablissement() {
    }

    public Etablissement(int compte_id, int num_contact, String nom, String adresse) {
        this.compte_id = compte_id;
        this.num_contact = num_contact;
        this.nom = nom;
        this.adresse = adresse;
    }

    public Etablissement(int id, int compte_id, int num_contact, String nom, String adresse) {
        this.id = id;
        this.compte_id = compte_id;
        this.num_contact = num_contact;
        this.nom = nom;
        this.adresse = adresse;
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

    public int getNum_contact() {
        return num_contact;
    }

    public void setNum_contact(int num_contact) {
        this.num_contact = num_contact;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Etablissement{" +
                "id=" + id +
                ", compte_id=" + compte_id +
                ", num_contact=" + num_contact +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
