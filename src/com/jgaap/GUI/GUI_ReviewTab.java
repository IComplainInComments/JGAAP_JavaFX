package com.jgaap.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import java.util.ArrayList;

import com.jgaap.backend.API;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventDriver;
import com.jgaap.util.Document;
import com.jgaap.util.Pair;
import com.jgaap.generics.Canonicizer;

/**
 * Review Tab Class.
 * This Class creates the scene for the Review Tab and it's GUI elements.
 */
public class GUI_ReviewTab {

   private ListView<String> anList;
   private ListView<String> ecList;
   private ListView<String> edList;
   private ListView<String> canList;
   private ObservableList<String> anItems;
   private ObservableList<String> ecItems;
   private ObservableList<String> edItems;
   private ObservableList<String> canItems;
   private ArrayList<String> canVals;
   private ArrayList<String> ecVals;
   private ArrayList<String> edVals;
   private ArrayList<String> anVals;
   private List<Pair<Canonicizer, Object>> SelectedCanonicizerList;
   private GUI_ResultsWindow res;
   private VBox box;
   private HBox bottomButtons;
   private static Logger logger;
   private static API JAPI;

   /**
    * Constructor for the class.
    */
   public GUI_ReviewTab() {
      logger = Logger.getLogger(GUI_ReviewTab.class);
      this.SelectedCanonicizerList = new ArrayList<Pair<Canonicizer, Object>>();
      this.box = new VBox();
      this.res = new GUI_ResultsWindow();
      this.res.hideStage();
      this.SelectedCanonicizerList = new ArrayList<Pair<Canonicizer, Object>>();
      JAPI = API.getInstance();
   }
   public GUI_ReviewTab(GUI_ResultsWindow grw) {
      logger = Logger.getLogger(GUI_ReviewTab.class);
      this.SelectedCanonicizerList = new ArrayList<Pair<Canonicizer, Object>>();
      this.box = new VBox();
      this.res = grw;
      this.SelectedCanonicizerList = new ArrayList<Pair<Canonicizer, Object>>();
      JAPI = API.getInstance();
   }

   /**
    * Builds the window row by row.
    */
   private void build_pane() {
      this.box.getChildren().add(init_firstRow());
      this.box.getChildren().add(init_secondRow());
      this.box.getChildren().add(init_bottomButtons());
   }

   /**
    * Builds the 'Tope Row' of GUI elements.
    * 
    * @return VBox
    */
   private VBox init_firstRow() {
      VBox box = new VBox(5);
      Label can = new Label("Canonicizer");
      can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

      box.getChildren().addAll(can, init_canonicizerTable());
      return box;
   }

   /**
    * Builds the 'Second Row' of GUI elements.
    * 
    * @return HBox
    */
   private HBox init_secondRow() {
      HBox box = new HBox(5);
      VBox edBox = new VBox();
      VBox ecBox = new VBox();
      VBox anBox = new VBox();
      Label ed = new Label("Event Driver");
      Label ec = new Label("Event Culling");
      Label an = new Label("Analysis Method");
      an.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
      ed.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
      ec.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

      edBox.getChildren().addAll(ed, init_eventDriverTable());
      ecBox.getChildren().addAll(ec, init_eventCullingTable());
      anBox.getChildren().addAll(an, init_analysisTable());

      box.getChildren().addAll(edBox, ecBox, anBox);
      return box;
   }

   /**
    * Builds the 'Common' bottom buttons of the GUI.
    * 
    * @return HBox
    */
   private HBox init_bottomButtons() {
      HBox box = new HBox(5);
      Button process = new Button("Process");
      Region region1 = new Region();
      HBox.setHgrow(region1, Priority.ALWAYS);
      process.setOnAction(e -> {
         try {
            JAPI.clearData();
            JAPI.clearCanonicizers();
            this.SelectedCanonicizerList = GUI_CanTab.getSelectedCanList();
            for(Pair<Canonicizer, Object> canonicizerPair : this.SelectedCanonicizerList){
               if(canonicizerPair.getSecond() instanceof Document.Type){
                  JAPI.addCanonicizer(canonicizerPair.getFirst().displayName(), (Document.Type)canonicizerPair.getSecond());
               } else if(canonicizerPair.getSecond() instanceof Document){
                  JAPI.addCanonicizer(canonicizerPair.getFirst().displayName(), (Document)canonicizerPair.getSecond());
               } else {
                  JAPI.addCanonicizer(canonicizerPair.getFirst().displayName());
               }
            }
            JAPI.execute();
            List<Document> documents = JAPI.getDocuments();
            StringBuilder buffer = new StringBuilder();
            for (Document document : documents) {
               String result = document.getResult();
               buffer.append(result);
            }
            buffer.append("dicks");
            this.res.build_resultTab(buffer.toString());
            this.res.showStage();

         } catch (Exception e1) {
            logger.error(e1.getCause(), e1);
            e1.printStackTrace();
         }
         e.consume();
      });
      box.getChildren().add(region1);
      box.getChildren().add(process);
      return box;
   }

