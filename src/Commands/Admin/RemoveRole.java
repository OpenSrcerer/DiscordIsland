package discordIsland.Commands.Admin;

import discordIsland.Functions.Chat;
import discordIsland.MySQL.Connection;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class RemoveRole extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent ev)
    {
        String[] args = ev.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(discordIsland.dIsland.prefix + "removerole"))
        {
            // checks for the permission (self explanatory)
            if (!ev.getMember().hasPermission(Permission.MANAGE_ROLES))
            {
                Chat.sendMessageWithTyping(ev.getChannel(), "You must have the `MANAGE_ROLES` permission in order to use this command.");
                return;
            }

            try
            {
                // connects to database and updates role
                Connection conn = new Connection();
                int updx = conn.update("UPDATE GUILDS SET ROLE_ID = null WHERE GUILD_ID = " + ev.getGuild().getId());
                Chat.sendMessageWithTyping(ev.getChannel(), "@<" + ev.getMember().getId() + ">\nRole on user join successfully removed.");
                conn.closeConnection();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
