public class BodyMaths {
  public static final double G = SolarBody.G; // actual value

    // public static final double G = 8.64960768*Math.pow(10, 0); //N body small scale

  public static double dualBodyAccel(double obj[][], double body[][], double massBody, int i, int axis){
    double acceleration;
    acceleration = -G*massBody*(obj[axis][i]-body[axis][i])/Math.pow(radius(obj, body, i), 3);
    return acceleration;
  }//dualBodyAccel()

  public static double norm(double x, double y) {
    double norm = Math.sqrt(x*x+y*y);
    return norm;
  }//norm()

  public static double radius(double body1[][], double body2[][], int i) {
    double x = body1[0][i] - body2[0][i];
    double y = body1[1][i] - body2[1][i];
    return norm(x, y);
  }//radius()

  public static double[][][] updateBody(double obj[][][], double dt, int i){
    obj[1][0][i]=obj[1][0][i-1]+obj[2][0][i]*dt;
    obj[1][1][i]=obj[1][1][i-1]+obj[2][1][i]*dt;
    // position = xI + v*dt
    obj[0][0][i]=obj[0][0][i-1]+obj[1][0][i]*dt;
    obj[0][1][i]=obj[0][1][i-1]+obj[1][1][i]*dt;
    return obj;
  }//updateBody()
}//class
