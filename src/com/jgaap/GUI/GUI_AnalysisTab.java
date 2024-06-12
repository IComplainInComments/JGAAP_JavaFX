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
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.backend.AnalysisDrivers;
import com.jgaap.backend.DistanceFunctions;
import com.jgaap.generics.DistanceFunction;

import java.util.ArrayList;
import java.util.Iterator;

import com.jgaap.backend.API;
/**
 * Analysis Tab Class.
 * This Class creates the scene for the Analysis Tab and it's GUI elements.
 */
public class GUI_AnalysisTab {

    private ArrayList<AnalysisDriver> AnalysisDriverMasterList;
    private ArrayList<DistanceFunction> DistanceFunctionsMasterList;
    private ArrayList<AnalysisDriver> anSel;
    private ArrayList<DistanceFunction> dfSel;
    private ArrayList<String> anName;
    private ArrayList<String> dfName;
    private ArrayList<String> selName;
    private ObservableList<String> selItems;
    private ObservableList<String> anItems;
    private ObservableList<String> dfItems;
    private ListView<String> anList;
    private ListView<String> dfList;
    private ListView<String> selList;
    private TextArea anArea;
    private TextArea dfArea;
    private static API JAPI;
    private VBox box;
    private static GUI_NotesWindow notesBox;

