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
import com.jgaap.backend.EventCullers;
import com.jgaap.backend.API;
/**
 * Event Culling Tab Class.
 * This Class creates the scene for the Event CUlling Tab and it's GUI elements.
 * @author Edward Polens
 */
public class GUI_ECTab {
    

    private ObservableList<String> selItems;
    private ObservableList<String> items;
    private VBox paraBox;
    private VBox paraBoxChild;
    private VBox box;
    private static HBox bottomButtons;
    private static ArrayList<String> ecName;
    private static ArrayList<String> ecSelect;
    private static ArrayList<EventCuller> ecSel;
    private static ArrayList<EventCuller> EventCullersMasterList;
    private static ListView<String> listLeft;
    private static ListView<String> listRight;
    private static TextArea area;
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
        logger.info("Building Event Culler Tab");
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(bottomButtons);
    }
    /**
     * Method for building the 'Top Level' of GUI Elements.
     * @return HBox
     */
    private HBox init_rowOne(){
        listLeft = init_ListLeft();
        listRight = init_ListRight();
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
        area = new TextArea();

        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        area.setText("");
        area.setWrapText(true);
        area.setEditable(false);
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
    private ListView<String> init_ListLeft(){
        ecName = new ArrayList<String>();
        ecSelect = new ArrayList<String>();
        ecSel = new ArrayList<EventCuller>();
        listLeft = new ListView<String>();
        listRight = new ListView<String>();
        for (EventCuller i : EventCullersMasterList) {
            ecName.add(i.displayName());
        }
        this.items = FXCollections.observableArrayList (ecName);
        listLeft.setOnMouseClicked(e -> {
            String sel = listLeft.getSelectionModel().getSelectedItem();
            Iterator<EventCuller> iter = EventCullersMasterList.iterator();
            while(iter.hasNext()){
                EventCuller temp = iter.next();
                if(sel.equalsIgnoreCase(temp.displayName())){
                    area.setText(temp.longDescription());
                }
            }
            e.consume();
        });

        listLeft.setItems(this.items);
        listLeft.prefHeightProperty().bind(this.box.heightProperty());
        listLeft.prefWidthProperty().bind(this.box.widthProperty());

        return listLeft;
    }
    /**
     * Method for showing the Selected Event Culling box.
     * @return ListView<String>
     */
    private ListView<String> init_ListRight(){
        this.selItems = FXCollections.observableArrayList (ecSelect);
        listRight.setOnMouseClicked(e -> {
            String sel = listRight.getSelectionModel().getSelectedItem();
            Iterator<EventCuller> iter = ecSel.iterator();
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
                    area.setText(temp.longDescription());
                }
            }
            e.consume();
        });

        listRight.setItems(this.selItems);
        listRight.prefHeightProperty().bind(this.box.heightProperty());
        listRight.prefWidthProperty().bind(this.box.widthProperty());

        return listRight;
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
            if(!ecSelect.isEmpty()){
                ecDeselected(listRight.getSelectionModel().getSelectedItem().trim());
                listLeft.refresh();
                listRight.refresh();
            }
            e.consume();
        });
        right.setOnAction(e -> {
            ecSelected(listLeft.getSelectionModel().getSelectedItem().trim());
            listLeft.refresh();
            listRight.refresh();
            listRight.getSelectionModel().select(this.selItems.getLast());
            e.consume();
        });
        all.setOnAction(e -> {
            allSelected();
            listLeft.refresh();
            listRight.refresh();
            e.consume();
        });
        clear.setOnAction(e -> {
            clearSelected();
            listLeft.refresh();
            listRight.refresh();
            e.consume();
        });

        box.getChildren().addAll(region1, right, left, all, clear, region2);
        box.setAlignment(Pos.TOP_CENTER);

        return box;
    }
    /**
     * Method for initializing the Event Culler Master list
     */
    private void init_eventCullers(){
        EventCullersMasterList = new ArrayList<EventCuller>();
        for (int i = 0; i < EventCullers.getEventCullers().size(); i++){
            //for (EventCuller eventCuller : EventCullers.getEventCullers()) {
                EventCuller eventCuller = EventCullers.getEventCullers().get(i);
                if (eventCuller.showInGUI())
                    EventCullersMasterList.add(eventCuller);
            }
    }
    /**
     * Method for adding a selected Event Culler
     * @param method String
     */
    private void ecSelected(String method) {
        logger.info("Adding Event Culler "+method);
        ecSelect.add(method);
        Iterator<EventCuller> master = EventCullersMasterList.iterator();
        while(master.hasNext()) {
            EventCuller temp = master.next();
            if (temp.displayName().equalsIgnoreCase(method)) {
                try {
                    ecSel.add(JAPI.addEventCuller(temp.displayName()));
                } catch (Exception e) {
                    logger.error(e.getCause(), e);
                    e.printStackTrace();
                }
                //ecSel.add(temp);
            }
        }
        this.items = FXCollections.observableArrayList(ecName);
        this.selItems = FXCollections.observableArrayList(ecSelect);
        listLeft.setItems(this.items);
        listRight.setItems(this.selItems);
    }
    /**
     * Method for adding all Event Cullers
     */
    private void allSelected() {
        logger.info("Adding all Event Cullers");
        ecSelect.addAll(ecName);
        ecName.clear();
        Iterator<EventCuller> master = EventCullersMasterList.iterator();
        while(master.hasNext()) {
            EventCuller temp = master.next();
                try {
                    ecSel.add(JAPI.addEventCuller(temp.displayName()));
                } catch (Exception e) {
                    logger.error(e.getCause(), e);
                    e.printStackTrace();
                }
        }
        this.items = FXCollections.observableArrayList(ecName);
        this.selItems = FXCollections.observableArrayList(ecSelect);
        listLeft.setItems(this.items);
        listRight.setItems(this.selItems);
    }

    private void clearSelected() {
        logger.info("Removing all selected Event Cullers");
        ecSelect.clear();
        ecSel.clear();
        JAPI.removeAllEventCullers();
        Iterator<EventCuller> master = EventCullersMasterList.iterator();
        while(master.hasNext()) {
            EventCuller temp = master.next();
            ecName.add(temp.displayName());
        }
        this.items = FXCollections.observableArrayList(ecName);
        this.selItems = FXCollections.observableArrayList(ecSelect);
        listLeft.setItems(this.items);
        listRight.setItems(this.selItems);
    }
    /**
     * Method for removing a selected Event Culler
     * @param method String
     */
    private void ecDeselected(String method) {
        logger.info("Removing Event Culler "+method);
        ecSelect.remove(method);
        Iterator<EventCuller> canMeth = ecSel.iterator();
        while(canMeth.hasNext()) {
            EventCuller temp = canMeth.next();
            if (temp.displayName().equalsIgnoreCase(method)) {
                JAPI.removeEventCuller(temp);
                canMeth.remove();
            }
        }
        this.items = FXCollections.observableArrayList(ecName);
        this.selItems = FXCollections.observableArrayList(ecSelect);
        listLeft.setItems(this.items);
        listRight.setItems(this.selItems);
    }
    /**
     * Getter for getting the built Pane.
     * @return VBox
     */
     public VBox getPane(){
        logger.info("Finished building Event Culler Tab");
        build_pane();
        return this.box;
    }
    /**
     * Method for applying the bottom buttons to the panel
     * @param box HBox
     */
    public void setBottomButtons(HBox box){
        bottomButtons = box;
    }
    
}
