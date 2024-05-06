package services;

import entitie.User;
import utils.DB;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceUser implements IService<User>{

     Connection con = DB.getInstance().getConnection();
    public ServiceUser() {
    }
    @Override
    public Set<User> afficher() throws SQLException {
        Set<User> creditList = new HashSet<>();
        String query = "SELECT * FROM `user`";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            User user = new User(
                    rs.getInt("id"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),

                    rs.getString("image"),

                    rs.getDouble("balance")


                    );
            creditList.add(user);
        }
        return creditList;
    }

    @Override
    public void ajouter(User user) {
        try {
            String query = "INSERT INTO `user` (id, password, name, email, phone, address, image, balance) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, user.getId());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getAddress());
            statement.setString(7, user.getImage());
            statement.setDouble(8, user.getBalance());
            statement.executeUpdate();
            System.out.println("Customer ajouté avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }




    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }



    @Override
    public void modifier(User user) throws SQLException {
        String query = "UPDATE user SET password=?, name=?, email=?, phone=?, address=?, image=?, balance=?WHERE id=?";
        PreparedStatement statement = con.prepareStatement(query);

        statement.setString(1, user.getPassword());
        statement.setString(2, user.getName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPhone());
        statement.setString(5, user.getAddress());
        statement.setString(6, user.getImage());
        statement.setDouble(7, user.getBalance());
        statement.setInt(8, user.getId());


        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Customer modifié !");
        } else {
            System.out.println("Aucun Customer modifié.");
        }
    }



}
