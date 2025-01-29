import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Input your weight in kg: ");
        Scanner scanner = new Scanner(System.in);

        double weight = scanner.nextDouble();
        double creatineAmount = Creatine.calculateNeedCreatine(weight);
        System.out.println("You should consume: " + String.format("%.2f", creatineAmount) + " grams of creatine per day");
        System.out.println("*Count on the support of a nutritionist to calculate the amount of creatine indicated for you");
    }
}