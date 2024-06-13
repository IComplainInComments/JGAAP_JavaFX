package com.jgaap.GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

public class GUI_ResultsWindow {

    private Stage stage;
    private TabPane tabs;
    private ArrayList<String> resultStorage;
    private static Logger logger;

    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    public GUI_ResultsWindow(){
        logger = Logger.getLogger(GUI_ResultsWindow.class);
        this.resultStorage = new ArrayList<String>();
        this.stage = new Stage();
        this.stage.setTitle("Results Window");
        this.stage.setScene(build_scene());

    }
    private Scene build_scene(){
        Scene scene;
        VBox box = new VBox(5);
        this.tabs = new TabPane();
        this.tabs.prefHeightProperty().bind(box.prefHeightProperty());
        this.tabs.prefWidthProperty().bind(box.prefWidthProperty());
        VBox.setVgrow(this.tabs, Priority.ALWAYS);
        box.getChildren().addAll(this.tabs);
        box.setMinSize(stage.getHeight(), stage.getWidth());

        scene = new Scene(box, 700, 900);
        return scene;
    }
    private HBox init_button(){
        HBox buttonBox = new HBox(5);
        Region region1 = new Region();
        Button clear = new Button("Clear");
        clear.setOnAction(e -> {
            this.resultStorage.clear();
            this.tabs.getTabs().clear();
            stage.close();
            e.consume();
        });
        HBox.setHgrow(region1, Priority.ALWAYS);
        buttonBox.getChildren().addAll(region1,clear);
        buttonBox.autosize();
        return buttonBox;
    }
    public void build_resultTab(String results){
        String now = now();
        VBox box = build_tab(results);
        Tab resultTab = new Tab(now);
        logger.info("Adding "+now);
        this.resultStorage.add(results);
        box.prefHeightProperty().bind(this.tabs.heightProperty());
        box.prefWidthProperty().bind(this.tabs.widthProperty());
        box.setPadding(new Insets(5));
        resultTab.setContent(box);

        this.tabs.getTabs().add(resultTab);
        
    }
    private VBox build_tab(String results){
        TextArea area = new TextArea();
        VBox box = new VBox(5);
        area.wrapTextProperty().set(true);
        area.setText(results);
        area.prefHeightProperty().bind(box.heightProperty());
        area.prefWidthProperty().bind(box.widthProperty());
        box.getChildren().addAll(area, init_button());
        return box;
    }
    public static String now(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }
    public void showStage(){
        this.stage.show();
    }
    public void hideStage(){
        this.stage.hide();
    }
    
}
