package entities;

public class Compte {


    int id;
    String num_compte, type_compte;

    public Compte() {
    }

    public Compte(int id, String num_compte, String type_compte) {
        this.id = id;
        this.num_compte = num_compte;
        this.type_compte = type_compte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum_compte() {
        return num_compte;
    }

    public void setNum_compte(String num_compte) {
        this.num_compte = num_compte;
    }

    public String getType_compte() {
        return type_compte;
    }

    public void setType_compte(String type_compte) {
        this.type_compte = type_compte;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", num_compte='" + num_compte + '\'' +
                ", type_compte='" + type_compte + '\'' +
                '}';
    }
}
