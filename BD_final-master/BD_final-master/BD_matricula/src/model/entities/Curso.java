package model.entities;

import java.io.Serializable;

public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String centro;
    private int n_disciplinas;
    private int n_alunos;
    private boolean ead;
    private int id_campus;
    private int id_coordenador;

    public Curso() {
    }

    public Curso(int id, String nome, String centro, int n_disciplinas, int n_alunos, boolean ead, int id_campus, int id_coordenador) {
        this.id = id;
        this.nome = nome;
        this.centro = centro;
        this.n_disciplinas = n_disciplinas;
        this.n_alunos = n_alunos;
        this.ead = ead;
        this.id_campus = id_campus;
        this.id_coordenador = id_coordenador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getCentro() {
        return centro;
    }

    public int getN_disciplinas() {
        return n_disciplinas;
    }

    public int getN_alunos() {
        return n_alunos;
    }

    public boolean isEad() {
        return ead;
    }

    public int getId_campus() {
        return id_campus;
    }

    public int getId_coordenador() {
        return id_coordenador;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public void setN_disciplinas(int n_disciplinas) {
        this.n_disciplinas = n_disciplinas;
    }

    public void setN_alunos(int n_alunos) {
        this.n_alunos = n_alunos;
    }

    public void setEad(boolean ead) {
        this.ead = ead;
    }

    public void setId_campus(int id_campus) {
        this.id_campus = id_campus;
    }

    public void setId_coordenador(int id_coordenador) {
        this.id_coordenador = id_coordenador;
    }
}
