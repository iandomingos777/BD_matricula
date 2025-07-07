package model.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.dao.DisciplinaDao;
import model.entities.Disciplina;

public class DisciplinaDaoJDBC implements DisciplinaDao {

    private Connection conn;

    public DisciplinaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Disciplina obj) throws SQLException {
        String sql = "INSERT INTO disciplina (nome, horas, requisito, eletiva, id_curso) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getNome());
            st.setInt(2, obj.getHoras());
            st.setInt(3, obj.getRequisito());
            st.setBoolean(4, obj.isEletiva());
            st.setInt(5, obj.getId_curso());

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
    public void update(Disciplina obj) throws SQLException {
        String sql = "UPDATE disciplina SET nome = ?, horas = ?, requisito = ?, eletiva = ?, id_curso = ? WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getNome());
            st.setInt(2, obj.getHoras());
            st.setInt(3, obj.getRequisito());
            st.setBoolean(4, obj.isEletiva());
            st.setInt(5, obj.getId_curso());
            st.setInt(6, obj.getId());

            st.executeUpdate();
        }
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM disciplina WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        }
    }

    @Override
    public Disciplina findById(int id) throws SQLException {
        String sql = "SELECT * FROM disciplina WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateDisciplina(rs);
                }
                return null;
            }
        }
    }

    @Override
    public List<Disciplina> findAll() throws SQLException {
        String sql = "SELECT * FROM disciplina ORDER BY nome";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Disciplina> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateDisciplina(rs));
            }
            return list;
        }
    }

    @Override
    public List<Disciplina> findBySubstring(String sub) throws SQLException {
        String sql = "SELECT * FROM disciplina WHERE nome ILIKE '%"+ sub +"%' ORDER BY nome";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Disciplina> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateDisciplina(rs));
            }
            return list;
        }
    }

    private Disciplina instantiateDisciplina(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        int horas = rs.getInt("horas");
        int requisito = rs.getInt("requisito");
        boolean eletiva = rs.getBoolean("eletiva");
        int id_curso = rs.getInt("id_curso");

        return new Disciplina(id, nome, horas, requisito, eletiva, id_curso);
    }
}
