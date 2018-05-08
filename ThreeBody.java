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

  public static final double au = 1.49597870700*Math.pow(10,8); //initial distance in km
  public static final double velocityEarth = 1.0725*Math.pow(10, 5); //km/h
  public static final double massEarth=5.972*Math.pow(10, 24);  //kg
  public static final double massSun=1.989*Math.pow(10, 30);    //kg
  public static final double massBody=10;    //kg




  public static Scanner kb = new Scanner(System.in);

  // toggle graphing
  public static final boolean graphing = true;
  public static final boolean print = false;
  public static final boolean inputMass = false;
  public static final boolean inputSat =true;


  public static void main(String[] args) {
    Scanner kb = new Scanner(System.in);
    int bodies = 3;
    double[] mass = new double[bodies];
    double[][][][] body= new double[bodies][3][2][imax];//[body][pos][x/y][timestep]
    String[] bodyName=new String[bodies];

    // for (int i=0;i<bodyName.length;i++) {
    //   bodyName[i]="body"+i;
    // }
    bodyName[0]="Earth";
    bodyName[1]="Sun";
    if (bodies>2) {
      bodyName[2]="Satellite";
    }

    mass[0]=massEarth;
    mass[1]=massSun;

    if (bodies>2) {
      if (inputMass) {
        for (int i=3;i<mass.length;i++) {
          System.out.println("Mass #"+(i+1)+":");
          mass[i] = kb.nextDouble();
        }
      }
      else mass[2]=massBody;
    }
    // System.out.println("velocity");
    // double velocityEarth = kb.nextDouble();


    body[0][0][0][0]=au;
    body[0][1][1][0]=velocityEarth;

    if (bodies>2) {
      // System.out.println("x postion");
      // body[2][0][0][0]=kb.nextDouble();
      body[2][0][0][0]=au*1/10;
      // System.out.println("y postion");
      // body[2][0][1][0]=kb.nextDouble();
      // body[2][0][1][0]=-Math.sqrt(3)*au/2;
      body[2][0][1][0]=-au*1/10;
      // System.out.println("x velocity");
      // body[2][1][0][0]=kb.nextDouble();
      body[2][1][0][0]=velocityEarth*1/10;
      // System.out.println("y velocity");
      // body[2][1][1][0]=kb.nextDouble();
      body[2][1][1][0]=velocityEarth*1/10;
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
