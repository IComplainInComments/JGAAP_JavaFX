package com.jgaap.GUI;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.core.appender.OutputStreamAppender;

public class GUI_LogWindow {

    private static Stage stage;
    private static TextArea area;
    private static Logger logs;
    public GUI_LogWindow(Logger log){
        logs = log;
        stage = new Stage();
        area = new TextArea();
        stage.setScene(build_scene());
    }
    public Scene build_scene(){
        VBox box = new VBox();
        Scene scene;
        area.prefHeightProperty().bind(box.prefHeightProperty());
        area.prefWidthProperty().bind(box.prefWidthProperty());
        box.getChildren().addAll(area);

        scene = new Scene(box, 500, 500);
        return scene;
    }
    public void updateWindow(){
    }
    public void showStage(){
        stage.show();
    }
    public void hideStage(){
        stage.hide();
    }
    
}