    /**
     * Constructor for the class.
     */
    public GUI_AnalysisTab(){
        JAPI = API.getInstance();
        this.box = new VBox();
        notesBox = new GUI_NotesWindow();
        init_analysisDrivers();
        init_distanceFunctions();
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
        this.anArea = new TextArea();
        this.dfArea = new TextArea();
        HBox box = new HBox(5);
        VBox amd = new VBox();
        VBox dfd = new VBox();

        this.anArea.prefHeightProperty().bind(this.box.heightProperty());
        this.anArea.prefWidthProperty().bind(this.box.widthProperty());
        this.dfArea.prefHeightProperty().bind(this.box.heightProperty());
        this.dfArea.prefWidthProperty().bind(this.box.widthProperty());
        this.anArea.setMinSize(100, 100);
        this.dfArea.setMinSize(100, 100);

        an.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        df.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        amd.getChildren().addAll(an,this.anArea);
        dfd.getChildren().addAll(df,this.dfArea);

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
        this.selItems = FXCollections.observableArrayList (this.selName);

        this.selList.setItems(this.selItems);
        this.selList.prefHeightProperty().bind(this.box.heightProperty());
        this.selList.prefWidthProperty().bind(this.box.widthProperty());

        return this.selList;
    }
    /**
     * Method for generating the analysis method selection box.
     * @return ListView<String>
     */
    private ListView<String> init_AnalysisMethodBox(){
        this.anSel = new ArrayList<AnalysisDriver>();
        this.dfSel = new ArrayList<DistanceFunction>();
        this.selList = new ListView<String>();
        this.anName = new ArrayList<String>();
        this.dfName = new ArrayList<String>();
        this.selName = new ArrayList<String>();
        this.anList = new ListView<String>();
        for (AnalysisDriver i : this.AnalysisDriverMasterList) {
            this.anName.add(i.displayName());
        }
        this.anItems = FXCollections.observableArrayList (this.anName);

        this.anList.setItems(this.anItems);
        this.anList.prefHeightProperty().bind(this.box.heightProperty());
        this.anList.prefWidthProperty().bind(this.box.widthProperty());
        this.anList.setOnMouseClicked(e -> {
            String sel = this.anList.getSelectionModel().getSelectedItem();
            Iterator<AnalysisDriver> iter = this.AnalysisDriverMasterList.iterator();
            while(iter.hasNext()){
                AnalysisDriver temp = iter.next();
                if(sel.equalsIgnoreCase(temp.displayName())){
                    this.anArea.setText(temp.longDescription());
                }
            }
        });

        return this.anList;
    }
    /**
     * Method for generating the distance function selection box.
     * @return ListView<String>
     */
    private ListView<String> init_DistanceFunctionBox(){
        this.dfList = new ListView<String>();
        for (DistanceFunction i : this.DistanceFunctionsMasterList) {
            this.dfName.add(i.displayName());
        }
        this.dfItems = FXCollections.observableArrayList (this.dfName);

        this.dfList.setItems(this.dfItems);
        this.dfList.prefHeightProperty().bind(this.box.heightProperty());
        this.dfList.prefWidthProperty().bind(this.box.widthProperty());
        this.dfList.setOnMouseClicked(e -> {
            String sel = this.dfList.getSelectionModel().getSelectedItem();
            Iterator<DistanceFunction> iter = this.DistanceFunctionsMasterList.iterator();
            while(iter.hasNext()){
                DistanceFunction temp = iter.next();
                if(sel.equalsIgnoreCase(temp.displayName())){
                    this.dfArea.setText(temp.longDescription());
                }
            }
        });

        return this.dfList;
    }
    /**
     * Method for generating a VBox containing the buttons for de/selecting items for the Selection Box.
     * @return VBox
     */
    private VBox init_rowOneButtons(){
        VBox box = new VBox(5);
        Region region1 = new Region();
        Region region2 = new Region();
        Button left = new Button("<-");
        Button right = new Button("->");
        Button clear = new Button("Clear");
        Button all = new Button("All");

        box.setMinSize(50, 0);

        VBox.setVgrow(region1, Priority.ALWAYS);
        VBox.setVgrow(region2, Priority.ALWAYS);
        left.setOnAction(e -> {
            anDeselected(this.selList.getSelectionModel().getSelectedItem());
            this.selItems = FXCollections.observableArrayList(this.selName);
            this.selList.setItems(this.selItems);
            this.selList.refresh();
        });
        right.setOnAction(e -> {
            if(this.dfList.getSelectionModel().isEmpty()){
                anSelected(this.anList.getSelectionModel().getSelectedItem());
            } else {
                Iterator<AnalysisDriver> iter = this.AnalysisDriverMasterList.iterator();
                while(iter.hasNext()){
                    AnalysisDriver temp = iter.next();
                    if(temp.displayName().equalsIgnoreCase(this.anList.getSelectionModel().getSelectedItem())){
                        dfAdd("NOMETHOD", this.dfList.getSelectionModel().getSelectedItem(), temp);
                    }
                }
            }
            this.selItems = FXCollections.observableArrayList(this.selName);
            this.selList.setItems(this.selItems);
            this.selList.refresh();
        });
        clear.setOnAction(e -> {
            JAPI.removeAllAnalysisDrivers();
            this.anName.clear();
            this.dfName.clear();
            this.selName.clear();
            this.anSel.clear();
            this.dfSel.clear();
            for (AnalysisDriver i : this.AnalysisDriverMasterList) {
                this.anName.add(i.displayName());
            }
            for (DistanceFunction i : this.DistanceFunctionsMasterList) {
                this.dfName.add(i.displayName());
            }
            this.selItems = FXCollections.observableArrayList(this.selName);
            this.selList.setItems(this.selItems);
            this.selList.refresh();
            
        });
        all.setOnAction(e -> {
            
        });

        box.getChildren().add(region1);
        box.getChildren().add(left);
        box.getChildren().add(right);
        box.getChildren().add(all);
        box.getChildren().add(clear);
        box.getChildren().add(region2);
        box.setAlignment(Pos.TOP_CENTER);

        return box;
        
    }
    private void anSelected(String method){
        this.selName.add(method);
        Iterator<AnalysisDriver> iter = this.AnalysisDriverMasterList.iterator();
        while(iter.hasNext()){
            AnalysisDriver temp = iter.next();
            if(temp.displayName().equalsIgnoreCase(method)){
                this.anSel.add(JAPI.addAnalysisDriver(temp));
            }
        }
        this.selItems = FXCollections.observableArrayList(this.selName);
        this.selList.setItems(this.selItems);
        this.selList.refresh();
    }
    private void anDeselected(String method){
        this.selName.remove(method);
        Iterator<AnalysisDriver> iter = this.anSel.iterator();
        while(iter.hasNext()){
            AnalysisDriver temp = iter.next();
            if(temp.displayName().equalsIgnoreCase(method)){
                JAPI.removeAnalysisDriver(temp);
                iter.remove();
            }
        }
        this.selItems = FXCollections.observableArrayList(this.selName);
        this.selList.setItems(this.selItems);
        this.selList.refresh();
    }
    private void dfAdd(String type, String method, AnalysisDriver ad){
        this.selName.add(ad.displayName());
        switch(type){
            case "METHOD":
                try {
                    this.dfSel.add(JAPI.addDistanceFunction(method, ad));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "NOMETHOD":
                Iterator<DistanceFunction> iter = this.DistanceFunctionsMasterList.iterator();
                while(iter.hasNext()){
                    DistanceFunction temp = iter.next();
                    if(temp.displayName().equalsIgnoreCase(method)){
                        this.dfSel.add(JAPI.addDistanceFunction(temp, ad));
                    }
                }
                break;
            default:
                Iterator<DistanceFunction> iter2 = this.DistanceFunctionsMasterList.iterator();
                while(iter2.hasNext()){
                    DistanceFunction temp = iter2.next();
                    if(temp.displayName().equalsIgnoreCase(method)){
                        this.dfSel.add(JAPI.addDistanceFunction(temp, ad));
                    }
                }
                break;
        }
        this.selItems = FXCollections.observableArrayList(this.selName);
        this.selList.setItems(this.selItems);
        this.selList.refresh();
    }
    private void dfRemove(DistanceFunction df){

    }
    private void init_distanceFunctions(){
        this.DistanceFunctionsMasterList = new ArrayList<DistanceFunction>();
        for (int i = 0; i < DistanceFunctions.getDistanceFunctions().size(); i++){
            //for (DistanceFunction distanceFunction : DistanceFunctions.getDistanceFunctions()) {
                DistanceFunction distanceFunction = DistanceFunctions.getDistanceFunctions().get(i);
                if (distanceFunction.showInGUI())
                    this.DistanceFunctionsMasterList.add(distanceFunction);
            }
    }
    private void init_analysisDrivers(){
        this.AnalysisDriverMasterList = new ArrayList<AnalysisDriver>();
        for (int i = 0; i < AnalysisDrivers.getAnalysisDrivers().size(); i++){
            //for (AnalysisDriver analysisDriver : AnalysisDrivers.getAnalysisDrivers()) {
                AnalysisDriver analysisDriver = AnalysisDrivers.getAnalysisDrivers().get(i);
                if (analysisDriver.showInGUI())
                    this.AnalysisDriverMasterList.add(analysisDriver);
            }
    }
    /**
     * Getter method for getting the built pane.
     * @return VBox
     */
     public VBox getPane(){
        return this.box;
    }
}
