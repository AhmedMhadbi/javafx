package test;

import entitie.User;
import services.ServiceUser;

import java.sql.SQLException;

public class main {

    public main() {
    }
    public static void main(String[] args) throws SQLException {

        ServiceUser su = new ServiceUser();


      //  User c = new User( 3,"azerty12","AMIR","amirj5353@gmail.com", "23142134","tunis","image.png",10.10);
        //su.ajouter(c);


        su.supprimer(2);


       // System.out.println(su.afficher().toString());




    }
}
