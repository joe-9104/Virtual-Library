package classes;

import java.sql.*;
import exceptions.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Emprunt {

    private int id = 0;
    private int id_livre;
    private int id_utilisateur;
    private Date dateEmprunt;
    private Date dateRetour;
    private String statut;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/biblio";
    private static final String USER = "jdbc";
    private static final String PWD = "1234";

    public Emprunt(int id_livre, int id_utilisateur, Date dateEmprunt, Date dateRetour, String statut) {
        this.id_livre = id_livre;
        this.id_utilisateur = id_utilisateur;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.statut = statut;
    }

    // Getters et setters pour les attributs
    public int getId() {
        return id;
    }
    public int getIdUser() {
        return id_utilisateur;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDateEmprunt() {
        return dateEmprunt;
    }
    public Date getDateRetour() {
        return dateRetour;
    }
    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
    public int getIdLivre() {
        return id_livre;
    }

    @Override
    public String toString() {
        return "Id _Livre: " + id_livre + " Id_User:" + id_utilisateur + " Date Emprunt: " + dateEmprunt + " Date Retour : " + dateRetour;
    }

    public Emprunt(ResultSet resultat) throws SQLException {
        //constructeur a partir d' une requete 
        id = resultat.getInt(1);
        id_utilisateur = resultat.getInt(2);
        id_livre = resultat.getInt(3);
        dateEmprunt = resultat.getDate(4);
        dateRetour = resultat.getDate(5);
        statut = resultat.getString(6);

    }

    public static Emprunt nouvEmrunt(Livre livre, Utilisateur user) throws ExceptionLivre {
        //la creation d'un nouvel emprunt
        if (livre.getDisponibilite() == true) {
            Date now = new Date();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 15);
            return new Emprunt(livre.getId(), user.getId(), now, cal.getTime(), "en cours");
        } else {
            throw new ExceptionLivre();
        }

    }

    public void EnregEmprunt() throws SQLException {
        //enregistrer l'emprunt dans la base de données
        Connection cnx = null;
        PreparedStatement pst = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.INSERT_EMPRUNT);
            pst.setInt(1, id_livre);
            pst.setInt(2, id_utilisateur);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String emprunt = sdf.format(dateEmprunt);
            pst.setString(3, emprunt);
            String retour = sdf.format(dateRetour);
            pst.setString(4, retour);
            pst.setString(5, statut);
            pst.execute();
            Livre.livreEmprunte(id_livre);
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cnx != null) {
                cnx.close();
            }
        }
    }

    public static Emprunt[] historiqueEmprunt(int user_id) throws SQLException {
        //consulter l'historique des emprunts d'un utilisateur
        Connection cnx = null;
        PreparedStatement pst = null;
        ResultSet resultat = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.USER_HISTORY);
            pst.setInt(1, user_id);
            resultat = pst.executeQuery();
            List<Emprunt> listeEmprunt = new ArrayList<>();
            while (resultat.next()) {
                Emprunt e = new Emprunt(resultat);
                listeEmprunt.add(e);
            }
            Emprunt[] tableauEmprunts = listeEmprunt.toArray(new Emprunt[listeEmprunt.size()]);
            return tableauEmprunts;
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

    public void EmpruntTermine() throws SQLException {
        //terminer un emprunt 
        Livre.livreLibre(id_livre);
        Connection cnx = null;
        PreparedStatement pst = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.STATUT_EMPRUNT);
            pst.setString(1, "terminé");
            pst.setInt(2, id);
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

    public static void EmpruntTermineAuto() throws SQLException {
        //mettre a jour automatiquement la table emprunt : pour les emprunts dont la date de retour depasse le le jour actuel 
        Connection cnx = null;
        Statement st = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            st = cnx.createStatement();
            st.execute(Queries.ACTUALIER_EMPRUNT);
        } finally {
            if (st != null) {
                st.close();
            }
            if (cnx != null) {
                cnx.close();
            }
        }
    }

    public static Emprunt[] EmpruntenCours(Utilisateur user) throws SQLException {
        //recuperer les emprunts courants pour un utilisateur donné
        Connection cnx = null;
        PreparedStatement pst = null;
        ResultSet resultat = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.EMP_ACTUEL);
            pst.setInt(1, user.getId());
            resultat = pst.executeQuery();
            List<Emprunt> listeEmprunt = new ArrayList<>();
            while (resultat.next()) {
                Emprunt e = new Emprunt(resultat);
                listeEmprunt.add(e);
            }
            Emprunt[] tableauEmprunts = listeEmprunt.toArray(new Emprunt[listeEmprunt.size()]);
            return tableauEmprunts;
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

    public static Emprunt[] EmpruntenCoursTout() throws SQLException {
        ////recuperer les emprunts courants pour tout les utilisateurs
        Connection cnx = null;
        Statement st = null;
        ResultSet resultat = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            st = cnx.createStatement();
            resultat = st.executeQuery("SELECT * FROM emprunt WHERE statut = \"en cours\"");
            List<Emprunt> listeEmprunt = new ArrayList<>();
            while (resultat.next()) {
                Emprunt e = new Emprunt(resultat);
                listeEmprunt.add(e);
            }
            Emprunt[] tableauEmprunts = listeEmprunt.toArray(new Emprunt[listeEmprunt.size()]);
            return tableauEmprunts;
        } finally {
            if (resultat != null) {
                resultat.close();
            }
            if (st != null) {
                st.close();
            }
            if (cnx != null) {
                cnx.close();
            }
        }
    }
}
