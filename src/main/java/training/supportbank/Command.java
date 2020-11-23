package training.supportbank;

public class Command {
    private final String text;
    private final String description;

    public Command(String text, String description) {
        this.text = text;
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public String getDescription() {
        return description;
    }
}
