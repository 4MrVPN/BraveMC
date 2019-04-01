package dev.bravemc.specsystem.utils;

import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	ItemStack itemStack;
	ItemMeta itemMeta;

	public ItemBuilder(Material material) {
		itemStack = new ItemStack(material);
		itemMeta = itemStack.getItemMeta();
	}

	@SuppressWarnings("deprecation")
	public ItemBuilder(Integer amount, DyeColor color) {
		itemStack = new ItemStack(Material.INK_SACK, amount, color.getData());
		itemMeta = itemStack.getItemMeta();
	}

	public ItemBuilder(Material material, Integer amount, byte b) {
		itemStack = new ItemStack(material, amount, b);
		itemMeta = itemStack.getItemMeta();
	}

	public ItemBuilder setDisplayname(String displayname) {
		itemMeta.setDisplayName(displayname);
		return this;
	}

	public ItemBuilder setLore(List<String> loretextlist) {
		itemMeta.setLore(loretextlist);
		return this;

	}

	public ItemBuilder addEnchantment(Enchantment enchantment, Integer level, boolean b) {
		itemMeta.addEnchant(enchantment, level, b);

		return this;
	}

	public ItemBuilder setUnbreakable(boolean b) {
		itemMeta.spigot().setUnbreakable(b);
		return this;
	}

	public ItemBuilder addItemFlags(ItemFlag flag) {
		itemMeta.addItemFlags(flag);
		return this;
	}

	public ItemBuilder setAmount(Integer amount) {
		itemStack.setAmount(amount);
		return this;
	}

	public ItemStack build() {
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

}
