package discordIsland.Functions;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class Chat
{
    public static void sendMessageWithTyping (TextChannel ch, String msg)
    {
        ch.sendTyping().queue();
        ch.sendMessage(msg).queue();
    }

    public static void sendEmbedWithTyping (TextChannel ch, EmbedBuilder eb)
    {
        ch.sendTyping().queue();
        ch.sendMessage(eb.build()).queue();
    }
}
