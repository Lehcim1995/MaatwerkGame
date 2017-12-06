package classes.database;

import java.sql.SQLException;
import java.util.List;

public interface IDatabase
{
    /**
     * reusable database function
     *
     * @param sql The sql query
     * @param returnFunction the function for query to restore the data
     * @return A list of objects of the specified object
     * @throws SQLException A SQLException
     */
    <T> List<T> getFromDatabase(String sql, Database.DatabaseReturn<T> returnFunction, Object... arguments) throws SQLException;

    /**
     * Makes a save SQL statement and executes it
     *
     * @param sql       The query, use an "?" at the place of a input. Like this:
     *                  INSERT INTO TABLE('name', 'lastname' , enz ) VALUES(?,?, enz);
     * @param arguments The arguments correspont to same questionmark.
     * @return The generated key
     * @throws SQLException A SQLException
     */
    Integer setDatabase(String sql, Object... arguments) throws SQLException;
}
