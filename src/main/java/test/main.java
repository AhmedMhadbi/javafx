package test;

import services.SCarteBancaire;

import java.sql.SQLException;

public class main {

        public main() {
        }

        public static void main(String[] args) throws SQLException {

            SCarteBancaire sc = new SCarteBancaire();


            System.out.println(sc.afficher().toString());



    }

}
