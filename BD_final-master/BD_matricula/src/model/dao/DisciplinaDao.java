package model.dao;

import java.sql.SQLException;
import java.util.List;
import model.entities.Disciplina;

public interface DisciplinaDao {
    void insert(Disciplina obj) throws Exception;
    void update(Disciplina obj) throws Exception;
    void deleteById(int id) throws Exception;
    Disciplina findById(int id) throws Exception;
    List<Disciplina> findAll() throws Exception;
    List<Disciplina> findBySubstring(String sub) throws SQLException;
}
