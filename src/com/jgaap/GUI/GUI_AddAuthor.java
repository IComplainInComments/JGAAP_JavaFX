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
    private ArrayList<Document> doc;
    private static API JGAAP_API;
    private ArrayList<Document> remDocs;
    private TextField auth;
    private Stage stage;
    private static GUI_DataStorage data;

    public GUI_AddAuthor(GUI_DataStorage obj) {
        this.doc = new ArrayList<Document>();
        this.remDocs = new ArrayList<Document>();
        this.stage = new Stage();
        data = obj;
        stage.setTitle("Add Author");
        stage.setScene(init_scene());
        stage.show();

    }

    private Scene init_scene() {
        VBox box = new VBox();
        box.getChildren().addAll(init_authorBox(), init_authorTable(), init_bottomButtons());
        Scene scene = new Scene(box);
        return scene;

    }

    private HBox init_authorBox() {
        HBox box = new HBox();
        Label auth = new Label("Author");
        this.auth = new TextField();
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
        column1.setCellValueFactory(new PropertyValueFactory<Document, String>("author"));
        column2.setCellValueFactory(new PropertyValueFactory<Document, String>("filepath"));
        column1.prefWidthProperty().bind(table.widthProperty().divide(2));
        column2.prefWidthProperty().bind(table.widthProperty().divide(2));
        this.table.getColumns().add(column1);
        this.table.getColumns().add(column2);
        this.table.setItems(FXCollections.observableArrayList(this.doc));

        add.setOnAction(e -> {
            FileChooser FileChoser = new FileChooser();
            List<File> choice = FileChoser.showOpenMultipleDialog(this.stage);
            for (File file : choice) {
                try {
                    String filepath = file.getCanonicalPath();
                    String[] Split = filepath.split("[\\\\[\\/]]");
                    String Title = Split[Split.length - 1];
                    Document temp = new Document(filepath, Title);
                    doc.add(temp);
                    // Filepath = filepath;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        rem.setOnAction(e -> {
            TableViewSelectionModel<Document> selected = this.table.getSelectionModel();
            List<Document> docs = new ArrayList<Document>();
            docs.addAll(selected.getSelectedItems());
            this.remDocs.addAll(docs);
            this.table.getItems().removeAll(docs);
        });
        butBox.getChildren().addAll(add, rem);
        box.getChildren().addAll(this.table, butBox);
        return box;

    }

    private HBox init_bottomButtons() {
        HBox box = new HBox();
        Button ok = new Button("OK");
        Button can = new Button("Cancel");
        ok.setOnAction(e -> {
            ObservableList<Document> temp = this.table.getItems();
            for (int i = 0; i < temp.size(); i++) {
                JGAAP_API.addDocument(temp.get(i));
            }
            data.addData("author", this.auth.getText().trim());
            data.addData("knownDocuments", temp);
            for (int i = 0; i < temp.size(); i++) {
                JGAAP_API.addDocument(this.remDocs.get(i));
                data.removeData("knownDocuments", this.remDocs.get(i));
            }
        });
        can.setOnAction(e -> {
            this.doc.clear();
            this.remDocs.clear();
            this.auth.setText("");
        });

        box.getChildren().addAll(ok, can);

        return box;
    }

}
