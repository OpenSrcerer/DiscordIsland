package discordIsland.MySQL;

import net.dv8tion.jda.api.entities.Category;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Delete
{
    public static class Guild
    {
        public static void expunge (String GUILD_ID)
        {
            // initiates connection with database
            Connection conn = new Connection();

            // runs query to check whether guild exists
            // in the guilds table
            if (!conn.checkDatabaseForData("SELECT GUILD_ID FROM GUILDS WHERE GUILD_ID = " + GUILD_ID))
            {
                // adds it if it isn't
                int returnCode = conn.update("DELETE FROM GUILDS WHERE GUILD_ID = " + GUILD_ID + ");");
            }

            conn.closeConnection();
        }
    }

    public static class Member
    {
        // returns category ID so it can get deleted
        public static String expunge (String MEMBER_ID, String GUILD_ID)
        {
            // creates temporary category that will get stored
            String catID = null;
            // initiates connection with database
            Connection conn = new Connection();

            // runs query to check whether member exists
            // in the members table
            if (conn.checkDatabaseForData("SELECT MEMBER_ID FROM MEMBERS WHERE MEMBER_ID = " + MEMBER_ID + " AND GUILD_ID = " + GUILD_ID))
            {
                // call for getCategory to handle SQLException
                // and retrieve category id
                catID = getCategory(conn, MEMBER_ID, GUILD_ID);
                // deletes row
                int returnCode = conn.update("DELETE FROM MEMBERS WHERE MEMBER_ID = " + MEMBER_ID + " AND GUILD_ID = " + GUILD_ID);
            }

            conn.closeConnection();
            return catID;
        }

        public static String getCategory(Connection conn, String MEMBER_ID, String GUILD_ID)
        {
            try
            {
                // need to explicitly set a resultset and
                // .next() due to returning a raw value
                ResultSet rs = conn.query("SELECT CAT_ID FROM MEMBERS WHERE MEMBER_ID = " + MEMBER_ID + " AND GUILD_ID = " + GUILD_ID);
                rs.next();
                return rs.getString(1);
            }
            catch (SQLException ex)
            {
                System.out.println(ex);
            }

            return null;
        }
    }
}
