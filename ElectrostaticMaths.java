/**
* This logic class contains all of the equations for three body problems involving electrostatics.
* has BodyMaths.java as a dependency
* @author Matthew Williams, Yulia Kosharych
* @version 2018-05-16
**/
public class ElectrostaticMaths {

  // import constants
  public static final double Ke = Electrostatics.Ke;

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
    acceleration = Ke*qObj*qBody*(obj[axis][i]-body[axis][i])/Math.pow(BodyMaths.radius(obj, body, i), 3)/massObj;
    return acceleration;
  }//electrostaticAccel()
}
