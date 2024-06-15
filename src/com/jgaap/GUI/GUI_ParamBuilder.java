package com.jgaap.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import java.util.List;
import java.util.ArrayList;
import com.jgaap.backend.API;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.Parameterizable;

public class GUI_ParamBuilder<T> {
    
    private VBox panel;
    private ComboBox<String> com1;
    private ComboBox<String> com2;
    private ObservableList<String> list1;
    private ObservableList<String> list2;
    private String itemName;
    private String[] split;
    private String val1, val2;
    private int paramSize;
    private T item;
    private static API JAPI;

    public GUI_ParamBuilder(T item){
        this.item = item;
        this.list1 = null;
        this.list2 = null;
        JAPI = API.getInstance();
        this.itemName = this.item.getClass().getPackageName();
        String[] choice = this.itemName.split("\\.");
        setUp(choice[2]);
    }
    public void setUp(String kind){
        switch(kind){
            case "eventDrivers":
                EventDriver temp = (EventDriver)this.item;
                build_panel(temp.getParameters().replaceAll("\\s", ""));
                break;
            case "eventCullers":
                break;
            case "analysisDrivers":
                break;
            case "distanceFunctions":
                break;
            default:
                break;
        }
    }
    public void setUpAction(String kind, int amount){
        switch(kind){
            case "eventDrivers":
                if(amount == 2){
                    this.com1.setOnAction(e -> {
                        this.val1 = this.com1.getSelectionModel().getSelectedItem();
                    });
                }else if(amount == 4){
                    com1.setOnAction(e -> {
                        this.val1 = com1.getSelectionModel().getSelectedItem();
        
                    });
                    com2.setOnAction(e -> {
                        this.val2 = com2.getSelectionModel().getSelectedItem();
                    });
                }
                break;
            case "eventCullers":
                break;
            case "analysisDrivers":
                break;
            case "distanceFunctions":
                break;
            default:
                break;
        }
    }
    private void build_panel(String vars){
        this.split = vars.split("[\\,\\:]");
        Region region1;
        Region region2;
        Label label1;
        Label label2;
        this.panel = new VBox(5);
        this.paramSize = split.length;
        if(this.split.length == 2){
            label1 = build_title(this.split[0]);
            List<String> temp = new ArrayList<String>();
            for(int i = 0; i < 50; i++){
                temp.add(i+"");
            }
            setListTop(temp);
            this.com1 = build_comboBox();
            this.com1.setId("com1");
            setUpAction(this.itemName, paramSize);
            region1 = new Region();
            region2 = new Region();
            VBox.setVgrow(region1, Priority.ALWAYS);
            VBox.setVgrow(region2, Priority.ALWAYS);
            this.com1.setItems(this.list1);
            this.com1.getSelectionModel().select(this.split[1]);
            this.panel.getChildren().addAll(region1,label1, this.com1, region2);

        } else if(split.length == 4){
            label1 = build_title(this.split[0]);
            label2 = build_title(this.split[2]);
            List<String> temp = new ArrayList<String>();
            for(int i = 0; i < 50; i++){
                temp.add(i+"");
            }
            setListTop(temp);
            setListBottom(temp);
            ComboBox<String> com1 = build_comboBox();
            ComboBox<String> com2 = build_comboBox();
            com1.setId("com1");
            com2.setId("com2");
            setUpAction(this.itemName, paramSize);
            region1 = new Region();
            region2 = new Region();
            VBox.setVgrow(region1, Priority.ALWAYS);
            VBox.setVgrow(region2, Priority.ALWAYS);
            this.com1.setItems(this.list1);
            this.com2.setItems(this.list2);
            this.com1.getSelectionModel().select(this.split[1]);
            this.com2.getSelectionModel().select(this.split[3]);
            this.panel.getChildren().addAll(region1, label1, this.com1, label2, this.com2, region2);
        }
    }
    private Spinner<T> build_spinner(){
        Spinner<T> temp = new Spinner<T>();

        return temp;
    }
    private ComboBox<String> build_comboBox(){
        ComboBox<String> temp = new ComboBox<String>();

        return temp;
    }
    private Label build_title(String label){
        Label temp = new Label(label);
        temp.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 16));

        return temp;
    }
    public Parameterizable getParams(){
        if(this.itemName.equalsIgnoreCase("eventdrivers")){
            EventDriver temp = (EventDriver)this.item;
            return Parameterizable.converToParameters(temp.getParameters());
        }else if(this.itemName.equalsIgnoreCase("eventcullers")){
            EventCuller temp = (EventCuller)this.item;
            return Parameterizable.converToParameters(temp.getParameters());
        } else if(this.itemName.equalsIgnoreCase("analysismethods")){
            AnalysisDriver temp = (AnalysisDriver)this.item;
            return Parameterizable.converToParameters(temp.getParameters());
        } else if(this.itemName.equalsIgnoreCase("distancefunctions")){
            DistanceFunction temp = (DistanceFunction)this.item;
            return Parameterizable.converToParameters(temp.getParameters());
        } else {
            return null;
        }
    }
    public void setListTop(List<String> items){
        this.list1 = FXCollections.observableList(items);
    }
    public void setListBottom(List<String> items){
        this.list2 = FXCollections.observableList(items);
    }
    public VBox getPanel(){
        this.panel.setStyle("-fx-border-color: black");
        return this.panel;
    }
    public String getVal1(){
        return this.val1;
    }
    public String getVal2(){
        return this.val2;
    }
    public int getParamSize(){
        return this.paramSize;
    }
    public String[] getStringParams(){
        return this.split;
    }
}
