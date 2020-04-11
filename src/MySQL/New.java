package discordIsland.MySQL;

import discordIsland.MySQL.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;

// class for all the new MYSQL objects that get created
// inner static classes -> a new object

public class New
{
    public static class Guild
    {
        public static void checkAdd (String GUILD_ID)
        {
            // initiates connection with database
            Connection conn = new Connection();

            // runs query to check whether guild exists
            // in the guilds table
            if (!conn.checkDatabaseForData("SELECT GUILD_ID FROM GUILDS WHERE GUILD_ID = " + GUILD_ID))
            {
                // adds it if it isn't
                int returnCode = conn.update("INSERT INTO GUILDS (GUILD_ID) VALUES (" + GUILD_ID + ");");
            }

            conn.closeConnection();
        }
    }

    public static class Member
    {
        public static void checkAdd (String MEMBER_ID, String GUILD_ID, String CAT_ID)
        {
            // initiates connection with database
            Connection conn = new Connection();

            // runs query to check whether member exists
            // in the members table
            if (!conn.checkDatabaseForData("SELECT MEMBER_ID FROM MEMBERS WHERE MEMBER_ID = " + MEMBER_ID + " AND GUILD_ID = " + GUILD_ID))
            {
                // adds them if they aren't
                int returnCode = conn.update("INSERT INTO MEMBERS (MEMBER_ID, GUILD_ID, CAT_ID) VALUES (" + MEMBER_ID + "," + GUILD_ID + "," + CAT_ID + ");");
            }

            conn.closeConnection();
        }
    }
}
