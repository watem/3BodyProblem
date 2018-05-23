/**
* This logic class contains all of the equations for three body problems involving gravity or electrostatics.
* This class also contains the formulae for gravitational Lagrange points.
* @author Matthew Williams, Yulia Kosharych
* @version 2018-05-16
**/
public class BodyMaths {


  // import constants
  public static final double G = SolarBody.G;


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
    obj[1][0][0]+=obj[2][0][i]*dt;
    obj[1][1][0]+=obj[2][1][i]*dt;
    // position = xI + v*dt
    obj[0][0][i]=obj[0][0][i-1]+obj[1][0][0]*dt;
    obj[0][1][i]=obj[0][1][i-1]+obj[1][1][0]*dt;
  }//updateBody()



  // Lagrange points
  /**
  * These methods (L1 to L3) give the distances to the legrange points. m1>m2
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

  /**
  * This method calculates the required orbital velocity for a circular orbit
  * @param massBig double       this is the mass of the driving body
  * @param dis double           this is the starting distance between the two bodies
  * @return velocity double     this is velocity required for a circular orbit
  * @version 2018-05-16
  **/
  public static double circleVelocityG(double massBig, double dis){
    double velocity = Math.sqrt(G*massBig/dis);
    return velocity;
  }//circleVelocityG()

  /**
  * This method calculates the the angularVelocity of a body in circular gravitational motion
  * @param massBig double       this is the mass of the driving body
  * @param dis double           this is the distance between the two bodies
  * @return omega double        this is the angular velocity of the body
  * @version 2018-05-16
  **/
  public static double angularVelocity(double massBig, double dis){
    double omega = circleVelocityG(massBig, dis)/dis;
    return omega;
  }//angularVelocity()

  /**
  * This method calculates the orbital period of two bodies in circular gravitational motion
  * @param massBig double       this is the mass of the driving body
  * @param dis double           this is the distance between the two bodies
  * @return time double         this is the orbital period in hours
  * @version 2018-05-16
  **/
  public static double orbitalPeriod(double massBig, double dis){
    double time = 2*Math.PI/angularVelocity(massBig, dis);
    return time;
  }//orbitalPeriod()

  /**
  * This method converts the positions from cartesian to polar coordinates in order to make the plot relative to one body
  * @param body[][][][] double    these are the position, velocity, acceleration vectors for a body
  * @param inertiaNum int         this is the body number to use for the reference
  * @version 2018-05-18
  **/
  public static void inertialReference(double body[][][][], int inertiaNum){
    int bodies = body.length;
    int imax = body[0][0][0].length;
    double bodyTheta[][] = new double[bodies][imax];
    double inertiaTheta[] = new double[imax];
    double radial[][] = new double[bodies][imax];
    for (int bodynum=1;bodynum<bodyTheta.length;bodynum++) {
        for (int i=0;i<bodyTheta[0].length;i++) {
          if (body[bodynum][0][0][i]==0) {
            bodyTheta[bodynum][i]=Math.PI/2;
          }
          else {
            bodyTheta[bodynum][i]=Math.atan(body[bodynum][0][1][i]/body[bodynum][0][0][i]);
          }
          if (body[bodynum][0][0][i]<0) {
            bodyTheta[bodynum][i]+=Math.PI;
          }
          radial[bodynum][i]=BodyMaths.norm(body[bodynum][0][1][i],body[bodynum][0][0][i]);
        }
      }
      for (int i = 0;i<inertiaTheta.length;i++) {
        inertiaTheta[i]=bodyTheta[inertiaNum][i];
        for (int bodynum=0;bodynum<body.length;bodynum++) {
          bodyTheta[bodynum][i]-=inertiaTheta[i];
          body[bodynum][0][0][i]=radial[bodynum][i]*Math.cos(bodyTheta[bodynum][i]);
          body[bodynum][0][1][i]=radial[bodynum][i]*Math.sin(bodyTheta[bodynum][i]);
        }
      }
      inertiaTheta=null;
  }//inertialReference()



}//class
