import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GUI_DocTab {
    private VBox box;
    public GUI_DocTab(){
        this.box = new VBox();
        this.box.setSpacing(5);
        build_tab();
    }

    private void build_tab(){
        this.box.getChildren().add(init_LangSelection());
        this.box.getChildren().add(init_UnknownAuth());
        this.box.getChildren().add(init_UnknownAuthButtons());
        this.box.getChildren().add(init_KnownAuth());
        this.box.getChildren().add(init_KnownAuthButtons());
        this.box.getChildren().add(init_bottomButtons());
    }

     private VBox init_LangSelection(){
        VBox box = new VBox();
        HBox hbox;


        Label lang = new Label("Language");
        lang.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        Button notes = new Button("Notes");
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        hbox = new HBox(lang, region1, notes);
        

        ComboBox<String> langSelect = init_langSelectBox();

        box.getChildren().add(hbox);
        box.getChildren().add(langSelect);
        box.setSpacing(3);

        return box;
     }

     private VBox init_UnknownAuth(){
        VBox box = new VBox();
        TableView<Object> table = init_authorTable();
        Label unAuth =  new Label("Uknown Authors");

        unAuth.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        table.prefHeightProperty().bind(box.heightProperty());
        table.prefWidthProperty().bind(box.widthProperty());

        box.getChildren().add(unAuth);
        box.getChildren().add(table);

        return box;
     }
     private VBox init_KnownAuth(){
        VBox box = new VBox();
        Label knAuth = new Label("Known Authors");
        knAuth.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        TreeView<String> tree = init_TreeView();
        box.getChildren().add(knAuth);
        box.getChildren().add(tree);
        return box;
     }
     private HBox init_UnknownAuthButtons(){
        HBox box = new HBox();
        Button addDoc = new Button("Add Document");
        Button remDoc = new Button("Remove Document");
        box.getChildren().add(addDoc);
        box.getChildren().add(remDoc);
        return box;
     }
     private HBox init_KnownAuthButtons(){
        HBox box = new HBox();
        Button addAuth = new Button("Add Author");
        Button editAuth = new Button("Edit Author");
        Button remAuth = new Button("Remove Author");
        box.getChildren().add(addAuth);
        box.getChildren().add(editAuth);
        box.getChildren().add(remAuth);
        return box;
     }
     private HBox init_bottomButtons(){
        HBox box = new HBox();
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
     private TableView<Object> init_authorTable() {
        TableView<Object> table = new TableView<Object>();
        TableColumn<Object, String> column1 = new TableColumn<Object, String>("Title");
        TableColumn<Object, String> column2 = new TableColumn<Object, String>("File Path");
        //column1.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        //column2.setCellValueFactory(new PropertyValueFactory<Task, String>("startPeriod"));
        column1.prefWidthProperty().bind(table.widthProperty().divide(2));
        column2.prefWidthProperty().bind(table.widthProperty().divide(2));
        table.getColumns().add(column1);
        table.getColumns().add(column2);
        return table;
     }

     private TreeView<String> init_TreeView(){
        TreeItem<String> rootItem = new TreeItem<String> ("Authors");
        TreeView<String> tree = new TreeView<String>(rootItem);
        return tree;
     }
     private ComboBox<String> init_langSelectBox(){
        ComboBox<String> comboBox;
        ObservableList<String> options = 
            FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3"
            );
        comboBox = new ComboBox<String>(options);
        return comboBox;
     }

    public VBox getPane(){
        return this.box;
    }
}
