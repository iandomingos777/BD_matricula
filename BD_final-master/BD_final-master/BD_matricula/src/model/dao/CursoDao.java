package model.dao;

import java.util.List;
import model.entities.Curso;

public interface CursoDao {
    void insert(Curso obj) throws Exception;
    void update(Curso obj) throws Exception;
    void deleteById(int id) throws Exception;
    Curso findById(int id) throws Exception;
    List<Curso> findAll() throws Exception;
}
