import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI_NotesWindow{

    private static Stage stage;
    private static Button notes;
    private static TextArea area;
    private static String text;

    public GUI_NotesWindow(){
        stage = new Stage();
        stage.setTitle("Notes");
        stage.hide();
        build_stage();
        stage.setOnCloseRequest(e -> {
            area.setText(text);
            stage.close();
            e.consume();
        });
    }
    public void build_stage(){
        Scene scene;
        VBox box = new VBox();
        area = new TextArea();
        notes = new Button("Notes");
        text = "";

        scene = new Scene(box, 600, 600);

        area.prefHeightProperty().bind(scene.heightProperty());
        area.prefWidthProperty().bind(scene.widthProperty());
        notes.setOnAction(e -> {
            if(e.getTarget().toString().contains("Notes")){
                if(!stage.isShowing()){
                    stage.show();
                    e.consume();
                } else {
                    e.consume();
                }
            }
        });

        box.getChildren().addAll(area,init_bottomButtons());


        stage.setScene(scene);
    }
    private HBox init_bottomButtons(){
        HBox box = new HBox();
        Button ok = new Button("OK");
        Button cancel = new Button("Cancel");
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        ok.setOnAction(e -> {
            if(e.getTarget().toString().contains("OK")){
                text = area.getText();
                e.consume();
            }
        });
        cancel.setOnAction(e -> {
            if(e.getTarget().toString().contains("Cancel")){
                area.setText(text);
                stage.hide();
                e.consume();
            }
        });

        box.getChildren().addAll(region,ok,cancel);
        
        return box;

    }
    public void show(){
        stage.show();
    }
    public void hide(){
        stage.hide();
    }
    public void close(){
        stage.close();
    }
    public String getNotes(){
        return text;
    }
    public Button getButton(){
        return notes;
    }
}