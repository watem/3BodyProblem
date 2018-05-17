import java.util.Scanner;
/**
* This class contains premade values and situations for electrostatic objects.
* more methods to easily add premades can easily be created.
* @author Matthew Williams, Yulia Kosharych
* @version 2018-05-16
**/
public class Electrostatics{
  public static final double Ke = 8.9875*Math.pow(10, 9);//N*m^2/C^2

  public String name;
  public double mass;
  public double charge;
  public double radius;
  public double centreDis;
  public double vInitial;
  public double theta;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public Electrostatics(String name, double mass, double charge, double centreDis, double vInitial, double radius, double theta){
    this.name=name;
    this.mass=mass;
    this.charge=charge;
    this.centreDis=centreDis;
    this.vInitial=vInitial;
    this.radius=radius;
    this.theta=theta;
  }


}
