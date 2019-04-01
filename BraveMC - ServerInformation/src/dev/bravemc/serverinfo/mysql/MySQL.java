package dev.bravemc.serverinfo.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.bravemc.serverinfo.ServerInfo;
import net.md_5.bungee.api.ProxyServer;

public class MySQL {

	private String username = ServerInfo.getInstance().getConfig().getString("MySQL.username");
	private String password = ServerInfo.getInstance().getConfig().getString("MySQL.password");
	private String host = ServerInfo.getInstance().getConfig().getString("MySQL.host");
	private String database = ServerInfo.getInstance().getConfig().getString("MySQL.database");
	private int port = ServerInfo.getInstance().getConfig().getInt("MySQL.port");
	private Connection con;
	private ExecutorService executor = Executors.newCachedThreadPool();;

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

	public void createTable() {
		PreparedStatement ps;

		try {
			ps = this.getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS serverStats (Gruppenname varchar(100), Taeglich int(5), Taeglich_End varchar(64), Woechentlich int(5), Woechentlich_End varchar(64), Monatlich int(5), Monatlich_End varchar(64), Jaehrlich int(5), Jaehrlich_End varchar(64), Gesamt int(10))");

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			ProxyServer.getInstance().getLogger()
					.info("Beim erstellen der MySQL-Tabelle > serverStats < ist ein Fehler aufgetreten. ");
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

	public ResultSet query(String qry) {
		ResultSet rs = null;
		try {
			Statement st = this.con.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			connect();
			System.err.println(e);
		}
		return rs;
	}

}
