import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GUI_ReviewTab{
    private VBox box;
    public GUI_ReviewTab(){
        this.box = new VBox();
        this.box.setSpacing(5);
        build_pane();
    }
    private void build_pane(){
        this.box.getChildren().add(init_canonicizersTable());
        this.box.getChildren().add(init_SecondRow());
        this.box.getChildren().add(init_bottomButtons());
    }
    private HBox init_SecondRow(){
        HBox box = new HBox();
        VBox edBox = new VBox();
        VBox ecBox = new VBox();
        VBox anBox = new VBox();
        Label ed = new Label("Canonicizer Description");
        Label ec = new Label("Canonicizer Description");
        Label an = new Label("Canonicizer Description");
        an.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        ed.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        ec.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        edBox.getChildren().addAll(ed, init_eventDriverTable());
        ecBox.getChildren().addAll(ec, init_eventCullingTable());
        anBox.getChildren().addAll(an, init_analysisTable());

        box.getChildren().addAll(edBox, ecBox, anBox);
        return box;
     }
     private VBox init_canonicizersTable() {
        VBox box = new VBox();
        Label can = new Label("Canonicizer Description");
        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        TableView<Object> table = new TableView<Object>();
        TableColumn<Object, String> column1 = new TableColumn<Object, String>("Title");
        column1.prefWidthProperty().bind(table.widthProperty());
        table.getColumns().add(column1);
        box.getChildren().addAll(can,table);
        return box;
     }
    private HBox init_bottomButtons(){
        HBox box = new HBox();
        Button finish = new Button("Finish & Review");
        Button next = new Button("Next");
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        box.getChildren().add(region1);
        box.getChildren().add(finish);
        box.getChildren().add(next);
        box.setSpacing(10);
        return box;
     }
     private TableView<Object> init_eventDriverTable() {
        TableView<Object> table = new TableView<Object>();
        TableColumn<Object, String> column1 = new TableColumn<Object, String>("Title");
        column1.prefWidthProperty().bind(table.widthProperty());
        table.getColumns().add(column1);
        return table;
     }
     private TableView<Object> init_eventCullingTable() {
        TableView<Object> table = new TableView<Object>();
        TableColumn<Object, String> column1 = new TableColumn<Object, String>("Title");
        column1.prefWidthProperty().bind(table.widthProperty());
        table.getColumns().add(column1);
        return table;
     }
     private TableView<Object> init_analysisTable() {
        TableView<Object> table = new TableView<Object>();
        TableColumn<Object, String> column1 = new TableColumn<Object, String>("Title");
        column1.prefWidthProperty().bind(table.widthProperty());
        table.getColumns().add(column1);
        return table;
     }
     public VBox getPane(){
        return this.box;
     }
}