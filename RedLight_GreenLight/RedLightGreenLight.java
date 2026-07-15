package RedLight_GreenLight;
import java.util.Scanner;
import java.util.Random;

public class RedLightGreenLight {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        int distance = 0;      
        int goal = 10;         
        int maxTime = 40;      
        long startTime = System.currentTimeMillis();

        System.out.println(GameUtils.CYAN + "\n=======================================" + GameUtils.RESET);
        System.out.println(GameUtils.YELLOW + "       RED LIGHT, GREEN LIGHT" + GameUtils.RESET);
        System.out.println(GameUtils.CYAN + "=======================================" + GameUtils.RESET);
        System.out.println("Reach the tree (" + GameUtils.GREEN + "🌲" + GameUtils.RESET + ") in " + maxTime + " seconds!");
        System.out.println("Type " + GameUtils.GREEN + "'yes' (or 'y')" + GameUtils.RESET + " to step forward, " + GameUtils.RED + "'no' (or 'n')" + GameUtils.RESET + " to stay still.");
        System.out.println("---------------------------------------\n");
        
        try {
            System.out.print("Preparing game... ");
            Thread.sleep(1500);
            System.out.println("Go!\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            long elapsed = (System.currentTimeMillis() - startTime) / 1000;
            if (elapsed >= maxTime) {
                System.out.println("\n" + GameUtils.RED + "⏰ Time’s up! You didn’t make it to the tree." + GameUtils.RESET);
                System.out.println(GameUtils.RED + "Eliminated." + GameUtils.RESET);
                break;
            }

            long timeLeft = maxTime - elapsed;

            // Doll decision
            boolean green = rand.nextBoolean();
            String lightColor = green ? GameUtils.GREEN : GameUtils.RED;
            String lightText = green ? "GREEN LIGHT (Doll turned away)" : "RED LIGHT (Doll watching!)";
            
            // Suspense buildup
            System.out.print("\nThe doll is turning... ");
            try { Thread.sleep(800); } catch (InterruptedException e) {}

            System.out.println(lightColor + "🚦 " + lightText + GameUtils.RESET + " | Time left: " + timeLeft + "s");

            // Player response
            long roundStart = System.currentTimeMillis();
            System.out.print("Action? (y/n): ");
            String action = GameUtils.getPlayerInput(sc, roundStart);

            // Timeout handling
            if (action.isEmpty()) {
                System.out.println(GameUtils.YELLOW + "⚡ Too slow! You panicked and froze." + GameUtils.RESET);
                System.out.println("Track: " + GameUtils.getProgressBar(distance, goal));
                continue;
            }

            // Evaluate action
            distance = GameUtils.evaluateMove(action, green, distance, goal);
            if (distance == -1) break; // eliminated

            // Win condition
            if (distance >= goal) {
                System.out.println("\n" + GameUtils.GREEN + "🎉 SUCCESS! You crossed the line and survived! 🎉" + GameUtils.RESET);
                break;
            }
        }

        sc.close();
    }
}