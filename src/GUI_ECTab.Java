import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GUI_ECTab {

    private VBox box;

    public GUI_ECTab(){
        box = new VBox();
        build_pane();
    }

    private void build_pane(){
        this.box.getChildren().add(init_rowOne());
        //this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(init_rowThree());
        this.box.getChildren().add(init_bottomButtons());
    }

    private HBox init_rowOne(){
        ListView<String> listLeft = init_ListBoxLeft();
        ListView<String> listRight = init_ListBoxRight();
        HBox box = new HBox();
        HBox paraNotes = new HBox();
        HBox canButBox = new HBox();
        VBox canBox = new VBox();
        VBox selBox = new VBox();
        VBox paraBoxParent = new VBox();
        VBox paraBoxChild = new VBox();
        Label can = new Label("Event Culling");
        Label sel = new Label("Selected");
        Label para = new Label("Parameters");
        Button notes = new Button("Notes");
        Region region1 = new Region();

        HBox.setHgrow(region1, Priority.ALWAYS);
        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        sel.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        para.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        notes.prefHeightProperty().set(10);
        notes.prefWidthProperty().set(51);

        canButBox.getChildren().add(listLeft);
        canButBox.getChildren().add(init_rowTwoButtons());

        canBox.getChildren().add(can);
        canBox.getChildren().add(canButBox);

        selBox.getChildren().add(sel);
        selBox.getChildren().add(listRight);

        paraBoxChild.setStyle("-fx-border-color: black");
        //paraBoxChild.prefHeightProperty().bind(box.heightProperty());
        //paraBoxChild.prefWidthProperty().bind(box.widthProperty());
        paraNotes.getChildren().add(para);
        paraNotes.getChildren().add(region1);
        paraNotes.getChildren().add(notes);
        paraBoxParent.getChildren().add(paraNotes);
        paraBoxParent.getChildren().add(paraBoxChild);

        box.getChildren().add(canBox);
        box.getChildren().add(selBox);
        box.getChildren().add(paraBoxParent);
        //box.setPadding(Insets.EMPTY);

        return box;
    }
    private HBox init_rowTwo(){
        HBox box = new HBox();
        VBox vboxModal = new VBox();
        HBox optionPane = new HBox();
        ListView<String> listLeft = init_ListBoxLeft();
        ListView<String> listRight = init_ListBoxRight();
        ComboBox<String> comboBox = init_rowTwoSelectionDropDown();

        optionPane.setStyle("-fx-border-color: black");
        optionPane.prefHeightProperty().bind(listRight.heightProperty());
        optionPane.prefWidthProperty().bind(listRight.widthProperty());

        vboxModal.getChildren().add(comboBox);
        vboxModal.getChildren().add(init_rowTwoButtons());
        box.getChildren().add(listLeft);
        box.getChildren().add(vboxModal);
        box.getChildren().add(listRight);
        box.getChildren().add(optionPane);

        return box;
    }
    private VBox init_rowThree(){
        VBox box = new VBox();
        Label can = new Label("Event Culling Description");
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
        VBox box = new VBox();
        Button left = new Button("->");
        Button right = new Button("<-");
        Button clear = new Button("Clear");
        Button all = new Button("All");

        box.getChildren().add(left);
        box.getChildren().add(right);
        box.getChildren().add(all);
        box.getChildren().add(clear);
        box.setAlignment(Pos.TOP_CENTER);

        return box;
    }
    private ComboBox<String> init_rowTwoSelectionDropDown(){
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
