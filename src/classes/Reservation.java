package classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reservation {

    private int id;
    private int id_livre;
    private int id_utilisateur;
    private Date dateReservation;
    private String statut;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/biblio";
    private static final String USER = "jdbc";
    private static final String PWD = "1234";

    public Reservation(int id_livre, int id_utilisateur, Date dateReservation, String statut) {
        //constructeur
        //this.id = id;
        this.id_livre = id_livre;
        this.id_utilisateur = id_utilisateur;
        this.dateReservation = dateReservation;
        this.statut = statut;
    }

    // Getters et setters pour les attributs
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdLivre() {
        return id_livre;
    }
    public void setIdLivre(int id_livre) {
        this.id_livre = id_livre;
    }
    public int getIdUtilisateur() {
        return id_utilisateur;
    }
    public void setUtilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }
    public Date getDateReservation() {
        return dateReservation;
    }
    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }
    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Livre : " + id_livre + " date : " + dateReservation + " statut : " + statut;
    }

    public Reservation(ResultSet resultat) throws SQLException {
        id = resultat.getInt(1);
        id_utilisateur = resultat.getInt(2);
        id_livre = resultat.getInt(3);
        dateReservation = resultat.getDate(4);
        statut = resultat.getString(5);
    }

    public static Reservation nouvReservation(Utilisateur user, Livre livre) {
        //nouvelle instance
        if (livre.getDisponibilite() == false) {
            return new Reservation(livre.getId(), user.getId(), new Date(), "en attente");
        } else {
            return null;
        }
    }

    public void enregReservation() throws SQLException {
        //inserer une instance dans la BD
        Connection cnx = null;
        PreparedStatement pst = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.INSERT_RESERVATION);
            pst.setInt(1, id_utilisateur);
            pst.setInt(2, id_livre);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(dateReservation);
            pst.setString(3, date);
            pst.setString(4, statut);
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

    public void ConfirmerReservation() throws SQLException {
        //changer le champs statut en "confirmé"
        Livre l = Livre.rechercheLivre(id_livre);
        if (l.getDisponibilite()) {
            Connection cnx = null;
            PreparedStatement pst = null;
            try {
                cnx = DriverManager.getConnection(URL, USER, PWD);
                pst = cnx.prepareStatement(Queries.UPDATE_RESERVATION);
                pst.setInt(1, id);
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

    public void getIdReservation() throws SQLException {
        Connection cnx = null;
        PreparedStatement pst = null;
        ResultSet resultat = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.GET_ID_RES);
            pst.setInt(1, id_livre);
            pst.setInt(2, id_utilisateur);
            resultat = pst.executeQuery();
            if (resultat.next()) {
                id = resultat.getInt("id_reservation");
            }
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

    public void AnnuleRes() throws SQLException {
        //annuler une reservation
        Connection cnx = null;
        PreparedStatement pst = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.ANNULE_RES);
            pst.setInt(1, id);
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

    public static Reservation[] resEnAttente(int user_id) throws SQLException {
        //recuperer les reservation en attente de confirmation pour un utilisateur donné
        Connection cnx = null;
        PreparedStatement pst = null;
        ResultSet resultat = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.RES_ACTUEL);
            pst.setInt(1, user_id);
            resultat = pst.executeQuery();
            List<Reservation> listeRes = new ArrayList<>();
            while (resultat.next()) {
                Reservation r = new Reservation(resultat);
                listeRes.add(r);
            }
            Reservation[] tableauRes = listeRes.toArray(new Reservation[listeRes.size()]);
            return tableauRes;
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
}
