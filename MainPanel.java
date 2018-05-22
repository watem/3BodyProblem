import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JPanel;


/**
* This class contains all the information about three bodies involved in the 3-body problem necessary to produce a graph
* as well as to calculate minimum and maximum values of certain quantities
*
*@authors Matthew Williams, Yulia Kosharych
*@version 17-05-2017
*/

public class MainPanel extends JPanel{

public static Dimension MainPanelDim = Main.frameDim;
	
//Objects
Object sun;
Object sat;
Object earth;
	
//Dimensions of the simulation box
public static final double x0 = MainPanelDim.getWidth()/2;
public static final double y0 = MainPanelDim.getHeight()/2;
		
//Radius of simulation models
final double sunR = 75;
final double satR = 35;
final double earthR = 35;
	
//Simulation colors
Color sunColor;
Color satColor;
Color earthColor;
	
double t = 0;	
public static final double G = 8.64960768*Math.pow(10, -13);//gravitational constant (km^3/kg/h^2)
public static final double dt = 1.0; //delta t in hours
public static final double tmax = 8760 ; //number of hours to run (1 year)
public static double time = 0; //initial time
public static final boolean graphing = true;
public static final double au = 1.49597870700*Math.pow(10,8);//initial distance of earth from the sun in km
	
//Masses 
public static final double massSun=SolarBody.massSol;  //kg
public static final double massEarth= SolarBody.massEarth; //kg
public static final double massJupiter =SolarBody.massJupiter;//kg
public static final double massL5 = massEarth/1000; //kg
public static double massSat; //kg
public static double massPlanet; //mass of the planet
	
//Velocities
public static final double velocityEarth = BodyMaths.circleVelocityG(massSun,au);// initial velocity of Earth
public static final double velocityJupiter = BodyMaths.circleVelocityG(massSun,5.2*au);//velocity Jupiter 
	
public static double scaling =350;
public static Scanner kb = new Scanner(System.in);
public static double [][][] object = new double[3][3][2];
public static double [] maxAccel, minPosSun, minPosEarth;
double satX, satY, velInitialX, velInitialY;
double planetX, planetY, velInitialPlanetX, velInitialPlanetY;

/**
 * This method does the Sun-Earth-Moon simulation
 * 
 * @authors Matthew Williams,Yulia Kosharych
 * @version 17-05-2018
 */	
public void initSunEarthLuna(){
		
scaling = 350;
//Sat Conditions
massSat = SolarBody.massLuna;
satX = au-384400;
satY = 0;
velInitialX = 0;
velInitialY = velocityEarth+BodyMaths.circleVelocityG(massEarth,384402);;
		  
//Earth Condition
massPlanet=massEarth;
planetX =au;
planetY =0;
velInitialPlanetX = 0;
velInitialPlanetY =velocityEarth;
		  
		  		  
}
	
/**
 * This method does the Sun-Earth-L5 simulation
 * 
 * @authors Matthew Williams,Yulia Kosharych
 * @version 17-05-2018
 */		
public void initSunEarthL5(){
		
scaling = 150;
//Sat Conditions
massSat = massL5;
satX = 0.7*au;
satY = 0.7*au;
velInitialX = 0.7*velocityEarth;
velInitialY = 0.7*velocityEarth;
		  
//Planet Condition
massPlanet=massEarth;
planetX =au;
planetY =0;
velInitialPlanetX = 0;
velInitialPlanetY =velocityEarth;
		    
}
	
/**
 * This method does the Sun-Jupiter-PlanetX simulation
 * 
 * @authors Matthew Williams,Yulia Kosharych
 * @version 17-05-2018
 */		
public void initSunJupiterProbe(){
	
scaling =75;
//Sat Conditions
System.out.println("Please enter the mass of the satellite:");
massSat = kb.nextDouble();
		  
System.out.println("Please enter the x position of the satellite:");
satX = kb.nextDouble();
System.out.println("Please enter the y position of the satellite:");
satY = kb.nextDouble();
System.out.println("Please enter the initial  x component of the velocity of satellite");
velInitialX = kb.nextDouble();
System.out.println("Please enter the initial  y component of the velocity of satellite");
velInitialY = kb.nextDouble();
		  
//Jupiter Condition
massPlanet=massJupiter;
planetX =5.2*au;
planetY =0;
velInitialPlanetX = 0;
velInitialPlanetY =velocityJupiter;
}
	
/**
 * This method assigns values to vector components of velocity, acceleration and position of each object
 * 
 * @authors Matthew Williams,Yulia Kosharych
 * @version 17-05-2018
 */		
public void Algorithm(){
	
//Earth or any random planet
object[0][0][0] = planetX; 
object[0][0][1] = planetY; 
		  
object[0][1][0] = velInitialPlanetX; 
object[0][1][1] = velInitialPlanetY; 
		  
object[0][2][0] = 0; 
object[0][2][1] = 0; 
		  
//Sun 
object[1][0][0] = 0; 
object[1][0][1] = 0; 
		  
object[1][1][0] = 0; 
object[1][1][1] = 0; 
		  
object[1][2][0] = 0; 
object[1][2][1] = 0; 
		  
//Satellite
object[2][0][0] = satX; 
object[2][0][1] = satY; 
		  
object[2][1][0] = velInitialX; 
object[2][1][1] = velInitialY; 
		  
object[2][2][0] = 0; 
object[2][2][1] = 0; 		
}
	
/**
 * This is the main method which determines the simulation that will be displayed on the screen
 * 
 * @authors Matthew Williams,Yulia Kosharych
 * @version 17-05-2018
 */		
public MainPanel() {		
this.setSize(Main.dim2);
int sim;
System.out.println("Please enter the simulation number: ");
sim = kb.nextInt();
		
//Simulation number		
if(sim == 1) initSunEarthLuna();
if (sim == 2) initSunEarthL5();
if (sim == 3) initSunJupiterProbe();
		
//Algorithm for graphing	
Algorithm();		  		  
sunColor = new Color(255, 172, 0);
sun = new Object(object[1][0][0] + x0, object[1][0][1] +  y0, sunR);		
satColor = new Color(211,211,211);
sat = new Object((scaling*object[2][0][0]/au) + x0, (275*object[2][0][1]/au) +  y0, satR);		
earthColor = new Color(47,94,79);
earth = new Object((scaling*object[0][0][0]/au) + x0, (275*object[0][0][1]/au) +  y0, earthR);
maxAccel = object[2][2];	  
minPosSun = differencePos(object[2][0],object[1][0]);
minPosEarth = differencePos(object[2][0],object[0][0]);
}
	
/**
* This method updates acceleration, velocity and position of each object under the influence of two other objects
*
* @authors Matthew Williams,Yulia Kosharych
* @version 17-05-2018
*/	
public void update() {
		
object[0][2] = accelSunAnyObj(massSun,object[0][0],object[1][0]);
object[1][2] = accelSunAnyObj(massPlanet,object[1][0],object[0][0]);
object[2][2] = accelSunAnyObj(massSun,object[2][0], object[1][0]);
		  
object[1][2] = add(object[1][2], accelSunAnyObj(massSat,object[1][0], object[2][0]));
object[0][2] = add(object[0][2], accelSunAnyObj(massSat,object[0][0],object[2][0]));
object[2][2] = add(object[2][2], accelSunAnyObj(massPlanet, object[2][0],object[0][0]));
		  
object[0][1] = add((mult(object[0][2])), object[0][1]);
object[1][1] = add((mult(object[1][2])), object[1][1]);
object[2][1] = add((mult(object[2][2])), object[2][1]);
		  	  
object[0][0] = add((mult(object[0][1])), object[0][0]);
object[1][0] = add((mult(object[1][1])), object[1][0]);
object[2][0] = add((mult(object[2][1])), object[2][0]);		  
}
	
/**
 * This method does the graphing of the 3-body system
 * 
 * @return double add
 * @authors Matthew Williams,Yulia Kosharych
 * @version 17-05-2018
 */	
public void paintChildren(Graphics g)
{
		
super.paintChildren(g);
		
//findMaxAccel(); //satellite
maxAccel = compareAccel(maxAccel,object[2][2]);
		
// findMinDis(); //sun
minPosSun = comparePosition(minPosSun, differencePos(object[2][0],object[1][0]));

// findMinDis(); //earth
minPosEarth = comparePosition(minPosEarth, differencePos(object[2][0],object[0][0]));

//Set the background of the graph		
Graphics2D g2 = (Graphics2D) g;		
g2.setColor(Color.BLACK);
g2.fillRect(0, 0, MainPanelDim.width, MainPanelDim.height);
	
	
//Drawing Sun
sun.updateCelestialObj(object[1][0]);
sun.paintObj(g2, sunColor);
		
		
//Drawing Planet
earth.updateCelestialObj(object[0][0]);
earth.paintObj(g2, earthColor);
	
//Drawing Sat
sat.updateCelestialObj(object[2][0]);
sat.paintObj(g2, satColor);
update();
		
//Adjust for DELAY
		

//Display Time
		
g2.setColor(Color.RED);
time += dt;
		
//Display number of hours and years of simulation on the graph
		
g2.setStroke(new BasicStroke(3));
g2.drawString("Number of hours : " + Double.toString(time), 10, 12);
g2.drawString("Number of years : " + Double.toString(time/8760), 10, 29);
		
//Acceleration Vectors		
g2.drawLine((int) ((scaling*object[1][0][0]/au) + x0),(int) ((scaling*object[1][0][1]/au) + y0),  (int)(((scaling*object[1][0][0]/au) + x0 ) + (0.7*sunR)*getUnit(object[1][2], getMag(object[1][2]))[0]), (int)(((scaling*object[1][0][1]/au) + y0 ) + (0.7*sunR)*getUnit(object[1][2], getMag(object[1][2]))[1]));		
g2.drawLine((int) ((scaling*object[0][0][0]/au) + x0),(int) ((scaling*object[0][0][1]/au) + y0 ),  (int)(((scaling*object[0][0][0]/au) + x0 ) + (0.7*earthR)*getUnit(object[0][2], getMag(object[0][2]))[0]), (int)(((scaling*object[0][0][1]/au) + y0 ) + (0.7*earthR)*getUnit(object[0][2], getMag(object[0][2]))[1]));		
g2.drawLine((int) ((scaling*object[2][0][0]/au) + x0),(int) ((scaling*object[2][0][1]/au) + y0 ),  (int)(((scaling*object[2][0][0]/au) + x0 ) + (0.7*satR)*getUnit(object[2][2], getMag(object[2][2]))[0]), (int)(((scaling*object[2][0][1]/au) + y0 ) + (0.7*satR)*getUnit(object[2][2], getMag(object[2][2]))[1]));
		
		
repaint();
		
//Display maximum acceleration reached by the satellite as well as the minimum distance reached to
//two other objects	
		
if(time==tmax) {
System.out.println("The maximim acceleration is: " +getMag(maxAccel));
System.out.println("The minimum position of the satellite to the planet is: "+getMag(minPosEarth));
System.out.println("The minimum position of the satellite to the Sun is: "+getMag(minPosSun));
System.exit(0);
			
}		
}
	
