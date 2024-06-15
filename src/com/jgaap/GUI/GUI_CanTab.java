package com.jgaap.GUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.jgaap.backend.API;
import com.jgaap.backend.Canonicizers;
import com.jgaap.generics.Canonicizer;
import com.jgaap.util.Document;
import com.jgaap.util.Document.Type;
import com.jgaap.util.Pair;

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
    private ArrayList<Canonicizer> CanonicizerMasterList;
    private ArrayList<String> canonName;
    private ArrayList<String> canonSelect;
    private ArrayList<Canonicizer> canMethSel;
    private static ArrayList<String> CanonicizerComboBoxModel;
    private static ArrayList<Pair<Canonicizer, Object>> SelectedCanonicizerList;
    private ObservableList<String> selItems;
    private ObservableList<String> items;
    private ListView<String> canList;
    private ListView<String> selList;
    private static List<Object> docTypesList;
    private static ComboBox<String> comboBox;
    private TextArea area;
    private Type docTypes;
    private HBox bottomButtons;
    private static Logger logger;
    private static API JAPI;
    private static GUI_NotesWindow noteBox;

    /**
     * Constructor for the class.
     */
    public GUI_CanTab() {
        comboBox = new ComboBox<String>();
        logger = Logger.getLogger(GUI_CanTab.class);
        docTypesList = new ArrayList<Object>();
        SelectedCanonicizerList = new ArrayList<Pair<Canonicizer, Object>>();
        CanonicizerComboBoxModel = new ArrayList<String>();
        box = new VBox();
        noteBox = new GUI_NotesWindow();
        JAPI = API.getInstance();
        init_canonicizers();
    }

    /**
     * Builds the pane row by row.
     */
    private void build_pane() {
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(this.bottomButtons);
    }

    /**
     * Method for building the 'Top Row' of GUI elements.
     * 
     * @return HBox
     */
    private HBox init_rowOne() {
        HBox box = new HBox(5);
        HBox selNotes = new HBox();
        VBox canBox = new VBox();
        VBox selBox = new VBox();
        Label can = new Label("Canonicizers");
        Label sel = new Label("Selected");
        Button notes = noteBox.getButton();
        Region region1 = new Region();

        HBox.setHgrow(region1, Priority.ALWAYS);
        // HBox.setHgrow(region2, Priority.ALWAYS);
        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        sel.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        selNotes.getChildren().addAll(sel, region1, notes);
        canBox.getChildren().addAll(can, init_listBoxCan());
        selBox.getChildren().addAll(selNotes, init_listBoxSel());

        box.getChildren().addAll(canBox, init_rowTwoButtons(), selBox);
        return box;
    }

    /**
     * Method for building the 'Second Row' of GUI elements.
     * 
     * @return HBox
     */
    private VBox init_rowTwo() {
        VBox box = new VBox(5);
        Label can = new Label("Canonicizer Description");
        this.area = new TextArea();

        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        this.area.setText("Enter your description here");
        this.area.setWrapText(true);
        this.area.prefHeightProperty().bind(this.box.heightProperty());
        this.area.prefWidthProperty().bind(this.box.widthProperty());
        box.getChildren().add(can);
        box.getChildren().add(this.area);

        return box;

    }

    /**
     * Method for building the 'Bottom Buttons' of GUI elements.
     * 
     * @return HBox
     */

    /**
     * Method for generating the List Box of Canocinizers.
     * 
     * @return ListView<String>
     */
    private ListView<String> init_listBoxCan() {
        this.canonName = new ArrayList<String>();
        this.canonSelect = new ArrayList<String>();
        this.canMethSel = new ArrayList<Canonicizer>();
        this.canList = new ListView<String>();
        for (Canonicizer i : this.CanonicizerMasterList) {
            this.canonName.add(i.displayName());
        }
        this.items = FXCollections.observableArrayList(this.canonName);

        this.canList.setItems(this.items);
        this.canList.prefHeightProperty().bind(this.box.heightProperty());
        this.canList.prefWidthProperty().bind(this.box.widthProperty());
        this.canList.setOnMouseClicked(e -> {
            String sel = this.canList.getSelectionModel().getSelectedItem();
            Iterator<Canonicizer> iter = this.CanonicizerMasterList.iterator();
            while(iter.hasNext()){
                Canonicizer temp = iter.next();
                if(sel.equalsIgnoreCase(temp.displayName())){
                    this.area.setText(temp.longDescription());
                }
            }
            e.consume();
        });

        return this.canList;
    }

    /**
     * Method for generating List Box of selected Canocinizers.
     * 
     * @return ListView<String>
     */
    private ListView<String> init_listBoxSel() {
        this.selList = new ListView<String>();
        this.selItems = FXCollections.observableArrayList(this.canonSelect);

        this.selList.setItems(this.selItems);
        this.selList.prefHeightProperty().bind(this.box.heightProperty());
        this.selList.prefWidthProperty().bind(this.box.widthProperty());
        this.selList.setOnMouseClicked(e -> {
            String sel = this.selList.getSelectionModel().getSelectedItem();
            Iterator<Canonicizer> iter = this.canMethSel.iterator();
            while(iter.hasNext()){
                Canonicizer temp = iter.next();
                if(sel.equalsIgnoreCase(temp.displayName())){
                    this.area.setText(temp.longDescription());
                }
            }
            e.consume();
        });

        return this.selList;
    }

    /**
     * Method for generating a VBox containing the buttons for de/selecting items
     * for the Selection Box.
     * 
     * @return VBox
     */
    private VBox init_rowTwoButtons() {
        VBox box = new VBox(5);
        Button left = new Button("<-");
        Button right = new Button("->");
        Button clear = new Button("Clear");
        Region region1 = new Region();
        Region region2 = new Region();
        VBox.setVgrow(region1, Priority.ALWAYS);
        VBox.setVgrow(region2, Priority.ALWAYS);

        left.setOnAction(e -> {
            canonDeselected(this.selList.getSelectionModel().getSelectedItem().trim());
            this.selList.refresh();
            this.canList.refresh();
            e.consume();
        });
        right.setOnAction(e -> {
            canonSelected(this.canList.getSelectionModel().getSelectedItem().trim());
            this.selList.refresh();
            this.canList.refresh();
            e.consume();
        });
        clear.setOnAction(e -> {
            this.CanonicizerMasterList.clear();
            this.canonName.clear();
            this.canonSelect.clear();
            this.canMethSel.clear();
            init_canonicizers();
            for (Canonicizer i : this.CanonicizerMasterList) {
                this.canonName.add(i.displayName());
            }
            this.items = FXCollections.observableArrayList(this.canonName);
            this.selItems = FXCollections.observableArrayList(this.canonSelect);
            this.canList.setItems(this.items);
            this.selList.setItems(this.selItems);
            this.selList.refresh();
            this.canList.refresh();
            e.consume();
        });

        box.getChildren().addAll(region1, init_rowTwoSelectionDropDown(), left, right, clear, region2);
        box.setAlignment(Pos.BASELINE_CENTER);

        return box;
    }

    /**
     * Method for creation of the document format selection box.
     * 
     * @return ComboBox<String>
     */
    private ComboBox<String> init_rowTwoSelectionDropDown() {
        UpdateCanonicizerDocTypeComboBox();
        comboBox.setMinSize(100, 25);
        comboBox.getSelectionModel().select("All");
        comboBox.setOnAction(e -> {
            this.docTypes = Type.valueOf(comboBox.getSelectionModel().getSelectedItem());
        });

        return comboBox;
    }

    private void init_canonicizers() {
        CanonicizerMasterList = new ArrayList<Canonicizer>();
        for (int i = 0; i < Canonicizers.getCanonicizers().size(); i++) {
            // for (Canonicizer canonicizer : Canonicizers.getCanonicizers()) {
            Canonicizer canonicizer = Canonicizers.getCanonicizers().get(i);
            if (canonicizer.showInGUI())
                CanonicizerMasterList.add(canonicizer);
        }
    }

    //TODO: Update function to apply Canonicinizer to All selected documents in comboBox
    private void canonSelected(String method) {
        this.canonName.remove(method);
        Iterator<Canonicizer> master = this.CanonicizerMasterList.iterator();
        while(master.hasNext()) {
            Canonicizer temp = master.next();
            if (temp.displayName().equalsIgnoreCase(method)) {
                try {
                    SelectedCanonicizerList.add(new Pair<Canonicizer, Object>(temp,
					docTypesList.get(comboBox.getSelectionModel().getSelectedIndex())));
                    this.canMethSel.add(temp);
                    canonSelect.add(temp.displayName()+" ["+comboBox.getSelectionModel().getSelectedItem()+"]");
                } catch (Exception e) {
                    logger.error(e.getCause(), e);
                    e.printStackTrace();
                }
                master.remove();
            }
        }
        this.items = FXCollections.observableArrayList(this.canonName);
        this.selItems = FXCollections.observableArrayList(this.canonSelect);
        this.canList.setItems(this.items);
        this.selList.setItems(this.selItems);
    }
    //TODO: Update to removd from SelectedCanonicizerList
  private void canonDeselected(String method) {
        this.canonSelect.remove(method);
        this.canonName.add(method);
        Iterator<Canonicizer> canMeth = this.canMethSel.iterator();
        while(canMeth.hasNext()) {
            Canonicizer temp = canMeth.next();
            if (temp.displayName().equalsIgnoreCase(method)) {
                JAPI.removeCanonicizer(temp);
                canMeth.remove();
                this.CanonicizerMasterList.add(temp);
            }
        }
        this.items = FXCollections.observableArrayList(this.canonName);
        this.selItems = FXCollections.observableArrayList(this.canonSelect);
        this.canList.setItems(this.items);
        this.selList.setItems(this.selItems);
    }
    public static void UpdateCanonicizerDocTypeComboBox() {
        comboBox.getItems().clear();
        CanonicizerComboBoxModel.clear();
        docTypesList.clear();
		docTypesList.add("All");
		docTypesList.add(Document.Type.GENERIC);
		docTypesList.add(Document.Type.DOC);
		docTypesList.add(Document.Type.PDF);
		docTypesList.add(Document.Type.HTML);
		for(Document document : JAPI.getDocuments()){
			docTypesList.add(document.getTitle());
		}
		for(Object obj : docTypesList){
			CanonicizerComboBoxModel.add(obj.toString());
		}
        comboBox.setItems(FXCollections.observableArrayList(CanonicizerComboBoxModel));
	}

    /**
     * Returns the built Pane.
     * 
     * @return VBox
     */
    public VBox getPane() {
        build_pane();
        return this.box;
    }
    public void setBottomButtons(HBox box){
        this.bottomButtons = box;
    }
    public static ArrayList<Pair<Canonicizer, Object>> getSelectedCanList(){
        return SelectedCanonicizerList;
    }

}
