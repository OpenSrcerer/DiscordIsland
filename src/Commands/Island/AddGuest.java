package discordIsland.Commands.Island;

import discordIsland.Functions.Categories;
import discordIsland.Functions.Chat;
import discordIsland.MySQL.Connection;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.regex.Pattern;

public class AddGuest extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent ev)
    {
        String[] args = ev.getMessage().getContentRaw().split("\\s+");

        // first command match check
        if (
                args[0].equalsIgnoreCase(discordIsland.dIsland.prefix + "addguest")
                        && args.length > 1
        )
        {
            Member member = null;

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
                ev.getGuild().getCategoryById(cat)
                        .upsertPermissionOverride(member)
                        .setAllow(Permission.VIEW_CHANNEL).queue();
                Chat.sendMessageWithTyping(ev.getChannel(), "Successfully added <@" + member.getId() + "> to your guest list.");
                conn.closeConnection();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        else if (
                args[0].equalsIgnoreCase(discordIsland.dIsland.prefix + "addguest")
                        && args.length == 1
        )
        {
            Chat.sendMessageWithTyping(ev.getChannel(), "Please provide a user!");
        }
    }
}
