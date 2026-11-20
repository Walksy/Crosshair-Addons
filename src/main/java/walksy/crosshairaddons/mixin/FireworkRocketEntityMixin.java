package walksy.crosshairaddons.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.crosshairaddons.CrosshairAddons;
import walksy.crosshairaddons.config.CrosshairAddonsConfig;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin {

    @Shadow
    private int life;

    @Shadow
    @Nullable
    private LivingEntity attachedToEntity;

    @Inject(method = "tick", at = @At("TAIL"))
    private void crosshairaddons$trackBoost(final CallbackInfo ci) {
        if (this.attachedToEntity == null || this.attachedToEntity != Minecraft.getInstance().player
            || CrosshairAddonsConfig.rocketHideWithoutElytra && !this.attachedToEntity.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA)) {
            return;
        }
        CrosshairAddons.getStateManager().onBoostingRocketTick((FireworkRocketEntity) (Object) this, this.life);
    }
}
