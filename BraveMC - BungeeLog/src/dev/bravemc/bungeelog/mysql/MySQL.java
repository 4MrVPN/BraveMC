package dev.bravemc.bungeelog.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.bravemc.bungeelog.BungeeLog;
import dev.bravemc.bungeelog.Config;
import net.md_5.bungee.api.ProxyServer;

public class MySQL {

	private static String username = Config.cfg.getString("MySQL.username");
	private static String password = Config.cfg.getString("MySQL.password");
	private static String host = Config.cfg.getString("MySQL.host");
	private static String database = Config.cfg.getString("MySQL.database");
	private static int port = Config.cfg.getInt("MySQL.port");
	private static Connection con;
	private static ExecutorService executor = Executors.newCachedThreadPool();;

	public void connect() {

		if (!isConnected()) {
			try {

				con = DriverManager.getConnection(
						"jdbc:mysql://" + host + ":" + port + "/" + database + "?autoRecconect=true", username,
						password);

				ProxyServer.getInstance().getLogger().info("MySQL Verbindung wurde erfolgreich aufgebaut!");

			} catch (SQLException e) {
				e.printStackTrace();
				ProxyServer.getInstance().getLogger().info("MySQL Verbindung konnte nicht aufgebaut werden!");
			}

		}

	}

	public void createTable(String pName) {
		PreparedStatement ps;

		try {
			ps = BungeeLog.getMySQL().getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS " + pName + " (Text varchar(100), Uhrzeit varchar(20))");

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			ProxyServer.getInstance().getLogger()
					.info("Beim erstellen der MySQL-Tabelle > " + pName + " < ist ein Fehler aufgetreten. ");
		}

	}

	public Connection getConnection() {
		return con;
	}

	public boolean isConnected() {
		return con != null;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void update(PreparedStatement ps) {
		executor.execute(() -> {
			try {
				ps.executeUpdate();
				ps.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});
	}
}
