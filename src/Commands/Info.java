package discordIsland.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Info extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent ev)
    {
        String[] args = ev.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(discordIsland.dIsland.prefix + "info"))
        {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("ℹ Information for Discord Island ℹ");
            eb.setDescription("Hi! I'm Discord Island, a bot to bring people together to build their own fancy little corners of customization.");
            // eb.addField("Inf", "Displays this message.", false);
            eb.setColor(0xc2b280);
            eb.setFooter("Created by Bonkers#6969");

            ev.getChannel().sendTyping().queue();
            ev.getChannel().sendMessage(eb.build()).queue();
            eb.clear();
        }
    }
}