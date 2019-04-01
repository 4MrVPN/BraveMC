package dev.bravemc.serverinfo;

import dev.bravemc.serverinfo.commands.SInfoCMD;
import dev.bravemc.serverinfo.listener.ConnectListener;
import dev.bravemc.serverinfo.mysql.MySQL;
import dev.bravemc.serverinfo.mysql.ServerStats;
import dev.bravemc.serverinfo.utils.Config;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;

public class ServerInfo extends Plugin {

	private static String Prefix = "§cServerInfo §8» §7";
	private static String NoPermission = getPrefix() + "§cDu hast dazu keine Rechte...";
	private static ServerInfo instance;
	private Config cfg;
	private ServerStats serverStats;
	private MySQL MySQL;

	@Override
	public void onEnable() {
		instance = this;

		registerListener();
		registerCommands();
		this.serverStats = new ServerStats();
		this.cfg = new Config();
		getCfgClass().create();

		this.MySQL = new MySQL();

		getMySQL().connect();
		getMySQL().createTable();
	}



	public static ServerInfo getInstance() {
		return instance;
	}

	public void registerCommands() {
		PluginManager pm = ProxyServer.getInstance().getPluginManager();
		pm.registerCommand(this, new SInfoCMD());
	}

	public void registerListener() {
		PluginManager pm = ProxyServer.getInstance().getPluginManager();
		pm.registerListener(this, new ConnectListener());
	}

	public Configuration getConfig() {
		return cfg.cfg;
	}

	public Config getCfgClass() {
		return cfg;
	}

	public MySQL getMySQL() {
		return MySQL;
	}

	public ServerStats getServerStats() {
		return serverStats;
	}

	public static String getNoPermission() {
		return NoPermission;
	}
	
	public static String getPrefix() {
		return Prefix;
	}
}
