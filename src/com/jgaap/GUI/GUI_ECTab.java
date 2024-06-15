package com.jgaap.GUI;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

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
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventDriver;
import com.jgaap.backend.EventCullers;
import com.jgaap.backend.API;
/**
 * Event Culling Tab Class.
 * This Class creates the scene for the Event CUlling Tab and it's GUI elements.
 */
public class GUI_ECTab {
    
    private ArrayList<String> ecName;
    private ArrayList<String> ecSelect;
    private ArrayList<EventCuller> ecSel;
    private ArrayList<EventCuller> EventCullersMasterList;
    private ObservableList<String> selItems;
    private ObservableList<String> items;
    private ListView<String> listLeft;
    private ListView<String> listRight;
    private TextArea area;
    private VBox paraBox;
    private VBox paraBoxChild;
    private VBox box;
    private HBox bottomButtons;
    private static Logger logger;
    private static API JAPI;
    private static GUI_NotesWindow noteBox;
    /**
     * Constructor for the class.
     */
    public GUI_ECTab(){
        logger = Logger.getLogger(GUI_ECTab.class);
        JAPI = API.getInstance();
        init_eventCullers();
        box = new VBox();
        noteBox = new GUI_NotesWindow();
    }
    /**
     * Method for building the Window row by row.
     */
    private void build_pane(){
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(this.bottomButtons);
    }
    /**
     * Method for building the 'Top Level' of GUI Elements.
     * @return HBox
     */
    private HBox init_rowOne(){
        this.listLeft = init_ListBoxLeft();
        this.listRight = init_ListBoxRight();
        Label can = new Label("Event Culling");
        Label sel = new Label("Selected");
        Label para = new Label("Parameters");
        Button notes = noteBox.getButton();
        HBox box = new HBox(5);
        HBox notesBox = new HBox();
        VBox edBox = new VBox();
        VBox selBox = new VBox();
        this.paraBox = new VBox();
        this.paraBoxChild = new VBox();
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
    /**
     * Method for building the 'Second level' of GUI elements.
     * @return
     */
    private VBox init_rowTwo(){
        VBox box = new VBox(5);
        Label can = new Label("Event Culling Description");
        this.area = new TextArea();

        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        area.setText("Enter your description here");
        area.prefHeightProperty().bind(this.box.heightProperty());
        area.prefWidthProperty().bind(this.box.widthProperty());
        box.getChildren().add(can);
        box.getChildren().add(area);

        return box;

    
    }
     /**
      * Method for building the Event Culling Selection Box.
      * @return ListView<String>
      */
    private ListView<String> init_ListBoxLeft(){
        this.ecName = new ArrayList<String>();
        this.ecSelect = new ArrayList<String>();
        this.ecSel = new ArrayList<EventCuller>();
        this.listLeft = new ListView<String>();
        this.listRight = new ListView<String>();
        for (EventCuller i : this.EventCullersMasterList) {
            this.ecName.add(i.displayName());
        }
        this.items = FXCollections.observableArrayList (this.ecName);
        this.listLeft.setOnMouseClicked(e -> {
            String sel = this.listLeft.getSelectionModel().getSelectedItem();
            Iterator<EventCuller> iter = this.EventCullersMasterList.iterator();
            while(iter.hasNext()){
                EventCuller temp = iter.next();
                if(sel.equalsIgnoreCase(temp.displayName())){
                    this.area.setText(temp.longDescription());
                }
            }
            e.consume();
        });

        this.listLeft.setItems(this.items);
        this.listLeft.prefHeightProperty().bind(this.box.heightProperty());
        this.listLeft.prefWidthProperty().bind(this.box.widthProperty());

        return this.listLeft;
    }
    /**
     * Method for showing the Selected Event Culling box.
     * @return ListView<String>
     */
    private ListView<String> init_ListBoxRight(){
        this.selItems = FXCollections.observableArrayList (this.ecSelect);
        this.listRight.setOnMouseClicked(e -> {
            String sel = this.listRight.getSelectionModel().getSelectedItem();
            Iterator<EventCuller> iter = this.ecSel.iterator();
            while (iter.hasNext()) {
                EventCuller temp = iter.next();
                if (sel.equalsIgnoreCase(temp.displayName())) {
                    VBox para = temp.getNewGUILayout();
                    if (this.paraBox.getChildren().contains(this.paraBoxChild)) {
                        para.prefHeightProperty().bind(this.box.heightProperty());
                        para.prefWidthProperty().bind(this.box.widthProperty());
                        this.paraBox.getChildren().removeAll(this.paraBoxChild);
                        this.paraBox.getChildren().add(para);
                    } else if (!this.paraBox.getChildren().contains(para)) {
                        para.prefHeightProperty().bind(this.box.heightProperty());
                        para.prefWidthProperty().bind(this.box.widthProperty());
                        this.paraBox.getChildren().removeLast();
                        this.paraBox.getChildren().add(para);
                    }
                    this.area.setText(temp.longDescription());
                }
            }
            e.consume();
        });

        this.listRight.setItems(this.selItems);
        this.listRight.prefHeightProperty().bind(this.box.heightProperty());
        this.listRight.prefWidthProperty().bind(this.box.widthProperty());

        return this.listRight;
    }
    /**
     * Method for generating the selection box buttons.
     * @return VBox
     */
    private VBox init_rowTwoButtons(){
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
            ecDeselected(this.listRight.getSelectionModel().getSelectedItem().trim());
            this.listLeft.refresh();
            this.listRight.refresh();
            e.consume();
        });
        right.setOnAction(e -> {
            ecSelected(this.listLeft.getSelectionModel().getSelectedItem().trim());
            this.listLeft.refresh();
            this.listRight.refresh();
            e.consume();
        });
        clear.setOnAction(e -> {
            this.EventCullersMasterList.clear();
            this.ecName.clear();
            this.ecSelect.clear();
            this.ecSel.clear();
            init_eventCullers();
            for (EventCuller i : this.EventCullersMasterList) {
                this.ecName.add(i.displayName());
            }
            JAPI.removeAllEventDrivers();
            this.items = FXCollections.observableArrayList(this.ecName);
            this.selItems = FXCollections.observableArrayList(this.ecSelect);
            this.listLeft.setItems(this.items);
            this.listRight.setItems(this.selItems);
            this.listLeft.refresh();
            this.listRight.refresh();
            e.consume();
        });
        all.setOnAction(e -> {
            allSelected();
            this.listLeft.refresh();
            this.listRight.refresh();
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
    private void ecSelected(String method) {
        this.ecSelect.add(method);
        this.ecName.remove(method);
        Iterator<EventCuller> master = this.EventCullersMasterList.iterator();
        while(master.hasNext()) {
            EventCuller temp = master.next();
            if (temp.displayName().equalsIgnoreCase(method)) {
                try {
                    this.ecSel.add(JAPI.addEventCuller(temp.displayName()));
                } catch (Exception e) {
                    logger.error(e.getCause(), e);
                    e.printStackTrace();
                }
                this.ecSel.add(temp);
                master.remove();
            }
        }
        this.items = FXCollections.observableArrayList(this.ecName);
        this.selItems = FXCollections.observableArrayList(this.ecSelect);
        this.listLeft.setItems(this.items);
        this.listRight.setItems(this.selItems);
    }
    private void allSelected() {
        this.ecSelect.addAll(this.ecName);
        this.ecName.clear();
        Iterator<EventCuller> master = this.EventCullersMasterList.iterator();
        while(master.hasNext()) {
            EventCuller temp = master.next();
                try {
                    this.ecSel.add(JAPI.addEventCuller(temp.displayName()));
                } catch (Exception e) {
                    logger.error(e.getCause(), e);
                    e.printStackTrace();
                }
                master.remove();
        }
        this.items = FXCollections.observableArrayList(this.ecName);
        this.selItems = FXCollections.observableArrayList(this.ecSelect);
        this.listLeft.setItems(this.items);
        this.listRight.setItems(this.selItems);
    }
    private void ecDeselected(String method) {
        this.ecSelect.remove(method);
        this.ecName.add(method);
        Iterator<EventCuller> canMeth = this.ecSel.iterator();
        while(canMeth.hasNext()) {
            EventCuller temp = canMeth.next();
            if (temp.displayName().equalsIgnoreCase(method)) {
                JAPI.removeEventCuller(temp);
                canMeth.remove();
                this.EventCullersMasterList.add(temp);
            }
        }
        this.items = FXCollections.observableArrayList(this.ecName);
        this.selItems = FXCollections.observableArrayList(this.ecSelect);
        this.listLeft.setItems(this.items);
        this.listRight.setItems(this.selItems);
    }
    private void init_eventCullers(){
        this.EventCullersMasterList = new ArrayList<EventCuller>();
        for (int i = 0; i < EventCullers.getEventCullers().size(); i++){
            //for (EventCuller eventCuller : EventCullers.getEventCullers()) {
                EventCuller eventCuller = EventCullers.getEventCullers().get(i);
                if (eventCuller.showInGUI())
                    this.EventCullersMasterList.add(eventCuller);
            }
    }
    /**
     * Getter for getting the built Pane.
     * @return VBox
     */
     public VBox getPane(){
        build_pane();
        return this.box;
    }
    public void setBottomButtons(HBox box){
        this.bottomButtons = box;
    }
    
}
