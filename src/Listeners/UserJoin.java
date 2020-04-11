package discordIsland.Listeners;

import discordIsland.MySQL.New;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserJoin extends ListenerAdapter
{
    public void onGuildMemberJoin(GuildMemberJoinEvent ev)
    {
        // checks if the guild the member just joined
        // is part of the database, and adds it if it's not
        New.Guild.checkAdd(ev.getGuild().getId());
        New.Member.checkAdd(ev.getMember().getId(), ev.getGuild().getId(), createChannels(ev));
        setMemberRank(ev.getGuild(), ev.getMember());
        sendWelcome(ev.getGuild(), ev.getMember());
    }

    // adds a specific user-based permission to the category, upon it's creation
    public void updatePermissions(Category cat, Member member)
    {
        cat.createPermissionOverride(member)
        .setAllow(Permission.MANAGE_CHANNEL)
        .queue();
    }

    // sends a DM to user (guild welcome)
    public void sendWelcome(Guild guild, Member member)
    {
        String message = "Hello there! Welcome to " + guild.getName() + "! We hope you enjoy your time browsing islands.";
        member.getUser().openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(message).queue();
        });
    }

    // temporary for testing server
    public void setMemberRank(Guild guild, Member member)
    {
        if (guild.getId().equals("token"))
        {
            try
            {
                // gives member starting role
                guild.addRoleToMember(member, guild.getRoleById(697708811129061406L)).complete();
            }
            catch (InsufficientPermissionException ex)
            {
                System.out.println(ex);
            }
        }
    }

    // needs to return string to pass to database
    public String createChannels(GuildMemberJoinEvent ev)
    {
        // creates new category in user's name
        Category cat = ev.getGuild().createCategory(ev.getUser().getAsTag() + "'s Island").complete();
        // adds specific permissions to the user
        updatePermissions(cat, ev.getMember());
        // creates a starting text channel for the user
        TextChannel tc = ev.getGuild().createTextChannel("Island Chat").setParent(cat).complete();

        return cat.getId();
    }
}
