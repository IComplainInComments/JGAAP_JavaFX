package GUI;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
/**
 * About Window Class.
 * This Class creates the Stage for the About Window and it's GUI elements.
 */
public class GUI_JGAAPAboutWindow {
     private static Stage stage;
     /**
      * Constructor for the class.
      */

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
    /**
     * Builds the Window.
     */
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
    /**
     * Controls showing or closing (Hiding) the window.
     */
    public void show(){
        stage.show();
    }
}
