import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import org.math.plot.*;
import org.math.plot.plotObjects.*;
import java.util.Arrays;


public class FixedEarth {

  public static final double G = 8.64960768*Math.pow(10, -13);  //gravitational constant (km^3/kg/h^2)
  public static final double dt = 0.1;                        //delta t in hours
  public static final double tmax = 8766.15264/2;                       //number of hours to run
  public static double time = 0;                                //initial time
  public static final int imax =(int) (tmax/dt);                //number of time steps

  public static final double au = 1.49597870700*Math.pow(10,8); //initial distance of earth from the sun in km
  public static final double radEarth=6371;                     //km
  public static final double radSun=695508;                     //km

  public static final double massEarth=5.972*Math.pow(10, 24);  //kg
  public static final double massSun=1.989*Math.pow(10, 30);    //kg
  public static final double massSat=0;                         //kg

  public static Scanner kb = new Scanner(System.in);

  // toggle graphing
  public static final boolean graphing = true;
  public static final boolean swift = true;


  public static void main(String[] args) {
    Scanner kb = new Scanner(System.in);
    double[][][] sat;
    double[][] sun,earth;
    sun = new double[2][imax];
    earth = new double[2][imax];
    sat = new double[3][2][imax];
    double theta[]= new double[imax];
    double omega=2*Math.PI/8766.15264;
    double time[] = new double[imax];


    theta[0]=0;
    time[0]=0;
    earth[0][0]=au*Math.cos(theta[0]);
    earth[1][0]=au*Math.sin(theta[0]);

    for (int i=1;i<earth[0].length;i++) {
      theta[i]=theta[i-1]+omega*dt;
      earth[0][i]=au*Math.cos(theta[i]);
      earth[1][i]=au*Math.sin(theta[i]);
      time[i]=time[i-1]+dt;
      sun[0][i]=0;
      sun[1][i]=0;
    }


    sat[0][0][0]=99999*au/100000;
    sat[0][1][0]=0;
    sat[1][0][0]=0;
    sat[1][1][0]=1000;

    for (int i=1;i<sat[0][0].length;i++) {
      sat=BodyMaths.euler(sat, earth, sun, massEarth, massSun, i, dt);
    }
    System.out.println("done!");










    Plot2DPanel plot = new Plot2DPanel();

    // Define the legend position
    plot.addLegend("SOUTH");

    // Add a line plot to the PlotPanel
    plot.addLinePlot("Satellite", sat[0][0], sat[0][1]);
    plot.addLinePlot("Earth", earth[0], earth[1]);
    plot.addLinePlot("Sun", sun[0], sun[1]);
    plot.setAxisLabel(0,"x position");
    plot.getAxis(0).setLabelPosition(0.5,-0.1);
    plot.setAxisLabel(1,"y postion");
    BaseLabel title = new BaseLabel("Three body problem", Color.BLACK, 0.5, 1.1);
    title.setFont(new Font("Courier", Font.BOLD, 14));
    plot.addPlotable(title);

    JFrame frame = new JFrame("Output of ThreeBody.java");
    frame.setSize(525, 525);
    frame.setContentPane(plot);
    frame.setVisible(true);

  }
}
