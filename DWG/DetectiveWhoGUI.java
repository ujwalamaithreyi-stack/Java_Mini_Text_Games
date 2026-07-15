package DWG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DetectiveWhoGUI extends JFrame implements ActionListener {

    JTextArea display;
    JTextField input;
    JButton submitBtn;
    String playerName = "";
    boolean nameEntered = false, caseStarted = false, caseEnded = false;
    int stage = 0;
    ArrayList<String> suspectsIntro = new ArrayList<>();
    ArrayList<Suspect> caseSuspects = new ArrayList<>(); // Updated to capitalized Suspect
    ArrayList<String> clues = new ArrayList<>();
    String culprit = "", result = "";

    boolean foundUSB = false, foundCloth = false, foundBurntPlastic = false;

    public DetectiveWhoGUI() {
        setTitle("Detective Who - GUI Edition");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextArea();
        display.setEditable(false);
        display.setFont(new Font("Consolas", Font.PLAIN, 16));
        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        add(new JScrollPane(display), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        input = new JTextField();
        submitBtn = new JButton("Enter");
        submitBtn.addActionListener(this);
        inputPanel.add(input, BorderLayout.CENTER);
        inputPanel.add(submitBtn, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        display.setText("======== DETECTIVE WHO ========\n\nEnter your name, detective:");
    }

    public void actionPerformed(ActionEvent e) {
        String text = input.getText().trim();
        input.setText("");

        if (!nameEntered) {
            playerName = text;
            nameEntered = true;
            display.setText("\nWelcome aboard Detective " + playerName.toUpperCase() + "!\n"
                    + "A high-stakes case just landed on your desk.\n"
                    + "Gather evidence, question suspects, and uncover the truth!\n"
                    + "\nPress Enter to begin...");
            return;
        }

        if (!caseStarted) {
            startCase();
            caseStarted = true;
            return;
        }

        switch(stage) {
            case 0:
                showSuspectsIntro();
                stage++;
                break;
            case 1:
                showSuspectStatements();
                stage++;
                break;
            case 2:
                museumChoices(text);
                break;
            case 3:
                showClues();
                stage++;
                break;
            case 4:
                interrogation();
                stage++; 
                break;
            case 5:
                conclude(text);
                break;
        }
    }

    void startCase() {
        display.setText(">>> RAVENHALL MUSEUM - 12:01 AM <<<\n\n"
                + "ALARM BLARING... Museum security lights flicker back on.\n"
                + "The Emerald Diadem is gone, replaced by a fake!\n"
                + "Three people were inside.\nYour job: Find the thief before sunrise.\n\nPress Enter to meet the suspects.");

        suspectsIntro.clear();
        caseSuspects.clear();
        clues.clear();

        suspectsIntro.add("1. Evelyn - Art Restorer");
        suspectsIntro.add("2. Marcus - Security Guard");
        suspectsIntro.add("3. Dylan - Tech Consultant");

        caseSuspects.add(new Suspect("Evelyn", false,
                "I was working in the restoration lab and only left once to fetch resin.",
                "Burnt plastic? Oh, that must be from the old 3D printer. It must've overheated, I didn't even use it tonight."));
        caseSuspects.add(new Suspect("Marcus", true,
                "I was in the security room fixing cables when the alarm tripped.",
                "The USB is mine. I was just saving some security camera presets from the desk computer to a backup."));
        caseSuspects.add(new Suspect("Dylan", true,
                "I was calibrating the drone camera for tomorrow's exhibit.",
                "Cleaning cloth? I was dusting the display cases earlier. Maybe it tore when I tripped over the stand."));

        clues.add(">> CCTV froze: 11:43-11:48");
        clues.add(">> Jewel case unbroken");
        clues.add(">> USB drive found (to hack the CCTV?) - Marcus was there");
        clues.add(">> Torn cleaning cloth found (to wipe fingerprints?) - Dylan was there");
        clues.add(">> Burnt 3D printer plastic (to create a replica?) - Evelyn was there");

        culprit = "Evelyn";
        result = "It was EVELYN.\nShe cloned the diadem with the 3D printer, froze the cameras, and swapped the real one.\n";

        stage = 0;
    }

    void showSuspectsIntro() {
        display.setText("======== SUSPECTS ========\n");
        for (String x : suspectsIntro) {
            display.append("\n" + x);
        }
        display.append("\n\nPress Enter to hear their statements.");
    }

    void showSuspectStatements() {
        display.setText("======== SUSPECTS' STATEMENTS ========\n");
        for (Suspect s : caseSuspects) {
            s.pov(display);
        }
        display.append("\n\nType 'search' to begin investigating locations.");
    }

    void museumChoices(String text) {
        text = text.trim().toUpperCase();

        if (text.equals("SEARCH")) {
            display.setText("======== GATHER EVIDENCE ========\n\n"
                    + "Choose a place to search:\n"
                    + "A) Security Room\n"
                    + "B) Exhibition Hall\n"
                    + "C) Restoration Lab\n\n"
                    + "(Type A/B/C to search a location)");
            return;
        }

        switch (text) {
            case "A":
                display.append("\n\nSearching SECURITY ROOM...\n"
                        + "<evidence>: CCTV froze 11:43–11:48 PM.\n"
                        + "<evidence>: A warm USB stick sits on the desk.");
                foundUSB = true;
                break;

            case "B":
                display.append("\n\nSearching EXHIBITION HALL...\n"
                        + "<evidence>: Glass unbroken. Lock cleanly bypassed.\n"
                        + "<evidence>: A torn cleaning cloth on the floor.");
                foundCloth = true;
                break;

            case "C":
                display.append("\n\nSearching RESTORATION LAB...\n"
                        + "<evidence>: Smell of burnt plastic. A 3D printer still warm.");
                foundBurntPlastic = true;
                break;

            case "YES":
                display.append("\n\nChoose another place to search:\nA) Security Room\nB) Exhibition Hall\nC) Restoration Lab");
                return; 
            case "NO":
                display.append("\n\nDone searching. Press Enter to review clues.");
                stage = 3; 
                return;
            default:
                display.append("\n\nYou hesitate... time passes.");
                return;
        }

        display.append("\n\nSearch another? (yes/no)");
    }

    void showClues() {
        display.setText("======== CLUES ========\n");
        for (String x : clues) {
            display.append("\n" + x);
        }
        display.append("\n\nPress Enter to begin interrogation.");
    }

    void interrogation() {
        display.setText("======== INTERROGATION ========\n");

        if (foundBurntPlastic) {
            display.append("\nYOU: Evelyn, the 3D printer in the Restoration Lab was still warm - and smelled of burnt plastic.");
            caseSuspects.get(0).clueReact(display);
        }
        if (foundUSB) {
            display.append("\n\nYOU: Marcus, a USB was found still warm in the Security Room. Care to explain?");
            caseSuspects.get(1).clueReact(display);
        }
        if (foundCloth) {
            display.append("\n\nYOU: Dylan, a torn cleaning cloth was found near the display case.");
            caseSuspects.get(2).clueReact(display);
        }

        display.append("\n\nWho do you accuse? (Type name)");
    }

    void conclude(String guess) {
        if (guess.equalsIgnoreCase(culprit)) {
            display.setText("You are right, Detective " + playerName.toUpperCase() + "!\n\n" + result + "\nCASE SUCCESSFULLY CLOSED!");
        } else {
            display.setText("You are wrong, Detective " + playerName.toUpperCase() + "!\nThe real thief escapes into the night.\n\nThe culprit was " + culprit.toUpperCase() + "!\n" + result);
        }
        caseEnded = true;
        submitBtn.setEnabled(false);
        input.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DetectiveWhoGUI().setVisible(true));
    }
}