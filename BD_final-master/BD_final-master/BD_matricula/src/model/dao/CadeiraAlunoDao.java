package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.entities.CadeiraAluno;

public interface CadeiraAlunoDao {
    void insert(CadeiraAluno obj) throws SQLException;
    void update(CadeiraAluno obj) throws SQLException;
    void deleteById(int id) throws SQLException;
    CadeiraAluno findById(int id) throws SQLException;
    List<CadeiraAluno> findAll() throws SQLException;
}