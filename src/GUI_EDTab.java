import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GUI_EDTab {

    private VBox box;
    private static GUI_NotesWindow noteBox;
    public GUI_EDTab(){
        box = new VBox();
        noteBox = new GUI_NotesWindow();
        build_pane();
    }

    private void build_pane(){
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(init_bottomButtons());
    }

    private HBox init_rowOne(){
        ListView<String> listLeft = init_ListBoxLeft();
        ListView<String> listRight = init_ListBoxRight();
        Label can = new Label("Event Drivers");
        Label sel = new Label("Selected");
        Label para = new Label("Parameters");
        Button notes = noteBox.getButton();
        HBox box = new HBox(5);
        HBox notesBox = new HBox();
        VBox edBox = new VBox();
        VBox selBox = new VBox();
        VBox paraBox = new VBox();
        VBox paraBoxChild = new VBox();
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        sel.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        para.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));


        paraBoxChild.setStyle("-fx-border-color: black");
        paraBoxChild.prefHeightProperty().bind(this.box.heightProperty());
        paraBoxChild.prefWidthProperty().bind(this.box.widthProperty());

        notesBox.getChildren().addAll(para,region1,notes);

        edBox.getChildren().addAll(can, listLeft);
        selBox.getChildren().addAll(sel,listRight);
        paraBox.getChildren().addAll(notesBox, paraBoxChild);

        box.getChildren().addAll(edBox, init_rowTwoButtons(), selBox, paraBox);

        return box;
    }
    private VBox init_rowTwo(){
        VBox box = new VBox(5);
        Label can = new Label("Event Driver Description");
        TextArea area = new TextArea();

        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        area.setText("Enter your description here");
        area.prefHeightProperty().bind(this.box.heightProperty());
        area.prefWidthProperty().bind(this.box.widthProperty());
        box.getChildren().add(can);
        box.getChildren().add(area);

        return box;

    }
    private HBox init_bottomButtons(){
        HBox box = new HBox(5);
        Region region1 = new Region();
        Button finish = new Button("Finish & Review");
        Button next = new Button("Next");
        HBox.setHgrow(region1, Priority.ALWAYS);

        box.getChildren().add(region1);
        box.getChildren().add(finish);
        box.getChildren().add(next);
        box.setSpacing(10);

        return box;
     }
    private ListView<String> init_ListBoxLeft(){
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
            "Single", "Double", "Suite", "Family App");

        list.setItems(items);
        list.prefHeightProperty().bind(this.box.heightProperty());
        list.prefWidthProperty().bind(this.box.widthProperty());

        return list;
    }
    private ListView<String> init_ListBoxRight(){
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
            "Single", "Double", "Suite", "Family App");

        list.setItems(items);
        list.prefHeightProperty().bind(this.box.heightProperty());
        list.prefWidthProperty().bind(this.box.widthProperty());

        return list;
    }
    private VBox init_rowTwoButtons(){
        VBox box = new VBox(5);
        Region region1 = new Region();
        Region region2 = new Region();
        Button left = new Button("->");
        Button right = new Button("<-");
        Button clear = new Button("Clear");
        Button all = new Button("All");

        box.setMinSize(50, 0);

        VBox.setVgrow(region1, Priority.ALWAYS);
        VBox.setVgrow(region2, Priority.ALWAYS);

        box.getChildren().add(region1);
        box.getChildren().add(left);
        box.getChildren().add(right);
        box.getChildren().add(all);
        box.getChildren().add(clear);
        box.getChildren().add(region2);
        box.setAlignment(Pos.TOP_CENTER);

        return box;
    }
     public VBox getPane(){
        return this.box;
    }
    
}
