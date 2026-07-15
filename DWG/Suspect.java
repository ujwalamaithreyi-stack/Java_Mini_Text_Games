package DWG;
import javax.swing.JTextArea;

public class Suspect {
    private String name;
    private boolean isTruthful; // Clearer variable naming
    private String statement;
    private String clueResponse;

    public Suspect(String name, boolean isTruthful, String statement, String clueResponse) {
        this.name = name;
        this.isTruthful = isTruthful;
        this.statement = statement;
        this.clueResponse = clueResponse;
    }

    // Getters to follow proper encapsulation rules
    public String getName() {
        return name;
    }

    public boolean isTruthful() {
        return isTruthful;
    }

    public void pov(JTextArea area) {
        area.append("\n\n" + name.toUpperCase() + ": " + statement);
    }

    public void clueReact(JTextArea area) {
        area.append("\n\n" + name.toUpperCase() + ": " + clueResponse);
    }
}