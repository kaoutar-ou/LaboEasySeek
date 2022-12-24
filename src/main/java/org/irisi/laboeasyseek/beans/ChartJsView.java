package org.irisi.laboeasyseek.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.irisi.laboeasyseek.models.Comment;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class ChartJsView implements Serializable {

    private PieChartModel pieModel;

    @PostConstruct
    public void init() {
        createPieModel();
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    private void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(300);
        values.add(50);
        values.add(100);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Red");
        labels.add("Blue");
        labels.add("Yellow");
        data.setLabels(labels);

        pieModel.setData(data);
    }

//    PieChartModel customPieModel;
    public PieChartModel getCustomPieModel(List<Comment> comments) {

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (Comment comment : comments) {
            if (map.containsKey(comment.getSentiment())) {
                map.put(comment.getSentiment(), map.get(comment.getSentiment()) + 1);
            } else {
                map.put(comment.getSentiment(), 1);
            }
        }

        PieChartModel model = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        // iterate over map
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//            values.add(entry.getValue());
//        }

        values.add(map.get(0));
        values.add(map.get(1));
        values.add(map.get(2));
        values.add(map.get(3));
        values.add(map.get(4));
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        bgColors.add("rgb(205, 205, 86)");
        bgColors.add("rgb(25, 205, 86)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Very Negative");
        labels.add("Negative");
        labels.add("Neutral");
        labels.add("Positive");
        labels.add("Very Positive");
        data.setLabels(labels);

        model.setData(data);

        return model;
    }

}