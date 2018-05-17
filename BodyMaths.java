/**
* This logic class contains all of the equations for three body problems involving gravity or electrostatics.
* This class also contains the formulae for gravitational Lagrange points.
* @author Matthew Williams, Yulia Kosharych
* @version 2018-05-16
**/
public class BodyMaths {


  // import constants
  public static final double G = SolarBody.G;
  public static final double Ke = Electrostatics.Ke;

  /**
  * This method calculates the acceleration that one body causes on another due to gravity
  * @param obj[][] double               these are the positions of the object that the acceleration is calculated on
  * @param body[][] double              these are the positions of the body that causes the gravitional acceleration on the object
  * @param massBody double              this is the mass of the body that causes the acceleration
  * @param i int                        this is the current timestep
  * @param axis int                     this is the axis that is currently being calculated (0=x, y=1)
  * @return acceleration double         this is the value of acceleration along the selected axis
  * @version 2018-04-25
  **/
  public static double gravityAccel(double obj[][], double body[][], double massBody, int i, int axis){
    double acceleration;
    acceleration = -G*massBody*(obj[axis][i]-body[axis][i])/Math.pow(radius(obj, body, i), 3);
    return acceleration;
  }//dualBodyAccel()

  /**
  * This method calculates the acceleration that one body causes on another due to electrostatic forces
  * @param obj[][] double               these are the positions of the object that the acceleration is calculated on
  * @param body[][] double              these are the positions of the body that causes the electrostatic forces on the object
  * @param qObj double                  this is the charge of the object that is acted apon
  * @param qBody double                 this is the charge of the other body
  * @param massObj double               this is the mass of the object whose acceleration is calculated
  * @param i int                        this is the current timestep
  * @param axis int                     this is the axis that is currently being calculated (0=x, y=1)
  * @return acceleration double         this is the value of acceleration along the selected axis
  * @version 2018-04-30
  **/
  public static double electrostaticAccel(double obj[][], double body[][], double qObj, double qBody, double massObj, int i, int axis){
    double acceleration;
    acceleration = Ke*qObj*qBody*(obj[axis][i]-body[axis][i])/Math.pow(radius(obj, body, i), 3)/massObj;
    return acceleration;
  }//electrostaticAccel()

  /**
  * This method calculates the magnitude of a two dimensional vector
  * @param x double         this is the x component of the vector
  * @param y double         this is the y component of the vector
  * @return norm double     this is the magnitude of the vector
  * @version 2018-04-25
  **/
  public static double norm(double x, double y) {
    double norm = Math.sqrt(x*x+y*y);
    return norm;
  }//norm()

  /**
  * This method calculates the distance between two bodies at a specific timestep
  * @param body1[][] double       these are the positions of first body
  * @param body2[][] double       these are the positions of second body
  * @param i int                  this is the current timestep
  * @return norm                  this is the magnitude of the distance between the two bodies
  * @version 2018-04-25
  **/
  public static double radius(double body1[][], double body2[][], int i) {
    double x = body1[0][i] - body2[0][i];
    double y = body1[1][i] - body2[1][i];
    return norm(x, y);
  }//radius()

  /**
  * This method updates the velcity and positions for a timestep based on the acceleration
  * @param obj[][][] double       these are the position, velocity, acceleration vectors for a body
  * @param dt double              this is the timestep length
  * @param i int                  this is the current timestep
  * @version 2018-05-16
  **/
  public static void updateBody(double obj[][][], double dt, int i){
    // velocity = vI + a*dt
    obj[1][0][i]=obj[1][0][i-1]+obj[2][0][i]*dt;
    obj[1][1][i]=obj[1][1][i-1]+obj[2][1][i]*dt;
    // position = xI + v*dt
    obj[0][0][i]=obj[0][0][i-1]+obj[1][0][i]*dt;
    obj[0][1][i]=obj[0][1][i-1]+obj[1][1][i]*dt;
  }//updateBody()



  // Lagrange points
  /**
  * These methods give the distances to the legrange points. m1>m2
  *
  * @param distance double    this is the distance between the major bodies
  * @param m1       double    this is the mass of the body that is much larger than m2
  * @param m2       double    this is the mass of the body that is much smaller than m1
  *
  * @return r       double    for L1 this is the distance from the smaller object towards the larger object
  *                           for L2 this is the distance from the smaller object away from the larger object
  *                           for L3 this is the distance from the larger object away from the smaller object
  * @version 2018-05-08
  **/
  public static double L1(double distance, double m1, double m2){
    double r = Math.abs(distance)*Math.pow(m2/(3*m1), 1./3.);
    return r;
  }//L1()
  public static double L2(double distance, double m1, double m2){
    double r = distance*Math.pow(m2/(3*m1), 1./3.);
    return r;
  }//L2()
  public static double L3(double distance, double m1, double m2){
    double r = 7./12.*distance*m2/m1;
    return r;
  }//L3()



}//class
