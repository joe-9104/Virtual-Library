/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author idris
 */
public class Notification {

    private int id = 0;
    private int idUtilisateur;
    private String message;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/biblio";
    private static final String USER = "jdbc";
    private static final String PWD = "1234";

    public Notification(ResultSet res) throws SQLException {
        id = res.getInt(1);
        idUtilisateur = res.getInt(2);
        message = res.getString(3);
    }

    public Notification(int id_user, String message) {
        idUtilisateur = id_user;
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public static Notification[] getNotifList(int user_id) throws SQLException {
        //recuperer les notifications d'un utilisateur a partir de la BD
        Connection cnx = null;
        PreparedStatement pst = null;
        ResultSet resultat = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.GET_NOTIF);
            pst.setInt(1, user_id);
            resultat = pst.executeQuery();
            List<Notification> listeNotif = new ArrayList<>();
            while (resultat.next()) {
                Notification n = new Notification(resultat);
                listeNotif.add(n);
            }
            Notification[] tableauNotification = listeNotif.toArray(new Notification[listeNotif.size()]);
            return tableauNotification;
        } finally {
            if (resultat != null) {
                resultat.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cnx != null) {
                cnx.close();
            }
        }
    }

    public void EnregNotification() throws SQLException {
        //inserer une notification dans la BD
        Connection cnx = null;
        PreparedStatement pst = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.INSERT_NOTIF);
            pst.setInt(1, idUtilisateur);
            pst.setString(2, message);
            pst.execute();
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cnx != null) {
                cnx.close();
            }
        }
    }
}
