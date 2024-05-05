import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GUI_AnalysisTab {
        private VBox box;

    public GUI_AnalysisTab(){
        box = new VBox();
        build_pane();
    }

    private void build_pane(){
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(init_bottomButtons());
    }

    private HBox init_rowOne(){
        HBox box = new HBox();
        VBox meth = new VBox();
        VBox sel = new VBox();
        VBox param = new VBox();
        VBox paraBoxChildOne = new VBox();
        VBox paraBoxChildTwo = new VBox();
        Button notes = new Button("Notes");
        Label an = new Label("Analysis Method");
        Label df = new Label("Distance Function");
        Label se = new Label("Selected");
        Label am = new Label("AM Parameters");
        Label dfp = new Label("DF Parameters");

        an.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        df.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        se.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        am.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        dfp.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        paraBoxChildOne.setStyle("-fx-border-color: black");
        paraBoxChildTwo.setStyle("-fx-border-color: black");

        meth.getChildren().addAll(an, init_AnalysisMethodBox(),df, init_DistanceFunctionBox());
        sel.getChildren().addAll(se, init_SelectedBox());
        param.getChildren().addAll(am,paraBoxChildOne,paraBoxChildTwo,dfp);

        box.getChildren().addAll(meth,init_rowOneButtons(),sel,param,notes);


        return box;
    }
    private HBox init_rowTwo(){
        HBox box = new HBox();
        VBox amd = new VBox();
        VBox dfd = new VBox();
        Label an = new Label("Analysis Method Description");
        Label df = new Label("Distance Function Description");
        TextArea anArea = new TextArea();
        TextArea dfArea = new TextArea();

        an.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        df.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        amd.getChildren().addAll(an,anArea);
        dfd.getChildren().addAll(df,dfArea);

        box.getChildren().addAll(amd,dfd);

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
    private ListView<String> init_SelectedBox(){
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
            "Single", "Double", "Suite", "Family App");

        list.setItems(items);
        list.prefHeightProperty().bind(this.box.heightProperty());
        list.prefWidthProperty().bind(this.box.widthProperty());

        return list;
    }
    private ListView<String> init_AnalysisMethodBox(){
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
            "Single", "Double", "Suite", "Family App");

        list.setItems(items);
        list.prefHeightProperty().bind(this.box.heightProperty());
        list.prefWidthProperty().bind(this.box.widthProperty());

        return list;
    }
    private ListView<String> init_DistanceFunctionBox(){
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
            "Single", "Double", "Suite", "Family App");

        list.setItems(items);
        list.prefHeightProperty().bind(this.box.heightProperty());
        list.prefWidthProperty().bind(this.box.widthProperty());

        return list;
    }
    private VBox init_rowOneButtons(){
        VBox box = new VBox();
        Button left = new Button("->");
        Button right = new Button("<-");
        Button clear = new Button("Clear");
        Button all = new Button("All");

        box.getChildren().add(left);
        box.getChildren().add(right);
        box.getChildren().add(all);
        box.getChildren().add(clear);
        box.setAlignment(Pos.TOP_CENTER);

        return box;
    }
     public VBox getPane(){
        return this.box;
    }
}
