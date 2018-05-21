import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Object {

	
	double[] initPosVec = new double[2];
	double[] PosVec = new double[2];
	
	double radius;
	
	Ellipse2D object;
	
	public Object(double _x0, double _y0, double _radius)
	{
		
		
		radius = _radius;

		initPosVec[0] = _x0 - radius/2;
		initPosVec[1] = _y0 - radius/2;
		
		PosVec[0] = _x0 - radius/2;
		PosVec[1] = _y0 - radius/2;
		
		
		
		object = new Ellipse2D.Double(initPosVec[0], initPosVec[1], radius, radius); //sets centered object at x0, y0
	
		
	}
	
	public double[] getInitPosVec()
	{
		return initPosVec;
		
	}
	
	public void updateObj(double[] posVec)
	{
		
		PosVec[0] = posVec[0];
		PosVec[1] = posVec[1];
		
		object.setFrame(PosVec[0], PosVec[1], radius, radius);
	}
	
	public void updateCelestialObj(double[] posVec)
	{
		
		PosVec[0] = (MainPanel.scaling*posVec[0])/MainPanel.au + MainPanel.x0 - radius/2;
		PosVec[1] = (MainPanel.scaling*posVec[1])/MainPanel.au + MainPanel.y0 - radius/2;
		
		object.setFrame(PosVec[0], PosVec[1], radius, radius);
	}
	
	public void readObj(double[][] posVec, int t)
	{
		
		PosVec[0] = posVec[0][t];
		PosVec[1] = posVec[1][t];
		
		object.setFrame(posVec[0][t], posVec[1][t], radius, radius);
	}
	
	public void paintObj(Graphics2D g, Color color)
	{
	
		g.setColor(color);
		g.fill(object);
		
	}
	
}
