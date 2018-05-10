public class SolarBody{
  // mass*     = mass of the body (kg)
  // v*        = initial velocity of body (km/h)
  // solDis*   = distance from sun (km)
  // rad*      = radius of body (km)

  public static double G = 8.64960768*Math.pow(10, -13);
  public static double au = 1.49597870700*Math.pow(10,8);

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


  public String name;
  public double mass;
  public double solDis;
  public double vInitial;
  public double rad;
  public double theta;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public SolarBody(String name, double mass, double solDis, double vInitial, double rad, double theta){
    this.name=name;
    this.mass=mass;
    this.solDis=solDis;
    this.vInitial=vInitial;
    this.rad=rad;
    this.theta=theta;
  }//specific body constructor
  public SolarBody(String name){
    this.name=name;
    this.mass=-1;
    this.solDis=-1;
    this.vInitial=-1;
    this.rad=-1;
  }//input constructor


  public static SolarBody Sun = new SolarBody(nameSun, massSun, solDisSun, vSun, radSun, 0); //sun constructor
  public static SolarBody Jupiter = new SolarBody(nameJupiter, massJupiter, solDisJupiter, vJupiter, radJupiter, 0); //
  public static SolarBody Earth = new SolarBody(nameEarth, massEarth, solDisEarth, vEarth, radEarth, 0);
  public static SolarBody ES_L4 = new SolarBody("ES_L4", -1, solDisEarth, vEarth, 0, Math.PI/3);
  public static SolarBody PlanetX = new SolarBody("PlanetX", massEarth, solDisEarth, vEarth, radEarth, Math.PI);
  public static SolarBody Greeks = new SolarBody("Greeks", -1, solDisJupiter, vJupiter, 0, Math.PI/3);
  public static SolarBody Trojans = new SolarBody("Trojans", -1, solDisJupiter, vJupiter, 0, -Math.PI/3);
  public static SolarBody Satellite = new SolarBody("satellite", 100, -1, -1, 0)



  public static SolarBody chooseTheta(String name, double mass, double solDis, double vInitial, double rad){
    Scanner kb = new Scanner(System.in);
    System.out.println("desired theta for "+name);
    double theta = kb.nextDouble();
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
