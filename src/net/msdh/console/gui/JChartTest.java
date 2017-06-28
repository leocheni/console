package net.msdh.console.gui;

//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PiePlot3D;
//import org.jfree.chart.plot.RingPlot;
//import org.jfree.data.general.DefaultPieDataset;
//import org.jfree.data.general.PieDataset;
//import org.jfree.util.Rotation;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 30.06.16
 * Time: 16:52
 * To change this template use File | Settings | File Templates.
 */
public class JChartTest extends JPanel {


  private static final long serialVersionUID = 1L;

  public JChartTest(String applicationTitle, String chartTitle) {
        super();

//        PieDataset dataset = createDataset();
//        JFreeChart chart = createChart(dataset, chartTitle);
//
//        ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
//        // add it to our application
//        this.add(chartPanel);

    }


/**
     * Creates a sample dataset
     */

//    private  PieDataset createDataset() {
//        DefaultPieDataset result = new DefaultPieDataset();
//        result.setValue("Linux", 29);
//        result.setValue("Mac", 20);
//        result.setValue("Windows", 51);
//        return result;
//    }


/**
     * Creates a chart
     */

//    private JFreeChart createChart(PieDataset dataset, String title) {
//
//        JFreeChart chart = ChartFactory.createPieChart3D(title,          // chart title
//                dataset,                // data
//                true,                   // include legend
//                true,
//                false);
//
//        JFreeChart chart2 = ChartFactory.createRingChart(title,dataset,true,true,false);
//
//        //PiePlot3D
//
//        RingPlot  plot = (RingPlot) chart2.getPlot();
//
//        plot.setStartAngle(290);
//        plot.setDirection(Rotation.CLOCKWISE);
//        plot.setForegroundAlpha(0.5f);
//
//        return chart2;
//    }
}
