// Plotter
// Chris Wilcox
// wilcox@cs.colostate.edu
// 10/19/2012
// CS160

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

// Pie charts, bar charts, and line graphs
public class Plotter extends ApplicationFrame {

    // Serialization identifier
    private static final long serialVersionUID = 1L;

    // Set a theme using shadow generator
    {
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", true));
    }

    // Enumeration
    public enum eType {
        PIECHART, BARCHART, LINEGRAPH
    }

    // Chart title
    String chartTitle;
    
    // Instance data
    int pieCount;
    int barCount;
    int lineCount;
    
    // Static allocation!
    double[] pieData;
    String[] pieLabels;
    double[][] barData;
    double[][] lineData;

    public Plotter(String title) {
        super(title);
        chartTitle = title;
    }

    public void drawGraph(eType type)
    {
        JFreeChart chart;
        
        switch (type)
        {
            case PIECHART:
                chart = createPieChart();
                break;
            case BARCHART:
                chart = createBarChart();
                break;
            case LINEGRAPH:
            default:
                chart = createLineGraph();
        }
        
        // add the chart to a panel...
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        setContentPane(chartPanel);
        pack();
        RefineryUtilities.positionFrameRandomly(this);
        setVisible(true);
    }

    // Initialize pie chart data
    public void pieChartData(int count, double data[]) {
        
        // Copy data from user
        pieCount = count;
        pieData = new double[count];
        for (int i = 0; i < count; i++) {
            pieData[i] = data[i];
        }
    }
    // Initialize pie chart labels
    public void pieChartLabels(int count, String labels[]) {
        
        // Copy labels from user
        pieCount = count;
        pieLabels = new String[count];
        for (int i = 0; i < count; i++) {
            pieLabels[i] = labels[i];
        }
    }
    
    // Create pie chart
    private JFreeChart createPieChart()
    {
        // Fill in data set
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int i = 0; i < pieCount; i++)
                dataset.setValue(pieLabels[i], pieData[i]);

        JFreeChart chart = ChartFactory.createPieChart(chartTitle,
            dataset,            // data
            false,              // no legend
            true,               // tooltips
            false               // no URL generation
        );

        // Set a custom background
        chart.setBackgroundPaint(new GradientPaint(new Point(0, 0), 
            new Color(20, 20, 20), new Point(400, 200), Color.DARK_GRAY));

        // Customize the title position and font
        TextTitle title = chart.getTitle();
        title.setHorizontalAlignment(HorizontalAlignment.LEFT);
        title.setPaint(new Color(240, 240, 240));
        title.setFont(new Font("Arial", Font.BOLD, 26));

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setOutlineVisible(false);

        // Use gradients for section colors
        plot.setSectionPaint(pieLabels[0], createGradientPaint(new Color(200, 200, 255), Color.BLUE));
        plot.setSectionPaint(pieLabels[1], createGradientPaint(new Color(255, 200, 200), Color.RED));
        plot.setSectionPaint(pieLabels[2], createGradientPaint(new Color(200, 255, 200), Color.GREEN));
        plot.setSectionPaint(pieLabels[3], createGradientPaint(new Color(200, 255, 200), Color.YELLOW));
        plot.setBaseSectionOutlinePaint(Color.WHITE);
        plot.setSectionOutlinesVisible(true);
        plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));

        // Customize the section label appearance
        plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
        plot.setLabelLinkPaint(Color.WHITE);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);
        
        return chart;
    }
    
    // Set bar chart data
    public void barChartData(int series, int count, double data[]) {
        
        // Copy data from user
        barCount = count;
        if (series == 0) barData = new double[2][count];
        for (int i = 0; i < count; i++) {
                barData[series][i] = data[i];
        }
    }

    // Create bar chart
    private JFreeChart createBarChart()
    {
        // Fill in data set
        CategoryDataset dataset;
        dataset = DatasetUtilities.createCategoryDataset("Series ", "Factor ", barData);

        JFreeChart chart = ChartFactory.createBarChart(
                chartTitle,                 // chart title
                "Category",                 // domain axis label
                "Score (%)",                // range axis label
                dataset,                    // data
                PlotOrientation.HORIZONTAL, // orientation
                true,                       // include legend
                true,
                false
            );

        // Set the background color for the chart...
        chart.setBackgroundPaint(Color.lightGray);

        // Get a reference to the plot
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
            
        // Change the tick unit selection to integer units only
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0.0, 100.0);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            
        return chart;
    }

    // Set line graph data
    public void lineGraphData(int series, int count, double data[]) {
        
        // Copy data from user
        lineCount = count;
        if (series == 0) lineData = new double[3][count];
        for (int i = 0; i < count; i++) {
                lineData[series][i] = data[i];
        }
    }
    
    // Create line graph
    private JFreeChart createLineGraph()
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < 3; i++) // series
        {
            for (int j = 0; j < lineCount; j++) // data
            {
                String series = "Series " + i;
                String type   = "Type " + j;
                dataset.addValue(lineData[i][j], series, type);
            }
        }
    
        // create the chart...
        final JFreeChart chart = ChartFactory.createLineChart(
            chartTitle,                // chart title
            "Type",                    // domain axis label
            "Value",                   // range axis label
            dataset,                   // data
            PlotOrientation.VERTICAL,  // orientation
            true,                      // include legend
            true,                      // tooltips
            false                      // urls
        );

        // Chart customization
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        // Customize the range axis
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

        // Customize the renderer
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesStroke(
            0, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {10.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            1, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {6.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            2, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {2.0f, 6.0f}, 0.0f
            )
        );
         
        return chart;
    }

    // Utility method for creating gradient paints.
    private static RadialGradientPaint createGradientPaint(Color c1, Color c2)
    {
        Point2D center = new Point2D.Float(0, 0);
        float radius = 200;
        float[] dist = {0.0f, 1.0f};
        return new RadialGradientPaint(center, radius, dist,
                new Color[] {c1, c2});
    }
}

