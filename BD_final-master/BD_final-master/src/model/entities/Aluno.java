package model.entities;
import java.io.Serializable;

public class Aluno implements Serializable {
    private static final long serialVersionUID = 1L;

    private int matricula;           // ← Chave primária
    private String nome;
    private int idade;
    private int semestre;
    private int n_disciplinas;
    private String sem_inicial;
    private String prev_termino;

    public Aluno() {
    }

    public Aluno(int matricula, String nome, int idade, int semestre, int n_disciplinas, String sem_inicial, String prev_termino) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
        this.semestre = semestre;
        this.n_disciplinas = n_disciplinas;
        this.sem_inicial = sem_inicial;
        this.prev_termino = prev_termino;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public int getSemestre() {
        return semestre;
    }

    public int getN_disciplinas() {
        return n_disciplinas;
    }

    public String getSem_inicial() {
        return sem_inicial;
    }

    public String getPrev_termino() {
        return prev_termino;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public void setN_disciplinas(int n_disciplinas) {
        this.n_disciplinas = n_disciplinas;
    }

    public void setSem_inicial(String sem_inicial) {
        this.sem_inicial = sem_inicial;
    }

    public void setPrev_termino(String prev_termino) {
        this.prev_termino = prev_termino;
    }
}