	/**
 * This method takes a double array as an input and multiplies is by the derivative of time
 * 
 * @param double[] array 
 * @return array
 * @authors Matthew Williams,Yulia Kosharych
 * @version 17-05-2018
 */

public static double [] mult(double [] array) {
for(int i =0; i<array.length;i++) {
array[i] = array[i]*dt;	
}
return array;
}	
/**
 * This method takes two double arrays as input and adds them in a new array
 * 
 * @param double[] array
 * @return double add
 * @authors Matthew Williams,Yulia Kosharych
 * @version 17-05-2018
 */	
	
public static double [] add(double [] array1, double [] array2) {
double [] add = new double [array1.length];
for(int i = 0; i < array1.length; i++)
{
add[i] = array1[i] + array2[i];
}
return add;		
}
	
/**
* This method takes two double arrays as input representing the position of Sun/Earth and 
* Satellite and subtracts the position of Sun/Earth from that of Satellite
* 
* @param double[] pos1, double [] pos2
* @return result
* @authors Matthew Williams,Yulia Kosharych
* @version 17-05-2018
*/
	
public static double [] differencePos(double[] pos1, double [] pos2){
double [] result = new double[2];
for(int i =0; i<pos1.length;i++) {
result[i] = pos1[i]-pos2[i];
}
return result;
}
	
/**
 * This method takes a double array as an input and multiplies is by the derivative of time
 * 
 * @param double[] array
 * @return array
 * @authors Matthew Williams,Yulia Kosharych
 * @version 17-05-2018
 */		
public static double [] getR(double [] objPos1, double [] objPos2) {
double [] rVector = new double [2];
rVector[0]= objPos2[0] - objPos1[0];
rVector[1]= objPos2[1] - objPos1[1];
return rVector;
}
	
/**
  * This method takes a double array as input and calculates the magnitude
  * two array elements 
  * 
  * @param double[] rVector
  * @return rMagnitude
  * @authors Matthew Williams, Yulia Kosharych
  * @version 17-05-2018
  */
public static double getMag(double [] rVector) {
double rMagnitude;
rMagnitude = Math.sqrt(Math.pow(rVector[0],2) + Math.pow(rVector[1], 2));
return rMagnitude;
}
	
/**
 * This method calculates the unit vector which determines the direction of the acceleration vector
 * 
 * @param double [] rVector    this array contains the x and y components of the vector
 * @param double rMagnitude    this is the magnitude of the vector
 * @return array
 * @authors Matthew Williams,Yulia Kosharych
 * @version 17-05-2018
 */	
public static double [] getUnit(double [] rVector, double rMagnitude ) {
double [] unit = new double[2];
unit[0] = rVector[0]/rMagnitude;
unit[1] = rVector[1]/rMagnitude;	
return unit;
}
	
