import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GUI_MainWindow extends Application{
    private static BorderPane pane;

    public void start(Stage mainStage) {
        mainStage.setOnCloseRequest(e -> {
            e.consume();
            Platform.exit();
            System.exit(0);
        });
        mainStage.setTitle("JGAAP 2.0");
        mainStage.setScene(initMainScene());
        mainStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    private Scene initMainScene(){
        TabPane tabPane = new TabPane();
        Scene scene;
        MenuBar bar = init_MenuBar();
        VBox docPane, canPane;

        Tab doc = new Tab("Documents");
        Tab canon = new Tab("Canonicizers");
        Tab eve = new Tab("Event Culling");
        Tab anMeth = new Tab("Analysis Methods");
        Tab review = new Tab("Review & Process");

        doc.setClosable(false);
        canon.setClosable(false);
        eve.setClosable(false);
        anMeth.setClosable(false);
        review.setClosable(false);

        pane = new BorderPane();
        
        //tabPane.setPadding(new Insets(5));
        tabPane.prefHeightProperty().bind(pane.heightProperty());
        tabPane.prefWidthProperty().bind(pane.widthProperty());
        GUI_DocTab docTab = new GUI_DocTab();
        GUI_CanTab canTab = new GUI_CanTab();

        docPane = docTab.getPane();
        canPane = canTab.getPane();
        docPane.setPadding(new Insets(5));
        canPane.setPadding(new Insets(5));
        docPane.prefHeightProperty().bind(tabPane.heightProperty());
        docPane.prefWidthProperty().bind(tabPane.widthProperty());
        canPane.prefHeightProperty().bind(tabPane.heightProperty());
        canPane.prefWidthProperty().bind(tabPane.widthProperty());
        doc.setContent(docPane);
        canon.setContent(canPane);
        

        tabPane.getTabs().add(doc);
        tabPane.getTabs().add(canon);
        tabPane.getTabs().add(eve);
        tabPane.getTabs().add(anMeth);
        tabPane.getTabs().add(review);


        pane.setTop(bar);
        pane.setCenter(tabPane);

        scene = new Scene(pane, 1000, 600);


        return scene;
    }
     private MenuBar init_MenuBar() {
        MenuBar bar = new MenuBar();

        Menu file = new Menu("File");
        Menu help = new Menu("Help");

        bar.getMenus().add(file);
        bar.getMenus().add(help);

        return bar;
     }
}