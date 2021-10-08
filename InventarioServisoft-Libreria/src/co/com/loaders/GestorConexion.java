package co.com.loaders;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;


public class GestorConexion {

    private DataSource dataSource;
    public static final Logger logger = Logger.getLogger(GestorConexion.class);

    public GestorConexion() {
        if (dataSource == null) {
            try {
                Context initContext = new InitialContext();
                dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/empleados");
            } catch (Exception ex) {
                logger.error("Excepcion en el metodo GestorConexion " + ex.getCause() + " " + ex.getMessage());
            }
        }
    }

    public Connection buscarConexion() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (Exception ex) {
            logger.error("Excepción en el método buscarConexion " + ex.getCause() + " " + ex.getMessage());
        }
        return conn;
    }

    public static void closeResource(Connection conn, Statement stat, ResultSet rs) {
        close(conn);
        close(rs);
        close(stat);
    }

    public static void closeResource(Statement stat, ResultSet rs) {
        close(rs);
        close(stat);
    }

    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            logger.error("Excepción en el método close " + ex.getCause() + " " + ex.getMessage());
        }
    }

    public static void close(Statement stat) {
        try {
            if (stat != null) {
                stat.close();
            }
        } catch (SQLException ex) {
            logger.error("Excepción en el método close " + ex.getCause() + " " + ex.getMessage());
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            logger.error("Excepción en el metodo close " + ex.getCause() + " " + ex.getMessage());
        }
    }
}
