package io.github.vlouboos.staffplugin.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import io.github.floatingpointmc.sanctionmanager.minecraft.operation.BanOperation;
import io.github.vlouboos.staffplugin.StaffPlugin;
import io.github.vlouboos.staffplugin.service.BanService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BanInventory implements InventoryProvider {
    private final UUID target;

    private final String playerName;

    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack security = new ItemStack(Material.GLOWSTONE_DUST);
        ItemMeta securityMeta = security.getItemMeta();
        securityMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "不安全的账户");
        securityMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "以" + ChatColor.GREEN + ChatColor.BOLD + "不安全的账户" + ChatColor.YELLOW + "为理由封禁" + ChatColor.RED + playerName));
        security.setItemMeta(securityMeta);
        contents.set(SlotPos.of(0, 0),
                ClickableItem.of(security, _ -> ConfirmInventory.buildInventory(player, "%s确定以%s%s作为理由封禁%s".formatted(ChatColor.YELLOW, ChatColor.GREEN.toString() + ChatColor.BOLD + "不安全的账户", ChatColor.YELLOW, ChatColor.RED + playerName),
                        () -> StaffPlugin.sanctionManager.getSanctionService().executeBan(new BanOperation(target, player.getUniqueId(), null, "不安全的账户")))));

        ItemStack cheating = new ItemStack(Material.REDSTONE);
        ItemMeta cheatingMeta = cheating.getItemMeta();
        cheatingMeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "作弊");
        cheatingMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "以" + ChatColor.RED + ChatColor.BOLD + "作弊" + ChatColor.YELLOW + "为理由封禁" + ChatColor.RED + playerName));
        cheating.setItemMeta(cheatingMeta);
        contents.set(SlotPos.of(0, 4), ClickableItem.of(cheating, _ -> ConfirmInventory.buildInventory(player, "%s确定以%s%s作为理由封禁%s".formatted(ChatColor.YELLOW, ChatColor.RED.toString() + ChatColor.BOLD + "作弊", ChatColor.YELLOW, ChatColor.RED + playerName), () -> BanService.banCheating(target, player.getUniqueId()))));

        ItemStack illegal = new ItemStack(Material.CLAY_BALL);
        ItemMeta illegalMeta = illegal.getItemMeta();
        illegalMeta.setDisplayName(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "违反法律");
        illegalMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "以" + ChatColor.DARK_RED + ChatColor.BOLD + "违反法律" + ChatColor.YELLOW + "为理由封禁" + ChatColor.RED + playerName));
        illegal.setItemMeta(illegalMeta);
        contents.set(SlotPos.of(0, 8),
                ClickableItem.of(illegal, _ -> ConfirmInventory.buildInventory(player, "%s确定以%s%s作为理由封禁%s".formatted(ChatColor.YELLOW, ChatColor.DARK_RED.toString() + ChatColor.BOLD + "违反法律", ChatColor.YELLOW, ChatColor.RED + playerName),
                        () -> StaffPlugin.sanctionManager.getSanctionService().executeBan(new BanOperation(target, player.getUniqueId(), null, "违反法律")))));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    public static void buildInventory(Player player, UUID uuid, String playerName) {
        SmartInventory.builder()
                .id("ban-player")
                .title("Ban " + playerName)
                .size(1, 9)
                .provider(new BanInventory(uuid, playerName))
                .build()
                .open(player);
    }
}
