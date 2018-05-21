import java.util.Scanner;

/**
* This class contains premade values and situations for gravitational objects.
* It already contains several important sol system values.
* This class also allows for easy reference to starting positions.
* @author Matthew Williams, Yulia Kosharych
* @version 2018-05-16
**/
public class SolarBody{
  // mass*     = mass of the body (kg)
  // v*        = initial velocity of body (km/h)
  // solDis*   = distance from Sol (km)
  // rad*      = radius of body (km)

  // universal constants
  public static double G = 8.64960768*Math.pow(10, -13);
  public static double au = 1.49597870700*Math.pow(10,8);


  // values for common objects
  public static String nameSol = "Sol";
  public static double massSol = 1.989*Math.pow(10, 30);
  public static double solDisSol = 0;
  public static double vSol = 0;
  public static double radSol = 695508;

  public static String nameEarth = "Earth";
  public static double massEarth = 5.972*Math.pow(10, 24);
  public static double solDisEarth = au;
  public static double vEarth = BodyMaths.circleVelocityG(massSol,solDisEarth);
  public static double radEarth = 6371;

  public static String nameLuna = "Luna";
  public static double massLuna = 7.342*Math.pow(10, 22);
  public static double earthDisLuna = 384402;
  public static double solDisLuna = solDisEarth+earthDisLuna;
  public static double vLuna = vEarth+BodyMaths.circleVelocityG(massEarth,earthDisLuna);
  public static double radLuna = 1737;

  public static String nameJupiter = "Jupiter";
  public static double massJupiter = 1.898*Math.pow(10, 27);
  public static double solDisJupiter = 5.2*au;
  public static double vJupiter = BodyMaths.circleVelocityG(massSol,solDisJupiter);
  public static double radJupiter = 69911;

  public static String nameCallisto = "Callisto";
  public static double massCallisto = 1.076*Math.pow(10, 23);
  public static double jupDisCallisto = 1880000;
  public static double solDisCallisto = solDisJupiter+jupDisCallisto;
  public static double vCallisto = vJupiter+BodyMaths.circleVelocityG(massSol, solDisJupiter);
  public static double radCallisto = 2410.3;


  public String name;       // name of the body
  public double mass;       // mass of the body (kg)            strictly positive
  public double solDis;     // distance from Sol (km)           strictly positive
  public double vInitial;   // initial velocity of body (km/h)  positive/negative
  public double rad;        // radius of body (km)              strictly positive
  public double theta;      // starting angle (rad)             positive/negative
  //replace velocity location or theta location with string location in constructor be able to select one of them. keep at beginning if removing both
  // all other variables allow for adaption when set to a negative value
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
  * This method constructs a Solarbody object
  * @param name String         name of the body
  * @param mass double        mass of the body (kg)
  * @param solDis double      distance from Sol (km)
  * @param vInitial double    initial velocity of body (km/h)
  * @param rad double         radius of body (km)
  * @param theta double       starting angle (rad)
  * @version 2018-05-16
  **/
  public SolarBody(String name, double mass, double solDis, double vInitial, double rad, double theta){
    this.name=name;
    this.mass=mass;
    this.solDis=solDis;
    this.vInitial=vInitial;
    this.rad=rad;
    this.theta=theta;
  }//specific body constructor

