package discordIsland.Listeners;

import discordIsland.MySQL.Delete;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

// stuff that happens when the bot joins a new guild
// removes everything related to guild from db

public class GuildLeave extends ListenerAdapter
{
    public void onGuildLeave(GuildLeaveEvent ev)
    {
        try
        {
            Delete.Guild.expunge(ev.getGuild().getId());
            System.out.println("Guild " + ev.getGuild().getName() + " (" + ev.getGuild().getId() + ") just removed the bot!");
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
