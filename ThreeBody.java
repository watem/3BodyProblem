import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import org.math.plot.*;
import org.math.plot.plotObjects.*;
import java.util.Arrays;

// TODO: javadocs

public class ThreeBody {

  public static final double G = 8.64960768*Math.pow(10, -13);  //gravitational constant (km^3/kg/h^2)
  public static final double dt = 0.001;                          //delta t in hours
  // public static final double tmax = 2190;                       //number of hours to run
  public static final double tmax = 1;
  public static double time = 0;                                //initial time
  // public static double tmax;                                    //number of hours to run {inputs}
  public static final int imax =(int) (tmax/dt);                //number of time steps
  // public static double imax;                                    //number of time steps {inputs}

  public static final double au = 1.49597870700*Math.pow(10,8); //initial distance of earth from the sun in km
  public static final double radEarth=6371;                     //km
  public static final double radSun=695508;                     //km
  public static final double massEarth=5.972*Math.pow(10, 24);  //kg
  public static final double massSun=1.989*Math.pow(10, 30);    //kg
  public static final double massSat=0;                        //kg
  // public static double massSat;                                 //kg
  public static final double velocityEarth = Math.pow(10, 20);


  public static Scanner kb = new Scanner(System.in);

  // toggle graphing
  public static final boolean graphing = true;
  public static final boolean swift = true;



  public static void main(String[] args) {
    // System.out.print("Please enter the mass of the satellite: ");
    // 	massSat = kb.nextDouble();
    // System.out.println("please enter the time of the simulation in hours "); //{inputs}
    // tmax = kb.nextDouble(); //{inputs}
    // imax = tmax/dt; //{inputs}



    //TODO: variable declaration, arrays
    double[][][] sun,earth,satellite;           // [position/velocity/accel][x/y][timestep]
    sun = new double[3][2][imax];
    earth = new double[3][2][imax];
    satellite = new double[3][2][imax];






      // initialization
      System.out.print("Please enter the initial x position of the satellite: ");
      satellite[0][0][0] = kb.nextDouble();
      System.out.print("Please enter the initial y position of the satellite: ");
      satellite[0][1][0] = kb.nextDouble();
      System.out.print("Please enter the initial x velocity of the satellite: ");
      satellite[1][0][0] = kb.nextDouble();
      System.out.print("Please enter the initial y velocity of the satellite: ");
      satellite[1][1][0] = kb.nextDouble();


    //Earth
    earth[0][0][0] = au;               //positions
    earth[0][1][0] = 0;

    earth[1][0][0] = 0;                //velocities
    earth[1][1][0] = velocityEarth;


    for (int i=1;i<satellite[0][0].length;i++) {
      satellite = euler(satellite, earth[0], sun[0], massEarth, massSun, i);
      earth = euler(earth, satellite[0], sun[0], massSat, massSun, i);
      sun = euler(sun, satellite[0], earth[0], massSat, massEarth, i);
      System.out.println("finished i = "+i);
    }//end loop

    System.out.println("finished calculating...");
    System.out.println("current position of earth: ("+earth[0][0][imax-1]+","+earth[0][1][imax-1]+")");


    // findMaxAcc();
    // findMinDis(); //earth
    // findMinDis(); //sun
    //
    // if (graphing) {
    //   graph();
    // }

    Plot2DPanel plot = new Plot2DPanel();

    // Define the legend position
    plot.addLegend("SOUTH");

    // Add a line plot to the PlotPanel
    plot.addLinePlot("Satellite", satellite[0][0], satellite[0][1]);
    plot.addLinePlot("Earth", earth[0][0], earth[0][1]);
    plot.addLinePlot("Sun", sun[0][0], sun[0][1]);
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
  }//main()



  public static double[][][] euler(double obj[][][], double body1[][], double body2[][], double m1, double m2, int i) {
    obj[2][0][i]+=accel(obj[0], body1, m1, i-1, 0); //x accel from first body
    obj[2][0][i]+=accel(obj[0], body2, m2, i-1, 0); //x accel from second body
    obj[2][1][i]+=accel(obj[0], body1, m1, i-1, 1); //y accel from first body
    obj[2][1][i]+=accel(obj[0], body2, m2, i-1, 1); //y accel from second body
    // vF = vI + a*dt
    obj[1][0][i]=obj[1][0][i-1]+obj[2][0][i]*dt;
    obj[1][1][i]=obj[1][1][i-1]+obj[2][1][i]*dt;
    // xF = xI + v*dt
    obj[0][0][i]=obj[0][0][i-1]+obj[1][0][i]*dt;
    obj[0][1][i]=obj[0][1][i-1]+obj[1][1][i]*dt;
    return obj;
  }

