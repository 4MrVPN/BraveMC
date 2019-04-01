package dev.bravemc.specsystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev.bravemc.specsystem.listener.ClickListener;
import dev.bravemc.specsystem.listener.InteractListener;
import dev.bravemc.specsystem.listener.SignPlaceListener;
import dev.bravemc.specsystem.utils.SpecGUI;

public class SpecSystem extends JavaPlugin {

	private static String Prefix = "§cSpecSystem §8» §7";
	private static String NoPermission = getPrefix() + "§cDu hast dazu keine Rechte...";
	private static SpecSystem instance;
	private static SpecGUI GUI;

	@Override
	public void onEnable() {
		instance = this;
		GUI = new SpecGUI();

		registerListener();

	}

	public void registerListener() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new SignPlaceListener(), this);
		pm.registerEvents(new InteractListener(), this);
		pm.registerEvents(new ClickListener(), this);
	}

	public static String getPrefix() {
		return Prefix;
	}

	public static String getNoPermission() {
		return NoPermission;
	}

	public static SpecSystem getInstance() {
		return instance;
	}

	public static SpecGUI getGUI() {
		return GUI;
	}
}
