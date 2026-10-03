package io.github.vlouboos.staffplugin.service;

import io.github.floatingpointmc.sanctionmanager.minecraft.operation.BanOperation;
import io.github.floatingpointmc.sanctionmanager.minecraft.operation.WarnOperation;
import io.github.vlouboos.staffplugin.StaffPlugin;

import java.time.Duration;
import java.util.UUID;

public class BanService {
    public static void banCheating(UUID target, UUID operator) {
        int phase = StaffPlugin.sanctionManager.getPunishmentManager().queryActiveWarns(target).size();
        Duration duration = switch (phase) {
            case 0 -> Duration.ofDays(30);
            case 1 -> Duration.ofDays(90);
            case 2 -> Duration.ofDays(360);
            default -> null;
        };
        StaffPlugin.sanctionManager.getSanctionService().executeBan(new BanOperation(target, operator, duration, "利用破坏平衡性的方式作弊"));
        if (duration != null) {
            StaffPlugin.sanctionManager.getSanctionService().executeWarn(new WarnOperation(target, operator, duration.plus(Duration.ofDays(1)), "cheating"));
            StaffPlugin.sanctionManager.getSanctionService().executeWarn(new WarnOperation(target, operator, duration.plus(Duration.ofDays(7)), "cheating"));
            StaffPlugin.sanctionManager.getSanctionService().executeWarn(new WarnOperation(target, operator, duration.plus(Duration.ofDays(30)), "cheating"));
        }
    }
}
