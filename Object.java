import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
* This class contains useful data about objects used in the simulation
*
* @author Matthew Williams, Yulia Kosharych
* @version 17-05-2017
*/
public class Object {

double[] initPosVec = new double[2];
double[] PosVec = new double[2];
double radius;

Ellipse2D object;

/**
* This method is used to get the initial position vector
*
* @return initPosVec
* @author Matthew Williams, Yulia Kosharych
* @version 17-05-2018
*/
public Object(double _x0, double _y0, double _radius){
radius = _radius;

initPosVec[0] = _x0 - radius/2;
initPosVec[1] = _y0 - radius/2;

PosVec[0] = _x0 - radius/2;
PosVec[1] = _y0 - radius/2;

object = new Ellipse2D.Double(initPosVec[0], initPosVec[1], radius, radius); //sets centered object at x0, y0
}

/**
* This method is used to get the initial position vector
*
* @return initPosVec double[]
* @author Matthew Williams, Yulia Kosharych
* @version 17-05-2018
*/
public double[] getInitPosVec(){
return initPosVec;
}

/**
* This method updates the position vector of an object used in the graphing
*
* @param posVec double[]
* @author Matthew Williams, Yulia Kosharych
* @version 17-05-2018
*/
public void updateObj(double[] posVec){

PosVec[0] = posVec[0];
PosVec[1] = posVec[1];
object.setFrame(PosVec[0], PosVec[1], radius, radius);
}

/**
* This method calculates the position vector of an object used in the graphing
*
* @param posVector double[]
* @author Matthew Williams, Yulia Kosharych
* @version 17-05-2018
*/
public void updateCelestialObj(double[] posVec)
{

PosVec[0] = (MainPanel.scaling*posVec[0])/MainPanel.au + MainPanel.x0 - radius/2;
PosVec[1] = (MainPanel.scaling*posVec[1])/MainPanel.au + MainPanel.y0 - radius/2;
object.setFrame(PosVec[0], PosVec[1], radius, radius);
}

/**
* This method reads the current position vectors of the bodies
*
* @param posVec double[]
* @param t int
* @author Matthew Williams, Yulia Kosharych
* @version 17-05-2018
*/
public void readObj(double[][] posVec, int t)
{

PosVec[0] = posVec[0][t];
PosVec[1] = posVec[1][t];
object.setFrame(posVec[0][t], posVec[1][t], radius, radius);
}

/**
* This method is responsible for setting colors for each body
*
* @param g Graphics2D
* @param color Color
* @return rMagnitude
* @author Matthew Williams, Yulia Kosharych
* @version 17-05-2018
*/
public void paintObj(Graphics2D g, Color color){

g.setColor(color);
g.fill(object);

}

}
