package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.entities.Aluno;

public class Main {
    //para acessar o banco de dados (aqui tá com as minhas informações):
    static String url = "jdbc:postgresql://localhost:5432/universidade";
    static String user = "postgres";
    static String senha = "bdtest";


    public static void insertAlunos(ArrayList<Aluno> alunos){ //isso é um exemplo de função de query!
        //o corpo do try/catch deve ser repetido para cada função de query (a de insert, update, search, etc)
        try (Connection con = DriverManager.getConnection(url, user, senha)) {
            Statement st = con.createStatement();
            //------ essa é a parte que muda de função para função ------
            for (Aluno aluno : alunos) {
                st.executeUpdate("INSERT INTO alunos (nome, idade, semestre, n_disciplinas, prev_termino, sem_inicial) VALUES ('"+
                        aluno.getNome() + "', " +
                        aluno.getIdade() + ", " +
                        aluno.getSemestre() + ", " +
                        aluno.getN_disciplinas() + ", '" +
                        aluno.getPrev_termino() + "', '" +
                        aluno.getSem_inicial() + "'" +
                ")");
            }
            //------ fim da parte que muda de função para função ------
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        //AQUI ONDE FICA A INTERFACE DE USUÁRIO
        //eu acho que pra interface, os processos de pesquisar e editar deveriam ser integrados
        //tipo, vc tem que pesquisar o nome de alguém, aparece os dados, daí vc escolhe se quer editar ou não

        //teste rápido de inserção:
        ArrayList<Aluno> alunos = new ArrayList<>();
        alunos.add(new Aluno("Joazinho", 19, 3, 5, "2024.1", "2028.1"));
        insertAlunos(alunos);
    }
}
