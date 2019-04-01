package dev.bravemc.bungeelog;

import dev.bravemc.bungeelog.listener.ChatListener;
import dev.bravemc.bungeelog.listener.ConnectListener;
import dev.bravemc.bungeelog.mysql.MySQL;
import dev.bravemc.bungeelog.mysql.SQLLogger;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeLog extends Plugin {

	private static String Prefix = "BungeeLog - ";
	private static BungeeLog instance;
	private static SQLLogger sqllogger = new SQLLogger();
	private static MySQL mysql = new MySQL();
	private static Config config = new Config();

	@Override
	public void onEnable() {
		getProxy().getLogger().info("BungeeLog wurde aktiviert!");
		instance = this;

		getProxy().getPluginManager().registerListener(this, new ConnectListener());
		getProxy().getPluginManager().registerListener(this, new ChatListener());

		getConfig().create();
		getMySQL().connect();
	}

	@Override
	public void onDisable() {
		getProxy().getLogger().info("BungeeLog wurde deaktiviert!");
	}

	public static String getPrefix() {
		return Prefix;
	}

	public static BungeeLog getInstance() {
		return instance;
	}

	public static SQLLogger getSQLLogger() {
		return sqllogger;
	}

	public static MySQL getMySQL() {
		return mysql;
	}

	public static Config getConfig() {
		return config;
	}
}
