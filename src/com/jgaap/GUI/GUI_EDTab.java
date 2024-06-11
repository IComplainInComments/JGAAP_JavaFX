package com.jgaap.GUI;
import java.util.ArrayList;
import java.util.Iterator;

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
import com.jgaap.generics.EventDriver;
import com.jgaap.backend.EventDrivers;
import com.jgaap.backend.API;
/**
 * Event Driver Tab Class.
 * This Class creates the scene for the Event Driver Tab and it's GUI elements.
 */
public class GUI_EDTab {

    private ArrayList<String> edName;
    private ArrayList<String> edSelect;
    private ArrayList<EventDriver> canMethSel;
    private ArrayList<EventDriver> EventDriverMasterList;
    private ObservableList<String> selItems;
    private ObservableList<String> items;
    private ListView<String> edList;
    private ListView<String> selList;
    private TextArea area;
    private VBox box;
    private static API JAPI;
    private static GUI_NotesWindow noteBox;
    /**
     * Constructor for the class.
     */
    public GUI_EDTab(){
        JAPI = API.getInstance();
        init_EventDrivers();
        box = new VBox();
        noteBox = new GUI_NotesWindow();
        build_pane();
    }
    /**
     * Method for building the Window row by row.
     */
    private void build_pane(){
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(init_bottomButtons());
    }
    /**
     * Method for building the 'Top Level' of GUI Elements.
     * @return HBox
     */
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
    /**
    * Method for building the 'Second level' of GUI elements.
    * @return
    */    
    private VBox init_rowTwo(){
        VBox box = new VBox(5);
        Label can = new Label("Event Driver Description");
        this.area = new TextArea();

        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        this.area.setText("Enter your description here");
        this.area.prefHeightProperty().bind(this.box.heightProperty());
        this.area.prefWidthProperty().bind(this.box.widthProperty());
        box.getChildren().add(can);
        box.getChildren().add(this.area);

        return box;

    }        
    /**
    * Method for building the 'Bottom Buttons' of GUI elements.
    * @return HBox
    */
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
    /**
      * Method for building the Event Driver Selection Box.
      * @return ListView<String>
      */
    private ListView<String> init_ListBoxLeft(){
        this.edName = new ArrayList<String>();
        this.edSelect = new ArrayList<String>();
        this.canMethSel = new ArrayList<EventDriver>();
        this.edList = new ListView<String>();
        this.edList = new ListView<String>();
        for (EventDriver i : this.EventDriverMasterList) {
            this.edName.add(i.displayName());
        }
        this.items = FXCollections.observableArrayList (this.edName);

        this.edList.setItems(items);
        this.edList.prefHeightProperty().bind(this.box.heightProperty());
        this.edList.prefWidthProperty().bind(this.box.widthProperty());
        this.edList.setOnMouseClicked(e -> {
            String sel = this.edList.getSelectionModel().getSelectedItem();
            Iterator<EventDriver> iter = this.EventDriverMasterList.iterator();
            while(iter.hasNext()){
                EventDriver temp = iter.next();
                if(sel.equalsIgnoreCase(temp.displayName())){
                    this.area.setText(temp.longDescription());
                }
            }

        });

        return this.edList;
    }
    /**
     * Method for showing the Selected Driver Culling box.
     * @return ListView<String>
     */
    private ListView<String> init_ListBoxRight(){
        this.selList = new ListView<String>();
        this.selItems = FXCollections.observableArrayList (this.edSelect);

        this.selList.setItems(this.selItems);
        this.selList.prefHeightProperty().bind(this.box.heightProperty());
        this.selList.prefWidthProperty().bind(this.box.widthProperty());
        this.selList.setOnMouseClicked(e -> {
            String sel = this.selList.getSelectionModel().getSelectedItem();
            Iterator<EventDriver> iter = this.canMethSel.iterator();
            while(iter.hasNext()){
                EventDriver temp = iter.next();
                if(sel.equalsIgnoreCase(temp.displayName())){
                    this.area.setText(temp.longDescription());
                }
            }
        });

        return selList;
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
            canonDeselected(this.selList.getSelectionModel().getSelectedItem().trim());
            this.selList.refresh();
            this.edList.refresh();
        });
        right.setOnAction(e -> {
            canonSelected(this.edList.getSelectionModel().getSelectedItem().trim());
            this.selList.refresh();
            this.edList.refresh();
        });
        clear.setOnAction(e -> {
            this.EventDriverMasterList.clear();
            this.edName.clear();
            this.edSelect.clear();
            this.canMethSel.clear();
            init_EventDrivers();
            for (EventDriver i : this.EventDriverMasterList) {
                this.edName.add(i.displayName());
            }
            JAPI.removeAllEventDrivers();
            this.items = FXCollections.observableArrayList(this.edName);
            this.selItems = FXCollections.observableArrayList(this.edSelect);
            this.edList.setItems(this.items);
            this.selList.setItems(this.selItems);
            this.selList.refresh();
            this.edList.refresh();
        });
        all.setOnAction(e -> {
            allSelected();
            this.selList.refresh();
            this.edList.refresh();
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
        private void canonSelected(String method) {
        this.edSelect.add(method);
        this.edName.remove(method);
        Iterator<EventDriver> master = this.EventDriverMasterList.iterator();
        while(master.hasNext()) {
            EventDriver temp = master.next();
            if (temp.displayName().equalsIgnoreCase(method)) {
                try {
                    this.canMethSel.add(JAPI.addEventDriver(temp.displayName()));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                this.canMethSel.add(temp);
                master.remove();
            }
        }
        this.items = FXCollections.observableArrayList(this.edName);
        this.selItems = FXCollections.observableArrayList(this.edSelect);
        this.edList.setItems(this.items);
        this.selList.setItems(this.selItems);
    }
        private void allSelected() {
        this.edSelect.addAll(this.edName);
        this.edName.clear();
        Iterator<EventDriver> master = this.EventDriverMasterList.iterator();
        while(master.hasNext()) {
            EventDriver temp = master.next();
                try {
                    this.canMethSel.add(JAPI.addEventDriver(temp.displayName()));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                master.remove();
        }
        this.items = FXCollections.observableArrayList(this.edName);
        this.selItems = FXCollections.observableArrayList(this.edSelect);
        this.edList.setItems(this.items);
        this.selList.setItems(this.selItems);
    }
    private void canonDeselected(String method) {
        this.edSelect.remove(method);
        this.edName.add(method);
        Iterator<EventDriver> canMeth = this.canMethSel.iterator();
        while(canMeth.hasNext()) {
            EventDriver temp = canMeth.next();
            if (temp.displayName().equalsIgnoreCase(method)) {
                JAPI.removeEventDriver(temp);
                canMeth.remove();
                this.EventDriverMasterList.add(temp);
            }
        }
        this.items = FXCollections.observableArrayList(this.edName);
        this.selItems = FXCollections.observableArrayList(this.edSelect);
        this.edList.setItems(this.items);
        this.selList.setItems(this.selItems);
    }
    private void init_EventDrivers(){
        this.EventDriverMasterList = new ArrayList<EventDriver>();
        for (int i = 0; i < EventDrivers.getEventDrivers().size(); i++){
            //for (EventDriver eventDriver : EventDrivers.getEventDrivers()) {
                EventDriver eventDriver = EventDrivers.getEventDrivers().get(i);
                if (eventDriver.showInGUI())
                    this.EventDriverMasterList.add(eventDriver);
            }
    }
    /**
     * Getter for getting the built Pane.
     * @return VBox
     */
     public VBox getPane(){
        return this.box;
    }
    
}