   private ListView<String> init_canonicizerTable() {
      this.canList = new ListView<String>();
      Iterator<Document> iter = JAPI.getDocuments().iterator();
      this.canVals = new ArrayList<String>();
      while (iter.hasNext()) {
         Document docItem = iter.next();
         Iterator<Canonicizer> iter2 = docItem.getCanonicizers().iterator();
         while (iter2.hasNext()) {
            Canonicizer temp = iter2.next();
            Pair<Canonicizer, Object> thing = new Pair<Canonicizer,Object>(temp, docItem);
            this.canVals.add(temp.displayName());
            this.SelectedCanonicizerList.add(thing);
         }
      }
      this.canItems = FXCollections.observableArrayList(this.canVals);
      this.canList.setItems(this.canItems);
      this.canList.prefHeightProperty().bind(this.box.prefHeightProperty());
      this.canList.prefWidthProperty().bind(this.box.prefWidthProperty());
      this.canList.refresh();
      return this.canList;
   }

   /**
    * Method for generating the Selected Event Driver Table.
    * 
    * @return TableView<Object>
    */
   private ListView<String> init_eventDriverTable() {
      this.edList = new ListView<String>();
      Iterator<EventDriver> iter = JAPI.getEventDrivers().iterator();
      this.edVals = new ArrayList<String>();
      while (iter.hasNext()) {
         edVals.add(iter.next().displayName());
      }
      this.edItems = FXCollections.observableArrayList(this.edVals);
      this.edList.setItems(this.edItems);
      this.edList.prefHeightProperty().bind(this.box.prefHeightProperty());
      this.edList.prefWidthProperty().bind(this.box.prefWidthProperty());
      this.edList.refresh();
      return this.edList;
   }

   /**
    * Method for generating the Selected Event Culling Table.
    * 
    * @return TableView<Object>
    */
   private ListView<String> init_eventCullingTable() {
      this.ecList = new ListView<String>();
      Iterator<EventCuller> iter = JAPI.getEventCullers().iterator();
      this.ecVals = new ArrayList<String>();
      while (iter.hasNext()) {
         ecVals.add(iter.next().displayName());
      }
      this.ecItems = FXCollections.observableArrayList(this.ecVals);
      this.ecList.setItems(this.ecItems);
      this.ecList.prefHeightProperty().bind(this.box.prefHeightProperty());
      this.ecList.prefWidthProperty().bind(this.box.prefWidthProperty());
      this.ecList.refresh();
      return this.ecList;
   }

   /**
    * Method for generating the Selected Analysis Method Table.
    * 
    * @return TableView<Object>
    */
   private ListView<String> init_analysisTable() {
      this.anList = new ListView<String>();
      Iterator<AnalysisDriver> iter = JAPI.getAnalysisDrivers().iterator();
      this.anVals = new ArrayList<String>();
      while (iter.hasNext()) {
         anVals.add(iter.next().displayName());
      }
      this.anItems = FXCollections.observableArrayList(this.anVals);
      this.anList.setItems(this.anItems);
      this.anList.prefHeightProperty().bind(this.box.prefHeightProperty());
      this.anList.prefWidthProperty().bind(this.box.prefWidthProperty());
      this.anList.refresh();
      return this.anList;
   }

   public void refresh_canList() {
      Iterator<Document> iter = JAPI.getDocuments().iterator();
      this.canVals.clear();
      while (iter.hasNext()) {
         Iterator<Canonicizer> iter2 = iter.next().getCanonicizers().iterator();
         while (iter2.hasNext()) {
            this.canVals.add(iter2.next().displayName());
         }
      }
      this.canItems = FXCollections.observableArrayList(this.canVals);
      this.canList.setItems(this.canItems);
   }

   public void refresh_edList() {
      Iterator<EventDriver> iter = JAPI.getEventDrivers().iterator();
      this.edVals.clear();
      while (iter.hasNext()) {
         edVals.add(iter.next().displayName());
      }
      this.edItems = FXCollections.observableArrayList(this.edVals);
      this.edList.setItems(this.edItems);
   }

   public void refresh_ecList() {
      Iterator<EventCuller> iter = JAPI.getEventCullers().iterator();
      this.ecVals.clear();
      while (iter.hasNext()) {
         ecVals.add(iter.next().displayName());
      }
      this.ecItems = FXCollections.observableArrayList(this.ecVals);
      this.ecList.setItems(this.ecItems);
   }

   public void refresh_anList() {
      Iterator<AnalysisDriver> iter = JAPI.getAnalysisDrivers().iterator();
      this.anVals.clear();
      while (iter.hasNext()) {
         anVals.add(iter.next().displayName());
      }
      this.anItems = FXCollections.observableArrayList(this.anVals);
      this.anList.setItems(this.anItems);
   }

   public void refreshView() {
      refresh_canList();
      refresh_edList();
      refresh_ecList();
      refresh_anList();

      this.anList.refresh();
      this.edList.refresh();
      this.ecList.refresh();
      this.canList.refresh();
   }

   /**
    * Getter for getting the built Pane.
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
}