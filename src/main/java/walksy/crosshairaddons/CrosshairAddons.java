package walksy.crosshairaddons;

import main.walksy.lib.core.WalksyLib;
import main.walksy.lib.api.config.value.PixelGridAnimation;
import main.walksy.lib.api.event.WalksyLibEvents;
import main.walksy.lib.api.hud.HudElement;
import main.walksy.lib.api.hud.HudElements;
import main.walksy.lib.core.hud.HudSpace;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import walksy.crosshairaddons.config.CrosshairAddonsConfig;
import walksy.crosshairaddons.manager.AddonStateManager;
import walksy.crosshairaddons.config.State;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;


public class CrosshairAddons implements ModInitializer {

    public static final String MOD_ID = "crosshairaddons";
    private static AddonStateManager stateManager;

    @Override
    public void onInitialize() {
        stateManager = new AddonStateManager();
        WalksyLibEvents.START_CLIENT_TICK.register(client -> {
            if (CrosshairAddonsConfig.modEnabled) {
                stateManager.tick();
            }
        });
        WalksyLibEvents.ATTACK_ENTITY.register((player, target) -> {
            if (CrosshairAddonsConfig.modEnabled && target instanceof final LivingEntity living) {
                stateManager.onAttackEntity(living);
            }
        });
        WalksyLibEvents.START_RECEIVE_PACKET_MAIN.register((packet, listener, cancellable) -> {
            if (CrosshairAddonsConfig.modEnabled && packet instanceof final ClientboundGameEventPacket event && event.getEvent() == ClientboundGameEventPacket.PLAY_ARROW_HIT_SOUND) {
                stateManager.onArrowHit();
            }
        });
        WalksyLibEvents.END_CLIENT_TICK.register((client) -> {
            if (!addonsVisible() || CrosshairAddonsConfig.isEditing()) {
                return;
            }
            if (stateManager.get(State.INDICATOR)) {
                stateManager.checkTarget();
            }
        });
    }

    public static HudElement addonHud(final Supplier<PixelGridAnimation> animation, final State state, final String iconKey) {
        return new AddonHud(HudElements.when(() -> addonsVisible() && stateManager.get(state), HudElements.pixelGrid(animation, () -> CrosshairAddonsConfig.environmentBlend)), iconKey);
    }

    public static boolean addonsVisible() {
        if (!CrosshairAddonsConfig.modEnabled) {
            return false;
        }
        final boolean thirdPerson = !Minecraft.getInstance().options.getCameraType().isFirstPerson();
        return !thirdPerson || CrosshairAddonsConfig.showInThirdPerson;
    }

    public static List<HudSpace> addonSpaces() {
        return WalksyLib.getInstance().getHudSpaces().forMod(MOD_ID);
    }


    public static @Nullable EntityType<?> getEntityTypeFromString(@Nullable String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        String normalized = name.trim()
                .toLowerCase(Locale.ROOT)
                .replace(" ", "_");
        try {
            Identifier id = normalized.contains(":")
                    ? Identifier.parse(normalized)
                    : Identifier.fromNamespaceAndPath("minecraft", normalized);

            Optional<EntityType<?>> direct = BuiltInRegistries.ENTITY_TYPE.getOptional(id);
            if (direct.isPresent()) {
                return direct.get();
            }

            for (Map.Entry<ResourceKey<EntityType<?>>, EntityType<?>> entry : BuiltInRegistries.ENTITY_TYPE.entrySet()) {
                String path = entry.getKey().identifier().getPath();
                if (path.contains(normalized)) {
                    return entry.getValue();
                }
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }
    public static AddonStateManager getStateManager() {
        return stateManager;
    }
}
