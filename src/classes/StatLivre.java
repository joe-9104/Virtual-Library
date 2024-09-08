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
public class StatLivre {

    private int id_livre;
    private String titre;
    private int nb_emprunt;

    public StatLivre(ResultSet res) throws SQLException {
        id_livre = res.getInt(1);
        titre = res.getString(2);
        nb_emprunt = res.getInt(3);
    }

    public int getId() {
        return id_livre;
    }
    public String getTitre() {
        return titre;
    }
    public int getNb() {
        return nb_emprunt;
    }

    @Override
    public String toString() {
        return "ID : " + id_livre + " " + titre + " nombre d'emprunts :" + nb_emprunt;
    }

}
