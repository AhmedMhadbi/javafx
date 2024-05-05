package services;

import entities.Credit;
import utils.DB;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class SCredit implements IService<Credit>{
    private Connection con = DB.getInstance().getConnection();

    public SCredit() {
    }
    @Override
    public Set<Credit> afficher() throws SQLException {
        Set<Credit> creditList = new HashSet<>();
        String query = "SELECT * FROM `credit`"; // Changer 'relever' à 'credit'
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            Credit credit = new Credit(
                    rs.getInt("id"),
                    rs.getInt("nb_mois"),
                    rs.getDouble("montant"),
                    rs.getDouble("montant_emprunte"),
                    rs.getDouble("montant_paye"),
                    rs.getDouble("mensualite"),
                    rs.getDouble("taux_interet"),
                    rs.getString("type"),
                    rs.getString("status"),
                    rs.getString("description"),
                    rs.getString("image_name"),
                    rs.getDate("date_demande").toLocalDate(),
                    rs.getDate("date_emission").toLocalDate(),
                    rs.getDate("date_echeance").toLocalDate()
            );
            creditList.add(credit);
        }
        return creditList;
    }

    @Override
    public void ajouter(Credit credit) {
        try {
            String query = "INSERT INTO credit (nb_mois, montant, montant_emprunte, montant_paye, mensualite, taux_interet, type, status, description, image_name, date_demande, date_emission, date_echeance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, credit.getNb_mois());
            statement.setDouble(2, credit.getMontant());
            statement.setDouble(3, credit.getMontant_emprunte());
            statement.setDouble(4, credit.getMontant_paye());
            statement.setDouble(5, credit.getMensualite());
            statement.setDouble(6, credit.getTaux_interet());
            statement.setString(7, credit.getType());
            statement.setString(8, credit.getStatus());
            statement.setString(9, credit.getDescription());
            statement.setString(10, credit.getImage_name());
            statement.setDate(11, credit.getSqlDate());
            statement.setDate(12, credit.getSqlDatee());
            statement.setDate(13, credit.getSqlDateee());
            statement.executeUpdate();
            System.out.println("Crédit ajouté avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM credit WHERE id = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }


    @Override
    public void modifier(Credit credit) throws SQLException {
        String query = "UPDATE credit SET nb_mois=?, montant=?, montant_emprunte=?, montant_paye=?, mensualite=?, taux_interet=?, type=?, status=?, description=?, image_name=?, date_demande=?, date_emission=?, date_echeance=? WHERE id=?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, credit.getNb_mois());
        pstmt.setDouble(2, credit.getMontant());
        pstmt.setDouble(3, credit.getMontant_emprunte());
        pstmt.setDouble(4, credit.getMontant_paye());
        pstmt.setDouble(5, credit.getMensualite());
        pstmt.setDouble(6, credit.getTaux_interet());
        pstmt.setString(7, credit.getType());
        pstmt.setString(8, credit.getStatus());
        pstmt.setString(9, credit.getDescription());
        pstmt.setString(10, credit.getImage_name());

        pstmt.setDate(11, credit.getSqlDate());
        pstmt.setDate(12, credit.getSqlDatee());
        pstmt.setDate(13, credit.getSqlDateee());

        pstmt.setInt(14, credit.getId());

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Crédit modifié !");
        } else {
            System.out.println("Aucun crédit modifié.");
        }
    }

}
