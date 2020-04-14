package discordIsland.Commands.Admin;

import discordIsland.Functions.Chat;
import discordIsland.MySQL.Connection;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.regex.Pattern;

public class SetRole extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent ev)
    {
        String[] args = ev.getMessage().getContentRaw().split("\\s+");

        if (
                args[0].equalsIgnoreCase(discordIsland.dIsland.prefix + "setrole")
                        && args.length > 1
        )
        {
            // check if member has perms
            if (!ev.getMember().hasPermission(Permission.MANAGE_ROLES))
            {
                Chat.sendMessageWithTyping(ev.getChannel(), "You must have the `MANAGE_ROLES` permission in order to use this command.");
                return;
            }

            // check if argument is valid
            if (!Pattern.matches("[0-9]+", args[1]))
            {
                Chat.sendMessageWithTyping(ev.getChannel(), "Please provide a valid role ID!");
                return;
            }

            // check if role exists
            if (ev.getGuild().getRoleById(args[1]) == null)
            {
                Chat.sendMessageWithTyping(ev.getChannel(), "Role with provided ID not found.");
                return;
            }

            else
            {
                try
                {
                    Connection conn = new Connection();
                    int updx = conn.update("UPDATE GUILDS SET ROLE_ID = " + args[1] + " WHERE GUILD_ID = " + ev.getGuild().getId());
                    Chat.sendMessageWithTyping(ev.getChannel(), "<@" + ev.getMember().getId() + ">\nRole " + ev.getGuild().getRoleById(args[1]) + " successfully set.");
                    conn.closeConnection();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }

        else if (
                args[0].equalsIgnoreCase(discordIsland.dIsland.prefix + "setrole")
                        && args.length == 1
        )
        {
            // need to dupe this for now so members
            // don't get the message in the bottom instad of manage roles.
            if (!ev.getMember().hasPermission(Permission.MANAGE_ROLES))
            {
                Chat.sendMessageWithTyping(ev.getChannel(), "You must have the `MANAGE_ROLES` permission in order to use this command.");
                return;
            }

            Chat.sendMessageWithTyping(ev.getChannel(), "Please provide a valid role ID!");
        }
    }
}
