package classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Livre {

    private int id;
    private String titre;
    private String auteur;
    private String genre;
    private boolean disp = true;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/biblio";
    private static final String USER = "jdbc";
    private static final String PWD = "1234";

    public Livre(int id, String titre, String auteur, String genre, boolean disp) {
        //constructeur complet de la classe livre
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        this.disp = disp;
    }

    public Livre(String titre, String auteur, String genre, boolean disp) {
        //constructeur reduit
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        this.disp = disp;
    }

    public Livre(ResultSet rs) throws SQLException {
        // constructeur a partir de la classe livre
        id = rs.getInt("id_livre");
        titre = rs.getString("titre");
        auteur = rs.getString("auteur");
        genre = rs.getString("genre");
        disp = rs.getBoolean("disponibilite");
    }

    // les getters et les setters
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getGenre() {
        return genre;
    }

    public boolean getDisponibilite() {
        return disp;
    }

    @Override
    public String toString() {
        return id + " - " + titre + ", par :" + auteur;
    }

    public void afficherDetail() {
        //Methode d'affichage detaillé
        System.out.println("Livre [ id =" + id + ", titre=" + titre + ", auteur=" + auteur + ", genre=" + genre + ", disponibilité=" + disp + "]");
    }

    public void ajoutLivre() throws SQLException {
        //ajouter un livre
        Connection cnx = null;
        PreparedStatement pst = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.INSERT_BOOK);
            pst.setString(1, titre);
            pst.setString(2, auteur);
            pst.setString(3, genre);
            pst.setBoolean(4, disp);
            pst.execute();
            id = Livre.recupererDernierIdLivre();
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cnx != null) {
                cnx.close();
            }
        }
    }

    public static int recupererDernierIdLivre() {
        //recuperer l'id du dernier livre 
        int dernierIdLivre = 0;
        Connection cnx = null;
        Statement st = null;
        ResultSet resultSet = null;
        String query = "SELECT MAX(id_livre) AS dernierIdLivre FROM livre";

        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            st = cnx.createStatement();
            resultSet = st.executeQuery(query);
            if (resultSet.next()) {
                dernierIdLivre = resultSet.getInt("dernierIdLivre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dernierIdLivre;
    }

    public static Livre[] rechercheLivre(String titre) throws SQLException {
        //rechercher un livre a partir de son titre
        Connection cnx = null;
        PreparedStatement pst = null;
        ResultSet resultat = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.SELECT_BOOK);
            pst.setString(1, titre);
            resultat = pst.executeQuery();
            List<Livre> listeLivres = new ArrayList<>();
            while (resultat.next()) {
                Livre livre = new Livre(resultat);
                listeLivres.add(livre);
            }
            Livre[] tableauLivres = listeLivres.toArray(new Livre[listeLivres.size()]);

            return tableauLivres;
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

    public static Livre[] rechercheLivre2(String auteur) throws SQLException {
        //rechercher un livre a partir de son auteur
        Connection cnx = null;
        PreparedStatement pst = null;
        ResultSet resultat = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.SELECT_BOOK3);
            pst.setString(1, auteur);
            resultat = pst.executeQuery();
            List<Livre> listeLivres = new ArrayList<>();
            while (resultat.next()) {
                Livre livre = new Livre(resultat);
                listeLivres.add(livre);
            }
            Livre[] tableauLivres = listeLivres.toArray(new Livre[listeLivres.size()]);
            return tableauLivres;
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

    public static Livre[] rechercheLivre3(String genre) throws SQLException {
        //rechercher un livre a partir de son genre
        Connection cnx = null;
        PreparedStatement pst = null;
        ResultSet resultat = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.SELECT_BOOK4);
            pst.setString(1, genre);
            resultat = pst.executeQuery();
            List<Livre> listeLivres = new ArrayList<>();
            while (resultat.next()) {
                Livre livre = new Livre(resultat);
                listeLivres.add(livre);
            }
            Livre[] tableauLivres = listeLivres.toArray(new Livre[listeLivres.size()]);
            return tableauLivres;
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

    public static Livre rechercheLivre(int id) throws SQLException {
        //rechercher un livre a partir de son id
        Connection cnx = null;
        PreparedStatement pst = null;
        ResultSet resultat = null;
        Livre livre = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.SELECT_BOOK2);
            pst.setInt(1, id);
            resultat = pst.executeQuery();
            if (resultat.next()) {
                livre = new Livre(resultat);
            }
            return livre;
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

    public static void supprimerLivre(int id) throws SQLException {
        //supprimer un livre
        Connection cnx = null;
        PreparedStatement pst = null;
        try {

            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.DELETE_BOOK);
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

    public static Livre[] getListeLivres() throws SQLException {
        //recuperer un tableau contenant tous les livres
        Connection cnx = null;
        Statement st = null;
        ResultSet resultat = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            st = cnx.createStatement();
            resultat = st.executeQuery(Queries.SELECT_ALL_BOOKS);
            List<Livre> listeLivres = new ArrayList<>();
            while (resultat.next()) {
                Livre livre = new Livre(resultat);
                listeLivres.add(livre);
            }
            Livre[] tableauLivres = listeLivres.toArray(new Livre[listeLivres.size()]);

            return tableauLivres;
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

    public static void livreEmprunte(int id) throws SQLException {
        //changer la disponibilite en false 
        Connection cnx = null;
        PreparedStatement pst = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.INDISPONIBLE);
            pst.setInt(1, id);
            pst.execute();
        } finally {
            if (cnx != null) {
                cnx.close();
            }
            if (pst != null) {
                pst.close();
            }
        }
    }

    public static void livreLibre(int id) throws SQLException {
        //changer la disponibilite en true 
        Connection cnx = null;
        PreparedStatement pst = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.DISPONIBLE);
            pst.setInt(1, id);
            pst.execute();
        } finally {
            if (cnx != null) {
                cnx.close();
            }
            if (pst != null) {
                pst.close();
            }
        }
    }

    public static Livre[] getLivreEmprunte() throws SQLException {
        //récuperer la liste des livres Empruntés
        Connection cnx = null;
        Statement st = null;
        ResultSet resultat = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            st = cnx.createStatement();
            resultat = st.executeQuery(Queries.SELECT_BOOK_EMP);
            List<Livre> listeLivres = new ArrayList<>();
            while (resultat.next()) {
                Livre livre = new Livre(resultat);
                listeLivres.add(livre);
            }
            Livre[] tableauLivres = listeLivres.toArray(new Livre[listeLivres.size()]);

            return tableauLivres;
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

    public static Livre[] getLivreLibre() throws SQLException {
        //recuperer la liste des livres libres non empruntés 
        Connection cnx = null;
        Statement st = null;
        ResultSet resultat = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            st = cnx.createStatement();
            resultat = st.executeQuery(Queries.SELECT_BOOK_fREE);
            List<Livre> listeLivres = new ArrayList<>();
            while (resultat.next()) {
                Livre livre = new Livre(resultat);
                listeLivres.add(livre);
            }
            Livre[] tableauLivres = listeLivres.toArray(new Livre[listeLivres.size()]);

            return tableauLivres;
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
