package se.prolore.graphics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by matseriksson on 29/10/14.
 */
public class XYPlotCodeChurn   {


    static JFreeChart chart;

    XYSeriesCollection myDataset = new XYSeriesCollection();

    public XYPlotCodeChurn(final String title) {



    }


    private static JFreeChart createChart(XYDataset dataset) {
        chart = ChartFactory.createScatterPlot("Scatter Plot Demo 1",
                "Avg. complexity", "Code churn", dataset, PlotOrientation.VERTICAL, false, false, false);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setNoDataMessage("NO DATA");
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);

        XYLineAndShapeRenderer renderer
                = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesOutlinePaint(0, Color.black);
        renderer.setUseOutlinePaint(true);
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseItemLabelGenerator(new LegendXYItemLabelGenerator(plot.getLegendItems()));
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        domainAxis.setTickMarkInsideLength(2.0f);
        domainAxis.setTickMarkOutsideLength(0.0f);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickMarkInsideLength(2.0f);
        rangeAxis.setTickMarkOutsideLength(0.0f);



        return chart;
    }

/*
    public String getUniqueSeriesName(String fileName, int nbr) {
        for (int i = 0; i < myDataset.getSeriesCount();i++) {
            if (fileName.equals(myDataset.getSeries(i).getDescription())) {
               getUniqueSeriesName(fileName, nbr++);
            }
        }

    }
*/


    public void addFileMeasurement(double avgComplex, int eLoc, int codeChurn, String fileName) {
        XYSeries xS = new XYSeries(fileName);
        xS.add(avgComplex, codeChurn);
        xS.setDescription(fileName);

        try {
            myDataset.addSeries(xS);
        } catch (IllegalArgumentException e) {
            System.out.println(fileName + "; " + avgComplex + "; " + eLoc + "; " + codeChurn);
            xS = new XYSeries(fileName+"2");
            xS.add(avgComplex, codeChurn);
            xS.setDescription(fileName + "2");
            myDataset.addSeries(xS);
        }
    }

    public void saveGraph() {

        saveGraph("CodeChurn_Complexity.png");
    }

    public void saveGraph(String filename) {
        chart = createChart(myDataset);
        File imageFile = new File(filename);
        int width = 800;
        int height = 600;

        try {
            ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }


}
