public class BodyMaths {
  public static final double G = 8.64960768*Math.pow(10, -13);

  public static double[][][] euler(double obj[][][], double body1[][], double body2[][], double m1, double m2, int i, double dt){
    // acceleration
    obj[2][0][i]= dualBodyAccel(obj[0], body1, m1, i-1, 0) + dualBodyAccel(obj[0], body2, m2, i-1, 0); //x accel from first body
    obj[2][1][i]+= dualBodyAccel(obj[0], body1, m1, i-1, 1) + dualBodyAccel(obj[0], body2, m2, i-1, 1); //y accel from first body
    // velocity = vI + a*dt
    obj[1][0][i]=obj[1][0][i-1]+obj[2][0][i]*dt;
    obj[1][1][i]=obj[1][1][i-1]+obj[2][1][i]*dt;
    // position = xI + v*dt
    obj[0][0][i]=obj[0][0][i-1]+obj[1][0][i]*dt;
    obj[0][1][i]=obj[0][1][i-1]+obj[1][1][i]*dt;

    return obj;
  }

  public static double dualBodyAccel(double obj[][], double body[][], double massBody, int i, int axis){
    double acceleration;
    acceleration = -G*massBody*(obj[axis][i]-body[axis][i])/Math.pow(radius(obj, body, i), 3);
    return acceleration;
  }
  public static double norm(double x, double y) {
    double norm = Math.sqrt(x*x+y*y);
    return norm;
  }//norm()

  public static double radius(double body1[][], double body2[][], int i) {
    double x = body1[0][i] - body2[0][i];
    double y = body1[1][i] - body2[1][i];
    return norm(x, y);
  }//radius()


}
