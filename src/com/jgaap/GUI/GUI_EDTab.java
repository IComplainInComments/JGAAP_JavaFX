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
    private ArrayList<EventDriver> edSel;
    private ArrayList<EventDriver> EventDriverMasterList;
    private ObservableList<String> selItems;
    private ObservableList<String> items;
    private ListView<String> edList;
    private ListView<String> selList;
    private TextArea area;
    private HBox bottomButtons, notesBox;
    private VBox box, paraBoxChild, paraBox;
    private static API JAPI;
    private static Logger logger;
    private static GUI_NotesWindow noteBox;

    /**
     * Constructor for the class.
     */
    public GUI_EDTab() {
        logger = Logger.getLogger(GUI_EDTab.class);
        JAPI = API.getInstance();
        init_EventDrivers();
        box = new VBox();
        noteBox = new GUI_NotesWindow();
    }

    /**
     * Method for building the Window row by row.
     */
    private void build_pane() {
        logger.info("Building Event Driver Tab");
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(this.bottomButtons);
    }

    /**
     * Method for building the 'Top Level' of GUI Elements.
     * 
     * @return HBox
     */
    private HBox init_rowOne() {
        ListView<String> listLeft = init_ListBoxLeft();
        ListView<String> listRight = init_ListBoxRight();
        Label can = new Label("Event Drivers");
        Label sel = new Label("Selected");
        Label para = new Label("Parameters");
        Button notes = noteBox.getButton();
        HBox box = new HBox(5);
        this.notesBox = new HBox();
        VBox edBox = new VBox();
        VBox selBox = new VBox();
        this.paraBox = new VBox();
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        sel.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        para.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        paraBoxChild = new VBox();
        paraBoxChild.setStyle("-fx-border-color: black");
        paraBoxChild.prefHeightProperty().bind(this.box.heightProperty());
        paraBoxChild.prefWidthProperty().bind(this.box.widthProperty());

        this.notesBox.getChildren().addAll(para, region1, notes);

        edBox.getChildren().addAll(can, listLeft);
        selBox.getChildren().addAll(sel, listRight);
        paraBox.getChildren().addAll(this.notesBox, paraBoxChild);

        box.getChildren().addAll(edBox, init_rowTwoButtons(), selBox, paraBox);

        return box;
    }

    /**
     * Method for building the 'Second level' of GUI elements.
     * 
     * @return
     */
    private VBox init_rowTwo() {
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
     * Method for building the Event Driver Selection Box.
     * 
     * @return ListView<String>
     */
    private ListView<String> init_ListBoxLeft() {
        this.edName = new ArrayList<String>();
        this.edSelect = new ArrayList<String>();
        this.edSel = new ArrayList<EventDriver>();
        this.edList = new ListView<String>();
        this.edList = new ListView<String>();
        for (EventDriver i : this.EventDriverMasterList) {
            this.edName.add(i.displayName());
        }
        this.items = FXCollections.observableArrayList(this.edName);

        this.edList.setItems(items);
        this.edList.prefHeightProperty().bind(this.box.heightProperty());
        this.edList.prefWidthProperty().bind(this.box.widthProperty());
        this.edList.setOnMouseClicked(e -> {
            String sel = this.edList.getSelectionModel().getSelectedItem();
            Iterator<EventDriver> iter = this.EventDriverMasterList.iterator();
            while (iter.hasNext()) {
                EventDriver temp = iter.next();
                if (sel.equalsIgnoreCase(temp.displayName())) {
                    this.area.setText(temp.longDescription());
                }
            }
            e.consume();
        });

        return this.edList;
    }

    /**
     * Method for showing the Selected Driver Culling box.
     * 
     * @return ListView<String>
     */
    private ListView<String> init_ListBoxRight() {
        this.selList = new ListView<String>();
        this.selItems = FXCollections.observableArrayList(this.edSelect);
        this.selList.setItems(this.selItems);
        this.selList.prefHeightProperty().bind(this.box.heightProperty());
        this.selList.prefWidthProperty().bind(this.box.widthProperty());
        this.selList.setOnMouseClicked(e -> {
            String sel = this.selList.getSelectionModel().getSelectedItem();
            Iterator<EventDriver> iter = this.edSel.iterator();
            while (iter.hasNext()) {
                EventDriver temp = iter.next();
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

        return selList;
    }

    /**
     * Method for generating the selection box buttons.
     * 
     * @return VBox
     */
    private VBox init_rowTwoButtons() {
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
            edDeselected(this.selList.getSelectionModel().getSelectedItem().trim());
            this.selList.refresh();
            this.edList.refresh();
            e.consume();
        });
        right.setOnAction(e -> {
            edSelected(this.edList.getSelectionModel().getSelectedItem().trim());
            this.selList.refresh();
            this.edList.refresh();
            this.selList.getSelectionModel().select(this.selItems.getLast());
            e.consume();
        });
        clear.setOnAction(e -> {
            this.EventDriverMasterList.clear();
            this.edName.clear();
            this.edSelect.clear();
            this.edSel.clear();
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
            this.paraBox.getChildren().removeLast();
            this.paraBox.getChildren().add(this.paraBoxChild);
            e.consume();
        });
        all.setOnAction(e -> {
            allSelected();
            this.selList.refresh();
            this.edList.refresh();
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
     * Method for initializing the Event Driver Master List
     */
    private void init_EventDrivers() {
        this.EventDriverMasterList = new ArrayList<EventDriver>();
        for (int i = 0; i < EventDrivers.getEventDrivers().size(); i++) {
            EventDriver eventDriver = EventDrivers.getEventDrivers().get(i);
            if (eventDriver.showInGUI())
                this.EventDriverMasterList.add(eventDriver);
        }
    }
    /**
     * Method for adding a selected Event Driver
     * @param method String
     */
    private void edSelected(String method) {
        logger.info("Adding Event Driver "+method);
        this.edSelect.add(method);
        this.edName.remove(method);
        Iterator<EventDriver> master = this.EventDriverMasterList.iterator();
        while (master.hasNext()) {
            EventDriver temp = master.next();
            if (temp.displayName().equalsIgnoreCase(method)) {
                try {
                    this.edSel.add(JAPI.addEventDriver(temp.displayName()));
                } catch (Exception e) {
                    logger.error(e.getCause(), e);
                    e.printStackTrace();
                }
                // this.edSel.add(temp);
                master.remove();
            }
        }
        this.items = FXCollections.observableArrayList(this.edName);
        this.selItems = FXCollections.observableArrayList(this.edSelect);
        this.edList.setItems(this.items);
        this.selList.setItems(this.selItems);
    }
    /**
     * Method for selecting all Event Drivers
     */
    private void allSelected() {
        logger.info("Adding all Event Drivers");
        this.edSelect.addAll(this.edName);
        this.edName.clear();
        Iterator<EventDriver> master = this.EventDriverMasterList.iterator();
        while (master.hasNext()) {
            EventDriver temp = master.next();
            try {
                this.edSel.add(JAPI.addEventDriver(temp.displayName()));
            } catch (Exception e) {
                logger.error(e.getCause(), e);
                e.printStackTrace();
            }
            master.remove();
        }
        this.items = FXCollections.observableArrayList(this.edName);
        this.selItems = FXCollections.observableArrayList(this.edSelect);
        this.edList.setItems(this.items);
        this.selList.setItems(this.selItems);
    }
    /**
     * Method for removing selected Event Driver
     * @param method String
     */
    private void edDeselected(String method) {
        logger.info("Removing Event Driver "+method);
        this.edSelect.remove(method);
        this.edName.add(method);
        Iterator<EventDriver> canMeth = this.edSel.iterator();
        while (canMeth.hasNext()) {
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

    /**
     * Getter for getting the built Pane.
     * 
     * @return VBox
     */
    public VBox getPane() {
        logger.info("Finished building Event Culler Tab");
        build_pane();
        return this.box;
    }
    /**
     * Method for applying the bottom buttons to the panel
     * @param box HBOX
     */
    public void setBottomButtons(HBox box) {
        this.bottomButtons = box;
    }

}
