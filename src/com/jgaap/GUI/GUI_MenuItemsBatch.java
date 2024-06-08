package com.jgaap.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


import org.apache.log4j.Logger;

import com.jgaap.JGAAPConstants;
import com.jgaap.backend.API;
import com.jgaap.backend.CSVIO;
import com.jgaap.backend.Utils;
import com.jgaap.util.Document;

import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Menu Item Batch Class File.
 * This class is used to generate the menu items under File/Help that appear in
 * the menu bar.
 */
public class GUI_MenuItemsBatch {

    static Logger logger;
    private List<MenuItem> items;
    private List<MenuItem> problems;
    private List<String> docTypes;
    private static Stage mainStageRef;
    private static API JGAAP_API;
    private File filepath;

    /**
     * Constructor of the Class
     */
    public GUI_MenuItemsBatch(Stage stage, API JAPI) {
        this.items = new ArrayList<MenuItem>();
        this.problems = new ArrayList<MenuItem>();
        this.docTypes = new ArrayList<String>();
        logger = Logger.getLogger(GUI_MenuItemsBatch.class);
        JGAAP_API = JAPI;
        mainStageRef = stage;
        genDocTypes();
        genItems();
        genProblems();
    }

    /**
     * Generates the Menu Items for the "File" Menu
     */
    private void genItems(){
        MenuItem save = new MenuItem("Save Documents");
        MenuItem load = new MenuItem("Load Documents");
        save.setOnAction(e -> {
            ExtensionFilter filter = new ExtensionFilter("Text Documents", this.docTypes);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Resource File");
            fileChooser.getExtensionFilters().addAll(filter);
			try {
                List<Document> DocumentList = new ArrayList<Document>();
				JGAAP_API.loadDocuments();
				DocumentList = JGAAP_API.getDocuments();
                if(DocumentList != null){
                    Utils.saveDocumentsToCSV(DocumentList,
						fileChooser.showSaveDialog(mainStageRef));
                }
			} catch (Exception ex) {
                if(this.filepath != null){
                    ex.printStackTrace();
                    Alert error = new Alert(AlertType.ERROR, ex.getMessage());
                    error.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> error.close());
                }
            }
        });
        load.setOnAction(e -> {
            ExtensionFilter filter = new ExtensionFilter("Text Documents", this.docTypes);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(filter);
		    //if (choice == JFileChooser.APPROVE_OPTION) {
			    try {
				    this.filepath = fileChooser.showOpenDialog(mainStageRef);
                    if(fileChooser != null){
                        List<List<String>> DocumentCSVs = CSVIO.readCSV((InputStream)new FileInputStream(this.filepath));
                        for (int i = 0; i < DocumentCSVs.size(); i++) {
                            JGAAP_API.addDocument(DocumentCSVs.get(i).get(1),
                                DocumentCSVs.get(i).get(0), (DocumentCSVs.get(i)
                                        .size() > 2 ? DocumentCSVs.get(i).get(2)
                                        : null));
                    }
				}
				    //UpdateKnownDocumentsTree();
				    //UpdateUnknownDocumentsTable();
			    }catch (Exception ex) {
                    if(this.filepath != null){
                        ex.printStackTrace();
                        Alert error = new Alert(AlertType.ERROR, ex.getMessage());
                        error.showAndWait()
                            .filter(response -> response == ButtonType.OK)
                            .ifPresent(response -> error.close());
                    }
			    }
		});
        /*
         * Insert code for the menu items to do something here at a later date.
         */
        this.items.add(save);
        this.items.add(load);
    }

    /**
     * Generates the Menu Items for the "AAC Problems" Menu
     */
    private void genProblems() {
        MenuItem pa = new MenuItem("Problem A");
        MenuItem pb = new MenuItem("Problem B");
        MenuItem pc = new MenuItem("Problem C");
        MenuItem pd = new MenuItem("Problem D");
        MenuItem pe = new MenuItem("Problem E");
        MenuItem pf = new MenuItem("Problem F");
        MenuItem pg = new MenuItem("Problem G");
        MenuItem ph = new MenuItem("Problem H");
        MenuItem pi = new MenuItem("Problem I");
        MenuItem pj = new MenuItem("Problem J");
        MenuItem pk = new MenuItem("Problem K");
        MenuItem pl = new MenuItem("Problem L");
        MenuItem pm = new MenuItem("Problem M");
        ArrayList<String> probList = new ArrayList<String>();
        probList.add("A");
        probList.add("B");
        probList.add("C");
        probList.add("D");
        probList.add("E");
        probList.add("F");
        probList.add("G");
        probList.add("H");
        probList.add("I");
        probList.add("J");
        probList.add("K");
        probList.add("L");
        probList.add("M");
        this.problems.add(pa);
        this.problems.add(pb);
        this.problems.add(pc);
        this.problems.add(pd);
        this.problems.add(pe);
        this.problems.add(pf);
        this.problems.add(pg);
        this.problems.add(ph);
        this.problems.add(pi);
        this.problems.add(pj);
        this.problems.add(pk);
        this.problems.add(pl);
        this.problems.add(pm);
        Iterator<MenuItem> iterMenu = this.problems.iterator();
        Iterator<String> iterString = probList.iterator();
        while (iterMenu.hasNext() && iterString.hasNext()) {
            MenuItem item = iterMenu.next();
            item.setOnAction(e -> {
                loadAAACProblem(iterString.next());
            });
        }

    }

    private void genDocTypes() {
        this.docTypes.add("*.pdf");
        this.docTypes.add("*.doc");
        this.docTypes.add("*.txt");
        this.docTypes.add("*.html");
    }

    private void loadAAACProblem(String problem) {
        String filepath;
        filepath = JGAAPConstants.JGAAP_RESOURCE_PACKAGE + "aaac/problem"
                + problem + "/load" + problem + ".csv";
        List<Document> documents = Collections.emptyList();
        try {
            documents = Utils.getDocumentsFromCSV(CSVIO
                    .readCSV(com.jgaap.JGAAP.class
                            .getResourceAsStream(filepath.toString())));
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert error = new Alert(AlertType.ERROR, ex.getMessage());
            error.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> error.close());
        }
        for (Document document : documents) {
            JGAAP_API.addDocument(document);
        }
        // UpdateKnownDocumentsTree();
        // UpdateUnknownDocumentsTable();
    }

    /**
     * Getter Function for the "File" Menu Items
     * 
     * @return List
     */
    public List<MenuItem> getItems() {
        return this.items;
    }

    /**
     * Getter Function for the "AAAC Problem" Menu Items
     * 
     * @return List
     */
    public List<MenuItem> getProblems() {
        return this.problems;
    }

    public File getFile() {
        return this.filepath;
    }
}