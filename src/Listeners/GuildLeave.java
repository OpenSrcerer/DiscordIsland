package discordIsland.Listeners;

import discordIsland.MySQL.Delete;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// to be completed, what is the event that
// gets triggered upon a guild deletion?
public class GuildLeave extends ListenerAdapter
{
    public void onGuildLeaveEvent(GuildLeaveEvent ev)
    {
        Delete.Guild.expunge(ev.getGuild().getId());
    }
}
