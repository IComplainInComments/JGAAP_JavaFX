package com.jgaap.GUI;
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
/**
 * Analysis Tab Class.
 * This Class creates the scene for the Analysis Tab and it's GUI elements.
 */
public class GUI_AnalysisTab {
    private VBox box;
    private static GUI_NotesWindow notesBox;

    /**
     * Constructor for the class.
     */
    public GUI_AnalysisTab(){
        this.box = new VBox();
        notesBox = new GUI_NotesWindow();
        build_pane();
    }
    /**
     * Builds the Pane row by row.
     */
    private void build_pane(){
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(init_bottomButtons());
    }
    /**
     * Creates the 'Top Row' of GUI elements in the window.
     * @return HBox
     */
    private HBox init_rowOne(){
        HBox box = new HBox(5);
        HBox noteBox = new HBox();
        VBox meth = new VBox();
        VBox sel = new VBox();
        VBox param = new VBox();
        VBox paraBoxChildOne = new VBox();
        VBox paraBoxChildTwo = new VBox();
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        Button notes = notesBox.getButton();
        Label an = new Label("Analysis Method");
        Label df = new Label("Distance Function");
        Label se = new Label("Selected");
        Label am = new Label("AM Parameters");
        Label dfp = new Label("DF Parameters");

        an.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        df.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        se.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        am.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        dfp.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        paraBoxChildOne.setStyle("-fx-border-color: black");
        paraBoxChildTwo.setStyle("-fx-border-color: black");

        paraBoxChildOne.prefHeightProperty().bind(this.box.heightProperty());
        paraBoxChildOne.prefWidthProperty().bind(this.box.widthProperty());
        paraBoxChildTwo.prefHeightProperty().bind(this.box.heightProperty());
        paraBoxChildTwo.prefWidthProperty().bind(this.box.widthProperty());

        noteBox.getChildren().addAll(am, region1, notes);
        meth.getChildren().addAll(an, init_AnalysisMethodBox(),df, init_DistanceFunctionBox());
        sel.getChildren().addAll(se, init_SelectedBox());
        param.getChildren().addAll(noteBox,paraBoxChildOne,dfp,paraBoxChildTwo);

        box.getChildren().addAll(meth,init_rowOneButtons(),sel,param);


        return box;
    }
    /**
     * Creates the 'Second Row' of GUI elements in the window.
     * @return HBox
     */
    private HBox init_rowTwo(){
        Label an = new Label("Analysis Method Description");
        Label df = new Label("Distance Function Description");
        TextArea anArea = new TextArea();
        TextArea dfArea = new TextArea();
        HBox box = new HBox(5);
        VBox amd = new VBox();
        VBox dfd = new VBox();

        anArea.prefHeightProperty().bind(this.box.heightProperty());
        anArea.prefWidthProperty().bind(this.box.widthProperty());
        dfArea.prefHeightProperty().bind(this.box.heightProperty());
        dfArea.prefWidthProperty().bind(this.box.widthProperty());
        anArea.setMinSize(100, 100);
        dfArea.setMinSize(100, 100);

        an.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        df.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        amd.getChildren().addAll(an,anArea);
        dfd.getChildren().addAll(df,dfArea);

        box.getChildren().addAll(amd,dfd);

        return box;
    }
    /**
     * Creates the bottom buttons of GUI elements in the window.
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
        return box;
     }
     /**
      * Method for generating the selected list view bow.
      * @return ListView<String>
      */
    private ListView<String> init_SelectedBox(){
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
            "Single", "Double", "Suite", "Family App");

        list.setItems(items);
        list.prefHeightProperty().bind(this.box.heightProperty());
        list.prefWidthProperty().bind(this.box.widthProperty());

        return list;
    }
    /**
     * Method for generating the analysis method selection box.
     * @return ListView<String>
     */
    private ListView<String> init_AnalysisMethodBox(){
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
            "Single", "Double", "Suite", "Family App");

        list.setItems(items);
        list.prefHeightProperty().bind(this.box.heightProperty());
        list.prefWidthProperty().bind(this.box.widthProperty());

        return list;
    }
    /**
     * Method for generating the distance function selection box.
     * @return ListView<String>
     */
    private ListView<String> init_DistanceFunctionBox(){
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
    private VBox init_rowOneButtons(){
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
    /**
     * Getter method for getting the built pane.
     * @return VBox
     */
     public VBox getPane(){
        return this.box;
    }
}
