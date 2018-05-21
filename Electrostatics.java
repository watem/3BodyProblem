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

  public Electrostatics(String name){
    this.name=name;
    this.mass=-1;
    setCharge();
    this.centreDis=-1;
    setVelocity();
    this.radius=-1;
    setTheta();
  }//input constructor
  public Electrostatics(double mass, double charge, double centreDis, double vInitial, double rad, String name){
    this.name=name;
    this.mass=mass;
    this.charge=charge;
    this.centreDis=centreDis;
    this.vInitial=vInitial;
    this.radius=rad;
    setTheta();
  }
  public Electrostatics(double mass, double charge, double centreDis, String name, double rad, double theta){
    this.name=name;
    this.mass=mass;
    this.charge=charge;
    this.centreDis=centreDis;
    setVelocity();
    this.radius=rad;
    this.theta=theta;
  }//select a velocity constructor
  public Electrostatics(String name, double mass, double charge, double centreDis, double rad){
    this.name=name;
    this.mass=mass;
    this.charge=charge;
    this.centreDis=centreDis;
    setVelocity();
    this.radius=rad;
    setTheta();
  }//select theta and velocity constructor







  public void setPath(){
    this.mass=0;
    this.name=this.name+"'s path";
  }
  public void setTheta(){
    Scanner kb = new Scanner(System.in);
    System.out.println("desired theta for "+this.name+" in pi radians");
    this.theta=kb.nextDouble()*Math.PI;
  }
  public void setVelocity(){
    Scanner kb = new Scanner(System.in);
    System.out.println("desired initial velocity for "+this.name+" in km/h");
    double vInit = kb.nextDouble();
    System.out.print("times 10^");
    double power = Math.pow(10, kb.nextDouble());
    this.vInitial=vInit*power;
    System.out.println("initial velcity = "+this.vInitial);
  }
  public void setCharge(){
    Scanner kb = new Scanner(System.in);
    System.out.println("desired total charge for "+this.name+" in C");
    double vInit = kb.nextDouble();
    System.out.print("times 10^");
    double power = Math.pow(10, kb.nextDouble());
    this.vInitial=vInit*power;
    System.out.println("charge = "+this.vInitial);
  }


}
