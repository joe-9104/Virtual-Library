/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.sql.*;

/**
 *
 * @author idris
 */
public class StatUser {

    private int id_user;
    private String nom;
    private String prenom;
    private int nb_emprunt;

    public StatUser(ResultSet res) throws SQLException {
        id_user = res.getInt(1);
        nom = res.getString(2);
        prenom = res.getString(3);
        nb_emprunt = res.getInt(4);
    }
    public int getId() {
        return id_user;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public int getNb() {
        return nb_emprunt;
    }
    @Override
    public String toString() {
        return "ID : " + id_user + " " + nom + " " + prenom + " nombre d'emprunts :" + nb_emprunt;
    }

}
