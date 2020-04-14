package discordIsland.Functions;

import discordIsland.MySQL.Connection;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// functions file for Category-related stuff
// self explanatory titles

public class Categories
{
    public static String getSingleChannel(Guild guild, String CAT_ID)
    {
        Category category = guild.getCategoryById(CAT_ID);
        List<GuildChannel> channels = category.getChannels();
        return channels.get(0).getId();
    }

    public static String getCategory(Connection conn, String MEMBER_ID, String GUILD_ID)
    {
        try
        {
            // need to explicitly set a resultset and
            // .next() due to returning a raw value
            ResultSet rs = conn.query("SELECT CAT_ID FROM MEMBERS WHERE MEMBER_ID = " + MEMBER_ID + " AND GUILD_ID = " + GUILD_ID);
            rs.beforeFirst();
            rs.next();
            return rs.getString(1);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }
}
