import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import org.math.plot.*;
import org.math.plot.plotObjects.*;
import java.util.Arrays;

public class ThreeBody{
  public static final double dt = 0.01;                          //delta t in hours
  public static final double tmax = 8765.82;                       //number of hours to run
  public static double time = 0;                                //initial time
  // public static double tmax;                                    //number of hours to run {inputs}
  public static final int imax =(int) (tmax/dt);                //number of time steps
  // public static double imax;                                    //number of time steps {inputs}

  public static final int bodies = 3;

  public static SolarBody body0 = SolarBody.Sun;
  public static SolarBody body1 = SolarBody.Earth;
  public static SolarBody body2 = SolarBody.PlanetX;
  // public static SolarBody body2 = SolarBody.named("satellite");
  public static SolarBody body3 = SolarBody.named("probe");

  // toggle graphing
  public static final boolean graphing = true;
  public static final boolean print = false;

  public static void main(String[] args) {
    Scanner kb = new Scanner(System.in);
    double[] mass = new double[bodies];
    double[][][][] body= new double[bodies][3][2][imax];//[body][pos][x/y][timestep]
    String bodyName[] = new String[bodies];
    double vInit[] = new double[bodies];
    double radius[] = new double[bodies];
    double theta[] = new double[bodies];
    double distance;


    // names
    bodyName[0]=body0.name;
    bodyName[1]=body1.name;
    if (bodyName.length>2) {
      bodyName[2]=body2.name;
    }
    if (bodyName.length>3) {
      bodyName[3]=body3.name;
    }
    for (int i = 4;i<bodyName.length;i++) {
      bodyName[i]="body "+(i+1);
    }

    // masses
    mass[0]=body0.mass;
    mass[1]=body1.mass;
    if (mass.length>2) {
      mass[2]=body2.mass;
    }
    if (mass.length>3) {
      mass[3]=body3.mass;
    }
    for (int i=4;i<mass.length;i++) {
      mass[i] = -1;
    }
    for (int i=0;i<mass.length;i++) {
      if (mass[i]<0) {
        System.out.print("\nMass #"+i+":");
        mass[i] = kb.nextDouble();
      }
    }

    // thetas
    theta[0]=body0.theta;
    theta[1]=body1.theta;
    if (theta.length>2) {
      theta[2]=body2.theta;
    }
    if (theta.length>3) {
      theta[3]=body3.theta;
    }
    for (int i=4;i<theta.length;i++) {
      System.out.print("\nTheta #"+i+":");
      theta[i] = kb.nextDouble();
    }

    // initial position
    body[0][0][0][0]=body0.solDis*Math.cos(theta[0]);
    body[0][0][1][0]=body0.solDis*Math.sin(theta[0]);

    body[1][0][0][0]=body1.solDis*Math.cos(theta[1]);
    body[1][0][1][0]=body1.solDis*Math.sin(theta[1]);
    if (body.length>2){
    body[2][0][0][0]=body2.solDis*Math.cos(theta[2]);
    body[2][0][1][0]=body2.solDis*Math.sin(theta[2]);
    }
    if (body.length>3){
    body[3][0][0][0]=body3.solDis*Math.cos(theta[3]);
    body[3][0][1][0]=body3.solDis*Math.sin(theta[3]);
    }
    for (int i = 4;i<body.length;i++) {
      System.out.println("distance from centre");
      distance = kb.nextDouble();
      body[i][0][0][0]=distance*Math.cos(theta[i]);
      body[i][0][1][0]=distance*Math.sin(theta[i]);
    }

    body[0][1][0][0]=body0.vInitial*Math.sin(theta[0]);
    body[0][1][1][0]=body0.vInitial*Math.cos(theta[0]);

    body[1][1][0][0]=body1.vInitial*Math.sin(theta[1]);
    body[1][1][1][0]=body1.vInitial*Math.cos(theta[1]);
    if (body.length>2){
    body[2][1][0][0]=body2.vInitial*Math.sin(theta[2]);
    body[2][1][1][0]=body2.vInitial*Math.cos(theta[2]);
    }
    if (body.length>3){
    body[3][1][0][0]=body3.vInitial*Math.sin(theta[3]);
    body[3][1][1][0]=body3.vInitial*Math.cos(theta[3]);
    }
    for (int i = 4;i<body.length;i++) {
      System.out.println("initial velocity");
      distance = kb.nextDouble();
      body[i][1][0][0]=distance*Math.sin(theta[i]);
      body[i][1][1][0]=distance*Math.cos(theta[i]);
    }








    for (int t=1;t<imax;t++) {
      for (int num=0;num<bodies;num++) {
        for (int j=0;j<bodies;j++) {
          // System.out.println("J:"+j+" num:"+num);
          if (j!=num) {
            // System.out.println("update x");
            body[num][2][0][t]+=BodyMaths.dualBodyAccel(body[num][0], body[j][0], mass[j], t-1, 0);
            // System.out.println("update y");
            body[num][2][1][t]+=BodyMaths.dualBodyAccel(body[num][0], body[j][0], mass[j], t-1, 1);
          }
          else {
            // System.out.println("j=num");
          }
        }
        body[num]=BodyMaths.updateBody(body[num], dt, t);
        if (print) {
          System.out.println("body"+ (num+1) +" x,y");
          System.out.println(body[num][0][0][t]+","+body[num][0][1][t]);
        }
      }
    }


    if (graphing) {
      // GraphTool.plot2D(bodies, bodyName, body[][][][]);

      Plot2DPanel plot = new Plot2DPanel();

      // Define the legend position
      plot.addLegend("SOUTH");

      // Add a line plot to the PlotPanel
      for (int i=0;i<bodies;i++) {
        plot.addLinePlot(bodyName[i], body[i][0][0], body[i][0][1]);
      }
      plot.setAxisLabel(0,"x position");
      plot.getAxis(0).setLabelPosition(0.5,-0.1);
      plot.setAxisLabel(1,"y postion");
      BaseLabel title = new BaseLabel("N("+bodies+") body problem", Color.BLACK, 0.5, 1.1);
      title.setFont(new Font("Courier", Font.BOLD, 14));
      plot.addPlotable(title);

      JFrame frame = new JFrame("Output of NBody.java");
      frame.setSize(525, 525);
      frame.setContentPane(plot);
      frame.setVisible(true);
    }


  }//main()


}//class
