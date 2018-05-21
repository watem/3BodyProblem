import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import org.math.plot.*;
import org.math.plot.plotObjects.*;
import java.util.Arrays;

/**
* This class is used for the plotting tools
* @author Matthew Williams, Yulia Kosharych, J. Summer, J.F. Briere
* @version 2018-05-16
**/

public class NBodyGraph{

  /**
  * This method plots the positions of the bodies
  * @param bodyName[] String        these are the names of the bodies
  * @param body[][][][] double      these are the bodies that will be plotted
  * @param graphtype String         this is the type of plot that is printed
  **/
  public static void graphNBody(String bodyName[], double body[][][][], String graphtype){
    Plot2DPanel plot = new Plot2DPanel();

    // Define the legend position
    plot.addLegend("SOUTH");

    // Add a line plot to the PlotPanel
    for (int i=0;i<body.length;i++) {
      plot.addLinePlot(bodyName[i], body[i][0][0], body[i][0][1]);
    }
    plot.setAxisLabel(0,"x position");
    plot.getAxis(0).setLabelPosition(0.5,-0.1);
    plot.setAxisLabel(1,"y postion");
    BaseLabel title = new BaseLabel("N("+body.length+") body problem: "+graphtype, Color.BLACK, 0.5, 1.1);
    title.setFont(new Font("Courier", Font.BOLD, 14));
    plot.addPlotable(title);

    JFrame frame = new JFrame("Output of NBody.java");
    frame.setSize(525, 525);
    frame.setContentPane(plot);
    frame.setVisible(true);
  }//graphNBody()
}//class
