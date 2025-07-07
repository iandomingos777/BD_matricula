package model.dao.impl;

import model.dao.CampusDao;
import model.entities.Campus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CampusDaoJDBC implements CampusDao {

    private Connection conn;

    public CampusDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Campus obj) throws SQLException {
        String sql = "INSERT INTO campus (nome, endereco, ano_criacao, site) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getNome());
            st.setString(2, obj.getEndereco());
            st.setInt(3, obj.getAno_criacao());
            st.setString(4, obj.getSite());

            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Campus obj) throws SQLException {
        String sql = "UPDATE campus SET nome = ?, endereco = ?, ano_criacao = ?, site = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getNome());
            st.setString(2, obj.getEndereco());
            st.setInt(3, obj.getAno_criacao());
            st.setString(4, obj.getSite());
            st.setInt(5, obj.getId());

            st.executeUpdate();
        }
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM campus WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        }
    }

    @Override
    public Campus findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM campus WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateCampus(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Campus> findAll() throws SQLException {
        List<Campus> list = new ArrayList<>();
        String sql = "SELECT * FROM campus ORDER BY nome";
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                list.add(instantiateCampus(rs));
            }
        }
        return list;
    }

    private Campus instantiateCampus(ResultSet rs) throws SQLException {
        Campus campus = new Campus();
        campus.setId(rs.getInt("id"));
        campus.setNome(rs.getString("nome"));
        campus.setEndereco(rs.getString("endereco"));
        campus.setAno_criacao(rs.getInt("ano_criacao"));
        campus.setSite(rs.getString("site"));
        return campus;
    }
}
