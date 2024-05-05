import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class GUI_NotesWindow{

    private Scene scene;

    public GUI_NotesWindow(){
        build_scene();
    }
    public void build_scene(){
        VBox box = new VBox();
        TextArea area = new TextArea();

        box.getChildren().addAll(area,init_bottomButtons());

        this.scene = new Scene(box, 600, 600);

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
    public Scene getScene(){
        return this.scene;
    }
}