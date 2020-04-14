package discordIsland.Listeners;

import discordIsland.MySQL.New;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

// stuff that happens when the bot joins a new guild
// calls upon adding guild into db

public class GuildJoin extends ListenerAdapter
{
    public void onGuildJoin(GuildJoinEvent ev)
    {
        try
        {
            New.Guild.checkAdd(ev.getGuild().getId());
            System.out.println("Guild " + ev.getGuild().getName() + " (" + ev.getGuild().getId() + ") just added the bot!");
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }
    }
}
