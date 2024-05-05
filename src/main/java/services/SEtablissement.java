package services;

import entities.Etablissement;
import utils.DB;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class SEtablissement implements IService<Etablissement>{


    private Connection con = DB.getInstance().getConnection();

    public SEtablissement() {
    }

    @Override
    public Set<Etablissement> afficher() throws SQLException {
        Set<Etablissement> etablissements = new HashSet<>();
        String query = "SELECT * FROM `etablissement`";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            Etablissement etablissement = new Etablissement();
            etablissement.setId(rs.getInt("id"));
            etablissement.setCompte_id(rs.getInt("compte_id"));
            etablissement.setNum_contact(rs.getInt("num_contact"));
            etablissement.setNom(rs.getString("nom"));
            etablissement.setAdresse(rs.getString("adresse"));
            etablissements.add(etablissement);
        }
        return etablissements;
    }


    @Override
    public void ajouter(Etablissement etablissement) {
        try {
            String query = "INSERT INTO etablissement (compte_id, num_contact, nom, adresse) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, etablissement.getCompte_id());
            statement.setInt(2, etablissement.getNum_contact());
            statement.setString(3, etablissement.getNom());
            statement.setString(4, etablissement.getAdresse());
            statement.executeUpdate();
            System.out.println("Établissement ajouté avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM etablissement WHERE id = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }


    @Override
    public void modifier(Etablissement etablissement) throws SQLException {
        String query = "UPDATE etablissement SET compte_id=?, num_contact=?, nom=?, adresse=? WHERE id=?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, etablissement.getCompte_id());
        pstmt.setInt(2, etablissement.getNum_contact());
        pstmt.setString(3, etablissement.getNom());
        pstmt.setString(4, etablissement.getAdresse());
        pstmt.setInt(5, etablissement.getId());
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("L'établissement a été modifié !");
        } else {
            System.out.println("Aucun établissement n'a été modifié.");
        }
    }



}
