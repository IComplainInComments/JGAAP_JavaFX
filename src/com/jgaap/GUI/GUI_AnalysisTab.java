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
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.NonDistanceDependentAnalysisDriver;
import com.jgaap.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.jgaap.backend.API;
/**
 * Analysis Tab Class.
 * This Class creates the scene for the Analysis Tab and it's GUI elements.
 */
public class GUI_AnalysisTab {

    private ArrayList<AnalysisDriver> AnalysisDriverMasterList;
    private ArrayList<DistanceFunction> DistanceFunctionsMasterList;
    private ArrayList<AnalysisDriver> anSel;
    private ArrayList<String> anName;
    private ArrayList<String> dfName;
    private ArrayList<String> selName;
    private ObservableList<String> selItems;
    private ListView<String> anList;
    private ListView<String> dfList;
    private ListView<String> selList;
    private VBox param;
    private VBox paraBoxChildOne;
    private VBox paraBoxChildTwo;
    private TextArea anArea;
    private TextArea dfArea;
    private static HashMap<String, Pair<DistanceFunction, AnalysisDriver>> distanceFunctions;
    private static API JAPI;
    private static Logger logger;
    private HBox bottomButtons;
    private VBox box;
    private static GUI_NotesWindow notesBox;

    /**
     * Constructor for the class.
     */
    public GUI_AnalysisTab(){
        distanceFunctions = new HashMap<String, Pair<DistanceFunction, AnalysisDriver>>();
        logger = Logger.getLogger(GUI_AnalysisTab.class);
        JAPI = API.getInstance();
        this.box = new VBox();
        notesBox = new GUI_NotesWindow();
        init_analysisDrivers();
        init_distanceFunctions();
        logger.info("Finished building Analysis Tab");
    }
    /**
     * Builds the Pane row by row.
     */
    private void build_pane(){
        logger.info("Building Analysis Tab");
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(this.bottomButtons);
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
        this.param = new VBox();
        this.paraBoxChildOne = new VBox();
        this.paraBoxChildTwo = new VBox();
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
      * Method for generating the selected list view bow.
      * @return ListView<String>
      */
    private ListView<String> init_SelectedBox(){
        this.selItems = FXCollections.observableArrayList (this.selName);

        this.selList.setItems(this.selItems);
        this.selList.prefHeightProperty().bind(this.box.heightProperty());
        this.selList.prefWidthProperty().bind(this.box.widthProperty());
        this.selList.setOnMouseClicked(e -> {
            String sel = this.selList.getSelectionModel().getSelectedItem();
            Iterator<AnalysisDriver> iter = this.anSel.iterator();
            while (iter.hasNext()) {
                AnalysisDriver temp = iter.next();
                if (sel.equalsIgnoreCase(temp.displayName())) {
                    VBox para = temp.getNewGUILayout();
                    if (this.param.getChildren().contains(this.paraBoxChildOne) || this.param.getChildren().contains(this.paraBoxChildTwo)) {
                        para.prefHeightProperty().bind(this.box.heightProperty());
                        para.prefWidthProperty().bind(this.box.widthProperty());
                        if(para.getChildren().size() > 2){
                            logger.info("Changing Analysis Tab Parameter Boxes");
                            //this.param.getChildren().removeAll(this.paraBoxChildOne);
                            //this.param.getChildren().removeAll(this.paraBoxChildTwo);
                            this.param.getChildren().add(para);
                        }
                    } else if (!this.param.getChildren().contains(para) || !this.param.getChildren().contains(para)) {
                        logger.info("Changing Analysis Tab Parameter Boxes");
                        para.prefHeightProperty().bind(this.box.heightProperty());
                        para.prefWidthProperty().bind(this.box.widthProperty());
                        this.param.getChildren().removeAll(this.paraBoxChildOne);
                        this.param.getChildren().removeAll(this.paraBoxChildTwo);
                        this.param.getChildren().add(para);
                    }
                }
            }
            e.consume();
        });

        return this.selList;
    }
    /**
     * Method for generating the analysis method selection box.
     * @return ListView<String>
     */
    private ListView<String> init_AnalysisMethodBox(){
        this.anSel = new ArrayList<AnalysisDriver>();
        this.selList = new ListView<String>();
        this.anName = new ArrayList<String>();
        this.dfName = new ArrayList<String>();
        this.selName = new ArrayList<String>();
        this.anList = new ListView<String>();
        for (AnalysisDriver i : this.AnalysisDriverMasterList) {
            this.anName.add(i.displayName());
        }
        ObservableList<String> anItems = FXCollections.observableArrayList(this.anName);

        this.anList.setItems(anItems);
        this.anList.prefHeightProperty().bind(this.box.heightProperty());
        this.anList.prefWidthProperty().bind(this.box.widthProperty());
        this.anList.setOnMouseClicked(e -> {
            this.dfList.getSelectionModel().clearSelection();
            String sel = this.anList.getSelectionModel().getSelectedItem();
            Iterator<AnalysisDriver> iter = this.AnalysisDriverMasterList.iterator();
            while(iter.hasNext()){
                AnalysisDriver temp = iter.next();
                if(sel.equalsIgnoreCase(temp.displayName())){
                    this.anArea.setText(temp.longDescription());
                }
            }
            e.consume();
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
        ObservableList<String> dfItems = FXCollections.observableArrayList(this.dfName);

        this.dfList.setItems(dfItems);
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
            e.consume();
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
            if(!this.selName.isEmpty()){
                String selection = this.selList.getSelectionModel().getSelectedItem();
                if(selection.contains("with")){
                    dfRemove(this.selList.getSelectionModel().getSelectedItem());
                } else {
                    anDeselected(selection);
                }
                this.selItems = FXCollections.observableArrayList(this.selName);
                this.selList.setItems(this.selItems);
                this.selList.refresh();
            }
            e.consume();
        });
        right.setOnAction(e -> {
            if(this.dfList.getSelectionModel().isEmpty()){
                anSelected(this.anList.getSelectionModel().getSelectedItem());
            } else {
                dfAdd(this.dfList.getSelectionModel().getSelectedItem(), this.anList.getSelectionModel().getSelectedItem());
                this.dfList.getSelectionModel().clearSelection();
            }
            this.selItems = FXCollections.observableArrayList(this.selName);
            this.selList.setItems(this.selItems);
            this.selList.getSelectionModel().selectLast();
            this.selList.getFocusModel().focusNext();
            this.selList.refresh();
            //this.selList.getSelectionModel().select(this.selItems.getLast());
            e.consume();
        });
        clear.setOnAction(e -> {
            if(!this.selName.isEmpty()){
                JAPI.removeAllAnalysisDrivers();
                this.selName.clear();
                distanceFunctions.clear();
                this.selItems = FXCollections.observableArrayList(this.selName);
                this.selList.setItems(this.selItems);
                this.selList.refresh();
            }
            e.consume();
            
        });
        all.setOnAction(e -> {
            e.consume();
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
    /**
     * Method for initializing the Distance Function Master List
     */
    private void init_distanceFunctions(){
        this.DistanceFunctionsMasterList = new ArrayList<DistanceFunction>();
        for (int i = 0; i < DistanceFunctions.getDistanceFunctions().size(); i++){
                DistanceFunction distanceFunction = DistanceFunctions.getDistanceFunctions().get(i);
                if (distanceFunction.showInGUI())
                    this.DistanceFunctionsMasterList.add(distanceFunction);
            }
    }
    /**
     * Method for initializing the Analysis Driver Master List
     */
    private void init_analysisDrivers(){
        this.AnalysisDriverMasterList = new ArrayList<AnalysisDriver>();
        for (int i = 0; i < AnalysisDrivers.getAnalysisDrivers().size(); i++){
                AnalysisDriver analysisDriver = AnalysisDrivers.getAnalysisDrivers().get(i);
                if (analysisDriver.showInGUI())
                    this.AnalysisDriverMasterList.add(analysisDriver);
            }
    }
    /**
     * Method for adding an analysis driver
     * @param method String
     */
    private void anSelected(String method){
        logger.info("Adding Analysis Method "+method);
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
    /**
     * Method for removing an Analysis Driver
     * @param method String
     */
    private void anDeselected(String method){
        logger.info("Removing Analysis Method "+method);
        AnalysisDriver ad = null;
        this.selName.remove(method);
        for(AnalysisDriver i : AnalysisDriverMasterList){
            if(i.displayName().equalsIgnoreCase(method)){
                ad = i;
            }
        }
        JAPI.removeAnalysisDriver(ad);
        this.anSel.remove(ad);
        this.selItems = FXCollections.observableArrayList(this.selName);
        this.selList.setItems(this.selItems);
        this.selList.refresh();
    }
    private void dfAdd(String method, String and){
        logger.info("Adding Distance Function "+method);
        DistanceFunction df = null;
        AnalysisDriver ad = null;
        Pair<DistanceFunction, AnalysisDriver> item = null;
        for(DistanceFunction i : DistanceFunctionsMasterList){
            if(i.displayName().equalsIgnoreCase(method)){
                df = i;
            }
        }
        for(AnalysisDriver i : AnalysisDriverMasterList){
                if(i.displayName().contains("with")){
                    String[] temp = i.displayName().replace(" with metric ", ":").split("\\:");
                    if(temp[0].equalsIgnoreCase(and)){
                        ad = i;
                    }
                } else {
                    if(i.displayName().equalsIgnoreCase(and)){
                        ad = i;
                    }
                }
            }
            this.anSel.add(JAPI.addAnalysisDriver(ad));
        if (ad instanceof NeighborAnalysisDriver) {
			// If the analysis driver that was selected requires a distance,
			// add the selected distance function.
			try {
                JAPI.addDistanceFunction(df.displayName(), ad);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		}  else if (ad instanceof NonDistanceDependentAnalysisDriver) {
			// If the analysis driver that was selected is dependent on
			// another analysis driver being selected, add the one that
			// that is selected.
			try {
                JAPI.addAnalysisDriverAsParamToOther(method, (NonDistanceDependentAnalysisDriver) ad);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		}
        item = new Pair<DistanceFunction,AnalysisDriver>(df, ad);
        distanceFunctions.put(ad.displayName(), item);
        this.selName.add(ad.displayName());
        this.selItems = FXCollections.observableArrayList(this.selName);
        this.selList.setItems(this.selItems);
        this.selList.refresh();
    }
    private void dfRemove(String method){
        logger.info("Removing Distance Function "+method);
        Pair<DistanceFunction, AnalysisDriver> item = distanceFunctions.remove(method);
        JAPI.removeAnalysisDriver(item.getSecond());
        this.anSel.remove(item.getSecond());
        this.selName.remove(method);
        this.selItems = FXCollections.observableArrayList(this.selName);
        this.selList.setItems(this.selItems);
        this.selList.refresh();
    }
    /**
     * Getter method for getting the built pane.
     * @return VBox
     */
     public VBox getPane(){
        build_pane();
        return this.box;
    }
    /**
     * Method for adding bottom buttons to Panel
     * @param box HBox
     */
    public void setBottomButtons(HBox box){
        this.bottomButtons = box;
    }
}
