package model.dao;

import java.sql.SQLException;
import java.util.List;
import model.entities.Aluno;

public interface AlunoDao {
    void insert(Aluno aluno) throws SQLException;
    void massInsert(List<Aluno> alunos) throws SQLException;
    void update(Aluno aluno) throws SQLException;
    void deleteById(int matricula) throws SQLException;
    Aluno findById(int matricula) throws SQLException;
    List<Aluno> findAll() throws SQLException;
    List<Aluno> findBySubstring(String sub) throws SQLException;

    int somarHorasTotais(int mat) throws SQLException;
}
