package com.jgaap.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.jgaap.backend.API;
import com.jgaap.backend.Canonicizers;
import com.jgaap.generics.Canonicizer;
import com.jgaap.util.Document;
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
    private ObservableList<String> canItems;
    private ObservableList<String> selItems;
    private ListView<String> canList;
    private ListView<String> selList;
    private TextArea area;
    private HBox bottomButtons;
    private static ArrayList<String> CanonicizerComboBoxModel;
    private static HashMap<String, Pair<Canonicizer, Object>> SelectedCanonicizerMap;
    private static List<Object> docTypesList;
    private static ComboBox<String> comboBox;
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
        CanonicizerComboBoxModel = new ArrayList<String>();
        SelectedCanonicizerMap = new HashMap<String, Pair<Canonicizer, Object>>();
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
        this.canList = new ListView<String>();
        this.canonName = new ArrayList<String>();
        for (Canonicizer i : this.CanonicizerMasterList) {
            this.canonName.add(i.displayName());
        }
        this.canItems = FXCollections.observableArrayList(this.canonName);

        this.canList.setItems(this.canItems);
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
        this.selItems = FXCollections.observableList(SelectedCanonicizerMap.keySet().parallelStream().toList());

        this.selList.setItems(this.selItems);
        this.selList.prefHeightProperty().bind(this.box.heightProperty());
        this.selList.prefWidthProperty().bind(this.box.widthProperty());
        this.selList.setOnMouseClicked(e -> {
            String sel = this.selList.getSelectionModel().getSelectedItem();
            Iterator<Canonicizer> iter = CanonicizerMasterList.iterator();
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
            Iterator<String> iter = SelectedCanonicizerMap.keySet().iterator();
            while (iter.hasNext()) {
                String i = iter.next();
                if(this.selList.getSelectionModel().getSelectedItem().equalsIgnoreCase(i)){
                    canonDeselected(i);
                }
            }
            this.selList.refresh();
            this.canList.refresh();
            e.consume();
        });
        right.setOnAction(e -> {
            for (Canonicizer i : this.CanonicizerMasterList) {
                if(this.canList.getSelectionModel().getSelectedItem().equalsIgnoreCase(i.displayName())){
                    canonSelected(i, comboBox.getSelectionModel().getSelectedItem());
                }
            }
            this.selList.refresh();
            this.canList.refresh();
            e.consume();
        });
        clear.setOnAction(e -> {
            SelectedCanonicizerMap.clear();
            this.canItems = FXCollections.observableArrayList(this.canonName);
            this.selItems = FXCollections.observableList(SelectedCanonicizerMap.keySet().parallelStream().toList());
            this.canList.setItems(this.canItems);
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
        comboBox.getSelectionModel().select(0);

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

    private void canonSelected(Canonicizer method, Object doc) {

        Pair<Canonicizer, Object> temp = new Pair<Canonicizer, Object>(method, doc);
        String key = temp.getFirst().displayName()+" ["+comboBox.getSelectionModel().getSelectedItem()+"]";
        SelectedCanonicizerMap.put(key, temp);

        this.selItems = FXCollections.observableList(SelectedCanonicizerMap.keySet().parallelStream().toList());
        this.canList.setItems(this.canItems);
        this.selList.setItems(this.selItems);
        this.selList.getSelectionModel().select(this.selItems.getLast());
    }
  private void canonDeselected(String key) {
        //this.canonName.add(method);
        /*Iterator<Pair<Canonicizer, Object>> canMeth = SelectedCanonicizerList.iterator();
        while(canMeth.hasNext()) {
            Pair<Canonicizer, Object> temp = canMeth.next();
            if (temp.getFirst().displayName().equalsIgnoreCase(method.displayName()) && temp.getSecond().equals(doc)) {
                canonSelect.remove(temp.getFirst().displayName()+" ["+temp.getSecond().toString()+"]");
                canMeth.remove();
            }
        }*/
        SelectedCanonicizerMap.remove(key);
        this.canItems = FXCollections.observableArrayList(this.canonName);
        this.selItems = FXCollections.observableArrayList(SelectedCanonicizerMap.keySet().parallelStream().toList());
        this.canList.setItems(this.canItems);
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
        comboBox.getSelectionModel().select(0);
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
    public static HashMap<String,Pair<Canonicizer, Object>> getSelectedCanList(){
        return SelectedCanonicizerMap;
    }

}
