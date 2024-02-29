import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
//import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class GUI_MainWindow extends Application{
    private static GridPane grid;
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
        GridPane docGrid;
        grid = new GridPane();

        //grid.setHgap(10);
        //grid.setVgap(10);
        //grid.setPadding(new Insets(5, 5, 5, 5));

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

        tabPane.prefHeightProperty().bind(grid.heightProperty());
        tabPane.prefWidthProperty().bind(grid.widthProperty());
        GUI_DocTab docTab = new GUI_DocTab();

        docGrid = docTab.getGridPane();
        docGrid.prefHeightProperty().bind(tabPane.heightProperty());
        docGrid.prefWidthProperty().bind(tabPane.widthProperty());
        doc.setContent(docGrid);
        

        tabPane.getTabs().add(doc);
        tabPane.getTabs().add(canon);
        tabPane.getTabs().add(eve);
        tabPane.getTabs().add(anMeth);
        tabPane.getTabs().add(review);

        GridPane.setConstraints(bar, 0, 0);
        GridPane.setConstraints(tabPane, 0, 1);
        grid.getChildren().add(bar);
        grid.getChildren().add(tabPane);

        scene = new Scene(grid, 1000, 600);

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