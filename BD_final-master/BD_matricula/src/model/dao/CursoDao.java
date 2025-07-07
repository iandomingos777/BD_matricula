package model.dao;

import java.sql.SQLException;
import java.util.List;
import model.entities.Curso;

public interface CursoDao {
    void insert(Curso obj) throws Exception;
    void massInsert(List<Curso> cursos) throws SQLException;
    void update(Curso obj) throws Exception;
    void deleteById(int id) throws Exception;
    Curso findById(int id) throws Exception;
    List<Curso> findAll() throws Exception;
    List<Curso> findBySubstring(String sub) throws SQLException;
}
