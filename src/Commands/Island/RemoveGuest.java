package discordIsland.Commands.Island;

import discordIsland.Functions.Categories;
import discordIsland.Functions.Chat;
import discordIsland.Functions.PermissionOverride;
import discordIsland.MySQL.Connection;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.regex.Pattern;

public class RemoveGuest extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent ev)
    {
        String[] args = ev.getMessage().getContentRaw().split("\\s+");

        // first command check
        if (
                args[0].equalsIgnoreCase(discordIsland.dIsland.prefix + "removeguest")
                        && args.length > 1
        )
        {
            Member member = null;

            // pattern checks
            if (!Pattern.matches("[0-9]+", args[1]) && ev.getMessage().getMentionedMembers().isEmpty())
            {
                Chat.sendMessageWithTyping(ev.getChannel(), "Please provide a valid user ID or mention!");
                return;
            }
            else if (Pattern.matches("[0-9]+", args[1]))
            {
                // check if user exists
                if (ev.getGuild().getMemberById(args[1]) == null)
                {
                    Chat.sendMessageWithTyping(ev.getChannel(), "Member with that ID not found in this server!");
                    return;
                }

                member = ev.getGuild().getMemberById(args[1]);
            }
            else if (!ev.getMessage().getMentionedMembers().isEmpty())
            {
                member = ev.getMessage().getMentionedMembers().get(0);
            }

            try
            {
                Connection conn = new Connection();
                if (!conn.checkDatabaseForData("SELECT CAT_ID FROM MEMBERS WHERE MEMBER_ID = " + ev.getMember().getId() + " AND GUILD_ID = " + ev.getGuild().getId()))
                {
                    Chat.sendMessageWithTyping(ev.getChannel(), "You do not have an island!");
                    return;
                }

                String cat = Categories.getCategory(conn, ev.getMember().getId(), ev.getGuild().getId());

                // check if the permission override exists
                // if not, display that person isn't a guest
                // if condition that takes a list of member overrides
                // permissionoverride.checkuseroverride returns bool
                if (
                        !PermissionOverride.checkUserOverride(
                                ev.getGuild().getCategoryById(cat).getMemberPermissionOverrides(),
                                member
                        )
                )
                {
                    Chat.sendMessageWithTyping(ev.getChannel(), "User is not a guest in your island!");
                    return;
                }

                // Deletes PO of user completely.
                ev.getGuild().getCategoryById(cat)
                        .getPermissionOverride(member)
                        .delete().queue();

                Chat.sendMessageWithTyping(ev.getChannel(), "Successfully removed <@" + member.getId() + "> from your guest list.");
                conn.closeConnection();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        else if (
                args[0].equalsIgnoreCase(discordIsland.dIsland.prefix + "removeguest")
                        && args.length == 1
        )
        {
            Chat.sendMessageWithTyping(ev.getChannel(), "Please provide a user!");
        }
    }
}
