package discordIsland.MySQL;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection
{
    private java.sql.Connection conn = null;

    // creates connection to db
    public Connection()
    {
        try
        {
            conn = DriverManager.getConnection(
                    "YOUR-DB-WEBSITE",
                    "YOUR-DB-USERNAME",
                    "YOUR-DB-PASSWORD"
            );
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }
    }

    // queries database for non-modifying queries
    public ResultSet query (String Query)
    {
        Statement stmt;

        try
        {
            stmt = conn.createStatement();
            return (stmt.executeQuery(Query));
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }

        return null;
    }

    // executes update query (insert/alter etc)
    // on connected db
    public int update (String Query)
    {
        Statement stmt;

        try
        {
            stmt = conn.createStatement();
            return (stmt.executeUpdate(Query));
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }

        return -1;
    }

    // returns true upon finding data
    // false otherwise
    public boolean checkDatabaseForData (String Query)
    {
        try
        {
            ResultSet rs = query(Query);
            rs.beforeFirst();

            if (!rs.next())
                return false;
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }
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
            System.out.println(ex);
        }
    }
}
