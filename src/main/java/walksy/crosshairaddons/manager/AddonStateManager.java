package walksy.crosshairaddons.manager;

import main.walksy.lib.core.WalksyLib;
import main.walksy.lib.api.event.WalksyLibEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import walksy.crosshairaddons.CrosshairAddons;
import walksy.crosshairaddons.config.CrosshairAddonsConfig;
import walksy.crosshairaddons.config.State;

import java.util.HashSet;
import java.util.Set;

public class AddonStateManager {

    private int hitmarkerTime;
    private int shieldBreakTime;
    private int hoveredEntityTime;
    private int cacheUpdateTick;
    private final Set<EntityType<?>> cachedIndicatorEntities;

    public AddonStateManager() {
        this.hitmarkerTime = 0;
        this.shieldBreakTime = 0;
        this.hoveredEntityTime = 0;
        this.cacheUpdateTick = 0;
        this.cachedIndicatorEntities = new HashSet<>();
        WalksyLibEvents.SHIELD_DISABLE.register(this::onShieldBreak);
    }

    public void updateCachedEntityTypes() {
        this.cachedIndicatorEntities.clear();
        for (String entityID : CrosshairAddonsConfig.entityIndicatorEntityList) {
            EntityType<?> type = CrosshairAddons.getEntityTypeFromString(entityID);
            if (type != null) {
                this.cachedIndicatorEntities.add(type);
            }
        }
    }

    private void onShieldBreak(Player player) {
        this.shieldBreakTime = CrosshairAddonsConfig.shieldBreakDuration;
        CrosshairAddonsConfig.shieldBreakAddon.resetAnimation();
    }

    public void onAttackEntity(LivingEntity entity) {
        if (entity instanceof Player) {
            if (CrosshairAddonsConfig.hitmarkerToggleOnAttack) {
                this.hitmarkerTime = CrosshairAddonsConfig.hitmarkerDuration;
                CrosshairAddonsConfig.hitmarkerAddon.resetAnimation();
            }
        }
    }

    public void onArrowHit() {
        this.hitmarkerTime = CrosshairAddonsConfig.hitmarkerDuration;
        CrosshairAddonsConfig.hitmarkerAddon.resetAnimation();
    }

    public void tick() {
        if (this.hitmarkerTime > 0) {
            this.hitmarkerTime--;
        }
        if (this.shieldBreakTime > 0) {
            this.shieldBreakTime--;
        }

        this.cacheUpdateTick++;
        if (this.cacheUpdateTick >= 100) {
            this.updateCachedEntityTypes();
            this.cacheUpdateTick = 0;
        }
    }

    public void checkTarget() {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null && client.level != null) {
            if (client.crosshairPickEntity != null) {
                this.hoveredEntityTime++;
                if (this.hoveredEntityTime == 1) {
                    CrosshairAddonsConfig.entityIndicatorAddon.resetAnimation();
                    if (client.crosshairPickEntity instanceof Player player) {
                        if (WalksyLib.getInstance().getShieldStateData().isUsingShield(player)) {
                            CrosshairAddonsConfig.shieldIndicatorAddon.resetAnimation();
                        }
                    }
                }
            } else {
                this.hoveredEntityTime = 0;
            }
        }
    }

    public boolean get(State state) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.level == null) return false;

        return switch (state) {
            case ELYTRA -> CrosshairAddonsConfig.elytraAddonEnabled
                    && client.player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA);

            case HITMARKER -> CrosshairAddonsConfig.hitmarkerAddonEnabled
                    && (!CrosshairAddonsConfig.hitmarkerStopOnAnimationEnd && this.hitmarkerTime > 0 || CrosshairAddonsConfig.hitmarkerStopOnAnimationEnd && !CrosshairAddonsConfig.hitmarkerAddon.hasPlayedOnce());

            case SHIELD_BREAK -> CrosshairAddonsConfig.shieldBreakAddonEnabled
                    && (!CrosshairAddonsConfig.shieldBreakStopOnAnimationEnd && this.shieldBreakTime > 0 || CrosshairAddonsConfig.shieldBreakStopOnAnimationEnd && !CrosshairAddonsConfig.shieldBreakAddon.hasPlayedOnce());

            case SHIELD_INDICATOR -> {
                if (!CrosshairAddonsConfig.shieldIndicatorEnabled) yield false;
                Entity target = client.crosshairPickEntity;
                if (target instanceof Player player) {
                    yield WalksyLib.getInstance().getShieldStateData().isUsingShield(player);
                }
                yield false;
            }

            case INDICATOR -> {
                if (!CrosshairAddonsConfig.entityIndicatorAddonEnabled) yield false;
                Entity target = client.crosshairPickEntity;
                if (CrosshairAddonsConfig.entityIndicatorShowAll && target != null) yield true;
                if (target == null) yield false;
                yield this.cachedIndicatorEntities.contains(target.getType());
            }
        };
    }
}