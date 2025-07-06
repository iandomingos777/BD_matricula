package model.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.dao.DocenteDao;
import model.entities.Docente;

public class DocenteDaoJDBC implements DocenteDao {

    private Connection conn;

    public DocenteDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Docente obj) throws SQLException {
        String sql = "INSERT INTO docente (nome, formacao, telefone, area, lattes) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getNome());
            st.setString(2, obj.getFormacao());
            st.setInt(3, obj.getTelefone());
            st.setString(4, obj.getArea());
            st.setString(5, obj.getLattes());

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
    public void update(Docente obj) throws SQLException {
        String sql = "UPDATE docente SET nome = ?, formacao = ?, telefone = ?, area = ?, lattes = ? WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getNome());
            st.setString(2, obj.getFormacao());
            st.setInt(3, obj.getTelefone());
            st.setString(4, obj.getArea());
            st.setString(5, obj.getLattes());
            st.setInt(6, obj.getId());

            st.executeUpdate();
        }
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM docente WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        }
    }

    @Override
    public Docente findById(int id) throws SQLException {
        String sql = "SELECT * FROM docente WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateDocente(rs);
                }
                return null;
            }
        }
    }

    @Override
    public List<Docente> findAll() throws SQLException {
        String sql = "SELECT * FROM docente ORDER BY nome";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Docente> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateDocente(rs));
            }
            return list;
        }
    }

    private Docente instantiateDocente(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String formacao = rs.getString("formacao");
        int telefone = rs.getInt("telefone");
        String area = rs.getString("area");
        String lattes = rs.getString("lattes");

        return new Docente(id, nome, formacao, telefone, area, lattes);
    }
}
