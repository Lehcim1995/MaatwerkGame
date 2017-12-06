/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.database;

import com.sun.istack.internal.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

/**
 * @author michel
 */
public class Database implements IDatabase
{

    private static final String DATABASECLASS = "com.mysql.jdbc.Driver";
    private static final String DATABASE_SERVER = "";
    private static final String DATABASE_NAME = "";
    private static final String DATABASE_USERNAME = "";
    private static final String DATABASE_PASSWORD = "";
    private static final String URL = "jdbc:mysql://" + DATABASE_SERVER + ":3306/" + DATABASE_NAME + "?&relaxAutoCommit=true";
    private static Database instance;

    private Database()
    {
        try
        {
            if (databaseConnection(URL))
            {
                Logger.getAnonymousLogger().log(Level.INFO, "School db connected");
            }
            else
            {
                Logger.getAnonymousLogger().log(Level.INFO, "Cannot connect to any database");
            }
        }
        catch (SQLException e)
        {
            Logger.getAnonymousLogger().log(Level.WARNING, "SQLError in Constructor: " + e.getMessage(), e);
        }
    }

    /**
     * @return The singleton of the database
     */
    public static Database getInstance()
    {
        return (instance == null) ? (instance = new Database()) : instance; // kan helaas niet kleiner :'(
    }

    /**
     * Converts a date to timestamp
     *
     * @param date the date for converting
     * @return timestamp
     */
    private Timestamp dateToTimestamp(Date date)
    {
        return new Timestamp(date.getTime());
    }

    /**
     * Makes a save SQL statement and executes it
     *
     * @param sql       The query, use an "?" at the place of a input. Like this:
     *                  INSERT INTO TABLE('name', 'lastname' , enz ) VALUES(?,?, enz);
     * @param arguments The arguments correspont to same questionmark.
     * @return The generated key
     * @throws SQLException A SQLException
     */
    public Integer setDatabase(String sql, Object... arguments) throws SQLException // TODO remove sql exceptions
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try
        {
            Class.forName(DATABASECLASS);
            conn = DriverManager.getConnection(URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            escapeSQL(preparedStatement, arguments);

            preparedStatement.executeUpdate();
            rs = preparedStatement.getGeneratedKeys();
            if (rs != null && rs.next())
            {
                //maybe errors
                return rs.getInt(1) == 0 ? -1 : rs.getInt(1);
            }
            return -1;
        }
        catch (ClassNotFoundException e)
        {
            Logger.getAnonymousLogger().log(Level.WARNING, "Class Error " + e.getMessage(), e);
            return -1;
        }
        finally
        {
            if (conn != null)
            {
                //close and commit
                Logger.getAnonymousLogger().log(Level.INFO, "Commit" + sql);
                conn.commit();
                conn.close();
            }

            if (preparedStatement != null)
            {
                preparedStatement.close();
            }

            if (rs != null)
            {
                rs.close();
            }
        }
    }

    /**
     * @param preparedStatement The prepared statement that you want to escape
     * @param arguments The arguments that you want to add to the statement
     * @throws SQLException A SQLException
     */
    private void escapeSQL(PreparedStatement preparedStatement, Object... arguments) throws SQLException
    {
        int i = 0;
        for (Object obj : arguments)
        {
            i++;

            if (obj != null && obj.getClass() == Date.class) // when the argument is a date then convert to a timestamp
            {
                preparedStatement.setTimestamp(i, dateToTimestamp((Date) obj));
            }
            else if (obj != null && obj.getClass() == String.class) // when the argument is a string then escap all html4 stuff
            {
                preparedStatement.setObject(i, escapeHtml4((String) obj));
            }
            else
            {
                preparedStatement.setObject(i, obj);
            }
        }
    }

    /**
     * reusable database function
     *
     * @param sql The sql query
     * @param returnFunction the function for query to restore the data
     * @return A list of objects of the specified object
     * @throws SQLException A SQLException
     */
    public <T> List<T> getFromDatabase(String sql, DatabaseReturn<T> returnFunction, Object... arguments) throws SQLException
    {
        ArrayList<T> objList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement psta = null;
        ResultSet rs = null;

        try
        {
            Class.forName(DATABASECLASS);
            conn = DriverManager.getConnection(URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            psta = conn.prepareStatement(sql);

            escapeSQL(psta, arguments);

            rs = psta.executeQuery();

            while (rs.next())
            {
                objList.add(returnFunction.returnType(rs));
            }
        }
        catch (SQLException e)
        {
            Logger.getAnonymousLogger().log(Level.WARNING, "SQL Error: " + e.getMessage(), e);
        }
        catch (ClassNotFoundException e)
        {
            Logger.getAnonymousLogger().log(Level.WARNING, "Class Error: " + e.getMessage(), e);
        }
        finally
        {
            if (conn != null)
            {
                conn.close();
            }
            if (rs != null)
            {
                rs.close();
            }
        }

        return objList;
    }

    private boolean databaseConnection(String connection) throws SQLException
    {
        @Nullable
        Connection conn = null;
        Statement sta = null;
        try
        {
            Class.forName(DATABASECLASS);

            conn = DriverManager.getConnection(connection, DATABASE_USERNAME, DATABASE_PASSWORD);
            sta = conn.createStatement();

            return !conn.isClosed();
        }
        catch (SQLException e)
        {
            Logger.getAnonymousLogger().log(Level.WARNING, "SQL Error: " + e.getMessage(), e);
            return false;
        }
        catch (ClassNotFoundException e)
        {
            Logger.getAnonymousLogger().log(Level.WARNING, "Class Error: " + e.getMessage(), e);
            return false;
        }
        finally
        {
            if (conn != null)
            {
                conn.close();
            }

            if (sta != null)
            {
                sta.close();
            }
        }
    }

    /**
     * inteface to use as a delegete
     *
     * @param <T> The generic of the interface
     */
    interface DatabaseReturn<T>
    {

        /**
         * @param set the resultset from the query
         * @return returns whatever you want
         * @throws SQLException throws a SQLException
         */
        T returnType(ResultSet set) throws SQLException;
    }
}
