package model.dao;

import java.sql.SQLException;
import java.util.List;
import model.entities.Campus;

public interface CampusDao {
    void insert(Campus obj) throws SQLException;
    void update(Campus obj) throws SQLException;
    void deleteById(Integer id) throws SQLException;
    Campus findById(Integer id) throws SQLException;
    List<Campus> findAll() throws SQLException;
}