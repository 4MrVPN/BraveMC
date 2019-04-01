package dev.bravemc.supportsystem.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.bravemc.supportsystem.SupportSystem;
import net.md_5.bungee.api.ProxyServer;

public class MySQL {

	private SupportSystem plugin;

	public MySQL(SupportSystem plugin) {
		this.plugin = plugin;
	}

	private String username = SupportSystem.getInstance().getCfg().cfg.getString("MySQL.username");
	private String password = SupportSystem.getInstance().getCfg().cfg.getString("MySQL.password");
	private String host = SupportSystem.getInstance().getCfg().cfg.getString("MySQL.host");
	private String database = SupportSystem.getInstance().getCfg().cfg.getString("MySQL.database");
	private int port = SupportSystem.getInstance().getCfg().cfg.getInt("MySQL.port");
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
			ps = plugin.getMySQL().getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS supportStats (UUID varchar(100), Supports int(5), Durchschnitt double, "
							+ "Eins int(10), Zwei int(10), Drei int(10), Vier int(10), Fuenf int(10), Sechs int(10), Gesamt int(10))");

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			ProxyServer.getInstance().getLogger()
					.info("Beim erstellen der MySQL-Tabelle > supportStats < ist ein Fehler aufgetreten. ");
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
