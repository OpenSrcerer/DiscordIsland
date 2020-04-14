package discordIsland.Commands.Island;

import discordIsland.Functions.Categories;
import discordIsland.Functions.Chat;
import discordIsland.MySQL.Connection;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class MyIsland extends ListenerAdapter
{
    // TBD: dis~island for your island and
    // dis~island [user] for another person's permalink
    public void onGuildMessageReceived(GuildMessageReceivedEvent ev)
    {
        String[] args = ev.getMessage().getContentRaw().split("\\s+");

        // first cmd check
        if (args[0].equalsIgnoreCase(discordIsland.dIsland.prefix + "myisland"))
        {
            try
            {
                Connection conn = new Connection();

                // checks db if their category exists
                if (!conn.checkDatabaseForData("SELECT CAT_ID FROM MEMBERS WHERE MEMBER_ID = " + ev.getMember().getId() + " AND GUILD_ID = " + ev.getGuild().getId()))
                {
                    Chat.sendMessageWithTyping(ev.getChannel(), "You do not have an island!");
                    return;
                }

                // posts island link
                String channel = Categories.getSingleChannel(ev.getGuild(), Categories.getCategory(conn, ev.getMember().getId(), ev.getGuild().getId()));

                // quick fix for if user has no channels in category
                if (channel.isEmpty())
                {
                    String msg = "**Here's the link to your island, <@" + ev.getMember().getId() + ">!**\n" +
                            "https://www.discordapp.com/channels/" + ev.getGuild().getId() + "/" + Categories.getCategory(conn, ev.getMember().getId(), ev.getGuild().getId());
                    Chat.sendMessageWithTyping(ev.getChannel(), msg);
                    conn.closeConnection();
                    return;
                }

                String msg = "**Here's the link to your island, <@" + ev.getMember().getId() + ">!**\n" +
                        "https://www.discordapp.com/channels/" + ev.getGuild().getId() + "/" + channel;
                Chat.sendMessageWithTyping(ev.getChannel(), msg);
                conn.closeConnection();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
