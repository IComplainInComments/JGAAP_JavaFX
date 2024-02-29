import java.util.List;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GUI_DocTab {
    private GridPane grid;
    public GUI_DocTab(){
        this.grid = new GridPane();
        build_tab();
    }

    private void build_tab(){
        Label lang = new Label("Language");
        lang.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        Label unAuth =  new Label("Uknown Authors");
        unAuth.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        Label knAuth = new Label("Known Authors");
        knAuth.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        TableView<Object> table = init_authorTable();
        List<Button> buttons = init_Buttons();

        grid.setAlignment(Pos.BASELINE_RIGHT);
        this.grid.setHgap(1);
        this.grid.setVgap(1);
        this.grid.setPadding(new Insets(5, 5, 5, 5));

        table.prefHeightProperty().bind(grid.heightProperty());
        table.prefWidthProperty().bind(grid.widthProperty());

        this.grid.add(lang, 0, 0);
        ComboBox<String> langSelect = init_langSelectBox();
        this.grid.add(langSelect, 0, 1);
        this.grid.add(unAuth, 0, 2);
        this.grid.add(table, 0, 3);
        // Document Buttons ---
        HBox buttons1 = new HBox(3, buttons.get(0), buttons.get(1));
        this.grid.add(buttons1,0,4);
        //---
        this.grid.add(knAuth, 0, 5);
        //Author Buttons ---
        HBox buttons2 = new HBox(4, buttons.get(2), buttons.get(3), buttons.get(4));
        this.grid.add(buttons2, 0, 6);
        //---
        HBox buttons3 = new HBox(3, buttons.get(5), buttons.get(6));
        this.grid.add(buttons3, 0, 7);

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

     private List<Button> init_Buttons(){
        Vector<Button> buttons = new Vector<Button>();

        Button addDoc = new Button("Add Document");
        Button remDoc = new Button("Remove Document");
        Button addAuth = new Button("Add Author");
        Button editAuth = new Button("Edit Author");
        Button remAuth = new Button("Remove Author");
        Button finish = new Button("Finish & Review");
        Button next = new Button("Next");

        buttons.add(addDoc);
        buttons.add(remDoc);
        buttons.add(addAuth);
        buttons.add(editAuth);
        buttons.add(remAuth);
        buttons.add(finish);
        buttons.add(next);

        return buttons;

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

    public GridPane getGridPane(){
        return this.grid;
    }
}
