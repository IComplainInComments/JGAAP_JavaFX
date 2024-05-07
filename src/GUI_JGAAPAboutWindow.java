import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GUI_JGAAPAboutWindow {
     private static Stage stage;

    public GUI_JGAAPAboutWindow(){
        stage = new Stage();
        stage.setTitle("Notes");
        stage.hide();
        build_stage();
        stage.setOnCloseRequest(e -> {
            stage.close();
            e.consume();
        });
    }
    private void build_stage(){
        String jgaap = "JGAAP 2.0";
        Scene scene;
        VBox box = new VBox(5);
        Label aboutLabel = new Label(jgaap);
        Button close = new Button("Close");

        aboutLabel.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        scene = new Scene(box, 600, 600);

        close.setOnAction(e -> {
            if(e.getTarget().toString().contains("Notes")){
                stage.close();
                e.consume();
            }
        });

        box.getChildren().addAll(aboutLabel);
        stage.setScene(scene);
    }
    public void show(){
        stage.show();
    }
}
