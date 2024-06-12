package com.jgaap.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Calendar;

import java.text.SimpleDateFormat;

public class GUI_ResultsWindow {

    private Stage stage;
    //private BorderPane pane;
    private TabPane tab;
    private ArrayList<Tab> tabs;

    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    public GUI_ResultsWindow(){
        this.tabs = new ArrayList<Tab>();
        this.tab = new TabPane();
        this.stage = new Stage();
        this.stage.setScene(build_scene());

    }
    private Scene build_scene(){
        Scene scene;
        this.tab = new TabPane();
        scene = new Scene(this.tab, 1000, 600);
        return scene;
    }
    private HBox init_button(){
        HBox buttonBox = new HBox();
        Button clear = new Button("Clear");
        clear.setOnAction(e -> {
            this.tabs.clear();
            this.tab.getTabs().clear();
            this.stage.close();
        });
        buttonBox.getChildren().add(clear);
        return buttonBox;
    }
    public void build_resultTab(String results){
        VBox box = new VBox();
        TextArea area = new TextArea();
        Tab resultTab = new Tab(now());
        area.setText(results);
        box.getChildren().addAll(area, init_button());
        box.prefHeightProperty().bind(this.tab.prefHeightProperty());
        box.prefWidthProperty().bind(this.tab.prefWidthProperty());
        resultTab.setContent(box);
        this.tabs.add(resultTab);
        this.tab.getTabs().add(resultTab);
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
