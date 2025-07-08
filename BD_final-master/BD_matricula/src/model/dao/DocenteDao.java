package model.dao;

import java.sql.SQLException;
import java.util.List;
import model.entities.Docente;

public interface DocenteDao {
    void insert(Docente obj) throws SQLException;
    void massInsert(List<Docente> docentes) throws SQLException;
    void update(Docente obj) throws SQLException;
    void deleteById(int id) throws SQLException;
    Docente findById(int id) throws SQLException;
    List<Docente> findAll() throws SQLException;
    List<Docente> findBySubstring(String sub) throws SQLException;
    List<Docente> listarANYMenorCarga() throws SQLException;
}
