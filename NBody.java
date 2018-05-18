import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.Scanner;

/**
* This is the driving class for the N body solution for the problem using the Euler computational analysis method.
* not to be confused with the Euler general solution for 3 body problems involving two fixed bodies.
* @author Matthew Williams, Yulia Kosharych
* @version 2018-05-16
**/
public class NBody{
  //presets
  public static SolarBody body0 = SolarBody.Sol;
  // public static SolarBody body1 = SolarBody.Earth;
  public static SolarBody body1 = SolarBody.Jupiter;
  // public static SolarBody body2 = new SolarBody("satellite", 100, SolarBody.au/7, SolarBody.vJupiter, 0.001, 3*Math.PI/2);
  // public static SolarBody body2 = new SolarBody("satellite");
  // public static SolarBody body2 = SolarBody.Horseshoe;
  // public static SolarBody body2 = SolarBody.Luna;

  public static double dis = SolarBody.solDisJupiter+Math.pow(10, 5);
  public static SolarBody body2 = new SolarBody("Tadpole orbit", 10, dis, BodyMaths.circleVelocityG(body0.mass, dis), 0, -Math.PI/6);

  // public static SolarBody body3 = new SolarBody("probe", -1, -1, SolarBody.vEarth, 0, 0);
  public static SolarBody body3 = SolarBody.Jupiter;


  // if set to true, it shows the path the body without exerting any gravity on other bodies
  public static boolean setPath0 = false;
  public static boolean setPath1 = false;
  public static boolean setPath2 = false;
  public static boolean setPath3 = false;
  // useful to compare the affects of gravity on another object


  public static final double dt = 1.;                            //delta t in hours
  // public static final double tmax = 8765.82*5*3.54;                  //number of hours to run
  public static final double tmax =4*BodyMaths.orbitalPeriod(body0.mass, body1.solDis); //number of "years" to run (in hours)
  public static final int imax =(int) (tmax/dt);                   //number of time steps
  // number of bodies to use
  public static final int bodies = 3;

  public static final double G = SolarBody.G;


  // toggle outputs

  public static final double timeStart = 6*BodyMaths.orbitalPeriod(body0.mass, body1.solDis);
  public static final double timeEnd = 7*BodyMaths.orbitalPeriod(body0.mass, body1.solDis);;;
  public static final boolean partial = false;

  
  public static final boolean graphing = false;  //plots locations
  public static final boolean print = false;    //prints all steps in calculations. not recommended.
  public static final boolean verbose = true;   //provides details as to what the program is doing

  public static final boolean inertialPlot = true;
  public static final int inertiaNum = 1;          //which body the inertial frame is in reference to




