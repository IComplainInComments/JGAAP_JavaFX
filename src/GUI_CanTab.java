import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class GUI_CanTab {

    private VBox box;
    public GUI_CanTab(){
        box = new VBox();
        build_pane();
    }

    private void build_pane(){
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(init_rowThree());
        this.box.getChildren().add(init_bottomButtons());
    }

    private HBox init_rowOne(){
        HBox box = new HBox();
        Label can = new Label("Canonicizers");
        Label sel = new Label("Selected");
        Button notes = new Button("Notes");
        Region region1 = new Region();
        Region region2 = new Region();

        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);
        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        sel.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        box.getChildren().add(can);
        box.getChildren().add(region2);
        box.getChildren().add(sel);
        box.getChildren().add(region1);
        box.getChildren().add(notes);

        return box;
    }
    private HBox init_rowTwo(){
        HBox box = new HBox();
        VBox vboxModal = new VBox();
        ListView<String> listLeft = init_ListBoxLeft();
        ListView<String> listRight = init_ListBoxRight();
        ComboBox<String> comboBox = init_rowTwoSelectionDropDown();

        box.getChildren().add(listLeft);
        vboxModal.getChildren().add(comboBox);
        vboxModal.getChildren().add(init_rowTwoButtons());
        box.getChildren().add(vboxModal);
        box.getChildren().add(listRight);

        return box;
    }
    private VBox init_rowThree(){
        VBox box = new VBox();
        Label can = new Label("Canonicizer Description");
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

        box.getChildren().add(left);
        box.getChildren().add(right);
        box.getChildren().add(clear);
        box.setAlignment(Pos.BASELINE_CENTER);
        box.setSpacing(3);

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
