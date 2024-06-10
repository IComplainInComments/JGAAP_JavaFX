package com.jgaap.GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.jgaap.backend.API;
import com.jgaap.util.Document;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI_AddAuthor {

    private TableView<Document> table;
    private ArrayList<Document> docs;
    private static API JAPI;
    private TextField auth;
    private Stage stage;

    public GUI_AddAuthor() {
        this.docs = new ArrayList<Document>();
        this.stage = new Stage();
        JAPI = API.getInstance();
        stage.setTitle("Add Author");
        stage.setScene(init_scene());

    }

    public GUI_AddAuthor(String author) {
        this.stage = new Stage();
        JAPI = API.getInstance();
        stage.setTitle("Add Author");
        stage.setScene(init_scene(author));

    }

    private Scene init_scene() {
        VBox box = new VBox();
        box.getChildren().addAll(init_authorBox(), init_authorTable(), init_bottomButtons());
        Scene scene = new Scene(box);
        return scene;

    }

    private Scene init_scene(String author) {
        VBox box = new VBox();
        box.getChildren().addAll(init_authorBox(author), init_authorTable(author), init_bottomButtons());
        Scene scene = new Scene(box);
        return scene;

    }

    private HBox init_authorBox() {
        HBox box = new HBox();
        Label auth = new Label("Author");
        this.auth = new TextField();
        this.auth.setOnAction(e -> {
            updateItemView();
        });
        box.getChildren().addAll(auth, this.auth);
        return box;
    }

    private VBox init_authorTable() {
        VBox box = new VBox();
        HBox butBox = new HBox();
        Button add = new Button("Add Document");
        Button rem = new Button("Remove Document");
        this.table = new TableView<Document>();
        TableColumn<Document, String> column1 = new TableColumn<Document, String>("Title");
        TableColumn<Document, String> column2 = new TableColumn<Document, String>("File Path");
        column1.setCellValueFactory(new PropertyValueFactory<Document, String>("Title"));
        column2.setCellValueFactory(new PropertyValueFactory<Document, String>("FilePath"));
        column1.prefWidthProperty().bind(table.widthProperty().divide(2));
        column2.prefWidthProperty().bind(table.widthProperty().divide(2));
        this.table.getColumns().add(column1);
        this.table.getColumns().add(column2);
        this.table.setItems(FXCollections.observableArrayList(JAPI.getDocumentsByAuthor(this.auth.getText().trim())));
        // ===============================================================================
        add.setOnAction(e -> {
            FileChooser FileChoser = new FileChooser();
            List<File> choice = FileChoser.showOpenMultipleDialog(this.stage);
            for (File file : choice) {
                try {
                    String filepath = file.getCanonicalPath();
                    String[] Split = filepath.split("[\\\\[\\/]]");
                    String Title = Split[Split.length - 1];
                    Document temp = new Document(filepath, this.auth.getText().trim(), Title);
                    // JAPI.addDocument(temp);
                    this.docs.add(temp);
                    this.table.setItems(FXCollections.observableArrayList(this.docs));
                    this.table.refresh();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        // ===============================================================================
        // ===============================================================================
        rem.setOnAction(e -> {
            TableViewSelectionModel<Document> selected = this.table.getSelectionModel();
            List<Document> docs = new ArrayList<Document>();
            docs.addAll(selected.getSelectedItems());
            for (Document doc : docs) {
                this.docs.remove(doc);
            }
            this.table.setItems(FXCollections.observableArrayList(this.docs));
            this.table.refresh();
        });
        // ===============================================================================
        butBox.getChildren().addAll(add, rem);
        box.getChildren().addAll(this.table, butBox);
        return box;

    }

    private HBox init_authorBox(String author) {
        HBox box = new HBox();
        Label auth = new Label("Author");
        this.auth = new TextField();
        this.auth.setText(author);
        box.getChildren().addAll(auth, this.auth);
        return box;
    }

    private VBox init_authorTable(String author) {
        VBox box = new VBox();
        HBox butBox = new HBox();
        Button add = new Button("Add Document");
        Button rem = new Button("Remove Document");
        this.table = new TableView<Document>();
        TableColumn<Document, String> column1 = new TableColumn<Document, String>("Title");
        TableColumn<Document, String> column2 = new TableColumn<Document, String>("File Path");
        column1.setCellValueFactory(new PropertyValueFactory<Document, String>("Title"));
        column2.setCellValueFactory(new PropertyValueFactory<Document, String>("FilePath"));
        column1.prefWidthProperty().bind(table.widthProperty().divide(2));
        column2.prefWidthProperty().bind(table.widthProperty().divide(2));
        this.table.getColumns().add(column1);
        this.table.getColumns().add(column2);
        this.table.setItems(FXCollections.observableArrayList(JAPI.getDocumentsByAuthor(author)));
        this.table.refresh();
        // ===============================================================================
        add.setOnAction(e -> {
            FileChooser FileChoser = new FileChooser();
            List<File> choice = FileChoser.showOpenMultipleDialog(this.stage);
            for (File file : choice) {
                try {
                    String filepath = file.getCanonicalPath();
                    String[] Split = filepath.split("[\\\\[\\/]]");
                    String Title = Split[Split.length - 1];
                    Document temp = new Document(filepath, this.auth.getText(), Title);
                    this.docs.add(temp);
                    this.table.setItems(FXCollections.observableArrayList(JAPI.getDocumentsByAuthor(author)));
                    this.table.refresh();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            this.table.setItems(FXCollections.observableArrayList(JAPI.getDocumentsByAuthor(author)));
            this.table.refresh();
        });
        // ===============================================================================
        // ===============================================================================
        rem.setOnAction(e -> {
            TableViewSelectionModel<Document> selected = this.table.getSelectionModel();
            List<Document> docs = new ArrayList<Document>();
            docs.addAll(selected.getSelectedItems());
            for (Document doc : docs) {
                this.docs.remove(doc);
            }
            this.table.setItems(FXCollections.observableArrayList(this.docs));
            this.table.refresh();
        });
        // ===============================================================================
        butBox.getChildren().addAll(add, rem);
        box.getChildren().addAll(this.table, butBox);
        return box;

    }

    private HBox init_bottomButtons() {
        HBox box = new HBox();
        Button ok = new Button("OK");
        Button can = new Button("Cancel");
        // ===============================================================================
        ok.setOnAction(e -> {
            if (!this.docs.isEmpty()) {
                for (Document temp : this.docs) {
                    JAPI.addDocument(temp);
                }
            }
            stage.close();
        });
        // ===============================================================================
        // ===============================================================================
        can.setOnAction(e -> {
            this.auth.setText("");
            stage.close();
        });
        // ===============================================================================
        box.getChildren().addAll(ok, can);

        return box;
    }

    public String getTextFieldString() {
        return this.auth.getText();
    }

    private void updateItemView() {
        this.table.setItems(FXCollections.observableArrayList(JAPI.getDocumentsByAuthor(this.auth.getText().trim())));
        this.table.refresh();
    }

    public Stage getStage() {
        return this.stage;
    }

}
