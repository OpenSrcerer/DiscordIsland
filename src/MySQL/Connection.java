package discordIsland.MySQL;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// base driver file for connection with your database

public class Connection
{
    private java.sql.Connection conn = null;

    // creates connection to db
    public Connection()
    {
        try
        {
            // FORMAT: DriverManager.getConnection(
            //            "jdbc:mysql://servername:port/dbname"
            //            "DBUSERNAME"
            //            "DBPASSWORD"

            conn = DriverManager.getConnection(
                    "LINE1",
                    "LINE2",
                    "LINE3"
            );
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }
    }

    // queries database for non-modifying queries
    public ResultSet query (String Query) throws SQLException
    {
        Statement stmt;
        stmt = conn.createStatement();
        return (stmt.executeQuery(Query));
    }

    // executes update query (insert/alter/update etc)
    // on connected db
    public int update (String Query) throws SQLException
    {
        Statement stmt;
        stmt = conn.createStatement();
        return (stmt.executeUpdate(Query));
    }

    // returns true upon finding data
    // false otherwise
    public boolean checkDatabaseForData (String Query) throws SQLException
    {
        ResultSet rs = query(Query);
        rs.beforeFirst();

        if (!rs.next())
            return false;

        return true;
    }

    public void closeConnection()
    {
        try
        {
            conn.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
