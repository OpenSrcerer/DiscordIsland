package discordIsland.Commands.Island;

import discordIsland.Functions.Categories;
import discordIsland.Functions.Chat;
import discordIsland.Functions.PermissionOverride;
import discordIsland.MySQL.Connection;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.regex.Pattern;

public class RemoveContributor extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent ev)
    {
        String[] args = ev.getMessage().getContentRaw().split("\\s+");

        // first check, command match
        if (
                args[0].equalsIgnoreCase(discordIsland.dIsland.prefix + "removecontributor")
                        && args.length > 1
        )
        {
            Member member = null;

            // second check, if message has user ID or mention
            if (!Pattern.matches("[0-9]+", args[1]) && ev.getMessage().getMentionedMembers().isEmpty())
            {
                Chat.sendMessageWithTyping(ev.getChannel(), "Please provide a valid user ID or mention!");
                return;
            }

            // third checks in order to fill member variable
            else if (Pattern.matches("[0-9]+", args[1]))
            {
                // check if user exists in guild
                if (ev.getGuild().getMemberById(args[1]) == null)
                {
                    Chat.sendMessageWithTyping(ev.getChannel(), "Member with that ID not found in this server!");
                    return;
                }
                member = ev.getGuild().getMemberById(args[1]);
            }

            // if mentioned member list isn't empty, pass value to member
            else if (!ev.getMessage().getMentionedMembers().isEmpty())
            {
                member = ev.getMessage().getMentionedMembers().get(0);
            }

            try
            {
                Connection conn = new Connection();

                // check if the island exists
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
                    Chat.sendMessageWithTyping(ev.getChannel(), "User does not have access to your island!");
                    return;
                }

                net.dv8tion.jda.api.entities.PermissionOverride po =
                        ev.getGuild().getCategoryById(cat).getPermissionOverride(member);

                // checks if the override has the manage channel permission
                if (!PermissionOverride.checkUserOverridePermission(
                        po.getAllowed(), Permission.MANAGE_CHANNEL
                        ))
                {
                    Chat.sendMessageWithTyping(ev.getChannel(), "<@" + member.getId() + "> is not a contributor in your island!");
                    return;
                }

                // removes MANAGE_CHANNEL from user
                ev.getGuild().getCategoryById(cat)
                        .upsertPermissionOverride(member)
                        .setDeny(Permission.MANAGE_CHANNEL).queue();

                Chat.sendMessageWithTyping(ev.getChannel(), "Successfully removed <@" + member.getId() + "> from your contributor list.");
                conn.closeConnection();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        else if (
                args[0].equalsIgnoreCase(discordIsland.dIsland.prefix + "removecontributor")
                        && args.length == 1
        )
        {
            Chat.sendMessageWithTyping(ev.getChannel(), "Please provide a user!");
        }
    }
}
