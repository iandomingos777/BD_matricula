package model.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.dao.CursoDao;
import model.entities.Curso;

public class CursoDaoJDBC implements CursoDao {

    private Connection conn;

    public CursoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Curso obj) throws SQLException {
        String sql = "INSERT INTO curso (nome, centro, n_disciplinas, n_alunos, ead, id_campus, id_coordenador) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getNome());
            st.setString(2, obj.getCentro());
            st.setInt(3, 0);
            st.setInt(4, 0);
            st.setBoolean(5, obj.isEad());
            st.setInt(6, obj.getId_campus());
            st.setInt(7, obj.getId_coordenador());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        obj.setId(rs.getInt(1));
                    }
                }
            } else {
                throw new SQLException("Erro inesperado: nenhuma linha inserida!");
            }
        }
    }

    @Override
    public void massInsert(List<Curso> cursos) throws SQLException{
        for (Curso curso : cursos){
            insert(curso);
        }
    }

    @Override
    public void update(Curso obj) throws SQLException {
        String sql = "UPDATE curso SET nome = ?, centro = ?, n_disciplinas = ?, n_alunos = ?, ead = ?, id_campus = ?, id_coordenador = ? " +
                     "WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getNome());
            st.setString(2, obj.getCentro());
            st.setInt(3, obj.getN_disciplinas());
            st.setInt(4, obj.getN_alunos());
            st.setInt(3, obj.getN_disciplinas());
            st.setInt(4, obj.getN_alunos());
            st.setBoolean(5, obj.isEad());
            st.setInt(6, obj.getId_campus());
            st.setInt(7, obj.getId_coordenador());
            st.setInt(8, obj.getId());

            st.executeUpdate();
        }
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM curso WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        }
    }

    @Override
    public Curso findById(int id) throws SQLException {
        String sql = "SELECT * FROM curso WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateCurso(rs);
                }
                return null;
            }
        }
    }

    @Override
    public List<Curso> findAll() throws SQLException {
        String sql = "SELECT * FROM curso ORDER BY nome";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Curso> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateCurso(rs));
            }
            return list;
        }
    }
  
    @Override
    public List<Curso> findBySubstring(String sub) throws SQLException {
        String sql = "SELECT * FROM curso WHERE nome ILIKE '%"+ sub +"%' ORDER BY nome";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Curso> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateCurso(rs));
            }
            return list;
        }
    }

    private Curso instantiateCurso(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String centro = rs.getString("centro");
        int n_disciplinas = rs.getInt("n_disciplinas");
        int n_alunos = rs.getInt("n_alunos");
        boolean ead = rs.getBoolean("ead");
        int id_campus = rs.getInt("id_campus");
        int id_coordenador = rs.getInt("id_coordenador");

        return new Curso(id, nome, centro, n_disciplinas, n_alunos, ead, id_campus, id_coordenador);
    }
}
