package model.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.dao.CadeiraAlunoDao;
import model.entities.CadeiraAluno;

public class CadeiraAlunoDaoJDBC implements CadeiraAlunoDao {

    private Connection conn;

    public CadeiraAlunoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(CadeiraAluno obj) throws SQLException {
        String sql = "INSERT INTO cadeira_aluno (horario, sala, nota1, nota2, nota3, nota4, mat_aluno, id_professor, id_disciplina) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getHorario());
            st.setString(2, obj.getSala());
            st.setFloat(3, obj.getNota1());
            st.setFloat(4, obj.getNota2());
            st.setFloat(5, obj.getNota3());
            st.setFloat(6, obj.getNota4());
            st.setInt(7, obj.getMat_aluno());
            st.setInt(8, obj.getId_professor());
            st.setInt(9, obj.getId_disciplina());

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
    public void update(CadeiraAluno obj) throws SQLException {
        String sql = "UPDATE cadeira_aluno SET horario = ?, sala = ?, nota1 = ?, nota2 = ?, nota3 = ?, nota4 = ?, mat_aluno = ?, id_professor = ?, id_disciplina = ? WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getHorario());
            st.setString(2, obj.getSala());
            st.setFloat(3, obj.getNota1());
            st.setFloat(4, obj.getNota2());
            st.setFloat(5, obj.getNota3());
            st.setFloat(6, obj.getNota4());
            st.setInt(7, obj.getMat_aluno());
            st.setInt(8, obj.getId_professor());
            st.setInt(9, obj.getId_disciplina());
            st.setInt(10, obj.getId());

            st.executeUpdate();
        }
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM cadeira_aluno WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        }
    }

    @Override
    public CadeiraAluno findById(int id) throws SQLException {
        String sql = "SELECT * FROM cadeira_aluno WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateCadeiraAluno(rs);
                }
                return null;
            }
        }
    }

    @Override
    public List<CadeiraAluno> findAll() throws SQLException {
        String sql = "SELECT * FROM cadeira_aluno ORDER BY id";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<CadeiraAluno> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateCadeiraAluno(rs));
            }
            return list;
        }
    }

    private CadeiraAluno instantiateCadeiraAluno(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String horario = rs.getString("horario");
        String sala = rs.getString("sala");
        float nota1 = rs.getFloat("nota1");
        float nota2 = rs.getFloat("nota2");
        float nota3 = rs.getFloat("nota3");
        float nota4 = rs.getFloat("nota4");
        int mat_aluno = rs.getInt("mat_aluno");
        int id_professor = rs.getInt("id_professor");
        int id_disciplina = rs.getInt("id_disciplina");

        return new CadeiraAluno(id, horario, sala, nota1, nota2, nota3, nota4, mat_aluno, id_professor, id_disciplina);
    }
}