  /**
  * This method constructs a Solarbody with only a name and input values on all other variables
  * @param name String         name of the body
  * @version 2018-05-16
  **/
  public SolarBody(String name){
    this.name=name;
    this.mass=-1;
    this.solDis=-1;
    setVelocity();
    this.rad=-1;
    setTheta();
  }//input constructor
  public SolarBody(double mass, double solDis, double vInitial, double rad, String name){
    this.name=name;
    this.mass=mass;
    this.solDis=solDis;
    this.vInitial=vInitial;
    this.rad=rad;
    setTheta();
  }
  public SolarBody(double mass, double solDis, String name, double rad, double theta){
    this.name=name;
    this.mass=mass;
    this.solDis=solDis;
    setVelocity();
    this.rad=rad;
    this.theta=theta;
  }//select a velocity constructor
  public SolarBody(String name, double mass, double solDis, double rad){
    this.name=name;
    this.mass=mass;
    this.solDis=solDis;
    setVelocity();
    this.rad=rad;
    setTheta();
  }//select theta and velocity constructor



// premade starting objects that can be chosen
  public static SolarBody Sol = new SolarBody(nameSol, massSol, solDisSol, vSol, radSol, 0);
  public static SolarBody Jupiter = new SolarBody(nameJupiter, massJupiter, solDisJupiter, vJupiter, radJupiter, 0);
  public static SolarBody Callisto = new SolarBody(nameCallisto, massCallisto, solDisCallisto, vCallisto, radCallisto, 0);
  public static SolarBody Earth = new SolarBody(nameEarth, massEarth, solDisEarth, vEarth, radEarth, 0);
  public static SolarBody Luna = new SolarBody(nameLuna, massLuna, solDisLuna, vLuna, radLuna, 0);
  public static SolarBody SE_L4 = new SolarBody("SE_L4", -1, solDisEarth, vEarth, 0, Math.PI/3);
  public static SolarBody PlanetX = new SolarBody("PlanetX", massEarth, solDisEarth, vEarth, radEarth, Math.PI);
  public static SolarBody Greeks = new SolarBody("Greeks", -1, solDisJupiter, vJupiter, 0, Math.PI/3);
  public static SolarBody Trojans = new SolarBody("Trojans", 1000, solDisJupiter, vJupiter, 0, -Math.PI/3);
  public static SolarBody Horseshoe = new SolarBody("Horseshoe orbit", 10, SolarBody.solDisJupiter-BodyMaths.L1(SolarBody.solDisJupiter, SolarBody.massSol, SolarBody.massJupiter), BodyMaths.circleVelocityG(SolarBody.massSol,(SolarBody.solDisJupiter-BodyMaths.L1(SolarBody.solDisJupiter, SolarBody.massSol, SolarBody.massJupiter))), 0, -Math.PI/9);
  public static SolarBody IrregularTadpole = new SolarBody("Irregular Tadpole", 100, solDisJupiter+Math.pow(10, 7), vJupiter, 0, -Math.PI/3);
  public static SolarBody Tadpole = new SolarBody("Tadpole", 100, solDisJupiter+Math.pow(10, 7), vJupiter-600, 0, -Math.PI/3);


  /**
  * This method sets the mass to 0 to show the path the body would take without influencing any other bodies as well as changing the name to reflect the change
  * @version 2018-05-18
  **/
  public void setPath(){
    this.mass=0;
    this.name=this.name+"'s path";
  }//setPath()

  /**
  * This method sets the theta value for an object
  * @version 2018-05-18
  **/
  public void setTheta(){
    Scanner kb = new Scanner(System.in);
    System.out.println("desired theta for "+this.name+" in pi radians");
    this.theta=kb.nextDouble()*Math.PI;
  }//setTheta()

  /**
  * This method sets the velocity to a desired power of 10 from user input
  * @version 2018-05-18
  **/
  public void setVelocity(){
    Scanner kb = new Scanner(System.in);
    System.out.println("desired initial velocity for "+this.name+" in km/h");
    double vInit = kb.nextDouble();
    System.out.print("times 10^");
    double power = Math.pow(10, kb.nextDouble());
    this.vInitial=vInit*power;
    System.out.println("initial velcity = "+this.vInitial);
  }//setVelocity()

  /**
  * This method sets the starting distance to a desired power of 10 from user input
  * @version 2018-05-18
  **/
  public void setDis(){
    Scanner kb = new Scanner(System.in);
    System.out.println("desired initial distance for "+this.name+" in km");
    double dis = kb.nextDouble();
    System.out.print("times 10^");
    double power = Math.pow(10, kb.nextDouble());
    this.solDis=dis*power;
    System.out.println("initial distance = "+this.solDis);
  }//setDis()
  /**
  * This method sets the starting number of AUs from user input
  * @version 2018-05-18
  **/
  public void setDisAU(){
    Scanner kb = new Scanner(System.in);
    System.out.println("desired initial distance for "+this.name+" in AUs");
    double dis = kb.nextDouble();
    this.solDis=dis*au;
    System.out.println("initial distance = "+this.solDis);
  }//setDisAU()
  /**
  * This method sets the name from user input
  * @version 2018-05-18
  **/
  public void setName(String name){
    // Scanner kb = new Scanner(System.in);
    // System.out.println("name for the body");
    // String name = kb.nextString();
    this.name=name;
    System.out.println("new name:"+this.name);
  }//setnane()

  /**
  * This method adds to the starting distance to a desired power of 10 from user input
  * @version 2018-05-18
  **/
  public void addDis(){
    Scanner kb = new Scanner(System.in);
    System.out.println("desired additional distance for "+this.name+" in km");
    double dis = kb.nextDouble();
    System.out.print("times 10^");
    double power = Math.pow(10, kb.nextDouble());
    this.solDis+=dis*power;
    System.out.println("initial distance = "+this.solDis);
  }//setDis()

  /**
  * This method adds to the starting velocity to a desired power of 10 from user input
  * @version 2018-05-18
  **/
  public void addVel(){
    Scanner kb = new Scanner(System.in);
    System.out.println("desired additional velcity for "+this.name+" in km/h");
    double vel = kb.nextDouble();
    System.out.print("times 10^");
    double power = Math.pow(10, kb.nextDouble());
    this.vInitial+=vel*power;
    System.out.println("initial velocity = "+this.solDis);
  }//setDis()

}//class
