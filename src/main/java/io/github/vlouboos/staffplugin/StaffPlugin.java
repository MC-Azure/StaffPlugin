package io.github.vlouboos.staffplugin;

import io.github.floatingpointmc.sanctionmanager.minecraft.MinecraftSanctionManager;
import io.github.vlouboos.staffplugin.commands.BanCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class StaffPlugin extends JavaPlugin {
    public static MinecraftSanctionManager sanctionManager;
    public static StaffPlugin INSTANCE;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        try {
            @SuppressWarnings("unchecked")
            Class<? extends JavaPlugin> smClass = (Class<? extends JavaPlugin>) Class.forName("io.github.floatingpointmc.sanctionmanager.spigot.SpigotMain");
            Field smField = smClass.getDeclaredField("manager");
            smField.setAccessible(true);
            sanctionManager = (MinecraftSanctionManager) smField.get(getPlugin(smClass));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getServer().getCommandMap().register("ban", new BanCommand());
    }
}
