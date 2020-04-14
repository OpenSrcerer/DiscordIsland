package discordIsland;

import discordIsland.Commands.Island.*;
import discordIsland.Commands.Admin.*;
import discordIsland.Commands.Other.*;
import discordIsland.Listeners.*;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Collection;


public class dIsland
{
    public static JDA dis;
    public static String prefix = "di~";
    private static Collection<GatewayIntent> intents = Arrays.asList(
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_PRESENCES,
            GatewayIntent.GUILD_VOICE_STATES,
            GatewayIntent.GUILD_EMOJIS,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MESSAGE_TYPING
    );

    public static void main(String[] args) throws LoginException
    {
        dis = JDABuilder.create("YOUR-TOKEN-HERE", intents).build();
        dis.getPresence().setStatus(OnlineStatus.ONLINE);
        dis.getPresence().setActivity(Activity.listening("new islands to create!"));

        // StartupUserListener.checkUsers();
        // GUILD LISTENERS
        dis.addEventListener(new GuildJoin());
        dis.addEventListener(new GuildLeave());

        // USER LISTENERS
        dis.addEventListener(new MemberJoin());
        dis.addEventListener(new MemberLeave());

        // COMMAND LISTENERS
        dis.addEventListener(new MyIsland());
        dis.addEventListener(new AddGuest());
        dis.addEventListener(new AddContributor());
        dis.addEventListener(new RemoveGuest());
        dis.addEventListener(new RemoveContributor());
        dis.addEventListener(new Info());
        dis.addEventListener(new SetRole());
        dis.addEventListener(new RemoveRole());
    }
}
