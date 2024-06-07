package com.jgaap.GUI;
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
/**
 * Canonicizer Tab Class.
 * This Class creates the scene for the Canonicizer Tab and it's GUI elements.
 */
public class GUI_CanTab {

    private VBox box;
    private static GUI_NotesWindow noteBox;
    /**
     * Constructor for the class.
     */
    public GUI_CanTab(){
        box = new VBox();
        noteBox = new GUI_NotesWindow();
        build_pane();
    }
    /**
     * Builds the pane row by row.
     */
    private void build_pane(){
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(init_bottomButtons());
    }
    /**
     * Method for building the 'Top Row' of GUI elements.
     * @return HBox
     */
    private HBox init_rowOne(){
        HBox box = new HBox(5);
        HBox selNotes = new HBox();
        VBox canBox = new VBox();
        VBox selBox = new VBox();
        Label can = new Label("Canonicizers");
        Label sel = new Label("Selected");
        Button notes = noteBox.getButton();
        Region region1 = new Region();

        HBox.setHgrow(region1, Priority.ALWAYS);
        //HBox.setHgrow(region2, Priority.ALWAYS);
        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        sel.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        
        selNotes.getChildren().addAll(sel, region1, notes);
        canBox.getChildren().addAll(can, init_listBoxCan());
        selBox.getChildren().addAll(selNotes, init_listBoxSel());

        box.getChildren().addAll(canBox,init_rowTwoButtons(),selBox);
        return box;
    }
    /**
     * Method for building the 'Second Row' of GUI elements.
     * @return HBox
     */
    private VBox init_rowTwo(){
        VBox box = new VBox(5);
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
      * Method for generating the List Box of Canocinizers.
      * @return ListView<String>
      */
    private ListView<String> init_listBoxCan(){
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
            "Single", "Double", "Suite", "Family App");

        list.setItems(items);
        list.prefHeightProperty().bind(this.box.heightProperty());
        list.prefWidthProperty().bind(this.box.widthProperty());

        return list;
    }
    /**
     * Method for generating List Box of selected Canocinizers.
     * @return ListView<String>
     */
    private ListView<String> init_listBoxSel(){
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
            "Single", "Double", "Suite", "Family App");

        list.setItems(items);
        list.prefHeightProperty().bind(this.box.heightProperty());
        list.prefWidthProperty().bind(this.box.widthProperty());

        return list;
    }
    /**
     * Method for generating a VBox containing the buttons for de/selecting items for the Selection Box.
     * @return VBox
     */
    private VBox init_rowTwoButtons(){
        VBox box = new VBox(5);
        Button left = new Button("->");
        Button right = new Button("<-");
        Button clear = new Button("Clear");
        Region region1 = new Region();
        Region region2 = new Region();
        VBox.setVgrow(region1, Priority.ALWAYS);
        VBox.setVgrow(region2, Priority.ALWAYS);

        box.getChildren().addAll(region1,init_rowTwoSelectionDropDown(),left, right, clear, region2);
        box.setAlignment(Pos.BASELINE_CENTER);

        return box;
    }
    /**
     * Method for creation of the document format selection box.
     * @return ComboBox<String>
     */
    private ComboBox<String> init_rowTwoSelectionDropDown(){
        ComboBox<String> comboBox;
        ObservableList<String> options = 
            FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3"
            );

        comboBox = new ComboBox<String>(options);
        comboBox.setMinSize(100, 25);

        return comboBox;
     }
     /**
      * Returns the built Pane.
      * @return VBox
      */
     public VBox getPane(){
        return this.box;
    }
    
}
