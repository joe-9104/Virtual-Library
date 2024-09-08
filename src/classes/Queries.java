package classes;

public class Queries {

    public static final String SELECT_ALL_BOOKS = "SELECT * FROM livre";
    public static final String INSERT_BOOK = "INSERT INTO livre (titre, auteur,genre,disponibilite) VALUES ( ?, ?, ?,?)";
    public static final String UPDATE_BOOK = "UPDATE livre SET titre = ?, auteur = ? WHERE id = ?";
    public static final String DELETE_BOOK = "DELETE FROM livre WHERE id_livre = ?";
    public static final String SELECT_BOOK = "SELECT * FROM livre WHERE titre = ? ";
    public static final String SELECT_BOOK3 = "SELECT * FROM livre WHERE auteur = ? ";
    public static final String SELECT_BOOK4 = "SELECT * FROM livre WHERE genre = ? ";
    public static final String SELECT_BOOK2 = "SELECT * FROM livre WHERE id_livre = ? ";
    public static final String SELECT_USERS = "SELECT * FROM utilisateur WHERE  login= ? AND pwd = ? ";
    public static final String INDISPONIBLE = "UPDATE livre SET disponibilite=false WHERE id_livre = ?";
    public static final String DISPONIBLE = "UPDATE livre SET disponibilite=true WHERE id_livre = ?";
    public static final String INSERT_EMPRUNT = "INSERT INTO emprunt(id_livre,id_utilisateur,date_emprunt,date_retour,statut) VALUES(?,?,?,?,?)";
    public static final String SELECT_BOOK_EMP = "SELECT * FROM livre WHERE disponibilite = false ";
    public static final String SELECT_BOOK_fREE = "SELECT * FROM livre WHERE disponibilite = true ";
    public static final String USER_HISTORY = "SELECT * FROM emprunt WHERE id_utilisateur = ? ";
    public static final String STATUT_EMPRUNT = "UPDATE emprunt SET statut = ? WHERE id_emprunt = ?";
    public static final String GET_EMPRUNT = "SELECT * FROM emprunt WHERE id_emprunt= ?";
    public static final String ACTUALIER_EMPRUNT = "Update emprunt,livre SET emprunt.statut = \"terminé\" , livre.disponibilite = true WHERE  date_retour < CURDATE()";
    public static final String INSERT_RESERVATION = "INSERT INTO reservation(id_utilisateur,id_livre,date_reservation,statut) VALUES(?,?,?,?)";
    public static final String UPDATE_RESERVATION = "UPDATE reservation SET statut = \"confirmé\"  WHERE id_reservation = ?";
    public static final String GET_ID_RES = "SELECT * FROM reservation WHERE id_livre = ? AND id_utilisateur = ?";
    public static final String ANNULE_RES = "UPDATE reservation SET statut =\"annulé\" WHERE id_reservation = ?";
    public static final String STAT_LIVRE = "SELECT emprunt.id_livre,titre,COUNT(emprunt.id_livre) nb_emprunt FROM emprunt,livre WHERE livre.id_livre = emprunt.id_livre GROUP BY livre.id_livre ORDER BY nb_emprunt DESC";
    public static final String STAT_USER = "SELECT emprunt.id_utilisateur,nom,prenom,COUNT(emprunt.id_utilisateur) nb_emprunt FROM emprunt,utilisateur WHERE utilisateur.id_utilisateur = emprunt.id_utilisateur AND emprunt.statut=\"terminé\" GROUP BY utilisateur.id_utilisateur ORDER BY nb_emprunt DESC";
    public static final String EMP_ACTUEL = "SELECT * FROM emprunt WHERE statut=\"en cours\" AND id_utilisateur = ?";
    public static final String RES_ACTUEL = "SELECT * FROM reservation WHERE statut=\"en attente\" AND id_utilisateur = ?";
    public static final String GET_NOTIF = "SELECT * FROM notification WHERE id_utilisateur = ?";
    public static final String INSERT_NOTIF = "INSERT INTO notification(id_utilisateur,message)VALUES(?,?)";
    public static final String GET_ALL_USERS = "SELECT * FROM utilisateur";
    public static final String INSERT_USER = "INSERT INTO utilisateur(nom,prenom,login,pwd,role) VALUES(?,?,?,?,?)";
}
