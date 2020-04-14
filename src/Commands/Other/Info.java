package discordIsland.Commands.Other;

import discordIsland.Embeds.Embeds;
import discordIsland.Functions.Chat;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Info extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent ev)
    {
        String[] args = ev.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(discordIsland.dIsland.prefix + "info"))
        {
            Chat.sendEmbedWithTyping(ev.getChannel(), Embeds.getInfo());
        }
    }
}