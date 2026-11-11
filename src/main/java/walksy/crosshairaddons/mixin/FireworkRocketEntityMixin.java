package walksy.crosshairaddons.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.crosshairaddons.CrosshairAddons;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin {

    @Shadow
    private int life;

    @Shadow
    @Nullable
    private LivingEntity attachedToEntity;

    @Inject(method = "tick", at = @At("TAIL"))
    private void crosshairaddons$trackBoost(final CallbackInfo ci) {
        // The client resolves attachedToEntity from synced data during tick, so by TAIL it points at whoever is boosting.
        // Comparing against the local player also filters out the integrated server's copy of the rocket.
        if (this.attachedToEntity == null || this.attachedToEntity != Minecraft.getInstance().player) {
            return;
        }
        CrosshairAddons.getStateManager().onBoostingRocketTick((FireworkRocketEntity) (Object) this, this.life);
    }
}
