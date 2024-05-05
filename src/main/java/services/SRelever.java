package services;

import entities.Relever;
import utils.DB;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class SRelever implements IService<Relever> {

    private Connection con = DB.getInstance().getConnection();

    public SRelever() {
    }

    @Override
    public Set<Relever> afficher() throws SQLException {
        Set<Relever> releverList = new HashSet<>();
        String query = "SELECT * FROM `relever`";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            Relever relever = new Relever(
                    rs.getInt("id"),
                    rs.getDouble("debit"),
                    rs.getDouble("credit"),
                    rs.getString("iban"),
                    rs.getString("operation"),
                    rs.getDate("date").toLocalDate()
            );
            releverList.add(relever);
        }
        return releverList;
    }


    @Override
    public void ajouter(Relever relever) {
        try {
            String query = "INSERT INTO relever (debit, credit, iban, operation, date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setDouble(1, relever.getDebit());
            statement.setDouble(2, relever.getCredit());
            statement.setString(3, relever.getIban());
            statement.setString(4, relever.getOperation());
            statement.setDate(5, relever.getSqlDate());
            statement.executeUpdate();
            System.out.println("Relevé ajouté avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM relever WHERE id = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Relever relever) throws SQLException {
        String query = "UPDATE relever SET debit=?, credit=?, iban=?, operation=?, date=? WHERE id=?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setDouble(1, relever.getDebit());
        pstmt.setDouble(2, relever.getCredit());
        pstmt.setString(3, relever.getIban());
        pstmt.setString(4, relever.getOperation());
        pstmt.setDate(5, relever.getSqlDate());
        pstmt.setInt(6, relever.getId());

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Relevé modifié !");
        } else {
            System.out.println("Aucun relevé modifié.");
        }
    }

}
