package model.entities;
import java.io.Serializable;

public class Cadeira_aluno implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;              // novo campo ID autogerado (chave primária)
    private String horario;
    private String sala;
    private float nota1, nota2, nota3, nota4;
    private int mat_aluno;
    private int id_professor;
    private int id_disciplina;

    public Cadeira_aluno() {
    }

    public Cadeira_aluno(int id, String horario, String sala, float nota1, float nota2, float nota3, float nota4, int mat_aluno, int id_professor, int id_disciplina) {
        this.id = id;
        this.horario = horario;
        this.sala = sala;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
        this.mat_aluno = mat_aluno;
        this.id_professor = id_professor;
        this.id_disciplina = id_disciplina;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public float getNota1() {
		return nota1;
	}

	public void setNota1(float nota1) {
		this.nota1 = nota1;
	}

	public float getNota2() {
		return nota2;
	}

	public void setNota2(float nota2) {
		this.nota2 = nota2;
	}

	public float getNota3() {
		return nota3;
	}

	public void setNota3(float nota3) {
		this.nota3 = nota3;
	}

	public float getNota4() {
		return nota4;
	}

	public void setNota4(float nota4) {
		this.nota4 = nota4;
	}

	public int getMat_aluno() {
		return mat_aluno;
	}

	public void setMat_aluno(int mat_aluno) {
		this.mat_aluno = mat_aluno;
	}

	public int getId_professor() {
		return id_professor;
	}

	public void setId_professor(int id_professor) {
		this.id_professor = id_professor;
	}

	public int getId_disciplina() {
		return id_disciplina;
	}

	public void setId_disciplina(int id_disciplina) {
		this.id_disciplina = id_disciplina;
	}


}
