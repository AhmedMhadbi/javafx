package services;

import entities.Transaction;
import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class STransaction implements IService<Transaction> {

    private Connection con = DB.getInstance().getConnection();

    public STransaction() {
    }

    @Override
    public Set<Transaction> afficher() throws SQLException {
        Set<Transaction> transactionList = new HashSet<>();
        String query = "SELECT * FROM transaction";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            Transaction transaction = new Transaction(
                    rs.getInt("id"),
                    rs.getInt("client_id"),
                    rs.getInt("client2_id"),
                    rs.getString("nom_destinataire"),
                    rs.getString("prenom_destinataire"),
                    rs.getString("iban_destinataire"),
                    rs.getString("iban"),
                    rs.getDouble("montant"),
                    rs.getDate("date_transaction").toLocalDate()
            );
            transactionList.add(transaction);
        }
        return transactionList;
    }

    @Override
    public void ajouter(Transaction transaction) {
        try {
            String query = "INSERT INTO transaction (client_id, client2_id, nom_destinataire, prenom_destinataire, iban_destinataire, iban, montant, date_transaction) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, transaction.getClient_id());
            statement.setInt(2, transaction.getClient2_id());
            statement.setString(3, transaction.getNom_destinataire());
            statement.setString(4, transaction.getPrenom_destinataire());
            statement.setString(5, transaction.getIban_destinataire());
            statement.setString(6, transaction.getIban());
            statement.setDouble(7, transaction.getMontant());
            statement.setDate(8, java.sql.Date.valueOf(transaction.getDate_transaction()));
            statement.executeUpdate();
            System.out.println("Transaction ajoutée avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM transaction WHERE id = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Transaction transaction) throws SQLException {
        String query = "UPDATE transaction SET client_id=?, client2_id=?, nom_destinataire=?, prenom_destinataire=?, iban_destinataire=?, iban=?, montant=?, date_transaction=? WHERE id=?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, transaction.getClient_id());
        pstmt.setInt(2, transaction.getClient2_id());
        pstmt.setString(3, transaction.getNom_destinataire());
        pstmt.setString(4, transaction.getPrenom_destinataire());
        pstmt.setString(5, transaction.getIban_destinataire());
        pstmt.setString(6, transaction.getIban());
        pstmt.setDouble(7, transaction.getMontant());
        pstmt.setDate(8, java.sql.Date.valueOf(transaction.getDate_transaction()));
        pstmt.setInt(9, transaction.getId());

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Transaction modifiée !");
        } else {
            System.out.println("Aucune transaction modifiée.");
        }
    }
}
