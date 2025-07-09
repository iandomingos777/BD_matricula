package main;

import model.dao.impl.*;
import model.entities.*;

import db.Conexao;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class App extends Application {
	private Connection connect;
	
    private BorderPane rootLayout;
    private StackPane contentStack;
    private HBox topBar;
    private Button backButton;
    private Label currentTitleLabel;

    private Stack<StackPane> navigationHistory = new Stack<>();

    @Override
    public void start(Stage primaryStage) {
    	try {
	    	connect = Conexao.conectar();
	    	
	        rootLayout = new BorderPane();
	
	        topBar = new HBox(10);
	        topBar.setPadding(new Insets(10));
	        topBar.setAlignment(Pos.CENTER_LEFT);
	        topBar.setStyle("-fx-background-color: #336699;");
	
	        backButton = new Button("Voltar");
	        backButton.setVisible(false);
	        backButton.setOnAction(e -> navigateBack());
	        backButton.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: white; -fx-border-radius: 5;");
	
	        currentTitleLabel = new Label("Início");
	        currentTitleLabel.setText("Sistema Universitário");
	        currentTitleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
	        HBox.setHgrow(currentTitleLabel, javafx.scene.layout.Priority.ALWAYS);
	
	        topBar.getChildren().addAll(backButton, currentTitleLabel);
	        rootLayout.setTop(topBar);
	
	        contentStack = new StackPane();
	        rootLayout.setCenter(contentStack);
	
	        showView(createMainMenu());
	
	        Scene scene = new Scene(rootLayout, 600, 400);
	        primaryStage.setTitle("BD Trabalho");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
    	} catch (Exception e) {
    		System.out.println("Erro: " + e.getMessage());
    	}
    }

    private void showView(StackPane newView) {
        if (!contentStack.getChildren().isEmpty() && contentStack.getChildren().get(0) != newView) {
             if (navigationHistory.isEmpty() || navigationHistory.peek() != contentStack.getChildren().get(0)) {
                 navigationHistory.push((StackPane) contentStack.getChildren().get(0));
             }
        }

        contentStack.getChildren().clear();
        contentStack.getChildren().add(newView);

        backButton.setVisible(!navigationHistory.isEmpty());
    }

    private void navigateBack() {
        if (!navigationHistory.isEmpty()) {
            StackPane previousView = navigationHistory.pop();
            contentStack.getChildren().clear();
            contentStack.getChildren().add(previousView);
            backButton.setVisible(!navigationHistory.isEmpty());
        }
    }

    private StackPane createMainMenu() {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(50));
        menu.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Escolha a tabela");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button btnOption1 = new Button("Aluno");
        Button btnOption2 = new Button("Curso");
        Button btnOption3 = new Button("Campus");
        Button btnOption4 = new Button("Docente");
        Button btnOption5 = new Button("Disciplina");
        Button btnExit = new Button("Sair do App");

        btnOption1.setMaxWidth(Double.MAX_VALUE);
        btnOption2.setMaxWidth(Double.MAX_VALUE);
        btnOption3.setMaxWidth(Double.MAX_VALUE);
        btnOption4.setMaxWidth(Double.MAX_VALUE);
        btnOption5.setMaxWidth(Double.MAX_VALUE);
        btnExit.setMaxWidth(Double.MAX_VALUE);

        btnOption1.setOnAction(e -> showView(AlunoMenu()));
        btnOption2.setOnAction(e -> showView(CursoMenu()));
        btnOption3.setOnAction(e -> showView(CampusMenu()));
        btnOption4.setOnAction(e -> showView(DocenteMenu()));
        btnOption5.setOnAction(e -> showView(DisciplinaMenu()));
        btnExit.setOnAction(e -> System.exit(0));

        menu.getChildren().addAll(title, btnOption1, btnOption2, btnOption3, btnOption4, btnOption5, btnExit);

        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }

    private StackPane AlunoMenu() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));

        Label title = new Label("Escolha a operação");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button btn1 = new Button("Inserir");
        Button btn2 = new Button("Atualizar");
        Button btn3 = new Button("Remover");
        Button btn4 = new Button("Procurar");
        Button btn5 = new Button("Alunos destaque");
        Button btn6 = new Button("Alunos por média");
        
        btn1.setMaxWidth(Double.MAX_VALUE);
        btn2.setMaxWidth(Double.MAX_VALUE);
        btn3.setMaxWidth(Double.MAX_VALUE);
        btn4.setMaxWidth(Double.MAX_VALUE);
        btn5.setMaxWidth(Double.MAX_VALUE);
        btn6.setMaxWidth(Double.MAX_VALUE);
        
        btn1.setOnAction(e -> showView(AlunoInserir()));
        btn2.setOnAction(e -> showView(AlunoAtualizar()));
        btn3.setOnAction(e -> showView(AlunoRemover()));
        btn4.setOnAction(e -> showView(AlunoProcurar()));
        btn5.setOnAction(e -> showView(AlunoDestaques()));
        btn6.setOnAction(e -> showView(AlunoMedias()));

        menu.getChildren().addAll(title, btn1, btn2, btn3, btn4, btn5, btn6);
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane AlunoInserir() {
    	VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Dados do aluno");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    	
        Label nomeLabel = new Label("Nome*");
        TextField nomeInput = new TextField();
        nomeInput.setPromptText("Nome");
        nomeInput.setMaxWidth(200);
        
        Label idadeLabel = new Label("Idade");
        TextField idadeInput = new TextField();
        idadeInput.setPromptText("Idade");
        idadeInput.setMaxWidth(200);
        idadeInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                idadeInput.setText(oldValue);
            }
        });
        
        Label semLabel = new Label("Semestre*");
        TextField semInput = new TextField();
        semInput.setPromptText("Semestre");
        semInput.setMaxWidth(200);
        semInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                semInput.setText(oldValue);
            }
        });
        
        Label ndisLabel = new Label("Número de disciplinas*");
        TextField ndisInput = new TextField();
        ndisInput.setPromptText("Número de disciplinas");
        ndisInput.setMaxWidth(200);
        ndisInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ndisInput.setText(oldValue);
            }
        });
        
        Label sinLabel = new Label("Semestre inicial*");
        TextField sinInput = new TextField();
        sinInput.setPromptText("Semestre inicial");
        sinInput.setMaxWidth(200);
        
        Label pterLabel = new Label("Previsão de término");
        TextField pterInput = new TextField();
        pterInput.setPromptText("Previsão de término");
        pterInput.setMaxWidth(200);
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        
        confirm.setOnAction(e -> {
            try {
            	String nome = nomeInput.getText();
            	int idade = idadeInput.getText().trim().isEmpty() ? 0 : Integer.parseInt(idadeInput.getText());
            	int sem = semInput.getText().trim().isEmpty() ? 0 : Integer.parseInt(semInput.getText());
            	int ndis = ndisInput.getText().trim().isEmpty() ? 0 : Integer.parseInt(ndisInput.getText());
            	String sin = sinInput.getText();
            	String pter = pterInput.getText();

                if (nome.trim().isEmpty() || sem == 0 || ndis == 0 || sin.trim().isEmpty()) {    
                    return;
                }
                
                AlunoDaoJDBC alunodao = new AlunoDaoJDBC(connect);
                Aluno aluno = new Aluno();
                aluno.setNome(nome);
                if (idade != 0) aluno.setIdade(idade);
                aluno.setSemestre(sem);
                aluno.setN_disciplinas(ndis);
                aluno.setSem_inicial(sin);
                aluno.setPrev_termino(pter);
                alunodao.insert(aluno);
                
                navigateBack();
            } catch (NumberFormatException ex) {
            	System.out.println("Não numero: " + ex.getMessage());
            } catch (Exception ex) {
            	System.out.println("Erro: " + ex.getMessage());
            }
        });
        
        menu.getChildren().addAll(title, nomeLabel, nomeInput, idadeLabel, idadeInput, semLabel, semInput, ndisLabel, ndisInput, sinLabel, sinInput, pterLabel, pterInput, confirm);
    	StackPane wrapper = new StackPane(menu);
    	return wrapper;
    }
    
    private StackPane AlunoAtualizar() {
    	VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label matLabel = new Label("Informe a matrícula");
        TextField matInput = new TextField();
        matInput.setPromptText("Matrícula");
        matInput.setMaxWidth(200);
        matInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                matInput.setText(oldValue);
            }
        });
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        menu.getChildren().addAll(matLabel, matInput, confirm);
        
        confirm.setOnAction(e -> {
        	try {
	        	AlunoDaoJDBC alunodao = new AlunoDaoJDBC(connect);
	        	Aluno aluno = alunodao.findById(Integer.parseInt(matInput.getText()));
	        	
	            Label title = new Label("Dados do aluno");
	            title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
	        	
	            Label nomeLabel = new Label("Nome*");
	            TextField nomeInput = new TextField();
	            nomeInput.setText(aluno.getNome());
	            nomeInput.setMaxWidth(200);
	            
	            Label idadeLabel = new Label("Idade");
	            TextField idadeInput = new TextField();
	            idadeInput.setText(String.valueOf(aluno.getIdade()));
	            idadeInput.setMaxWidth(200);
	            idadeInput.textProperty().addListener((observable, oldValue, newValue) -> {
	                if (!newValue.matches("\\d*")) {
	                    idadeInput.setText(oldValue);
	                }
	            });
	            
	            Label semLabel = new Label("Semestre*");
	            TextField semInput = new TextField();
	            semInput.setText(String.valueOf(aluno.getSemestre()));
	            semInput.setMaxWidth(200);
	            semInput.textProperty().addListener((observable, oldValue, newValue) -> {
	                if (!newValue.matches("\\d*")) {
	                    semInput.setText(oldValue);
	                }
	            });
	            
	            Label ndisLabel = new Label("Número de disciplinas*");
	            TextField ndisInput = new TextField();
	            ndisInput.setText(String.valueOf(aluno.getN_disciplinas()));
	            ndisInput.setMaxWidth(200);
	            ndisInput.textProperty().addListener((observable, oldValue, newValue) -> {
	                if (!newValue.matches("\\d*")) {
	                    ndisInput.setText(oldValue);
	                }
	            });
	            
	            Label sinLabel = new Label("Semestre inicial*");
	            TextField sinInput = new TextField();
	            sinInput.setText(aluno.getSem_inicial());
	            sinInput.setMaxWidth(200);
	            
	            Label pterLabel = new Label("Previsão de término");
	            TextField pterInput = new TextField();
	            pterInput.setText(aluno.getPrev_termino());
	            pterInput.setMaxWidth(200);
	            
	            Button confirm2 = new Button("Confirmar");
	            confirm.setMaxWidth(150);
	            
	            menu.getChildren().clear();
	            menu.getChildren().addAll(title, nomeLabel, nomeInput, idadeLabel, idadeInput, semLabel, semInput, ndisLabel, ndisInput, sinLabel, sinInput, pterLabel, pterInput, confirm2);
	            
	            confirm2.setOnAction(ev -> {
	                try {
	                	String nome = nomeInput.getText();
	                	int idade = idadeInput.getText().trim().isEmpty() ? 0 : Integer.parseInt(idadeInput.getText());
	                	int sem = semInput.getText().trim().isEmpty() ? 0 : Integer.parseInt(semInput.getText());
	                	int ndis = ndisInput.getText().trim().isEmpty() ? 0 : Integer.parseInt(ndisInput.getText());
	                	String sin = sinInput.getText();
	                	String pter = pterInput.getText();

	                    if (nome.trim().isEmpty() || sem == 0 || ndis == 0 || sin.trim().isEmpty()) {    
	                        return;
	                    }
	                    
	                    aluno.setNome(nome);
	                    if (idade != 0) aluno.setIdade(idade);
	                    aluno.setSemestre(sem);
	                    aluno.setN_disciplinas(ndis);
	                    aluno.setSem_inicial(sin);
	                    aluno.setPrev_termino(pter);
	                    alunodao.update(aluno);
	                    
	                    navigateBack();
	                } catch (NumberFormatException ex) {
	                	System.out.println("Não numero: " + ex.getMessage());
	                } catch (Exception ex) {
	                	System.out.println("Erro: " + ex.getMessage());
	                }
	            });
        	} catch (Exception ex) {
        		System.out.println("Erro: " + ex.getMessage());
        	}
        });
        
    	StackPane wrapper = new StackPane(menu);
    	return wrapper;
    }
    
    private StackPane AlunoRemover() {
    	VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label matLabel = new Label("Informe a matrícula");
        TextField matInput = new TextField();
        matInput.setPromptText("Matrícula");
        matInput.setMaxWidth(200);
        matInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                matInput.setText(oldValue);
            }
        });
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        menu.getChildren().addAll(matLabel, matInput, confirm);
        
        confirm.setOnAction(e -> {
        	try {
        		AlunoDaoJDBC alunodao = new AlunoDaoJDBC(connect);
        		alunodao.deleteById(Integer.parseInt(matInput.getText()));
        		
        		navigateBack();
        	} catch (Exception ex) {
        		System.out.print("Erro: " + ex.getMessage());
        	}
        });
        
    	StackPane wrapper = new StackPane(menu);
    	return wrapper;
    }
    
    private StackPane AlunoProcurar() {
    	VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Procurar por nome");
        TextField nomeInput = new TextField();
        nomeInput.setPromptText("Nome");
        nomeInput.setMaxWidth(200);
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        
        confirm.setOnAction(e -> {
        	try {
	        	AlunoDaoJDBC alunodao = new AlunoDaoJDBC(connect);
	        	String nome = nomeInput.getText();
	        	List<Aluno> alunoList;
	        	if (nome.trim().isEmpty())
	        		alunoList = alunodao.findAll();
	        	else
	        		alunoList = alunodao.findBySubstring(nomeInput.getText());
	        	
	            List<String> nameList = new ArrayList<String>();
	            for (Aluno aluno : alunoList) {
	            	nameList.add(aluno.getNome() + " | Matrícula: " + aluno.getMatricula() + " | Horas: " + alunodao.somarHorasTotais(aluno.getMatricula()));
	            }
	            
	            ObservableList<String> names = FXCollections.observableArrayList(nameList);
	            ListView<String> nameListView = new ListView<String>(names);
	            nameListView.setPrefHeight(300);
	            nameListView.setPrefWidth(250);
	            title.setText("Alunos");
	            
	            menu.getChildren().clear();
	            menu.getChildren().addAll(title, nameListView);
        	} catch (Exception ex) {
        		System.out.println("Erro: " + ex.getMessage());
        	}
        });
        menu.getChildren().addAll(title, nomeInput, confirm);
        
    	StackPane wrapper = new StackPane(menu);
    	return wrapper;
    }
    
    private StackPane AlunoDestaques() {
    	VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Alunos destaques");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        try {
        	AlunoDaoJDBC alunodao = new AlunoDaoJDBC(connect);
        	List<Aluno> destaques = alunodao.listarAlunosDestaque();
        	
            List<String> nameList = new ArrayList<String>();
            for (Aluno aluno : destaques) {
            	nameList.add(aluno.getNome() + " | Matrícula: " + aluno.getMatricula());
            }
            
            ObservableList<String> names = FXCollections.observableArrayList(nameList);
            ListView<String> nameListView = new ListView<String>(names);
            nameListView.setPrefHeight(300);
            nameListView.setPrefWidth(250);
            
            menu.getChildren().addAll(title, nameListView);
        } catch (Exception ex) {
        	System.out.println("Erro: " + ex.getMessage());
        }
        
    	StackPane wrapper = new StackPane(menu);
    	return wrapper;
    }
    
    private StackPane AlunoMedias() {
    	VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Alunos por média (maior para menor)");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        try {
        	AlunoDaoJDBC alunodao = new AlunoDaoJDBC(connect);
        	List<Aluno> list = alunodao.ordernarAlunosPorMedia(false);
        	
            List<String> nameList = new ArrayList<String>();
            for (Aluno aluno : list) {
            	nameList.add(aluno.getNome() + " | Matrícula: " + aluno.getMatricula());
            }
            
            ObservableList<String> names = FXCollections.observableArrayList(nameList);
            ListView<String> nameListView = new ListView<String>(names);
            nameListView.setPrefHeight(300);
            nameListView.setPrefWidth(250);
            
            menu.getChildren().addAll(title, nameListView);
        } catch (Exception ex) {
        	System.out.println("Erro: " + ex.getMessage());
        }
        
    	StackPane wrapper = new StackPane(menu);
    	return wrapper;
    }
    
    private StackPane CursoMenu() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Escolha a operação");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button btn1 = new Button("Inserir");
        Button btn2 = new Button("Atualizar");
        Button btn3 = new Button("Remover");
        Button btn4 = new Button("Procurar");
        Button btn5 = new Button("Menos disciplinas");
        
        btn1.setMaxWidth(Double.MAX_VALUE);
        btn2.setMaxWidth(Double.MAX_VALUE);
        btn3.setMaxWidth(Double.MAX_VALUE);
        btn4.setMaxWidth(Double.MAX_VALUE);
        btn5.setMaxWidth(Double.MAX_VALUE);
        
        btn1.setOnAction(e -> showView(CursoInserir()));
        btn2.setOnAction(e -> showView(CursoAtualizar()));
        btn3.setOnAction(e -> showView(CursoRemover()));
        btn4.setOnAction(e -> showView(CursoProcurar()));
        btn5.setOnAction(e -> showView(CursoMenosDisc()));
        
        menu.getChildren().addAll(title, btn1, btn2, btn3, btn4, btn5);
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane CursoInserir() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Dados do curso");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    	
        Label nomeLabel = new Label("Nome*");
        TextField nomeInput = new TextField();
        nomeInput.setPromptText("Nome");
        nomeInput.setMaxWidth(200);
        
        Label centroLabel = new Label("Centro*");
        TextField centroInput = new TextField();
        centroInput.setPromptText("Centro");
        centroInput.setMaxWidth(200);
        
        Label nalLabel = new Label("Número de alunos*");
        TextField nalInput = new TextField();
        nalInput.setPromptText("Número de alunos");
        nalInput.setMaxWidth(200);
        nalInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                nalInput.setText(oldValue);
            }
        });
        
        Label ndisLabel = new Label("Número de disciplinas*");
        TextField ndisInput = new TextField();
        ndisInput.setPromptText("Número de disciplinas");
        ndisInput.setMaxWidth(200);
        ndisInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ndisInput.setText(oldValue);
            }
        });
        
        Label eadLabel = new Label("Ead?");
        CheckBox eadInput = new CheckBox();
        
        Label idcaLabel = new Label("Campus*");
        TextField idcaInput = new TextField();
        idcaInput.setPromptText("ID or nome");
        idcaInput.setMaxWidth(200);
        
        Label idcoLabel = new Label("Coordenador*");
        TextField idcoInput = new TextField();
        idcoInput.setPromptText("ID or nome");
        idcoInput.setMaxWidth(200);
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        
        confirm.setOnAction(e -> {
            try {
            	String nome = nomeInput.getText();
            	String centro = centroInput.getText();
            	int nal = Integer.parseInt(nalInput.getText());
            	int ndis = Integer.parseInt(ndisInput.getText());
            	boolean ead = eadInput.isSelected();
            	int idca;
            	int idco;
            	try {
            		idca = Integer.parseInt(idcaInput.getText());
            	} catch (NumberFormatException ex) {
            		try {
            			CampusDaoJDBC campusdao = new CampusDaoJDBC(connect);
            			if (campusdao.findBySubstring(idcaInput.getText()).size() != 1)
            				return;
            			else
            				idca = campusdao.findBySubstring(idcaInput.getText()).get(0).getId();
            		} catch (Exception ex1) {
            			System.out.println("Erro: " + ex1.getMessage());
            			return;
            		}
            	}
            	try {
            		idco = Integer.parseInt(idcoInput.getText());
            	} catch (NumberFormatException ex) {
            		try {
            			DocenteDaoJDBC docentedao = new DocenteDaoJDBC(connect);
            			if (docentedao.findBySubstring(idcoInput.getText()).size() != 1)
            				return;
            			else
            				idco = docentedao.findBySubstring(idcoInput.getText()).get(0).getId();
            		} catch (Exception ex1) {
            			System.out.println("Erro: " + ex1.getMessage());
            			return;
            		}
            	}

                if (nome.trim().isEmpty() || centro.trim().isEmpty()) {    
                    return;
                }
                
                CursoDaoJDBC cursodao = new CursoDaoJDBC(connect);
                Curso curso = new Curso();
                curso.setNome(nome);
                curso.setCentro(centro);
                curso.setN_alunos(nal);
                curso.setN_disciplinas(ndis);
                if (ead) curso.setEad(ead);
                curso.setId_campus(idca);
                curso.setId_coordenador(idco);
                cursodao.insert(curso);
                
                navigateBack();
            } catch (NumberFormatException ex) {
            	System.out.println("Não numero: " + ex.getMessage());
            } catch (Exception ex) {
            	System.out.println("Erro: " + ex.getMessage());
            }
        });
        
        menu.getChildren().addAll(title, nomeLabel, nomeInput, centroLabel, centroInput, nalLabel, nalInput, ndisLabel, ndisInput, eadLabel, eadInput, idcaLabel, idcaInput, idcoLabel, idcoInput, confirm);
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane CursoAtualizar() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label idLabel = new Label("Informe o ID");
        TextField idInput = new TextField();
        idInput.setPromptText("ID");
        idInput.setMaxWidth(200);
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(200);
        menu.getChildren().addAll(idLabel, idInput, confirm);
        
        confirm.setOnAction(e -> {
        	try {
        		CursoDaoJDBC cursodao = new CursoDaoJDBC(connect);
        		Curso curso = cursodao.findById(Integer.parseInt(idInput.getText()));
        		
		        Label title = new Label("Dados do curso");
		        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		    	
		        Label nomeLabel = new Label("Nome*");
		        TextField nomeInput = new TextField();
		        nomeInput.setText(curso.getNome());
		        nomeInput.setMaxWidth(200);
		        
		        Label centroLabel = new Label("Centro*");
		        TextField centroInput = new TextField();
		        centroInput.setText(curso.getCentro());
		        centroInput.setMaxWidth(200);
		        
		        Label nalLabel = new Label("Número de alunos*");
		        TextField nalInput = new TextField();
		        nalInput.setText(String.valueOf(curso.getN_alunos()));
		        nalInput.setMaxWidth(200);
		        nalInput.textProperty().addListener((observable, oldValue, newValue) -> {
		            if (!newValue.matches("\\d*")) {
		                nalInput.setText(oldValue);
		            }
		        });
		        
		        Label ndisLabel = new Label("Número de disciplinas*");
		        TextField ndisInput = new TextField();
		        ndisInput.setText(String.valueOf(curso.getN_disciplinas()));
		        ndisInput.setMaxWidth(200);
		        ndisInput.textProperty().addListener((observable, oldValue, newValue) -> {
		            if (!newValue.matches("\\d*")) {
		                ndisInput.setText(oldValue);
		            }
		        });
		        
		        Label eadLabel = new Label("Ead?");
		        CheckBox eadInput = new CheckBox();
		        eadInput.setSelected(curso.isEad());
		        
		        Label idcaLabel = new Label("Campus*");
		        TextField idcaInput = new TextField();
		        idcaInput.setText(String.valueOf(curso.getId_campus()));
		        idcaInput.setMaxWidth(200);
		        
		        Label idcoLabel = new Label("Coordenador*");
		        TextField idcoInput = new TextField();
		        idcoInput.setText(String.valueOf(curso.getId_coordenador()));
		        idcoInput.setMaxWidth(200);
		        
		        Button confirm2 = new Button("Confirmar");
		        confirm2.setMaxWidth(150);
		        
		        confirm2.setOnAction(ev -> {
		            try {
		            	String nome = nomeInput.getText();
		            	String centro = centroInput.getText();
		            	int nal = Integer.parseInt(nalInput.getText());
		            	int ndis = Integer.parseInt(ndisInput.getText());
		            	boolean ead = eadInput.isSelected();
		            	int idca = Integer.parseInt(idcaInput.getText());
		            	int idco = Integer.parseInt(idcoInput.getText());
		
		                if (nome.trim().isEmpty() || centro.trim().isEmpty()) {    
		                    return;
		                }
		                
		                curso.setNome(nome);
		                curso.setCentro(centro);
		                curso.setN_alunos(nal);
		                curso.setN_disciplinas(ndis);
		                if (ead) curso.setEad(ead);
		                curso.setId_campus(idca);
		                curso.setId_coordenador(idco);
		                cursodao.update(curso);
		                
		                navigateBack();
		            } catch (NumberFormatException ex) {
		            	System.out.println("Não numero: " + ex.getMessage());
		            } catch (Exception ex) {
		            	System.out.println("Erro: " + ex.getMessage());
		            }
		        });
		        
		        menu.getChildren().clear();
		        menu.getChildren().addAll(title, nomeLabel, nomeInput, centroLabel, centroInput, nalLabel, nalInput, ndisLabel, ndisInput, eadLabel, eadInput, idcaLabel, idcaInput, idcoLabel, idcoInput, confirm2);
        	} catch (Exception ex1) {
        		System.out.println("Erro: " + ex1.getMessage());
        	}
        });
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane CursoRemover() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label matLabel = new Label("Informe o ID");
        TextField matInput = new TextField();
        matInput.setPromptText("ID");
        matInput.setMaxWidth(200);
        matInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                matInput.setText(oldValue);
            }
        });
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        menu.getChildren().addAll(matLabel, matInput, confirm);
        
        confirm.setOnAction(e -> {
        	try {
        		CursoDaoJDBC cursodao = new CursoDaoJDBC(connect);
        		cursodao.deleteById(Integer.parseInt(matInput.getText()));
        		
        		navigateBack();
        	} catch (Exception ex) {
        		System.out.print("Erro: " + ex.getMessage());
        	}
        });
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane CursoProcurar() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Procurar por nome");
        TextField nomeInput = new TextField();
        nomeInput.setPromptText("Nome");
        nomeInput.setMaxWidth(200);
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        
        confirm.setOnAction(e -> {
        	try {
	        	CursoDaoJDBC cursodao = new CursoDaoJDBC(connect);
	        	String nome = nomeInput.getText();
	        	List<Curso> cursoList;
	        	if (nome.trim().isEmpty())
	        		cursoList = cursodao.findAll();
	        	else
	        		cursoList = cursodao.findBySubstring(nomeInput.getText());
	        	
	            List<String> nameList = new ArrayList<String>();
	            for (Curso curso : cursoList) {
	            	nameList.add(curso.getNome() + " | ID: " + curso.getId());
	            }
	            
	            ObservableList<String> names = FXCollections.observableArrayList(nameList);
	            ListView<String> nameListView = new ListView<String>(names);
	            nameListView.setPrefHeight(300);
	            nameListView.setPrefWidth(250);
	            title.setText("Cursos");
	            
	            menu.getChildren().clear();
	            menu.getChildren().addAll(title, nameListView);
        	} catch (Exception ex) {
        		System.out.println("Erro: " + ex.getMessage());
        	}
        });
        menu.getChildren().addAll(title, nomeInput, confirm);
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane CursoMenosDisc() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Cursos com menos disciplinas");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        try {
        	CursoDaoJDBC cursodao = new CursoDaoJDBC(connect);
        	List<Curso> cursoList = cursodao.listarCursosComMenosDisciplinas();
        	
            List<String> nameList = new ArrayList<String>();
            for (Curso curso : cursoList) {
            	nameList.add(curso.getNome() + " | ID: " + curso.getId());
            }
            
            ObservableList<String> names = FXCollections.observableArrayList(nameList);
            ListView<String> nameListView = new ListView<String>(names);
            nameListView.setPrefHeight(300);
            nameListView.setPrefWidth(250);
            
            menu.getChildren().addAll(title, nameListView);
        } catch (Exception ex) {
        	System.out.println("Erro: " + ex.getMessage());
        }
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane CampusMenu() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Escolha a operação");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button btn1 = new Button("Inserir");
        Button btn2 = new Button("Atualizar");
        Button btn3 = new Button("Remover");
        Button btn4 = new Button("Procurar");
        
        btn1.setMaxWidth(Double.MAX_VALUE);
        btn2.setMaxWidth(Double.MAX_VALUE);
        btn3.setMaxWidth(Double.MAX_VALUE);
        btn4.setMaxWidth(Double.MAX_VALUE);
        
        btn1.setOnAction(e -> showView(CampusInserir()));
        btn2.setOnAction(e -> showView(CampusAtualizar()));
        btn3.setOnAction(e -> showView(CampusRemover()));
        btn4.setOnAction(e -> showView(CampusProcurar()));

        menu.getChildren().addAll(title, btn1, btn2, btn3, btn4);
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane CampusInserir() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Dados do campus");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    	
        Label nomeLabel = new Label("Nome*");
        TextField nomeInput = new TextField();
        nomeInput.setPromptText("Nome");
        nomeInput.setMaxWidth(200);
        
        Label endLabel = new Label("Endereço*");
        TextField endInput = new TextField();
        endInput.setPromptText("Endereço");
        endInput.setMaxWidth(200);
        
        Label anoLabel = new Label("Ano de criação");
        TextField anoInput = new TextField();
        anoInput.setPromptText("Ano de cração");
        anoInput.setMaxWidth(200);
        anoInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                anoInput.setText(oldValue);
            }
        });
        
        Label siteLabel = new Label("Site*");
        TextField siteInput = new TextField();
        siteInput.setPromptText("Site");
        siteInput.setMaxWidth(200);

        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        
        confirm.setOnAction(e -> {
            try {
            	String nome = nomeInput.getText();
            	String end = endInput.getText();
            	int ano = anoInput.getText().trim().isEmpty() ? 0 : Integer.parseInt(anoInput.getText());
            	String site = siteInput.getText();

                if (nome.trim().isEmpty() || end.trim().isEmpty()) {    
                    return;
                }
                
                CampusDaoJDBC campusdao = new CampusDaoJDBC(connect);
                Campus campus = new Campus();
                campus.setNome(nome);
                campus.setEndereco(end);
                if (ano != 0) campus.setAno_criacao(ano);
                campus.setSite(site);
                campusdao.insert(campus);
                
                navigateBack();
            } catch (NumberFormatException ex) {
            	System.out.println("Não numero: " + ex.getMessage());
            } catch (Exception ex) {
            	System.out.println("Erro: " + ex.getMessage());
            }
        });
        
        menu.getChildren().addAll(title, nomeLabel, nomeInput, endLabel, endInput, anoLabel, anoInput, siteLabel, siteInput, confirm);
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane CampusAtualizar() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label idLabel = new Label("Informe o ID");
        TextField idInput = new TextField();
        idInput.setPromptText("ID");
        idInput.setMaxWidth(200);
        idInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                idInput.setText(oldValue);
            }
        });
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        menu.getChildren().addAll(idLabel, idInput, confirm);
        
        confirm.setOnAction(e -> {
        	try {
        		CampusDaoJDBC campusdao = new CampusDaoJDBC(connect);
        		Campus campus = campusdao.findById(Integer.parseInt(idInput.getText()));
        		
                Label title = new Label("Dados do campus");
                title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            	
                Label nomeLabel = new Label("Nome*");
                TextField nomeInput = new TextField();
                nomeInput.setText(campus.getNome());
                nomeInput.setMaxWidth(200);
                
                Label endLabel = new Label("Endereço*");
                TextField endInput = new TextField();
                endInput.setText(campus.getEndereco());
                endInput.setMaxWidth(200);
                
                Label anoLabel = new Label("Ano de criação");
                TextField anoInput = new TextField();
                anoInput.setText(String.valueOf(campus.getAno_criacao()));
                anoInput.setMaxWidth(200);
                anoInput.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        anoInput.setText(oldValue);
                    }
                });
                
                Label siteLabel = new Label("Site*");
                TextField siteInput = new TextField();
                siteInput.setText(campus.getSite());
                siteInput.setMaxWidth(200);

                Button confirm2 = new Button("Confirmar");
                confirm2.setMaxWidth(150);
                
                confirm2.setOnAction(ev -> {
                    try {
                    	String nome = nomeInput.getText();
                    	String end = endInput.getText();
                    	int ano = anoInput.getText().trim().isEmpty() ? 0 : Integer.parseInt(anoInput.getText());
                    	String site = siteInput.getText();

                        if (nome.trim().isEmpty() || end.trim().isEmpty()) {    
                            return;
                        }
                        
                        campus.setNome(nome);
                        campus.setEndereco(end);
                        if (ano != 0) campus.setAno_criacao(ano);
                        campus.setSite(site);
                        campusdao.update(campus);
                        
                        navigateBack();
                    } catch (NumberFormatException ex) {
                    	System.out.println("Não numero: " + ex.getMessage());
                    } catch (Exception ex) {
                    	System.out.println("Erro: " + ex.getMessage());
                    }
                });
                
                menu.getChildren().clear();
                menu.getChildren().addAll(title, nomeLabel, nomeInput, endLabel, endInput, anoLabel, anoInput, siteLabel, siteInput, confirm2);
        	} catch (Exception ex) {
        		System.out.println("Erro: " + ex.getMessage());
        	}
        });
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane CampusRemover() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label matLabel = new Label("Informe o ID");
        TextField matInput = new TextField();
        matInput.setPromptText("ID");
        matInput.setMaxWidth(200);
        matInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                matInput.setText(oldValue);
            }
        });
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        menu.getChildren().addAll(matLabel, matInput, confirm);
        
        confirm.setOnAction(e -> {
        	try {
        		CampusDaoJDBC campusdao = new CampusDaoJDBC(connect);
        		campusdao.deleteById(Integer.parseInt(matInput.getText()));
        		
        		navigateBack();
        	} catch (Exception ex) {
        		System.out.print("Erro: " + ex.getMessage());
        	}
        });
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane CampusProcurar() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Procurar por nome");
        TextField nomeInput = new TextField();
        nomeInput.setPromptText("Nome");
        nomeInput.setMaxWidth(200);
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        
        confirm.setOnAction(e -> {
        	try {
	        	CampusDaoJDBC campusdao = new CampusDaoJDBC(connect);
	        	String nome = nomeInput.getText();
	        	List<Campus> campusList;
	        	if (nome.trim().isEmpty())
	        		campusList = campusdao.findAll();
	        	else
	        		campusList = campusdao.findBySubstring(nomeInput.getText());
	        	
	            List<String> nameList = new ArrayList<String>();
	            for (Campus curso : campusList) {
	            	nameList.add(curso.getNome() + " | ID: " + curso.getId());
	            }
	            
	            ObservableList<String> names = FXCollections.observableArrayList(nameList);
	            ListView<String> nameListView = new ListView<String>(names);
	            nameListView.setPrefHeight(300);
	            nameListView.setPrefWidth(250);
	            title.setText("Campi");
	            
	            menu.getChildren().clear();
	            menu.getChildren().addAll(title, nameListView);
        	} catch (Exception ex) {
        		System.out.println("Erro: " + ex.getMessage());
        	}
        });
        menu.getChildren().addAll(title, nomeInput, confirm);
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }

    private StackPane DocenteMenu() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Escolha a operação");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button btn1 = new Button("Inserir");
        Button btn2 = new Button("Atualizar");
        Button btn3 = new Button("Remover");
        Button btn4 = new Button("Procurar");
        Button btn5 = new Button("Docentes com menos carga");
        
        btn1.setMaxWidth(Double.MAX_VALUE);
        btn2.setMaxWidth(Double.MAX_VALUE);
        btn3.setMaxWidth(Double.MAX_VALUE);
        btn4.setMaxWidth(Double.MAX_VALUE);
        btn5.setMaxWidth(Double.MAX_VALUE);
        
        btn1.setOnAction(e -> showView(DocenteInserir()));
        btn2.setOnAction(e -> showView(DocenteAtualizar()));
        btn3.setOnAction(e -> showView(DocenteRemover()));
        btn4.setOnAction(e -> showView(DocenteProcurar()));
        btn5.setOnAction(e -> showView(DocenteMenorCarga()));

        menu.getChildren().addAll(title, btn1, btn2, btn3, btn4, btn5);
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane DocenteInserir() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Dados do docente");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    	
        Label nomeLabel = new Label("Nome*");
        TextField nomeInput = new TextField();
        nomeInput.setPromptText("Nome");
        nomeInput.setMaxWidth(200);
        
        Label formLabel = new Label("Formação*");
        ObservableList<String> formOptions = FXCollections.observableArrayList(
                "Professor",
                "Coordenador"
            );
        ComboBox<String> formDrop = new ComboBox<>(formOptions);
        formDrop.setPromptText("Formação..");
        formDrop.setMaxWidth(200);
        
        Label telLabel = new Label("Telefone");
        TextField telInput = new TextField();
        telInput.setPromptText("Telefone");
        telInput.setMaxWidth(200);
        
        Label areaLabel = new Label("Area");
        TextField areaInput = new TextField();
        areaInput.setPromptText("Area");
        areaInput.setMaxWidth(200);
        
        Label latLabel = new Label("Lattes");
        TextField latInput = new TextField();
        latInput.setPromptText("Lattes");
        latInput.setMaxWidth(200);

        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        
        confirm.setOnAction(e -> {
            try {
            	String nome = nomeInput.getText();
            	String form = formDrop.getValue();
            	String tel = telInput.getText();
            	String area = areaInput.getText();
            	String lat = latInput.getText();

                if (nome.trim().isEmpty() || form.trim().isEmpty()) {    
                    return;
                }
                
                DocenteDaoJDBC docentedao = new DocenteDaoJDBC(connect);
                Docente docente = new Docente();
                docente.setNome(nome);
                docente.setFormacao(form);
                docente.setTelefone(tel);
                docente.setArea(area);
                docente.setLattes(lat);
                docentedao.insert(docente);
                
                navigateBack();
            } catch (NumberFormatException ex) {
            	System.out.println("Não numero: " + ex.getMessage());
            } catch (Exception ex) {
            	System.out.println("Erro: " + ex.getMessage());
            }
        });
        
        menu.getChildren().addAll(title, nomeLabel, nomeInput, formLabel, formDrop, telLabel, telInput, areaLabel, areaInput, latLabel, latInput, confirm);

        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane DocenteAtualizar() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label idLabel = new Label("Informe o ID");
        TextField idInput = new TextField();
        idInput.setPromptText("ID");
        idInput.setMaxWidth(200);
        idInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                idInput.setText(oldValue);
            }
        });
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        menu.getChildren().addAll(idLabel, idInput, confirm);
        
        confirm.setOnAction(e -> {
	        try {
	        	DocenteDaoJDBC docentedao = new DocenteDaoJDBC(connect);
	        	Docente docente = docentedao.findById(Integer.parseInt(idInput.getText()));
	        	
	            Label title = new Label("Dados do docente");
	            title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
	        	
	            Label nomeLabel = new Label("Nome*");
	            TextField nomeInput = new TextField();
	            nomeInput.setText(docente.getNome());
	            nomeInput.setMaxWidth(200);
	            
	            Label formLabel = new Label("Formação*");
	            ObservableList<String> formOptions = FXCollections.observableArrayList(
	                    "Professor",
	                    "Coordenador"
	                );
	            ComboBox<String> formDrop = new ComboBox<>(formOptions);
	            formDrop.setPromptText("Formação...");
	            formDrop.setValue(docente.getFormacao());
	            formDrop.setMaxWidth(200);
	            
	            Label telLabel = new Label("Telefone");
	            TextField telInput = new TextField();
	            telInput.setText(docente.getTelefone());
	            telInput.setMaxWidth(200);
	            
	            Label areaLabel = new Label("Area");
	            TextField areaInput = new TextField();
	            areaInput.setText(docente.getArea());
	            areaInput.setMaxWidth(200);
	            
	            Label latLabel = new Label("Lattes");
	            TextField latInput = new TextField();
	            latInput.setText(docente.getLattes());
	            latInput.setMaxWidth(200);
	
	            Button confirm2 = new Button("Confirmar");
	            confirm2.setMaxWidth(150);
	            
	            confirm2.setOnAction(ev -> {
	                try {
	                	String nome = nomeInput.getText();
	                	String form = formDrop.getValue();
	                	String tel = telInput.getText();
	                	String area = areaInput.getText();
	                	String lat = latInput.getText();
	
	                    if (nome.trim().isEmpty() || form.trim().isEmpty()) {    
	                        return;
	                    }
	                    
	                    docente.setNome(nome);
	                    docente.setFormacao(form);
	                    docente.setTelefone(tel);
	                    docente.setArea(area);
	                    docente.setLattes(lat);
	                    docentedao.update(docente);
	                    
	                    navigateBack();
	                } catch (NumberFormatException ex) {
	                	System.out.println("Não numero: " + ex.getMessage());
	                } catch (Exception ex) {
	                	System.out.println("Erro: " + ex.getMessage());
	                }
	            });
	            
	            menu.getChildren().clear();
	            menu.getChildren().addAll(title, nomeLabel, nomeInput, formLabel, formDrop, telLabel, telInput, areaLabel, areaInput, latLabel, latInput, confirm2);
	        } catch (Exception ex) {
	        	System.out.println("Erro: " + ex.getMessage());
	        }
        });
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane DocenteRemover() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label matLabel = new Label("Informe o ID");
        TextField matInput = new TextField();
        matInput.setPromptText("ID");
        matInput.setMaxWidth(200);
        matInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                matInput.setText(oldValue);
            }
        });
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        menu.getChildren().addAll(matLabel, matInput, confirm);
        
        confirm.setOnAction(e -> {
        	try {
        		DocenteDaoJDBC docentedao = new DocenteDaoJDBC(connect);
        		docentedao.deleteById(Integer.parseInt(matInput.getText()));
        		
        		navigateBack();
        	} catch (Exception ex) {
        		System.out.print("Erro: " + ex.getMessage());
        	}
        });
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane DocenteProcurar() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Procurar por nome");
        TextField nomeInput = new TextField();
        nomeInput.setPromptText("Nome");
        nomeInput.setMaxWidth(200);
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        
        confirm.setOnAction(e -> {
        	try {
	        	DocenteDaoJDBC docentesdao = new DocenteDaoJDBC(connect);
	        	String nome = nomeInput.getText();
	        	List<Docente> docenteList;
	        	if (nome.trim().isEmpty())
	        		docenteList = docentesdao.findAll();
	        	else
	        		docenteList = docentesdao.findBySubstring(nomeInput.getText());
	        	
	            List<String> nameList = new ArrayList<String>();
	            for (Docente docente : docenteList) {
	            	nameList.add(docente.getNome() + " | ID: " + docente.getId());
	            }
	            
	            ObservableList<String> names = FXCollections.observableArrayList(nameList);
	            ListView<String> nameListView = new ListView<String>(names);
	            nameListView.setPrefHeight(300);
	            nameListView.setPrefWidth(250);
	            title.setText("Docentes");
	            
	            menu.getChildren().clear();
	            menu.getChildren().addAll(title, nameListView);
        	} catch (Exception ex) {
        		System.out.println("Erro: " + ex.getMessage());
        	}
        });
        menu.getChildren().addAll(title, nomeInput, confirm);
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane DocenteMenorCarga() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
    	try {
        	DocenteDaoJDBC docentesdao = new DocenteDaoJDBC(connect);
        	List<Docente> docenteList = docentesdao.listarANYMenorCarga();
        	
            List<String> nameList = new ArrayList<String>();
            for (Docente docente : docenteList) {
            	nameList.add(docente.getNome() + " | ID: " + docente.getId());
            }
            
            ObservableList<String> names = FXCollections.observableArrayList(nameList);
            ListView<String> nameListView = new ListView<String>(names);
            nameListView.setPrefHeight(300);
            nameListView.setPrefWidth(250);
            
            menu.getChildren().addAll(title, nameListView);
    	} catch (Exception ex) {
    		System.out.println("Erro: " + ex.getMessage());
    	}
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane DisciplinaMenu() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Escolha a operação");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button btn1 = new Button("Inserir");
        Button btn2 = new Button("Atualizar");
        Button btn3 = new Button("Remover");
        Button btn4 = new Button("Procurar");
        
        btn1.setMaxWidth(Double.MAX_VALUE);
        btn2.setMaxWidth(Double.MAX_VALUE);
        btn3.setMaxWidth(Double.MAX_VALUE);
        btn4.setMaxWidth(Double.MAX_VALUE);
        
        btn1.setOnAction(e -> showView(DisciplinaInserir()));
        btn2.setOnAction(e -> showView(DisciplinaAtualizar()));
        btn3.setOnAction(e -> showView(DisciplinaRemover()));
        btn4.setOnAction(e -> showView(DisciplinaProcurar()));
        
        menu.getChildren().addAll(title, btn1, btn2, btn3, btn4);
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane DisciplinaInserir() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Dados da disciplina");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    	
        Label nomeLabel = new Label("Nome*");
        TextField nomeInput = new TextField();
        nomeInput.setPromptText("Nome");
        nomeInput.setMaxWidth(200);
        
        Label hrLabel = new Label("Horas*");
        TextField hrInput = new TextField();
        hrInput.setPromptText("Horas");
        hrInput.setMaxWidth(200);
        hrInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                hrInput.setText(oldValue);
            }
        });
        
        Label reqLabel = new Label("Requisitos");
        TextField reqInput = new TextField();
        reqInput.setPromptText("Requisitos");
        reqInput.setMaxWidth(200);
        reqInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                reqInput.setText(oldValue);
            }
        });
        
        Label eleLabel = new Label("Eletiva?");
        CheckBox eleInput = new CheckBox();
        
        Label idcuLabel = new Label("ID do curso");
        TextField idcuInput = new TextField();
        idcuInput.setPromptText("ID ou nome");
        idcuInput.setMaxWidth(200);

        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        
        confirm.setOnAction(e -> {
            try {
            	String nome = nomeInput.getText();
            	int hora = Integer.parseInt(hrInput.getText());
            	int req = reqInput.getText().trim().isEmpty() ? 0 : Integer.parseInt(reqInput.getText());
            	boolean ele = eleInput.isSelected();
            	int idcu;
            	try {
            		idcu = Integer.parseInt(idcuInput.getText());
            	} catch (NumberFormatException ex) {
            		try {
            			CursoDaoJDBC cursodao = new CursoDaoJDBC(connect);
            			if (cursodao.findBySubstring(idcuInput.getText()).size() != 1)
            				return;
            			else
            				idcu = cursodao.findBySubstring(idcuInput.getText()).get(0).getId();
            		} catch (Exception ex1) {
            			System.out.println("Erro: " + ex1.getMessage());
            			return;
            		}
            	}

                if (nome.trim().isEmpty()) {    
                    return;
                }
                
                DisciplinaDaoJDBC disciplinadao = new DisciplinaDaoJDBC(connect);
                Disciplina disciplina = new Disciplina();
                disciplina.setNome(nome);
                disciplina.setHoras(hora);
                //if (req != 0) disciplina.setRequisito(req);
                disciplina.setEletiva(ele);
                disciplina.setId_curso(idcu);
                disciplinadao.insert(disciplina);
                
                navigateBack();
            } catch (NumberFormatException ex) {
            	System.out.println("Não numero: " + ex.getMessage());
            } catch (Exception ex) {
            	System.out.println("Erro: " + ex.getMessage());
            }
        });
        
        menu.getChildren().addAll(title, nomeLabel, nomeInput, hrLabel, hrInput, reqLabel, reqInput, eleLabel, eleInput, idcuLabel, idcuInput, confirm);

        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane DisciplinaAtualizar() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label idLabel = new Label("Informe o ID");
        TextField idInput = new TextField();
        idInput.setPromptText("ID");
        idInput.setMaxWidth(200);
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(200);
        menu.getChildren().addAll(idLabel, idInput, confirm);
        
        confirm.setOnAction(e -> {
        	try {
        		DisciplinaDaoJDBC disciplinadao = new DisciplinaDaoJDBC(connect);
        		Disciplina disciplina = disciplinadao.findById(Integer.parseInt(idInput.getText()));
        		
		        Label title = new Label("Dados da disciplina");
		        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        		
                Label nomeLabel = new Label("Nome*");
                TextField nomeInput = new TextField();
                nomeInput.setText(disciplina.getNome());
                nomeInput.setMaxWidth(200);
                
                Label hrLabel = new Label("Horas*");
                TextField hrInput = new TextField();
                hrInput.setText(String.valueOf(disciplina.getHoras()));
                hrInput.setMaxWidth(200);
                hrInput.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        hrInput.setText(oldValue);
                    }
                });
                
                Label reqLabel = new Label("Requisitos");
                TextField reqInput = new TextField();
                reqInput.setText(String.valueOf(disciplina.getRequisito()));
                reqInput.setMaxWidth(200);
                reqInput.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        reqInput.setText(oldValue);
                    }
                });
                
                Label eleLabel = new Label("Eletiva?");
                CheckBox eleInput = new CheckBox();
                eleInput.setSelected(disciplina.isEletiva());
                
                Label idcuLabel = new Label("ID do curso");
                TextField idcuInput = new TextField();
                idcuInput.setText(String.valueOf(disciplina.getId_curso()));
                idcuInput.setMaxWidth(200);
                idcuInput.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        idcuInput.setText(oldValue);
                    }
                });        
                menu.getChildren().addAll(title, nomeLabel, nomeInput, hrLabel, hrInput, reqLabel, reqInput, eleLabel, eleInput, idcuLabel, idcuInput, confirm);

                Button confirm2 = new Button("Confirmar");
                confirm2.setMaxWidth(150);
                
                confirm2.setOnAction(ev -> {
                    try {
                    	String nome = nomeInput.getText();
                    	int hora = Integer.parseInt(hrInput.getText());
                    	int req = reqInput.getText().trim().isEmpty() ? 0 : Integer.parseInt(reqInput.getText());
                    	boolean ele = eleInput.isSelected();
                    	int idcu = Integer.parseInt(idcuInput.getText());

                        if (nome.trim().isEmpty()) {    
                            return;
                        }
                        
                        disciplina.setNome(nome);
                        disciplina.setHoras(hora);
                        if (req != 0) disciplina.setRequisito(req);
                        if (ele) disciplina.setEletiva(ele);
                        disciplina.setId_curso(idcu);
                        disciplinadao.update(disciplina);
                        
                        navigateBack();
                    } catch (NumberFormatException ex) {
                    	System.out.println("Não numero: " + ex.getMessage());
                    } catch (Exception ex) {
                    	System.out.println("Erro: " + ex.getMessage());
                    }
                });
        	} catch (Exception ex1) {
        		System.out.println("Erro: " + ex1.getMessage());
        	}
        });
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane DisciplinaRemover() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label matLabel = new Label("Informe o ID");
        TextField matInput = new TextField();
        matInput.setPromptText("ID");
        matInput.setMaxWidth(200);
        matInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                matInput.setText(oldValue);
            }
        });
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        menu.getChildren().addAll(matLabel, matInput, confirm);
        
        confirm.setOnAction(e -> {
        	try {
        		DisciplinaDaoJDBC disciplinadao = new DisciplinaDaoJDBC(connect);
        		disciplinadao.deleteById(Integer.parseInt(matInput.getText()));
        		
        		navigateBack();
        	} catch (Exception ex) {
        		System.out.print("Erro: " + ex.getMessage());
        	}
        });
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }
    
    private StackPane DisciplinaProcurar() {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        
        Label title = new Label("Procurar por nome");
        TextField nomeInput = new TextField();
        nomeInput.setPromptText("Nome");
        nomeInput.setMaxWidth(200);
        
        Button confirm = new Button("Confirmar");
        confirm.setMaxWidth(150);
        
        confirm.setOnAction(e -> {
        	try {
        		DisciplinaDaoJDBC disciplinadao = new DisciplinaDaoJDBC(connect);
	        	String nome = nomeInput.getText();
	        	List<Disciplina> disciplinaList;
	        	if (nome.trim().isEmpty())
	        		disciplinaList = disciplinadao.findAll();
	        	else
	        		disciplinaList = disciplinadao.findBySubstring(nomeInput.getText());
	        	
	            List<String> nameList = new ArrayList<String>();
	            for (Disciplina disciplina : disciplinaList) {
	            	nameList.add(disciplina.getNome() + " | ID: " + disciplina.getId() + " | Média: " + disciplinadao.mediaDisciplina(disciplina.getId()));
	            }
	            
	            ObservableList<String> names = FXCollections.observableArrayList(nameList);
	            ListView<String> nameListView = new ListView<String>(names);
	            nameListView.setPrefHeight(300);
	            nameListView.setPrefWidth(250);
	            title.setText("Disciplinas");
	            
	            menu.getChildren().clear();
	            menu.getChildren().addAll(title, nameListView);
        	} catch (Exception ex) {
        		System.out.println("Erro: " + ex.getMessage());
        	}
        });
        menu.getChildren().addAll(title, nomeInput, confirm);
        
        StackPane wrapper = new StackPane(menu);
        return wrapper;
    }

    public static void main(String[] args) {
        launch(args);
    }
}