package main;

import java.sql.Connection;

import db.Conexao;
import model.dao.*;
import model.dao.impl.*;
import model.entities.*;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = Conexao.conectar()) {
        	CampusDao CampusDao = new CampusDaoJDBC(conn);
        	CampusDao.deleteById(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("OK");
    }
}
