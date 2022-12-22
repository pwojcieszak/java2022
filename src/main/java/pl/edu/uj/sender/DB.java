package pl.edu.uj.sender;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DB {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private static final Logger logger = LoggerFactory.getLogger(SenderApplication.class);
    private String url = "";

    public synchronized void connect(String url, String username, String password) throws Exception {
        if (conn == null) {
            logger.info("Connecting to %s".formatted(url));
            this.url = url;
            try {
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                throw new Exception("Error connecting to %s".formatted(url), ex);
            }
        } else {
            throw new Exception("Already connected to %s".formatted(url));
        }
    }

    public synchronized void disconnect() throws Exception {
        logger.info("Closing connection to %s".formatted(url));
        if (conn != null) {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                throw new Exception("Can't close connection %s".formatted(url), ex);
            }
        }
    }

    public ResultSet executeQuery(String statement) throws Exception {
        if (conn != null) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(statement);
                return rs;
            } catch (SQLException ex) {
                throw new Exception("Can't execute statement '%s'".formatted(statement), ex);
            }
        } else {
            throw new Exception("Not connected to %s".formatted(url));
        }
    }

    public long executeUpdate(String statement, boolean returnGeneratedKeys) throws Exception {
        if (conn != null) {
            try {
                try (Statement cStatement = conn.createStatement()) {
                    long updated = cStatement.executeUpdate(statement,
                            Statement.RETURN_GENERATED_KEYS);
                    if (returnGeneratedKeys) {
                        final ResultSet rs = cStatement.getGeneratedKeys();
                        if (rs.next()) {
                            return rs.getLong(1);
                        }
                    }
                    return updated;
                }
            } catch (SQLException ex) {
                throw new Exception("Can't execute statement '%s'".formatted(statement), ex);
            }
        } else {
            throw new Exception("Not connected to %s".formatted(url));
        }
    }
}

