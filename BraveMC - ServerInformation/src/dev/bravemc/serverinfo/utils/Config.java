package dev.bravemc.serverinfo.utils;

import java.io.File;
import java.io.IOException;

import dev.bravemc.serverinfo.ServerInfo;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {
	public Configuration cfg;
	private static File file;

	public void create() {
		if (!ServerInfo.getInstance().getDataFolder().exists()) {
			ServerInfo.getInstance().getDataFolder().mkdir();
		}

		file = new File(ServerInfo.getInstance().getDataFolder(), "config.yml");
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
