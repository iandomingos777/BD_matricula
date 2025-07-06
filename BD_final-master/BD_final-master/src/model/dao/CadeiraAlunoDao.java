package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.entities.Cadeira_aluno;

public interface CadeiraAlunoDao {
    void insert(Cadeira_aluno obj) throws SQLException;
    void update(Cadeira_aluno obj) throws SQLException;
    void deleteById(int id) throws SQLException;
    Cadeira_aluno findById(int id) throws SQLException;
    List<Cadeira_aluno> findAll() throws SQLException;
}