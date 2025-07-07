package model.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.dao.AlunoDao;
import model.entities.Aluno;

public class AlunoDaoJDBC implements AlunoDao {

    private Connection conn;

    public AlunoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Aluno obj) throws SQLException {
        String sql = "INSERT INTO aluno (nome, idade, semestre, n_disciplinas, sem_inicial, prev_termino) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getNome());
            st.setInt(2, obj.getIdade());
            st.setInt(3, obj.getSemestre());
            st.setInt(4, 0);
            st.setInt(4, obj.getN_disciplinas());
            st.setString(5, obj.getSem_inicial());
            st.setString(6, obj.getPrev_termino());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        obj.setMatricula(rs.getInt(1));
                    }
                }
            } else {
                throw new SQLException("Unexpected error! No rows affected!");
            }
        }
    }

    @Override
    public void massInsert(List<Aluno> alunos) throws SQLException {
        for (Aluno aluno : alunos){
            insert(aluno);
        }
    }

    @Override
    public void update(Aluno obj) throws SQLException {
        String sql = "UPDATE aluno SET nome = ?, idade = ?, semestre = ?, n_disciplinas = ?, sem_inicial = ?, prev_termino = ? " +
                     "WHERE matricula = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getNome());
            st.setInt(2, obj.getIdade());
            st.setInt(3, obj.getSemestre());
            st.setInt(4, obj.getN_disciplinas());
            st.setString(5, obj.getSem_inicial());
            st.setString(6, obj.getPrev_termino());
            st.setInt(7, obj.getMatricula());

            st.executeUpdate();
        }
    }

    @Override
    public void deleteById(int matricula) throws SQLException {
        String sql = "DELETE FROM aluno WHERE matricula = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, matricula);
            st.executeUpdate();
        }
    }

    @Override
    public Aluno findById(int matricula) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE matricula = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, matricula);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateAluno(rs);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<Aluno> findAll() throws SQLException {
        String sql = "SELECT * FROM aluno ORDER BY nome";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Aluno> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateAluno(rs));
            }
            return list;
        }
    }

    @Override
    public List<Aluno> findBySubstring(String sub) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE nome ILIKE '%"+ sub +"%' ORDER BY nome";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Aluno> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateAluno(rs));
            }
            return list;
        }
    }

    private Aluno instantiateAluno(ResultSet rs) throws SQLException {
        int matricula = rs.getInt("matricula");
        String nome = rs.getString("nome");
        int idade = rs.getInt("idade");
        int semestre = rs.getInt("semestre");
        int n_disciplinas = rs.getInt("n_disciplinas");
        String sem_inicial = rs.getString("sem_inicial");
        String prev_termino = rs.getString("prev_termino");

        return new Aluno(matricula, nome, idade, semestre, n_disciplinas, sem_inicial, prev_termino);
    }

    @Override
    public int somarHorasTotais(int mat) throws SQLException {
        String sql = "SELECT SUM(horas) FROM (" +
                "SELECT horas, matricula FROM Aluno " +
                "INNER JOIN Cadeira_aluno ON Aluno.matricula = Cadeira_aluno.mat_aluno " +
                "INNER JOIN Disciplina ON Cadeira_aluno.id_disciplina = Disciplina.id" +
                ") WHERE matricula= ? ";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, mat);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }
}
