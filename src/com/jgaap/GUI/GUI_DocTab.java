package com.jgaap.GUI;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jgaap.backend.API;
import com.jgaap.backend.Languages;
import com.jgaap.generics.Language;
import com.jgaap.util.Document;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
/**
 * Document Tab Class.
 * This Class creates the scene for the Document Tab and it's GUI elements.
 */
public class GUI_DocTab {
    private VBox box;
    private static GUI_NotesWindow noteBox;
    static Logger logger;
      private static Stage mainStageRef;
    private static API JGAAP_API;
    private ArrayList<TreeItem<String>> knownAuthors;
    private ArrayList<Document> unknownAuthors;
    private TableView<Document> table;
    /**
     * Constructor for the class.
     */
    public GUI_DocTab(Stage stage){
      this.knownAuthors = new ArrayList<TreeItem<String>>();
      this.unknownAuthors = new ArrayList<Document>();
      logger = Logger.getLogger(GUI_DocTab.class);
      mainStageRef = stage;
        this.box = new VBox();
        noteBox = new GUI_NotesWindow();
        build_tab();
    }
    /**
     * Builds the window row by row.
     */
    private void build_tab(){
        this.box.getChildren().add(init_LangSelection());
        this.box.getChildren().add(init_UnknownAuth());
        this.box.getChildren().add(init_UnknownAuthButtons());
        this.box.getChildren().add(init_KnownAuth());
        this.box.getChildren().add(init_KnownAuthButtons());
        this.box.getChildren().add(init_bottomButtons());
    }
    /*
     * Method for building the Language Selection row.
     */
     private VBox init_LangSelection(){
        VBox box = new VBox(5);
        HBox hbox;


        Label lang = new Label("Language");
        lang.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        Button notes = noteBox.getButton();
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        hbox = new HBox(lang, region1, notes);
        

        ComboBox<String> langSelect = init_langSelectBox();

        box.getChildren().add(hbox);
        box.getChildren().add(langSelect);

        return box;
     }
     /**
      * Method for building the Unknown Author Row.
      */
     private VBox init_UnknownAuth(){
        VBox box = new VBox(5);
        init_authorTable();
        Label unAuth =  new Label("Uknown Authors");

        unAuth.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        table.prefHeightProperty().bind(this.box.heightProperty());
        table.prefWidthProperty().bind(this.box.widthProperty());

        box.getChildren().add(unAuth);
        box.getChildren().add(this.table);

        return box;
     }
     /**
      * Method for building the Known Author Row.
      * @return VBox
      */
     private VBox init_KnownAuth(){
        VBox box = new VBox(5);
        Label knAuth = new Label("Known Authors");
        knAuth.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        TreeView<String> tree = init_TreeView();
        tree.prefHeightProperty().bind(this.box.heightProperty());
        tree.prefWidthProperty().bind(this.box.widthProperty());
        box.getChildren().add(knAuth);
        box.getChildren().add(tree);
        return box;
     }
     /**
      * Method for Building the Unknown Author Row Buttons.
      * @return HBox
      */
     private HBox init_UnknownAuthButtons(){
        HBox box = new HBox(5);
        Button addDoc = new Button("Add Document");
        Button remDoc = new Button("Remove Document");
        addDoc.setOnAction(e -> {
         String filepath = "";
         FileChooser FileChoser = new FileChooser();
         List<File> choice = FileChoser.showOpenMultipleDialog(mainStageRef);
            for (File file : choice) {
               try {
                  JGAAP_API.addDocument(file.getCanonicalPath(), "", "");
                  filepath = file.getCanonicalPath();
               } catch (Exception ex) {
                  logger.error("Error adding document(s)", ex);
                if(filepath != null){
                    ex.printStackTrace();
                    Alert error = new Alert(AlertType.ERROR, ex.getMessage());
                    error.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> error.close());
                }
               }
               //UpdateUnknownDocumentsTable();
            }
        });
        remDoc.setOnAction(e -> {
         TableViewSelectionModel<Document> selected = this.table.getSelectionModel();
         Document[] docs = new Document[selected.getSelectedItems().size()];
         this.table.getItems().removeAll(docs);
        });
        box.getChildren().add(addDoc);
        box.getChildren().add(remDoc);
        return box;
     }
     /**
      * Method for building the Known Author Buttons.
      * @return HBox
      */
     private HBox init_KnownAuthButtons(){
        HBox box = new HBox(5);
        Button addAuth = new Button("Add Author");
        Button editAuth = new Button("Edit Author");
        Button remAuth = new Button("Remove Author");
        box.getChildren().add(addAuth);
        box.getChildren().add(editAuth);
        box.getChildren().add(remAuth);
        return box;
     }
   /**
     * Method for building the 'Bottom Buttons' of GUI elements.
     * @return HBox
     */
     private HBox init_bottomButtons(){
        HBox box = new HBox(5);
        Button finish = new Button("Finish & Review");
        Button next = new Button("Next");
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        box.getChildren().add(region1);
        box.getChildren().add(finish);
        box.getChildren().add(next);
        box.setSpacing(10);
        return box;
     }
     /**
      * Method for building the Author Selection Table.
      * @return TableView<Object>
      */
     private void init_authorTable() {
       this.table = new TableView<Document>();
        TableColumn<Document, String> column1 = new TableColumn<Document, String> ("Title");
        TableColumn<Document, String> column2 = new TableColumn<Document, String>("File Path");
         column1.setCellValueFactory(new PropertyValueFactory<Document, String>("author"));
        column2.setCellValueFactory(new PropertyValueFactory<Document, String>("filepath"));
        column1.prefWidthProperty().bind(table.widthProperty().divide(2));
        column2.prefWidthProperty().bind(table.widthProperty().divide(2));
        this.table.getColumns().add(column1);
        this.table.getColumns().add(column2);
        this.table.setItems(FXCollections.observableArrayList(this.unknownAuthors));
     }
     /**
      * Method for building the Known Authors Table.
      * @return TableView <String>
      */
     private TreeView<String> init_TreeView(){
        TreeItem<String> rootItem = new TreeItem<String> ("Authors");
        rootItem.getChildren().addAll(this.knownAuthors);
        TreeView<String> tree = new TreeView<String>(rootItem);
        return tree;
     }
     /**
      * Method for building the Language Selection Combo Box.
      * @return ComboBox<String>
      */
     private ComboBox<String> init_langSelectBox(){
         ArrayList<String> LanguagesMasterList = new ArrayList<String>();
         for (Language language : Languages.getLanguages()) {
			   if (language.showInGUI())
				   LanguagesMasterList.add(language.displayName());
		   }
         ComboBox<String> comboBox;
         ObservableList<String> options = 
            FXCollections.observableArrayList(
               LanguagesMasterList
            );
         comboBox = new ComboBox<String>(options);
         return comboBox;
     }
     /**
      * Getter for getting the built Pane.
      * @return VBox
      */
    public VBox getPane(){
        return this.box;
    }
    public void updateKnownAuthors(String author){
      TreeItem<String> item = new TreeItem<String>(author);
      this.knownAuthors.add(item);
    }
    public void updateUnknownAuthors(Document author){
      this.unknownAuthors.add(author);
    }
}
