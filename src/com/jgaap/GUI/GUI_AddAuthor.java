package com.jgaap.GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jgaap.backend.API;
import com.jgaap.util.Document;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * GUI_AddAuthor class is for building a Window to add an Author to the program
 */
public class GUI_AddAuthor {

    private TableView<Document> table;
    private ArrayList<Document> docs;
    private static Logger logger;
    private static API JAPI;
    private TextField auth;
    private Stage stage;

    /**
     * Initial constructor for the class
     */
    public GUI_AddAuthor() {
        logger = Logger.getLogger(GUI_AddAuthor.class);
        this.docs = new ArrayList<Document>();
        this.stage = new Stage();
        JAPI = API.getInstance();
        stage.setTitle("Add Author");
        stage.setScene(init_scene());

    }
    /**
     * Constructor for the class to edit an author
     * @param author String
     */
    public GUI_AddAuthor(String author) {
        logger = Logger.getLogger(GUI_AddAuthor.class);
        this.docs = new ArrayList<Document>();
        this.stage = new Stage();
        JAPI = API.getInstance();
        stage.setTitle("Add Author");
        stage.setScene(init_scene(author));

    }
    /**
     * Method to build the Scene for the Stage
     * @return Scene
     */
    private Scene init_scene() {
        VBox box = new VBox();
        box.getChildren().addAll(init_authorBox(), init_authorTable(), init_bottomButtons());
        box.setPadding(new Insets(5));
        Scene scene = new Scene(box,500,300);
        return scene;

    }
    /**
     * Method to initialize the Author name text box
     * @return HBox
     */
    private HBox init_authorBox() {
        HBox box = new HBox(5);
        Label auth = new Label("Author");
        this.auth = new TextField();
        this.auth.setOnAction(e -> {
            updateItemView();
            e.consume();
        });
        box.getChildren().addAll(auth, this.auth);
        return box;
    }
    /**
     * Method to initialize the Documents to be added to the Author
     * @return VBox
     */
    private VBox init_authorTable() {
        VBox box = new VBox(5);
        HBox butBox = new HBox(5);
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
        this.table.prefHeightProperty().bind(this.stage.heightProperty());
        this.table.prefWidthProperty().bind(this.stage.widthProperty());
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
                    logger.error(ex.getCause(), ex);
                    ex.printStackTrace();
                }
            }
            e.consume();
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
            e.consume();
        });
        // ===============================================================================
        butBox.getChildren().addAll(add, rem);
        box.getChildren().addAll(this.table, butBox);
        return box;

    }
        /**
     * Method to build the Scene for the Stage for an author edit
     * @return Scene
     */
    private Scene init_scene(String author) {
        VBox box = new VBox(5);
        box.getChildren().addAll(init_authorBox(author), init_authorTable(author), init_bottomButtons());
        box.setPadding(new Insets(5));
        Scene scene = new Scene(box,500,300);
        return scene;

    }
    /**
     * Method to initialize the Author name text box for an Edit
     * @return HBox
     */
    private HBox init_authorBox(String author) {
        HBox box = new HBox(5);
        Label auth = new Label("Author");
        this.auth = new TextField();
        this.auth.setText(author);
        box.getChildren().addAll(auth, this.auth);
        return box;
    }
    /**
     * Method to initialize the Documents to be added to the Author for an Edit
     * @return VBox
     */
    private VBox init_authorTable(String author) {
        VBox box = new VBox(5);
        HBox butBox = new HBox(5);
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
        this.table.prefHeightProperty().bind(this.stage.heightProperty());
        this.table.prefWidthProperty().bind(this.stage.widthProperty());
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
                    Document temp = new Document(filepath, this.auth.getText().trim(), Title);
                    this.docs.add(temp);
                    this.table.setItems(FXCollections.observableArrayList(JAPI.getDocumentsByAuthor(author)));
                    this.table.refresh();
                } catch (Exception ex) {
                    logger.error(ex.getCause(), ex);
                    ex.printStackTrace();
                }
            }
            this.table.setItems(FXCollections.observableArrayList(JAPI.getDocumentsByAuthor(author)));
            this.table.refresh();
            e.consume();
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
            e.consume();
        });
        // ===============================================================================
        butBox.getChildren().addAll(add, rem);
        box.getChildren().addAll(this.table, butBox);
        return box;

    }
    /**
     * Build the bottom buttons on the Panel
     * @return HBox
     */
    private HBox init_bottomButtons() {
        HBox box = new HBox(5);
        Button ok = new Button("OK");
        Button can = new Button("Cancel");
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        // ===============================================================================
        ok.setOnAction(e -> {
            if (!this.docs.isEmpty()) {
                for (Document temp : this.docs) {
                    JAPI.addDocument(temp);
                }
            }
            stage.close();
            e.consume();
        });
        // ===============================================================================
        // ===============================================================================
        can.setOnAction(e -> {
            this.auth.setText("");
            this.docs.clear();
            stage.close();
            e.consume();
        });
        // ===============================================================================
        box.getChildren().addAll(region1,ok, can);

        return box;
    }
    /**
     * Update the Document List View box
     */
    private void updateItemView() {
        this.table.setItems(FXCollections.observableArrayList(JAPI.getDocumentsByAuthor(this.auth.getText().trim())));
        this.table.refresh();
    }
    /**
     * Get the constructed stage (Window)
     * @return Stage
     */
    public Stage getStage() {
        return this.stage;
    }

}