  public static void main(String[] args) {
    final int maxJavaHeap = 55855815; //max number of points that java can handle
    int nPoints=bodies*3*2*imax+bodies*5;
    // System.out.println(nPoints);
    if (nPoints>maxJavaHeap) {
      System.out.println("WARNING. Datapoints exceed maximum acceptable number of points within java (trying to use "+nPoints+" with a maximum of "+maxJavaHeap+" points)\nConsider either lowering accuracy or lowering the max time of simulation");
      // System.exit(0);
    }
    if (setPath0) {
      body0.setPath();
    }
    if (setPath1) {
      body1.setPath();
    }
    if (setPath2) {
      body2.setPath();
    }
    if (setPath3) {
      body3.setPath();
    }

    Scanner kb = new Scanner(System.in);
    double[] mass = new double[bodies];
    double[][][][] body= new double[bodies][3][2][imax];//[body][pos][x/y][timestep]
    String bodyName[] = new String[bodies];
    double vInit[] = new double[bodies];
    double radius[] = new double[bodies];
    double theta[] = new double[bodies];
    double distance;

    // System.out.println("multiple of vEarth");
    // double n = kb.nextDouble();
    // double l = 1;
    // kb.nextDouble();
    // for (;l<10;l+=0.1) {//long for(;;) loop
    // SolarBody body2 = SolarBody.other("satellite", 100, SolarBody.au/7, SolarBody.vEarth*3.694, 0.001, (5+l/10)*Math.PI/4);

    if (verbose) {
    System.out.println("\nstarting input phase");
    System.out.print("inputting names...");
    }
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
    if (verbose) {
      System.out.print("done\ninputting mass...");
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
    if (verbose) {
      System.out.print("done\ninputting angles...");
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
    while (body0.solDis<0){
      System.out.println("distance from centre for "+body0.name);
      body0.solDis = kb.nextDouble();
    }
    while (body1.solDis<0) {
      System.out.println("distance from centre for "+body1.name);
      body1.solDis = kb.nextDouble();
    }
    if (verbose) {
      System.out.print("done\ninputting initial positions...");
    }
    // initial position
    body[0][0][0][0]=body0.solDis*Math.cos(theta[0]); //body 1 x
    body[0][0][1][0]=body0.solDis*Math.sin(theta[0]); //body 1 y
    body[1][0][0][0]=body1.solDis*Math.cos(theta[1]); //body 2 x
    body[1][0][1][0]=body1.solDis*Math.sin(theta[1]); //body 2 y
    if (body.length>2){
      while (body2.solDis<0) {
        System.out.println("distance from centre for "+body2.name);
        body2.solDis = kb.nextDouble();
      }
      body[2][0][0][0]=body2.solDis*Math.cos(theta[2]);
      body[2][0][1][0]=body2.solDis*Math.sin(theta[2]);
    }
    if (body.length>3){
      while (body3.solDis<0) {
        System.out.println("distance from centre for "+body3.name);
        body3.solDis = kb.nextDouble();
      }
      body[3][0][0][0]=body3.solDis*Math.cos(theta[3]);
      body[3][0][1][0]=body3.solDis*Math.sin(theta[3]);
    }
    for (int i = 4;i<body.length;i++) {
      System.out.println("distance from centre");
      distance = kb.nextDouble();
      body[i][0][0][0]=distance*Math.cos(theta[i]);
      body[i][0][1][0]=distance*Math.sin(theta[i]);
    }
    if (verbose) {
      System.out.print("done\ninputting initial velocities...");
    }
    // while (body0.vInitial<0) {
    //   System.out.println("initial velocity for "+body0.name);
      // body0.vInitial = kb.nextDouble();
    // }
    body[0][1][0][0]=-body0.vInitial*Math.sin(theta[0]);
    body[0][1][1][0]=body0.vInitial*Math.cos(theta[0]);

    // while (body1.vInitial<0) {
    //   System.out.println("initial velocity for "+body1.name);
    //   body1.vInitial = kb.nextDouble();
    // }
    body[1][1][0][0]=-body1.vInitial*Math.sin(theta[1]);
    body[1][1][1][0]=body1.vInitial*Math.cos(theta[1]);
    if (body.length>2){
      // while (body2.vInitial<0) {
      //   System.out.println("initial velocity for "+body2.name);
      //   body2.vInitial = kb.nextDouble();
      // }
      body[2][1][0][0]=-body2.vInitial*Math.sin(theta[2]);
      body[2][1][1][0]=body2.vInitial*Math.cos(theta[2]);
    }
    if (body.length>3){
      // while (body3.vInitial<0) {
      //   System.out.println("initial velocity for "+body3.name);
      //   body3.vInitial = kb.nextDouble();
      // }
      body[3][1][0][0]=-body3.vInitial*Math.sin(theta[3]);
      body[3][1][1][0]=body3.vInitial*Math.cos(theta[3]);
    }
    for (int i = 4;i<body.length;i++) {
      System.out.println("initial velocity");
      distance = kb.nextDouble();
      body[i][1][0][0]=-distance*Math.sin(theta[i]);
      body[i][1][1][0]=distance*Math.cos(theta[i]);
    }

    vInit = null;
    theta = null;
    if (verbose) {
      System.out.print("done\nFinished input\n");
    }




    if (verbose) {
      System.out.println("\nStarting calculation...");
    }
    for (int t=1;t<imax;t++) {
      for (int num=0;num<bodies;num++) {
        for (int j=0;j<bodies;j++) {
          // System.out.println("J:"+j+" num:"+num);
          if (j!=num) {
            // System.out.println("update accel x");
            body[num][2][0][t]+=BodyMaths.gravityAccel(body[num][0], body[j][0], mass[j], t-1, 0);
            // System.out.println("update accel y");
            body[num][2][1][t]+=BodyMaths.gravityAccel(body[num][0], body[j][0], mass[j], t-1, 1);
          }
          else if (print) {
           System.out.println("j=num");
          }
        }
        BodyMaths.updateBody(body[num], dt, t);
        if (print) {
          System.out.println("body"+ (num+1) +" x,y");
          System.out.println(body[num][0][0][t]+","+body[num][0][1][t]);
        }
      }
    }
    if (verbose) {
      System.out.println("calculation complete");
    }

    if (partial) {
      int partialLength = (int)((timeEnd-timeStart)/dt);
      double partialBody[] = new double[partialLength];
      for (int bodynum=0;bodynum<body.length;bodynum++) {
        for (int axis=0;axis<2;axis++) {
          for (int i=0,j=(int)(timeStart/dt);i<partialBody.length&&j<body[bodynum][0][1].length;i++,j++) {
            partialBody[i]=body[bodynum][0][axis][j];
          }
          body[bodynum][0][axis]=partialBody;
        }
      }
      partialBody = null;
    }//partialLength

    if (graphing) {
      if (verbose) {
        System.out.println("starting graph");
      }
      NBodyGraph.graphNBody(bodyName, body);
      if (verbose) {
        System.out.println("graph input complete");
      }
    }
    else if (inertialPlot) {
      //TODO: return everything to an inertial frame of reference
      if (verbose) {
        System.out.println("calculating with respect to an inertial frame of reference");
      }
      BodyMaths.inertialReference(body, inertiaNum);

      if (verbose) {
        System.out.println("inertial calculations complete\nstarting inertial graph");
      }
      NBodyGraph.graphNBody(bodyName, body);
      if (verbose) {
        System.out.println("inertial graph input complete");
      }
    }

// }//long for(;;) loop
  }//main()
}//class
