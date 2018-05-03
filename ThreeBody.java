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
public static double time = 0; //initial time
// public static final double tmax; //number of hours to run {inputs}
public static final int imax =(int) (tmax/dt); //number of time steps
// public static final double imax; //number of time steps {inputs}
public static final boolean graphing = true;
public static final double au = 1.49597870700*Math.pow(10,8);//initial distance of earth from the sun in km
// public static final double radEarth=; //km
// public static final double radSun=; //km

public static final double massEarth=5.972*Math.pow(10, 24); //kg
public static final double massSun=1.989*Math.pow(10, 30);  //kg
public static final double massSat=50*Math.pow(10, 50); //kg

public static Scanner kb = new Scanner(System.in);



public static void main(String[] args) {
//TODO: variable declaration, arrays
	
 System.out.print("Please enter the duration of the simulation: ");
	  tmax = kb.nextDouble();
	  
System.out.print("Please enter the mass of the satellite: ");
	  massSat = kb.nextDouble();
	  
double satX, satY, velInitialX, velInitialY;
  // earthX[0][0]=1.495978*Math.pow(10,16);
  // earthX[1][0]=0;
  // earthY[1][0]=108000;
  // earthY[0][0]=0;
  // System.out.println("please enter the mass of the satellite: ");
  // massSat = kb.nextDouble();
	
  System.out.print("Please enter the initial x position of the satellite: ");
  satX = kb.nextDouble();
  System.out.print("Please enter the initial y position of the satellite: ");
  satY = kb.nextDouble();
  System.out.print("Please enter the initial x velocity of the satellite: ");
  velInitialX = kb.nextDouble();
  System.out.print("Please enter the initial y velocity of the satellite: ");
  velInitialY = kb.nextDouble();


  // System.out.println("please enter the time of the simulation in hours "); //{inputs}
  // tmax = kb.nextDouble(); //{inputs}
  // imax = tmax/dt; //{inputs}
	
	 double [][][] object = new double [3][3][2];
  
 // object[a] : a = 0(Earth), a=1(Sun), a=2(Sat)
// object[][b] : b = 0(position), b=1(velocity), b=2(acceleration)
//object[][][c] : c = 0(x position), c=1(y position)
  
  //Earth
  object[0][0][0] = au; 
  object[0][0][1] = 0; 
  
  object[0][1][0] = 0; 
  object[0][1][1] = velocityEarth; 
  
  object[0][2][0] = 0; 
  object[0][2][1] = 0; 
  
  //Sun 
  object[1][0][0] = 0; 
  object[1][0][1] = 0; 
  
  object[1][1][0] = 0; 
  object[1][1][1] = 0; 
  
  object[1][2][0] = 0; 
  object[1][2][1] = 0; 
  
  //Sat
  object[2][0][0] = satX; 
  object[2][0][1] = satY; 
  
  object[2][0][0] = velInitialX; 
  object[2][0][1] = velInitialY; 
  
  object[2][2][0] = 0; 
  object[2][2][1] = 0; 

  


  System.out.println(Arrays.toString(accelSunAnyObj(massSun,object[0][0],object[1][0])));
  
while (time<tmax) {
  
  time = time+dt;

  object[0][2] = accelSunAnyObj(massSun,object[0][0],object[1][0]);
  object[1][2] = accelSunAnyObj(massEarth,object[1][0],object[0][0]);
  object[2][2] = accelSunAnyObj(massSun,object[2][0], object[1][0]);
  object[1][2] = add(object[1][2], accelSunAnyObj(massSat,object[1][0], object[2][0]));
}


//   for (int i=1;i<satX[0].length;i++) {
//     satX = euler(satX, earthX[0][i-1], sunX[0][i-1], massEarth, massSun, i);
//     satY = euler(satY, earthY[0][i-1], sunY[0][i-1], massEarth, massSun, i);
//     earthX = euler(earthX, satX[0][i-1], sunX[0][i-1], massSat, massSun, i);
//     earthY = euler(earthY, satY[0][i-1], sunY[0][i-1], massSat, massSun, i);
//     sunX = euler(sunX, satX[0][i-1], earthX[0][i-1], massSat, massEarth, i);
//     sunY = euler(sunY, satY[0][i-1], earthY[0][i-1], massSat, massEarth, i);
//     System.out.println("finished i = "+i);
//   }//end loop
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
public static double [] compareAccel(double [] accelInitial, double [] accelFinal) {
	
	double accelMagIn = getMag(accelInitial);
	double accelMagFin = getMag(accelFinal);
	
	
	
	if (accelMagIn < accelMagFin) {
		
		return accelFinal;
		
	}else {
		
		return accelInitial;
	}
	
	
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
