package hyperbot;

import java.util.Random;

public class HyperBotLootPrompt extends ExternalCommand{
    public HyperBotLootPrompt() {
        super("text", guild, channel, "");
        content = pickPrompt();
    }

    public String pickPrompt(){
        String[] prompts = new String[]{
          "Woo Wee! Some loot here!",
          "Holy shit! You got loot!",
          "Yo, here's what you found!",
          "F U C K I N L O O T",
          "Loot is Loot",
          "Found this on the ground."
        };

        Random random = new Random();
        return prompts[random.nextInt(prompts.length)];
    }
}
