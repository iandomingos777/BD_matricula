package main;

import java.sql.Connection;
import java.sql.SQLException;

import db.Conexao;
import model.dao.impl.AlunoDaoJDBC;
import model.entities.Aluno;

public class TestaConexao {
	public static void main(String[] args) {
		try (Connection conn = Conexao.conectar()) {
		System.out.println("Conectado ao banco!");
		        AlunoDaoJDBC alunoDao = new AlunoDaoJDBC(conn);

		        // Criando um novo aluno
		        Aluno aluno = new Aluno();
		        aluno.setNome("Maria Silva");
		        aluno.setIdade(21);
		        aluno.setSemestre(5);
		        aluno.setN_disciplinas(6);
		        aluno.setSem_inicial("2021.1");
		        aluno.setPrev_termino("2025.2");

		        // Inserindo no banco
		        alunoDao.insert(aluno);
		        System.out.println("Aluno inserido com matrícula: " + aluno.getMatricula());

		        // Buscando do banco para verificar
		        Aluno recuperado = alunoDao.findById(aluno.getMatricula());
		        if (recuperado != null) {
		            System.out.println("Aluno recuperado: " + recuperado.getNome() + ", semestre " + recuperado.getSemestre());
		        } else {
		            System.out.println("Aluno não encontrado.");
		        }

		    } catch (Exception e) {
		        System.out.println("Erro: " + e.getMessage());
		    }
		}
}