import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import org.math.plot.*;
import org.math.plot.plotObjects.*;
import java.util.Arrays;

// TODO: javadocs

public class ThreeBody {
public static final double G = 8.64960768*Math.pow(10, -13);//gravitational constant (km^3/kg/h^2)
public static final double dt = 0.1; //delta t in hours
public static final double tmax = 2190; //number of hours to run
// public static final double tmax; //number of hours to run {inputs}
public static final int imax =(int) (tmax/dt); //number of time steps
// public static final double imax; //number of time steps {inputs}
public static final boolean graphing = true;

// public static final double radEarth=; //km
// public static final double radSun=; //km

public static final double massEarth=5.972*Math.pow(10, 24); //kg
public static final double massSun=1.989*Math.pow(10, 30);  //kg
public static final double massSat=50*Math.pow(10, 50); //kg

public static Scanner kb = new Scanner(System.in);



public static void main(String[] args) {
//TODO: variable declaration, arrays
  double [][] sunX, sunY, earthX, earthY, satX, satY;
  sunX = new double[3][imax];
  sunY = new double[3][imax];
  earthX = new double[3][imax];
  earthY = new double[3][imax];
  satX = new double[3][imax];
  satY = new double[3][imax];

  // earthX[0][0]=1.495978*Math.pow(10,16);
  // earthX[1][0]=0;
  // earthY[1][0]=108000;
  // earthY[0][0]=0;
  // System.out.println("please enter the mass of the satellite: ");
  // massSat = kb.nextDouble();
  System.out.println("please enter the initial x position of the satellite: ");
  satX[0][0] = kb.nextDouble();
  System.out.println("please enter the initial y position of the satellite: ");
  satY[0][0] = kb.nextDouble();



  // System.out.println("please enter the time of the simulation in hours "); //{inputs}
  // tmax = kb.nextDouble(); //{inputs}
  // imax = tmax/dt; //{inputs}

  for (int i=1;i<satX[0].length;i++) {
    satX = euler(satX, earthX[0][i-1], sunX[0][i-1], massEarth, massSun, i);
    satY = euler(satY, earthY[0][i-1], sunY[0][i-1], massEarth, massSun, i);
    earthX = euler(earthX, satX[0][i-1], sunX[0][i-1], massSat, massSun, i);
    earthY = euler(earthY, satY[0][i-1], sunY[0][i-1], massSat, massSun, i);
    sunX = euler(sunX, satX[0][i-1], earthX[0][i-1], massSat, massEarth, i);
    sunY = euler(sunY, satY[0][i-1], earthY[0][i-1], massSat, massEarth, i);
    System.out.println("finished i = "+i);
  }//end loop
  System.out.println("finished calculating...");
  System.out.println("current position of earth: ("+earthX[0][imax-1]+","+earthY[0][imax-1]+")");
  // findMaxAcc();
  // findMinDis(); //earth
  // findMinDis(); //sun
  //
  // if (graphing) {
  //   graph();
  // }
}//main()

public static double[][] euler(double obj[][], double p1, double p2, double m1, double m2, int i) {
  obj[2][i] = accel(obj[0][i-1], p1, p2, m1, m2);
  obj[1][i] = obj[2][i]*dt+obj[1][i-1];
  obj[0][i] = obj[1][i]*dt+obj[0][i-1];
  return obj;
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

}


//TODO: findMaxAcc method
// public static
// void or double?

//TODO: findMinDis method
// public static
// void or double?

//TODO: graphing method()
// public static void graph(double[][] satX,double[][] satY,double[][] sunX,double[][] sunY,double[][] earthX,double[][] earthY)


}//end class ThreeBody
