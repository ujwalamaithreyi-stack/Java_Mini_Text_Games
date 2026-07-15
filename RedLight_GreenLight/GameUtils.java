package RedLight_GreenLight;
import java.util.Scanner;

class GameUtils {
    // ANSI Color Codes for Terminal
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";

    // Handle player input with time limit
    public static String getPlayerInput(Scanner sc, long roundStart) {
        String action = "";
        while ((System.currentTimeMillis() - roundStart) < 4000 && action.isEmpty()) {
            if (sc.hasNextLine()) {
                action = sc.nextLine().trim().toLowerCase();
            }
        }
        return action;
    }

    // Check whether move was valid or not
    public static int evaluateMove(String action, boolean green, int distance, int goal) {
        // Forgiving inputs: accept 'y' or 'yes'
        boolean wantsToMove = action.equals("yes") || action.equals("y");
        boolean wantsToStop = action.equals("no") || action.equals("n");

        if (green && wantsToMove) {
            distance++;
            System.out.println(GREEN + "✓ You moved forward! " + RESET);
            System.out.println("Track: " + getProgressBar(distance, goal));
        } else if (!green && wantsToMove) {
            System.out.println("\n" + RED + "☠ DETECTED! The doll saw you move! ☠" + RESET);
            System.out.println(RED + "Eliminated." + RESET);
            return -1; 
        } else if (green && wantsToStop) {
            System.out.println(YELLOW + "⚠ You froze in place, but it was safe to move! Wasted time." + RESET);
            System.out.println("Track: " + getProgressBar(distance, goal));
        } else {
            System.out.println(GREEN + "✓ Safe! You stayed perfectly still." + RESET);
            System.out.println("Track: " + getProgressBar(distance, goal));
        }
        return distance;
    }

    // Generates a visual track: [..🏃.......] 🌲
    public static String getProgressBar(int distance, int goal) {
        StringBuilder track = new StringBuilder("[");
        for (int i = 0; i < goal; i++) {
            if (i == distance) {
                track.append("🏃");
            } else {
                track.append(".");
            }
        }
        if (distance >= goal) {
            track.append("🏃] 🏁");
        } else {
            track.append("] 🌲");
        }
        return track.toString();
    }
}