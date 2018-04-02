package de.f4ls3.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private static File file = new File("plugins/TheWalkingDead/MySQL", "mysql.yml");
    private static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public MySQL() {
        try {
            cfg.options().copyDefaults(true);
            cfg.addDefault("username", "username");
            cfg.addDefault("password", "password");
            cfg.addDefault("host", "host");
            cfg.addDefault("port", "3306");
            cfg.addDefault("database", "database");
            cfg.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Connection con;
    private static boolean isConnected;
    private static String user = (String) cfg.get("username");
    private static String pw = (String) cfg.get("password");
    private static String host = (String) cfg.get("host");
    private static String port = (String) cfg.get("port");
    private static String database = (String) cfg.get("database");


    public void connect() {
        try {
            if (con != null && !con.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host+":"+port+"/"+database, user, pw);
            Bukkit.getConsoleSender().sendMessage(Var.getPrefix() + "§2MySQL successfully Connected!");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if(!con.isClosed()) {
                con.close();
                Bukkit.getConsoleSender().sendMessage(Var.getPrefix() + "§2MySQL Connection is successfully Closed!");
            } else {
                Bukkit.getConsoleSender().sendMessage(Var.getPrefix() + "§cCan't close Connection: error: CONNECTION_ALREADY_CLOSED");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean connected() {
        try {
            if(con.isClosed()) {
                isConnected = false;
            } else if(!con.isClosed()) {
                isConnected = true;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return isConnected;
    }
}
