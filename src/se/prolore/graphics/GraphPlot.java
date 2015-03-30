package se.prolore.graphics;

import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BubbleXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYZToolTipGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.MatrixSeries;
import org.jfree.data.xy.MatrixSeriesCollection;
import org.jfree.data.xy.NormalizedMatrixSeries;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.PublicCloneable;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by matseriksson on 28/10/14.
 */
public class GraphPlot extends ApplicationFrame {
    //public class GraphPlot extends ApplicationFrame {

    /** The default title. */
    private static final String TITLE = "File complexity in relation to code churn and executable lines of code";

    /**
     * The normalized matrix series is used here to represent a changing
     * population on a grid.
     */
    NormalizedMatrixSeries series;

    GraphPlot demo;

    JFreeChart chart;
    LegendItemCollection legends;

    public GraphPlot(final String title) {
        super(title);

        this.series = createInitialSeries();
        this.legends = createInitialLegends();


        final MatrixSeriesCollection dataset = new MatrixSeriesCollection(this.series);
        //final MatrixSeriesCollection dataset = new MatrixSeriesCollection();

        final JFreeChart chart = ChartFactory.createBubbleChart(
                TITLE, "E LOC", "code churn", dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true, false);
        chart.getXYPlot().getLegendItems().addAll(this.legends);



        chart.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 0,
                1000, Color.blue));

        final XYPlot plot = chart.getXYPlot();
        plot.setForegroundAlpha(0.5f);

        final NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setLowerBound(0);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        // rangeAxis.setInverted(true);  // uncoment to reproduce a bug in jFreeChart
        rangeAxis.setLowerBound(0);




        //BubbleXYItemLabelGenerator generator = new LegendXYItemLabelGenerator(plot.getLegendItems());
        XYItemRenderer renderer = plot.getRenderer();
        System.out.println(
                plot.getIndexOf(renderer)
        );
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseItemLabelGenerator(new LegendXYItemLabelGenerator(this.legends));


        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setVerticalZoom(true);
        //      chartPanel.setHorizontalZoom(true);


//        StandardXYZToolTipGenerator tooltip = new StandardXYZToolTipGenerator();
//        tooltip.generateLabelString(dataset,816,86);


        setContentPane(chartPanel);
    }

    public GraphPlot( int maxCodeChurn, int maxELoc) {
        super(TITLE);
        this.series = createInitialSeries(maxCodeChurn,maxELoc);

        final MatrixSeriesCollection dataset = new MatrixSeriesCollection(this.series);

        chart = ChartFactory.createBubbleChart(
                TITLE, "E LOC", "code churn", dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true, false);

        chart.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 0,
                1000, Color.blue));

        final XYPlot plot = chart.getXYPlot();
        plot.setForegroundAlpha(0.5f);

        final NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setLowerBound(0);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        // rangeAxis.setInverted(true);  // uncoment to reproduce a bug in jFreeChart
        rangeAxis.setLowerBound(0);


        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setVerticalZoom(true);
        //      chartPanel.setHorizontalZoom(true);
        chartPanel.setDomainZoomable( true );
        chartPanel.setRangeZoomable( true );

        setContentPane(chartPanel);

    }

    private NormalizedMatrixSeries createInitialSeries(int maxCodeChurn, int maxELoc) {
        NormalizedMatrixSeries toReturn = new NormalizedMatrixSeries("avg complexity",maxCodeChurn+10,maxELoc+10);
        toReturn.setScaleFactor(1000);
        return toReturn;
    }

    private NormalizedMatrixSeries createInitialSeries() {
        NormalizedMatrixSeries toReturn = new NormalizedMatrixSeries("avg complexity",10,10);
        //NormalizedMatrixSeries toReturn = new NormalizedMatrixSeries("avg complexity",900,100);
        /*for (int i=0; i <10 ; i++)
        {
            toRetrun.update(i,i,i+i);
        }*/

        /*
        toReturn.update(201,2,2.9);
        toReturn.update(816,86,2.7);
        toReturn.update(162,36,3.6);
        toReturn.update(1,2,7.9);
        toReturn.update(52,33,7.6);
        */
        toReturn.update(5,5,10);
        toReturn.setScaleFactor(1);

        return toReturn;

    }

    private LegendItemCollection createInitialLegends() {
        LegendItemCollection l = new LegendItemCollection();
        LegendItem item = new LegendItem("a");

        //item.setDataset();
        item.setDatasetIndex(5);
        item.setSeriesIndex(5);
        item.setToolTipText("Wow");
        item.setDescription("wow");
        l.add(item);
        return l;
    }



    public void addFileMeasurement(int codeChurn, int eLoc, double avgComplex, String fileName) {
        this.series.update(codeChurn,eLoc,avgComplex);
        LegendItem item = new LegendItem(fileName);
        this.legends.add(item);

    }



    public void showGraph() {
        //final GraphPlot demo = new GraphPlot(TITLE,maxCodeChurn,maxELoc);
        JFrame f = new JFrame();

        f.setSize(800,600);
        f.getContentPane().add(new ChartPanel( chart));
        WindowListener wndCloser = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        f.addWindowListener(wndCloser);
        f.setVisible(true);

    }


    public static void main(final String[] args) {
        final GraphPlot demo = new GraphPlot(TITLE);

        demo.pack();
        demo.setSize(800, 600);
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

    public void saveGraph() {
        File imageFile = new File("XYLineChart.png");
        int width = 800;
        int height = 600;

        try {
            ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }


    /*
    public void plotGraph() {
        double[] values = {816,2.7};
        double[] values2 = {201,2.9};
        myDataset.addSeries("file1",values,);
        JFreeChart chart = ChartFactory.createHistogram("Churn","CodeChurn","Avg.complexity", myDataset, PlotOrientation.HORIZONTAL,true,true,false);

    }
*/

}


