package io.github.vlouboos.staffplugin.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import io.github.vlouboos.staffplugin.StaffPlugin;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfirmInventory implements InventoryProvider {
    private final String confirmMessage;
    private final Runnable confirm;

    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack confirm = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName(ChatColor.RED + "确认");
        confirmMeta.setLore(Collections.singletonList(confirmMessage));
        confirm.setItemMeta(confirmMeta);
        contents.set(SlotPos.of(0, 2), ClickableItem.of(confirm, _ -> Bukkit.getScheduler().runTask(StaffPlugin.INSTANCE, this.confirm)));
        ItemStack cancel = new ItemStack(Material.BARRIER);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName(ChatColor.RED + "取消");
        cancelMeta.setLore(Collections.singletonList(""));
        cancel.setItemMeta(cancelMeta);
        contents.set(SlotPos.of(0, 6), ClickableItem.of(cancel, _ -> player.closeInventory()));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    public static void buildInventory(Player player, String confirmMessage, Runnable runnable) {
        SmartInventory.builder()
                .id("confirm")
                .title("确认？")
                .size(1, 9)
                .provider(new ConfirmInventory(confirmMessage, runnable))
                .build()
                .open(player);
    }
}
