package discordIsland.Embeds;

import net.dv8tion.jda.api.EmbedBuilder;

public class Embeds
{
    public static EmbedBuilder getInitiation(String guild, String memberId)
    {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Welcome!");
        eb.setImage("https://cdn.discordapp.com/avatars/697763928528257044/58f76c2e6868aa401db121499046986f.png?size=256");
        eb.setDescription("Hi <@" + memberId + "> and welcome to " + guild + ", the place where you can customize your own little spot inside a server!");
        eb.addField("Let's get you started.", "The channel you're currently in is the first one in your category. You can add more of them " +
                "just like you usually do, by clicking the \"`+`r\" sign. Customize your island, and go visit other people's!" ,false);
        eb.addField("Currently, *only you* can see your island.", "In order to make it accessible to a member, you can use `di~addguest` " +
                "to give them permissions to view it. di~removeguest to revoke their permissions.", false);
        eb.addField("If you want to give a user island editing permissions:", "Use `di~addcontributor`. " +
                "Warning: by executing this command you will give that user full permissions to change anything in your island!", false);
        eb.addField("If you ever can't find your island:", "Use the command `di~myisland` to get a direct link to it.", false);
        eb.setColor(0xc2b280);
        return eb;
    }

    public static EmbedBuilder getInfo()
    {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("ℹ Command list for Discord Island ℹ");
        eb.addField("------------- Island Commands -------------", "", false);
        eb.addField("di~myisland", "Syntax: `di~myisland`. Gives you a permalink to your island.", false);
        eb.addField("di~addguest", "Syntax: `di~addguest [user]`. Adds view permissions to a user.", false);
        eb.addField("di~removeguest", "Syntax: `di~removeguest [user]`. Revokes view permissions to a user.", false);
        eb.addField("di~addcontibutor", "Syntax: `di~addcontributor [user]`. Adds category management permissions to a user.", false);
        eb.addField("di~removecontributor", "Syntax: `di~removecontributor [user]`. Revokes category management permissions to a user.", false);
        eb.addField("------------- Other Commands -------------", "", false);
        eb.addField("di~info", "Syntax: `di~info`. Shows this message.", false);
        eb.addField("------------- Administrator Commands -------------", "", false);
        eb.addField("di~setrole", "Syntax: `di~setrole [role]`. Sets up a role to be given to newcomers on join.", false);
        eb.addField("di~removerole", "Syntax: `di~removerole`. Disables the set on join role feature.", false);
        eb.setColor(0xc2b280);
        eb.setFooter("Created by Bonkers#6969");
        return eb;
    }
}