  public static double accel(double obj[][], double body[][], double massBody, int i, int axis) {
    double acceleration;
    acceleration = -G*massBody*(obj[axis][i]-body[axis][i])/Math.pow(radius(obj, body, i), 3);
    return acceleration;
  }//accel()

  public static double norm(double x, double y) {
    double norm = Math.sqrt(x*x+y*y);
    return norm;
  }//magnitude()

  public static double radius(double body1[][], double body2[][], int i) {
    double x = body1[0][i] - body2[0][i];
    double y = body1[1][i] - body2[1][i];
    return norm(x, y);
  }//distance()


  public static double [] add(double [] array1, double [] array2) {


  	double [] add = new double [array1.length];

  	for(int i = 0; i < array1.length; i++)
  	{
  		add[i] = array1[i] + array2[i];


  	}

  	return add;

  }

  // calculate accel();






  public static double [] getR(double [] objPos1, double [] objPos2) {

  	double [] rVector = new double [2];

  	rVector[0]= objPos2[0] - objPos1[0];
  	rVector[1]= objPos2[1] - objPos1[1];

  	return rVector;
  	}


  public static double getMag(double [] rVector) {

  	double rMagnitude;
  	rMagnitude = Math.sqrt(Math.pow(rVector[0],2) + Math.pow(rVector[1], 2));
  	return rMagnitude;
  }


  public static double [] getUnit(double [] rVector, double rMagnitude ) {

  	double [] unit = new double[2];

  	unit[0] = rVector[0]/rMagnitude;
  	unit[1] = rVector[1]/rMagnitude;

  return unit;
  }

  public static double [] accelSunAnyObj( double mPullObj,double [] objPos1, double [] objPos2) {

  double rMagnitude;
  double [] rVector = new double [2];
  double [] unit = new double [2];
  double [] accelVector = new double [2];
  double accelMagnitude;

  rVector = getR(objPos1,objPos2);
  rMagnitude = getMag(rVector);
  unit = getUnit(rVector,rMagnitude);

  accelMagnitude= (G*mPullObj)/ Math.pow(rMagnitude, 2);
  accelVector[0] = accelMagnitude * unit[0];
  accelVector[1] = accelMagnitude * unit[1];

    return accelVector;
  }

  public static double[][] switchRow(double array[][]){
    double newarray[][]=new double[array[0].length][array.length];
    for (int i = 0;i<newarray.length;i++) {
      for (int j = 0;j<newarray[0].length;j++) {
        newarray[i][j]=array[j][i];
      }
    }
    return newarray;
  }



  public static double [] updateVel(double [] velInitial, double [] accel) {


  	velInitial[0] = accel[0] *dt + velInitial[0];
  	velInitial[1] = accel[1] *dt + velInitial[1];


  	return velInitial;
  }



  public static double [] updatePos(double [] posInitial, double [] vel) {


  	posInitial[0] = vel[0] * dt + posInitial[0];
  	posInitial[1] = vel[1] * dt + posInitial[1];

  	return posInitial;

  }

  public static double compareAccel(double accelArray[][]) {
    double accel[][]=switchRow(accelArray);
    double accelMax = getMag(accel[0]);
    for (int i=1;i<accel[0].length;i++) {
      accelMax = getMag(accel[i]);
    }
    return accelMax
  }//compareAccel()

  
  public static double comparePosition(double[] posInitial, double[] posFinal) {

  	double magPosIn = getMag(posInitial);
  	double magPosFin = getMag(posFinal);


  	if (magPosIn < magPosFin ) {

  		return magPosFin;

  	}else {

  		return magPosIn;
  	}

  }



  public static double compareVelocity(double[] velInitial, double[] velFinal) {

  	double magVelIn = getMag(velInitial);
  	double magVelFin = getMag(velFinal);


  	if (magVelIn < magVelFin ) {

  		return magVelFin;

  	}else {

  		return magVelIn;
  	}

  }





  //TODO: findMinDis method
  // public static
  // void

  //TODO: graphing method()
  // public static void graph(double[][] satX,double[][] satY,double[][] sunX,double[][] sunY,double[][] earthX,double[][] earthY)




}//end class ThreeBody
