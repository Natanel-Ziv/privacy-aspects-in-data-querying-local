import java.text.DecimalFormat;
public class Client {

    private static Pair<Double,Double> getMinRange(Server srv, double xMin, double xMax, double timestemp){
        Pair<Double, Double> pair = new Pair(xMin,xMax);
        double sAvg;
        while ((sAvg = srv.getAvgVelocity(pair, timestemp))==-1) pair.setP2(pair.getP2()+1);
        return new Pair(pair.getP2(),sAvg);
    }

    public static double Attack(Server srv, double vTarget, double timestemp){
        int k = 3;
        double xMin, xMax;
        Pair<Double,Double> pair = getMinRange(srv,vTarget,vTarget,timestemp);
        xMax = pair.getP1();
        xMin = vTarget+1;
        xMax = pair.getP1();
        pair = getMinRange(srv,xMin,xMax,timestemp);
        double xFinal = pair.getP1();
        double sAvg1 = pair.getP2();
        pair.setP1(vTarget);
        pair.setP2(xFinal);
        double sAvg2 = srv.getAvgVelocity(pair,timestemp);
        return (k+1)*sAvg2 - k*sAvg1;
    }

    public static void main(String[] args) {
        DecimalFormat df2 = new DecimalFormat("#.##");
        Server srv = new Server();
        double vTarget = 9489.23037298, timestemp = 5703.0;
        double sTarget = Attack(srv, vTarget, timestemp);
        System.out.println("The speed of the attacked car is " + df2.format(sTarget));
    }
}
