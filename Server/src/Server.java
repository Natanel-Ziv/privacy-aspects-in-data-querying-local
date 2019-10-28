import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Server {
    private WorkingWithDatasets ds;
    private BST db;
    private int k;

    public Server(String path, int k) {
        try {
            ds = new WorkingWithDatasets(path);
            //db = ds.getDB();
            this.k = k;
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public BST getDb() {
        return db;
    }

    public double getAvgVelocity(Pair<Double, Double> range, double timestamp) {
        double res = -1;
        List<Double> allVelocities = ds.getVelocityInRange(timestamp, range);
        System.out.println("List Size: " + allVelocities.size());
        if(allVelocities.size() >= this.k) {
            res = 0;
            for(Double vel : allVelocities)
                res += vel;
            res /= allVelocities.size();
        }
        return res;
    }

    public static void main(String[] args) {
        Server server = new Server("Server/fixedVelocities_10_MB.txt", 1);
        //Server server = new Server("D:/koln-pruned.tr", 1);
        Pair<Double, Double> range = new Pair<>(5214.0, 13700.0);
        double timestamp = 3646;
        Instant start = Instant.now();
        double avgVel = server.getAvgVelocity(range, timestamp);
        Instant end = Instant.now();
        System.out.println("Avg velocity for range: [" + range.getP1() + ", " + range.getP2() +"]\n"
                + "timestamp: " + timestamp
                + " is: " + avgVel + "\n" + Duration.between(start, end));
    }
}
