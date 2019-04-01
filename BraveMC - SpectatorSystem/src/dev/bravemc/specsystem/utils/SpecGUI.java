package dev.bravemc.specsystem.utils;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.server.info.ServerInfo;

public class SpecGUI {

	@SuppressWarnings("deprecation")
	public void open(String server, Player p) {
		Inventory inv = Bukkit.createInventory(null, 5 * 9, "§7Zuschauen - §c" + server);

		for (ServerInfo cs : CloudAPI.getInstance().getServers(server)) {
			ItemStack is = new ItemBuilder(Material.WOOL, 1, DyeColor.GREEN.getData())
					.setDisplayname("§a" + cs.getServiceId().getServerId()).setLore(Arrays.asList("",
							"§7" + cs.getOnlineCount() + "§8/§7" + cs.getMaxPlayers(), "", "§7(§a§lKlick§7) §aBetreten"))
					.build();

			inv.addItem(is);
		}

		p.openInventory(inv);
	}
}
