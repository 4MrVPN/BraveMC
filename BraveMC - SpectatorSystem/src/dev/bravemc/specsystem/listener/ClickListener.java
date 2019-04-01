package dev.bravemc.specsystem.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.api.player.PlayerExecutorBridge;
import de.dytanic.cloudnet.lib.player.CloudPlayer;

public class ClickListener implements Listener {

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		final Player p = (Player) e.getWhoClicked();

		final CloudPlayer cP = CloudAPI.getInstance().getOnlinePlayer(p.getUniqueId());

		if (e.getClickedInventory() == null)
			return;

		if (e.getClickedInventory().getName().contains("§7Zuschauen -")) {
			e.setCancelled(true);

			String clickedServer = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
			PlayerExecutorBridge.INSTANCE.sendPlayer(cP, clickedServer);

		}
	}
}
