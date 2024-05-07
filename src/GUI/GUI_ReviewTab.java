package GUI;
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
    /**
     * Constructor for the class.
     */
    public GUI_ReviewTab(){
        this.box = new VBox();
        build_pane();
    }
    /**
     * Builds the window row by row.
     */
    private void build_pane(){
        this.box.getChildren().add(init_firstRow());
        this.box.getChildren().add(init_secondRow());
        this.box.getChildren().add(init_bottomButtons());
    }
    /**
     * Builds the 'Tope Row' of GUI elements.
     * @return VBox
     */
    private VBox init_firstRow() {
      VBox box = new VBox(5);
      Label can = new Label("Canonicizer");
      can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
      TableView<Object> table = new TableView<Object>();
      TableColumn<Object, String> column1 = new TableColumn<Object, String>("Title");
      table.prefHeightProperty().bind(this.box.heightProperty());
      table.prefWidthProperty().bind(this.box.widthProperty());
      column1.prefWidthProperty().bind(table.widthProperty());
      table.getColumns().add(column1);
      box.getChildren().addAll(can,table);
      return box;
   }
   /**
    * Builds the 'Second Row' of GUI elements.
    * @return HBox
    */
    private HBox init_secondRow(){
        HBox box = new HBox(5);
        VBox edBox = new VBox();
        VBox ecBox = new VBox();
        VBox anBox = new VBox();
        Label ed = new Label("Event Driver");
        Label ec = new Label("Event Culling");
        Label an = new Label("Analysis Method");
        an.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        ed.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        ec.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        edBox.getChildren().addAll(ed, init_eventDriverTable());
        ecBox.getChildren().addAll(ec, init_eventCullingTable());
        anBox.getChildren().addAll(an, init_analysisTable());

        box.getChildren().addAll(edBox, ecBox, anBox);
        return box;
     }
     /**
      * Builds the 'Common' bottom buttons of the GUI.
      * @return HBox
      */
    private HBox init_bottomButtons(){
        HBox box = new HBox(5);
        Button finish = new Button("Finish & Review");
        Button next = new Button("Next");
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        box.getChildren().add(region1);
        box.getChildren().add(finish);
        box.getChildren().add(next);
        return box;
     }
     /**
      * Method for generating the Selected Event Driver Table.
      * @return TableView<Object>
      */
     private TableView<Object> init_eventDriverTable() {
        TableView<Object> table = new TableView<Object>();
        TableColumn<Object, String> column1 = new TableColumn<Object, String>("Title");
        table.prefHeightProperty().bind(this.box.heightProperty());
        table.prefWidthProperty().bind(this.box.widthProperty());
        column1.prefWidthProperty().bind(table.widthProperty());
        table.getColumns().add(column1);
        return table;
     }
      /**
      * Method for generating the Selected Event Culling Table.
      * @return TableView<Object>
      */
     private TableView<Object> init_eventCullingTable() {
        TableView<Object> table = new TableView<Object>();
        TableColumn<Object, String> column1 = new TableColumn<Object, String>("Title");
        table.prefHeightProperty().bind(this.box.heightProperty());
        table.prefWidthProperty().bind(this.box.widthProperty());
        column1.prefWidthProperty().bind(table.widthProperty());
        table.getColumns().add(column1);
        return table;
     }
      /**
      * Method for generating the Selected Analysis Method Table.
      * @return TableView<Object>
      */
     private TableView<Object> init_analysisTable() {
        TableView<Object> table = new TableView<Object>();
        TableColumn<Object, String> column1 = new TableColumn<Object, String>("Title");
        table.prefHeightProperty().bind(this.box.heightProperty());
        table.prefWidthProperty().bind(this.box.widthProperty());
        column1.prefWidthProperty().bind(table.widthProperty());
        table.getColumns().add(column1);
        return table;
     }
     /**
      * Getter for getting the built Pane.
      * @return
      */
     public VBox getPane(){
        return this.box;
     }
}