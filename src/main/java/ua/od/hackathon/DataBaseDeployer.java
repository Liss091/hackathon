package ua.od.hackathon;

import com.ibatis.common.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import ua.od.hackathon.config.DataBaseConfig;

import static ua.od.hackathon.config.DataBaseConfig.*;

public class DataBaseDeployer {

//    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//    static final String DB_NAME = "hackathon";
//    static final String DB_URL = "jdbc:mysql://localhost/" + DB_NAME;
//    public static final String ABSOLUTE_CLASSPATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//    public static final String DB_SCRIPTS_FOLDER = ABSOLUTE_CLASSPATH + "db";
//    static final String USER = "root";
//    static final String PASS = "1234";


    public static void main(String[] args) throws Exception{
        createDb();
        executeSqlScripts();
    }

    public static void createDb() throws SQLException {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = DriverManager.getConnection(DB_DATABASE_URL);
            statement = conn.createStatement();
            statement.executeUpdate("DROP DATABASE IF EXISTS " + DB_NAME);
            statement.executeUpdate("CREATE DATABASE " + DB_NAME);
            statement.executeUpdate("USE " + DB_NAME);
        } finally {
            statement.close();
            conn.close();
        }
    }

    static void executeSqlScripts() throws IOException, SQLException {

        File folder = new File(DB_SCRIPTS_FOLDER);
        File[] files = folder.listFiles();
        Arrays.sort(files);
        Reader reader = null;
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(DB_DATABASE_URL);
            for (final File file : files) {
                ScriptRunner scriptExecutor = new ScriptRunner(conn, false, false);
                reader = new BufferedReader(new FileReader(file));
                scriptExecutor.runScript(reader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }



}
