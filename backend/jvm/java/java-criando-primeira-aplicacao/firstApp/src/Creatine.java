
public class Creatine {
    public static final double CREATINE_PER_KG = 0.06;

    public static double calculateNeedCreatine(double weight) {
        return weight * CREATINE_PER_KG;
    }
}
