import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
        mainStage.setScene(init_mainScene());
        mainStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    private Scene init_mainScene(){
        TabPane tabPane = new TabPane();
        Scene scene;
        MenuBar bar = init_MenuBar();
        VBox docPane, canPane, edPane, ecPane, anPane,reviewPane;

        Tab doc = new Tab("Documents");
        Tab canon = new Tab("Canonicizers");
        Tab eve = new Tab("Event Driver");
        Tab evecul = new Tab("Event Culling");
        Tab anMeth = new Tab("Analysis Methods");
        Tab review = new Tab("Review & Process");

        doc.setClosable(false);
        canon.setClosable(false);
        eve.setClosable(false);
        evecul.setClosable(false);
        anMeth.setClosable(false);
        review.setClosable(false);

        pane = new BorderPane();
        
        //tabPane.setPadding(new Insets(5));
        tabPane.prefHeightProperty().bind(pane.heightProperty());
        tabPane.prefWidthProperty().bind(pane.widthProperty());
        GUI_DocTab docTab = new GUI_DocTab();
        GUI_CanTab canTab = new GUI_CanTab();
        GUI_EDTab edTab = new GUI_EDTab();
        GUI_ECTab ecTab = new GUI_ECTab();
        GUI_AnalysisTab anTab = new GUI_AnalysisTab();
        GUI_ReviewTab reviewTab = new GUI_ReviewTab();

        docPane = docTab.getPane();
        canPane = canTab.getPane();
        edPane = edTab.getPane();
        ecPane = ecTab.getPane();
        anPane = anTab.getPane();
        reviewPane = reviewTab.getPane();
        docPane.setPadding(new Insets(5));
        canPane.setPadding(new Insets(5));
        edPane.setPadding(new Insets(5));
        ecPane.setPadding(new Insets(5));
        anPane.setPadding(new Insets(5));
        reviewPane.setPadding(new Insets(5));
        docPane.prefHeightProperty().bind(tabPane.heightProperty());
        docPane.prefWidthProperty().bind(tabPane.widthProperty());
        canPane.prefHeightProperty().bind(tabPane.heightProperty());
        canPane.prefWidthProperty().bind(tabPane.widthProperty());
        edPane.prefHeightProperty().bind(tabPane.heightProperty());
        edPane.prefWidthProperty().bind(tabPane.widthProperty());
        ecPane.prefHeightProperty().bind(tabPane.heightProperty());
        ecPane.prefWidthProperty().bind(tabPane.widthProperty());
        anPane.prefHeightProperty().bind(tabPane.heightProperty());
        anPane.prefWidthProperty().bind(tabPane.widthProperty());
        reviewPane.prefHeightProperty().bind(tabPane.heightProperty());
        reviewPane.prefWidthProperty().bind(tabPane.widthProperty());
        doc.setContent(docPane);
        canon.setContent(canPane);
        eve.setContent(edPane);
        evecul.setContent(ecPane);
        anMeth.setContent(anPane);
        review.setContent(reviewPane);
        

        tabPane.getTabs().add(doc);
        tabPane.getTabs().add(canon);
        tabPane.getTabs().add(eve);
        tabPane.getTabs().add(evecul);
        tabPane.getTabs().add(anMeth);
        tabPane.getTabs().add(review);


        pane.setTop(bar);
        pane.setCenter(tabPane);

        scene = new Scene(pane, 1000, 600);


        return scene;
    }
     private MenuBar init_MenuBar() {
        Menu file = new Menu("File");
        Menu help = new Menu("Help");
        Menu batch = new Menu("Batch Documents");
        Menu aaac = new Menu("AAAC Problems");
        MenuItem about = new MenuItem("About");
        MenuBar bar = new MenuBar();
        GUI_MenuItemsBatch items = new GUI_MenuItemsBatch();
        
        aaac.getItems().addAll(); //Add items when GUI_MenuItemsAAAC is made
        batch.getItems().addAll(items.getItems());
        file.getItems().addAll(batch,aaac);
        help.getItems().add(about);
        bar.getMenus().add(file);
        bar.getMenus().add(help);

        return bar;
     }
}