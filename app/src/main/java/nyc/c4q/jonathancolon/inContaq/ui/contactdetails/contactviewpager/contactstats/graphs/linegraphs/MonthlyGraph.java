package nyc.c4q.jonathancolon.inContaq.ui.contactdetails.contactviewpager.contactstats.graphs.linegraphs;

import android.view.animation.BounceInterpolator;

import com.db.chart.Tools;
import com.db.chart.animation.Animation;
import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;

import static android.graphics.Color.parseColor;
import static com.db.chart.renderer.AxisRenderer.LabelPosition.NONE;
import static com.db.chart.renderer.AxisRenderer.LabelPosition.OUTSIDE;

public class MonthlyGraph {

    private static final String SENT_COLOR = "#EF7674";
    private static final String LABEL_COLOR = "#FDFFFC";
    private static final String RECEIVED_COLOR = "#FDFFFC";
    private LineChartView lineGraph;
    private String[] xAxisLabels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};

    private float[] monthlySent;
    private float[] monthlyReceived;

    public MonthlyGraph(LineChartView lineGraph, float[] monthlyReceived, float[] monthlySent) {
        this.lineGraph = lineGraph;
        this.monthlySent = monthlySent;
        this.monthlyReceived = monthlyReceived;
    }

    public void showMonthlyGraph() {
        loadGraph();
    }

    synchronized private void loadGraph() {
        setGraphData();
        setGraphAttributes(getYValue());
        animateGraph();
    }

    private void setGraphData() {
        prepareReceivedLineSet(monthlyReceived);
        prepareSentLineSet(monthlySent);
    }

    private void setGraphAttributes(int maxYvalue) {
        lineGraph.setBorderSpacing(Tools.fromDpToPx(2))
                .setAxisBorderValues(0, maxYvalue)
                .setYLabels(NONE)
                .setXLabels(OUTSIDE)
                .setFontSize(24)
                .setAxisLabelsSpacing(15f)
                .setLabelsColor(parseColor(LABEL_COLOR))
                .setXAxis(false)
                .setYAxis(false);
    }

    private int getYValue() {
        int maxSent = findMaximumValue(monthlySent);
        int maxReceived = findMaximumValue(monthlyReceived);
        int highestValue = Math.max(maxSent, maxReceived);

        if (highestValue == 0) {
            highestValue = 100;
        }
        return increaseByQuarter(highestValue);
    }

    private void animateGraph() {
        Animation anim = new Animation().setEasing(new BounceInterpolator());
        lineGraph.show(anim);
    }

    private void prepareReceivedLineSet(float[] receivedValues) {
        LineSet dataReceivedValues = new LineSet(xAxisLabels, receivedValues);
        dataReceivedValues.setColor(parseColor(RECEIVED_COLOR))
                .setDotsColor(parseColor(RECEIVED_COLOR))
                .setThickness(4)
                .beginAt(0);
        lineGraph.addData(dataReceivedValues);
    }

    private void prepareSentLineSet(float[] sentValues) {
        LineSet dataSentValues = new LineSet(xAxisLabels, sentValues);
        dataSentValues.setColor(parseColor(SENT_COLOR))
                .setDotsColor(parseColor(SENT_COLOR))
                .setDashed(new float[]{1f, 1f})
                .setThickness(4)
                .beginAt(0);
        lineGraph.addData(dataSentValues);
    }

    private static int findMaximumValue(float[] input) {
        float maxValue = input[0];
        for (int i = 1; i < input.length; i++) {
            if (input[i] > maxValue) {
                maxValue = Math.round(input[i]);
            }
        }
        return (int) maxValue;
    }

    private int increaseByQuarter(int input) {
        return (int) Math.round(input * 1.25);
    }
}
