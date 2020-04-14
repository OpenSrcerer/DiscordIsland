package discordIsland.Listeners;

import discordIsland.MySQL.Delete;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;
import java.util.List;

public class MemberLeave extends ListenerAdapter
{
    public void onGuildMemberRemove(GuildMemberRemoveEvent ev)
    {
        try
        {
            // queues category for deletion (already gone from db)
            Category category = ev.getGuild().getCategoryById(Delete.Member.expunge(ev.getMember().getId(), ev.getGuild().getId()));

            // check if category isn't empty
            if (!category.equals(""))
            {
                // recursively deletes category channels
                List<GuildChannel> channels = category.getChannels();
                for (GuildChannel gc : channels)
                {
                    gc.delete().reason("User has left the server.").queue();
                }
                // deletes category itself
                category.delete().reason("User has left the server.").queue();
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }
    }
}
