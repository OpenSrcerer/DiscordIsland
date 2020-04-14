package discordIsland.Listeners;

import discordIsland.Embeds.Embeds;
import discordIsland.Functions.Categories;
import discordIsland.Functions.Chat;
import discordIsland.MySQL.Connection;
import discordIsland.MySQL.New;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberJoin extends ListenerAdapter
{
    public void onGuildMemberJoin(GuildMemberJoinEvent ev)
    {
        // checks if the guild the member just joined
        // is part of the database, and adds it if it's not
        try
        {
            // New.Guild.checkAdd(ev.getGuild().getId());
            New.Member.checkAdd(ev.getMember().getId(), ev.getGuild().getId(), createChannels(ev));
            setMemberRank(ev.getGuild(), ev.getMember());
            sendWelcome(ev.getGuild(), ev.getMember());
        }
        // catch for sql statement errors
        // if db doesn't work out, cancel adding new member to database
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    // adds a specific user-based permission to the category, upon it's creation
    public void setStartingPermissions(Category cat, Member member, Role everyone)
    {
        cat.createPermissionOverride(member)
                .setAllow(
                        Permission.VIEW_CHANNEL,
                        Permission.MESSAGE_WRITE,
                        Permission.MANAGE_CHANNEL
                )
                .queue();

        cat.createPermissionOverride(everyone)
                .setDeny(Permission.VIEW_CHANNEL)
                .queue();
    }

    // sends a DM to user (guild welcome)
    // (deprecated)
    /*
    public void sendWelcome(Guild guild, Member member)
    {
        String message = "Hello there! Welcome to " + guild.getName() + "! We hope you enjoy your time browsing islands.";
        member.getUser().openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(message).queue();
        });
    }*/

    public void sendWelcome(Guild guild, Member member)
    {
        // temporary channel variable
        TextChannel channel = null;

        try
        {
            // gets channel to send welcome in
            Connection conn = new Connection();
            channel = guild.getTextChannelById(
                    Categories.getSingleChannel(guild, Categories.getCategory(conn, member.getId(), guild.getId()))
            );
            conn.closeConnection();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        Chat.sendEmbedWithTyping(channel, Embeds.getInitiation(guild.getName(), member.getId()));
    }

    // set new member's rank, if added to db
    public void setMemberRank(Guild guild, Member member)
    {
        try
        {
            Connection conn = new Connection();
            ResultSet rs = conn.query("SELECT ROLE_ID FROM GUILDS WHERE GUILD_ID = " + guild.getId());
            rs.first();

            try
            {
                // gives member starting role
                // setup in the database by di~setrole
                guild.addRoleToMember(member, guild.getRoleById(rs.getString(1))).complete();
            }
            catch (Exception ex)
            {
                // if database returns a null value (role not set)
                // cancel giving role to user
                return;
            }

            conn.closeConnection();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    // needs to return string to pass to database
    public String createChannels(GuildMemberJoinEvent ev)
    {
        // creates new category in user's name
        Category cat = ev.getGuild().createCategory(ev.getUser().getAsTag() + "'s Island").complete();
        // creates a starting text channel for the user
        TextChannel tc = ev.getGuild().createTextChannel("Island Chat").setParent(cat).complete();
        // adds specific permissions to the user
        // after channel creation so the channel gets synced
        setStartingPermissions(cat, ev.getMember(), ev.getGuild().getPublicRole());

        return cat.getId();
    }
}
