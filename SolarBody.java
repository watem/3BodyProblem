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
  // solDis*   = distance from sun (km)
  // rad*      = radius of body (km)

  // universal constants
  public static double G = 8.64960768*Math.pow(10, -13);
  public static double au = 1.49597870700*Math.pow(10,8);


  // values for common objects
  public static String nameSun = "Sun";
  public static double massSun = 1.989*Math.pow(10, 30);
  public static double solDisSun = 0;
  public static double vSun = 0;
  public static double radSun = 695508;

  public static String nameEarth = "Earth";
  public static double massEarth = 5.972*Math.pow(10, 24);
  public static double solDisEarth = au;
  public static double vEarth = Math.sqrt(G*massSun/solDisEarth);
  public static double radEarth = 6371;

  public static String nameJupiter = "Jupiter";
  public static double massJupiter = 1.898*Math.pow(10, 27);
  public static double solDisJupiter = 5.2*au;
  public static double vJupiter = Math.sqrt(G*massSun/solDisJupiter);
  public static double radJupiter = 69911;


  public String name;       // name of the body
  public double mass;       // mass of the body (kg)
  public double solDis;     // distance from sun (km)
  public double vInitial;   // initial velocity of body (km/h)
  public double rad;        // radius of body (km)
  public double theta;      // starting angle (rad)

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
  * This method constructs a Solarbody object
  * @param name String         name of the body
  * @param mass double        mass of the body (kg)
  * @param solDis double      distance from sun (km)
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
  * This method constructs a Solarbody with only a name and input values on all other variables except theta
  * @param name String         name of the body
  * @version 2018-05-16
  **/
  public SolarBody(String name){
    this.name=name;
    this.mass=-1;
    this.solDis=-1;
    this.vInitial=-1;
    this.rad=-1;
  }//input constructor


// premade starting objects that can be chosen
  public static SolarBody Sun = new SolarBody(nameSun, massSun, solDisSun, vSun, radSun, 0);
  public static SolarBody Jupiter = new SolarBody(nameJupiter, massJupiter, solDisJupiter, vJupiter, radJupiter, 0);
  public static SolarBody Earth = new SolarBody(nameEarth, massEarth, solDisEarth, vEarth, radEarth, 0);
  public static SolarBody ES_L4 = new SolarBody("ES_L4", -1, solDisEarth, vEarth, 0, Math.PI/3);
  public static SolarBody PlanetX = new SolarBody("PlanetX", massEarth, solDisSun-BodyMaths.L3((solDisEarth-solDisSun),massSun, massEarth), vEarth, radEarth, Math.PI);
  public static SolarBody Greeks = new SolarBody("Greeks", -1, solDisJupiter, vJupiter, 0, Math.PI/3);
  public static SolarBody Trojans = new SolarBody("Trojans", -1, solDisJupiter, vJupiter, 0, -Math.PI/3);
  public static SolarBody Satellite()  {return chooseTheta("satellite", 100, -1, -1, 0);}


  /**
  * This method creates an object and asks for user input relating to the theta value
  * @param name String            name of the body
  * @param mass double            mass of the body (kg)
  * @param solDis double          distance from sun (km)
  * @param vInitial double        initial velocity of body (km/h)
  * @param rad double             radius of body (km)
  * @return thetaless SolarBody   full Solarbody object
  * @version 2018-05-16
  **/
  public static SolarBody chooseTheta(String name, double mass, double solDis, double vInitial, double rad){
    Scanner kb = new Scanner(System.in);
    System.out.println("desired theta for "+name+" in pi radians");
    double theta = kb.nextDouble()*Math.PI;
    SolarBody thetaless = new SolarBody(name, mass, solDis, vInitial, rad, theta);
    return thetaless;
  }
  public static SolarBody named(String name){
    SolarBody named = new SolarBody(name);
    return named;
  }//constructed named body
  public static SolarBody other(String name, double mass, double solDis, double vInitial, double rad, double theta){
    SolarBody other = new SolarBody(name, mass, solDis, vInitial, rad, theta);
    return other;
  }//other constructor
}
