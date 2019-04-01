package dev.bravemc.supportsystem;

import java.util.Map;
import java.util.Map.Entry;

import dev.bravemc.supportsystem.commands.SupportCMD;
import dev.bravemc.supportsystem.feedback.DisconnectListener;
import dev.bravemc.supportsystem.feedback.FeedbackListener;
import dev.bravemc.supportsystem.listener.ChatListener;
import dev.bravemc.supportsystem.listener.ConnectListener;
import dev.bravemc.supportsystem.mysql.MySQL;
import dev.bravemc.supportsystem.mysql.SupportStats;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class SupportSystem extends Plugin {

	private String prefix = "§9Support §8» ";
	private String NoPermission = getPrefix() + "§cDu hast dazu keine Rechte...";
	private SupportManager supportManager;
	private Config cfg;
	private MySQL MySQL;
	private SupportStats stats;
	private static SupportSystem instance;

	@Override
	public void onEnable() {
		instance = this;

		registerListener();
		registerCommands();
		this.cfg = new Config(this);
		getCfg().create();

		this.supportManager = new SupportManager();

		this.MySQL = new MySQL(this);
		this.stats = new SupportStats(this);

		getMySQL().connect();
		getMySQL().createTable();
	}

	private void registerListener() {
		PluginManager pm = getProxy().getPluginManager();

		pm.registerListener(this, new ChatListener());
		pm.registerListener(this, new ConnectListener());
		pm.registerListener(this, new FeedbackListener());
		pm.registerListener(this, new DisconnectListener());
	}

	private void registerCommands() {
		PluginManager pm = getProxy().getPluginManager();
		pm.registerCommand(this, new SupportCMD());

	}

	public String getPrefix() {
		return prefix;
	}

	public String getNoPermission() {
		return NoPermission;
	}

	public SupportManager getSupportManager() {
		return this.supportManager;
	}

	public <K, V> K getKey(Map<K, V> map, V value) {
		for (Entry<K, V> entry : map.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static SupportSystem getInstance() {
		return instance;
	}

	public MySQL getMySQL() {
		return this.MySQL;
	}

	public Config getCfg() {
		return this.cfg;
	}

	public SupportStats getStats() {
		return this.stats;
	}
}
