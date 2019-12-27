package hyperbot;

public class ExternalCommand {
    public String guildId;
    public String channelId;
    public String content;
    public String type;

    public static final String guild = "524035064523259924";
    public static final String channel = "524035064523259926";

    public ExternalCommand(String type, String guild, String channel, String content) {
        this.guildId = guild;
        this.channelId = channel;
        this.content = content;
        this.type = type;
    }

    public ExternalCommand(String type, String content) {
        this.guildId = guild;
        this.channelId = channel;//524035064523259926
        this.content = content;
        this.type = type;
    }

    public ExternalCommand(String type, String title, String body) {
        this.guildId = guild;
        this.channelId = channel;//524035064523259926
        this.content = String.format("%s;%s",title,body);
        this.type = type;
    }


}
