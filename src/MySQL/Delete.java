package discordIsland.MySQL;

import discordIsland.Functions.Categories;
import java.sql.SQLException;

public class Delete
{
    public static class Guild
    {
        public static void expunge (String GUILD_ID) throws SQLException
        {
            // initiates connection with database
            Connection conn = new Connection();

            // update queries to delete SQL rows related to guild
            // that just kicked bot
            conn.update("DELETE FROM MEMBERS WHERE GUILD_ID = " + GUILD_ID);
            conn.update("DELETE FROM GUILDS WHERE GUILD_ID = " + GUILD_ID);

            conn.closeConnection();
        }
    }

    public static class Member
    {
        // returns category ID so it can get deleted
        public static String expunge (String MEMBER_ID, String GUILD_ID) throws SQLException
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
                catID = Categories.getCategory(conn, MEMBER_ID, GUILD_ID);
                // deletes row
                int returnCode = conn.update("DELETE FROM MEMBERS WHERE MEMBER_ID = " + MEMBER_ID + " AND GUILD_ID = " + GUILD_ID);
            }

            conn.closeConnection();
            return catID;
        }
    }
}
