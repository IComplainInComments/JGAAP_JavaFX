import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    public GUI_NotesWindow(){
        stage = new Stage();
        stage.setTitle("Notes");
        build_stage();
        init_noteButton();
    }
    public void build_stage(){
        VBox box = new VBox();
        TextArea area = new TextArea();
        Scene scene;

        box.getChildren().addAll(area,init_bottomButtons());

        scene = new Scene(box, 600, 600);

        stage.setScene(scene);
    }
    private HBox init_bottomButtons(){
        HBox box = new HBox();
        Button ok = new Button("OK");
        Button cancel = new Button("Cancel");
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        box.getChildren().addAll(region,ok,cancel);
        
        return box;

    }
    private void init_noteButton(){
        notes = new Button("Notes");
        notes.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(stage.isShowing()){
                    stage.hide();
                } else {
                    stage.show();
                }
            }
        });
    }
    public Button getButton(){
        return notes;
    }
}