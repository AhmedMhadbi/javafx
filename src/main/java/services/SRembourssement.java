package services;

import entities.Rembourssement;
import utils.DB;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class SRembourssement implements IService<Rembourssement> {

    private Connection con = DB.getInstance().getConnection();

    public SRembourssement() {
    }

    @Override
    public Set<Rembourssement> afficher() throws SQLException {
        Set<Rembourssement> remboursements = new HashSet<>();
        String query = "SELECT * FROM `remboursement`";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            Rembourssement remboursement = new Rembourssement();
            remboursement.setId(rs.getInt("id"));
            remboursement.setCredit_id(rs.getInt("credit_id"));
            remboursement.setMontant(rs.getDouble("montant"));
            remboursement.setDate_remboursement(rs.getDate("date_remboursement").toLocalDate());
            remboursements.add(remboursement);
        }
        return remboursements;
    }

    @Override
    public void ajouter(Rembourssement rembourssement) {
        try {
            String query = "INSERT INTO remboursement (credit_id, montant, date_remboursement) VALUES (?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, rembourssement.getCredit_id());
            statement.setDouble(2, rembourssement.getMontant());
            statement.setDate(3, Date.valueOf(rembourssement.getDate_remboursement()));

            statement.executeUpdate();
            System.out.println("Remboursement ajouté avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM remboursement WHERE id = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Rembourssement rembourssement) throws SQLException {
        String query = "UPDATE remboursement SET credit_id=?, montant=?, date_remboursement=? WHERE id=?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, rembourssement.getCredit_id());
        pstmt.setDouble(2, rembourssement.getMontant());
        pstmt.setDate(3, Date.valueOf(rembourssement.getDate_remboursement()));
        pstmt.setInt(4, rembourssement.getId());
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Le remboursement est modifié !");
        } else {
            System.out.println("Aucun remboursement n'a été modifié.");
        }
    }
}
