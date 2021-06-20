package exKid.command;

public class Parser {
    private Command getCommandFromText(String text) {
        Command command = Command.NONE;
        if(text.contains(" ")) {
            text = text.substring(0, text.indexOf(" "));
        }

        if(text.startsWith("/"))
            text = text.substring(1);

        try {
            command = Command.valueOf(text);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return command;
    }

    public ParsedCommand getParsedCommand(String inputText) {
        String text = "";
        if(inputText != null) {
            text = inputText.trim().toUpperCase();
        }

        ParsedCommand result = new ParsedCommand(Command.NONE);

        if(text.equals(""))
            return result;

        result.setCommand(getCommandFromText(text));

        return result;
    }
}
