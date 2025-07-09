package model.dao;

import java.sql.SQLException;
import java.util.List;
import model.entities.Aluno;

public interface AlunoDao {
    void insert(Aluno aluno) throws SQLException;
    void update(Aluno aluno) throws SQLException;
    void deleteById(int matricula) throws SQLException;
    Aluno findById(int matricula) throws SQLException;
    List<Aluno> findAll() throws SQLException;
}
