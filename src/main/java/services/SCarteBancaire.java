package services;

import entities.Cartebancaire;
import utils.DB;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class SCarteBancaire implements IService<Cartebancaire> {

    private Connection con = DB.getInstance().getConnection();

    public SCarteBancaire() {
    }

    @Override
    public Set<Cartebancaire> afficher() throws SQLException {
        Set<Cartebancaire> cartesBancaires = new HashSet<>();
        String query = "SELECT * FROM `carte_bancaire`";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            Cartebancaire carteBancaire = new Cartebancaire();
            carteBancaire.setId(rs.getInt("id"));
            carteBancaire.setCompte_id(rs.getInt("compte_id"));
            carteBancaire.setIs_frozen(rs.getInt("is_frozen"));
            carteBancaire.setNum_carte(rs.getString("num_carte"));
            carteBancaire.setCvv(rs.getString("cvv"));
            carteBancaire.setDate_exp(rs.getDate("date_exp").toLocalDate());
            cartesBancaires.add(carteBancaire);
        }
        return cartesBancaires;
    }


    @Override
    public void ajouter(Cartebancaire cartebancaire) {
        try {


            String query = "INSERT INTO carte_bancaire (compte_id, is_frozen, num_carte, cvv, date_exp,etablissement_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, cartebancaire.getCompte_id());
            statement.setInt(2, cartebancaire.getIs_frozen());
            statement.setString(3, cartebancaire.getNum_carte());
            statement.setString(4, cartebancaire.getCvv());
            statement.setDate(5, cartebancaire.getSqlDate());
            statement.setInt(6, cartebancaire.getEtablissement_id());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Carte bancaire ajoutée avec succès.");
            } else {
                System.out.println("Aucune ligne affectée lors de l'insertion de la carte bancaire.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout de la carte bancaire : " + ex.getMessage());
        }
    }



    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM carte_bancaire WHERE id = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }


    @Override
    public void modifier(Cartebancaire cartebancaire) throws SQLException {
        String query = "UPDATE carte_bancaire SET compte_id=?, is_frozen=?, num_carte=?, cvv=?, date_exp=?, etablissement_id=? WHERE id=?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, cartebancaire.getCompte_id());
        pstmt.setInt(2, cartebancaire.getIs_frozen());
        pstmt.setString(3, cartebancaire.getNum_carte());
        pstmt.setString(4, cartebancaire.getCvv());
        pstmt.setDate(5, cartebancaire.getSqlDate());
        pstmt.setInt(6, cartebancaire.getEtablissement_id());
        pstmt.setInt(7, cartebancaire.getId());
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("La carte bancaire est modifiée !");
        } else {
            System.out.println("Aucune carte bancaire n'a été modifiée.");
        }
    }

}