 /**
  * This method calculates the x and y components of the instantaneous acceleration vector of any object of mass m 
  * under influence of another object 
  * 
  * @param double mPullObj      this is the mass of a object given
  * @param double [] objPos1    this is position vector of the first object
  * @param double [] objPos2    this is position vector of the second object
  * @return accelVector
  * @authors Matthew Williams,Yulia Kosharych
  * @version 17-05-2018
  */		
public static double [] accelSunAnyObj( double mPullObj,double [] objPos1, double [] objPos2) {
double rMagnitude;	
double [] rVector = new double [2];
double [] unit = new double [2];
double [] accelVector = new double [2];
double accelMagnitude;

rVector = getR(objPos1,objPos2); //vector components of pos
rMagnitude = getMag(rVector); //magnitude of accel
unit = getUnit(rVector,rMagnitude); //direction of accel

accelMagnitude= (G*mPullObj)/ Math.pow(rMagnitude, 2);
accelVector[0] = accelMagnitude * unit[0];  //x component of accel
accelVector[1] = accelMagnitude * unit[1];  //y component of accel
  
return accelVector; 
}
	
	
/**
  * This method updates instantaneous velocity of an object
  * 
  * @param double [] velInitial   this is the initial velocity vector of an object
  * @param double [] accel        this is the acceleration of the object 
  * @return velInitial            this is the final velocity vector of an object for each dt
  * @authors Matthew Williams,Yulia Kosharych
  * @version 17-05-2018
  */
public static double [] updateVel(double [] velInitial, double [] accel) {
velInitial[0] = accel[0] *dt + velInitial[0];
velInitial[1] = accel[1] *dt + velInitial[1];
return velInitial;
}
	
/**
  * This method updates instantaneous position of an object
  * 
  * @param double [] posInitial   this is the initial position of an object
  * @param double [] vel          this is the velocity of an object
  * @return posInitial            this is the final position vector of an object for each dt
  * @authors Matthew Williams,Yulia Kosharych
  * @version 17-05-2018
  */

public static double [] updatePos(double [] posInitial, double [] vel) {
posInitial[0] = vel[0] * dt + posInitial[0];
posInitial[1] = vel[1] * dt + posInitial[1];
return posInitial;		
}

/**
  * This method compares instantaneous accelerations of an object and returns the greatest acceleration reached
  * 
  * @param double [] accelInitial   
  * @param double [] accelFinal   
  * @return accelFinal           if following accel is greater that the previous one
  * @return accelInitial         if the previous accel is greater than the following one
  * @authors Matthew Williams,Yulia Kosharych
  * @version 17-05-2018
  */	
public static double [] compareAccel(double [] accelInitial, double [] accelFinal) {
double accelMagIn = getMag(accelInitial);
double accelMagFin = getMag(accelFinal);
if (accelMagIn < accelMagFin) {
return accelFinal;
}else {
return accelInitial;
}	
}

/**
  * This method compares instantaneous positions of an object and returns the smallest position the object reaches
  * to another object
  * @param double [] posInitial   
  * @param double [] posFinal   
  * @return posFinal            if the following position is closer to the object than the previous one
  * @return posInitial          if the following position is further to the object than the previous one
  * @authors Matthew Williams,Yulia Kosharych
  * @version 17-05-2018
  */	
public static double[] comparePosition(double[] posInitial, double[] posFinal) {
double magPosIn = getMag(posInitial);
double magPosFin = getMag(posFinal);
if (magPosIn > magPosFin ) {
return posFinal;
}else {
return posInitial;
}	
}
	
/**
  * This method compares instantaneous velocities of an object and returns the smallest one
  * @param double [] velInitial   
  * @param double [] velFinal   
  * @return velFinal            if the following velocity is greater than the previous one
  * @return velInitial          if the following velocity is smaller than the previous one
  * @authors Matthew Williams,Yulia Kosharych
  * @version 17-05-2018
  */		
public static double compareVelocity(double[] velInitial, double[] velFinal) {
double magVelIn = getMag(velInitial);
double magVelFin = getMag(velFinal);
if (magVelIn < magVelFin ) {
return magVelFin;
}else {
return magVelIn;
}	
}
	
}//end MainPanel class
