package classes;

import java.sql.*;
import exceptions.ExceptionUtilisateur;
import java.util.ArrayList;
import java.util.List;

public class Utilisateur {

    private int id;
    private String nom;
    private String prenom;
    private String login;
    private String pwd;
    private String role;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/biblio";
    private static final String USER = "jdbc";
    private static final String PWD = "1234";

    public Utilisateur(String n, String p, String log, String password, String r) {
        //id = x;
        nom = n;
        prenom = p;
        login = log;
        pwd = password;
        role = r;
    }

    public int getId() {
        return id;
    }
    public String getRole() {
        return role;
    }
    public String getLogin(){
        return login;
    }

    public Utilisateur(ResultSet res) throws SQLException {
        id = res.getInt(1);
        nom = res.getString(2);
        prenom = res.getString(3);
        login = res.getString(4);
        pwd = res.getString(5);
        role = res.getString(6);
    }

    public String toString() {
        return nom + " " + prenom;
    }

    public static Utilisateur authentification(String log, String password) throws SQLException, ExceptionUtilisateur {
        //executer la requete d'authentification
        Connection cnx = null;
        PreparedStatement pst = null;
        ResultSet resultat = null;
        Utilisateur user = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.SELECT_USERS);
            pst.setString(1, log);
            pst.setString(2, password);
            resultat = pst.executeQuery();
            if (resultat.next()) {
                user = new Utilisateur(resultat);
            } else {
                throw new ExceptionUtilisateur();
            }
            return user;
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

    public void afficherHistorique() {
        //affichere l'historique des emprunts de l'utilisateur 
        try {
            Emprunt[] tab = Emprunt.historiqueEmprunt(this.id);
            for (int i = 0; i < tab.length; i++) {
                System.out.println(tab[i]);
            }
        } catch (SQLException e) {

        }
    }

    public void EmprunterLivre(Livre livre) throws SQLException {
        //retourner un livre manuellement 
        try {
            Emprunt emp = Emprunt.nouvEmrunt(livre, this);
            if (emp != null) {
                emp.EnregEmprunt();
            } else {
                System.out.println("emprunt non effectuer ");
            }
        } catch (Exception e) {
            System.out.println("emprunt non effectuer ");
        }

    }

    public void RetounerLivre(Emprunt emp) {
        //effectuer le retour d' un livre
        try {
            emp.EmpruntTermine();
        } catch (Exception e) {
            System.out.println("erreur : livre non retourné");
        }
    }

    public StatLivre[] statLivreEmprunte() throws SQLException {
        //les statistique des livres les plus empruntes pour tout les utilisateurs ,cette methode peut seulement etre execute par un bibliothecaire
        if (role.equals("bibliothecaire")) {
            Connection cnx = null;
            Statement st = null;
            ResultSet resultat = null;
            try {
                cnx = DriverManager.getConnection(URL, USER, PWD);
                st = cnx.createStatement();
                resultat = st.executeQuery(Queries.STAT_LIVRE);
                List<StatLivre> listeLivres = new ArrayList<>();
                while (resultat.next()) {
                    StatLivre s = new StatLivre(resultat);
                    listeLivres.add(s);
                }
                StatLivre[] tableauLivres = listeLivres.toArray(new StatLivre[listeLivres.size()]);
                return tableauLivres;
            } finally {

            }
        } else {
            System.out.println("le role de l'tilisateur doit etre bibliothecaire");
        }
        return null;
    }

    public StatUser[] statUserEmprunte() throws SQLException {
        //statistique de tout les utilisateurs selon le nombre d'emprunts
        if (role.equals("bibliothecaire")) {
            Connection cnx = null;
            Statement st = null;
            ResultSet resultat = null;
            try {
                cnx = DriverManager.getConnection(URL, USER, PWD);
                st = cnx.createStatement();
                resultat = st.executeQuery(Queries.STAT_USER);
                List<StatUser> listeUser = new ArrayList<>();
                while (resultat.next()) {
                    StatUser s = new StatUser(resultat);
                    listeUser.add(s);
                }
                StatUser[] tableauUser = listeUser.toArray(new StatUser[listeUser.size()]);
                return tableauUser;
            } finally {

            }
        } else {
            System.out.println("le role de l'tilisateur doit etre bibliothecaire");
        }
        return null;
    }
    public static Utilisateur [] getListeUser() throws SQLException{
         //recuperer un tableau contenant tous les utilisateurs
        Connection cnx = null;
        Statement st = null;
        ResultSet resultat = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            st = cnx.createStatement();
            resultat = st.executeQuery(Queries.GET_ALL_USERS);
            List<Utilisateur> listeUser = new ArrayList<>();
            while (resultat.next()) {
                Utilisateur user = new Utilisateur(resultat);
                listeUser.add(user);
            }
            Utilisateur[] tableauUser= listeUser.toArray(new Utilisateur[listeUser.size()]);

            return tableauUser;
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
    public static boolean testLogin(String login)throws SQLException{
        //tester la validité du login (un login doit etre unique)
        Utilisateur [] tab = getListeUser();
        for(int i=0;i<tab.length;i++){
            if(login.equals(tab[i].getLogin()))
                return false ;
        }
        return true;
    }
    public void ajouterUser()throws SQLException{
        //ajouter un utilisateur
        Connection cnx = null;
        PreparedStatement pst = null;
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            pst = cnx.prepareStatement(Queries.INSERT_USER);
            pst.setString(1, nom);
            pst.setString(2, prenom);
            pst.setString(3, login);
            pst.setString(4, pwd);
            pst.setString(5, role);
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
