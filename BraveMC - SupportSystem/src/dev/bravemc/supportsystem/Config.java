package dev.bravemc.supportsystem;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {

	public Configuration cfg;
	private File file;

	private SupportSystem plugin;

	public Config(SupportSystem plugin) {
		this.plugin = plugin;
	}

	public void create() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}

		file = new File(plugin.getDataFolder(), "config.yml");
		try {
			if (!file.exists()) {

				file.createNewFile();

				cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
				cfg.set("MySQL.username", "username");
				cfg.set("MySQL.password", "password");
				cfg.set("MySQL.database", "database");
				cfg.set("MySQL.host", "host");
				cfg.set("MySQL.port", "port");

				save();
			} else {
				cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void save() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
