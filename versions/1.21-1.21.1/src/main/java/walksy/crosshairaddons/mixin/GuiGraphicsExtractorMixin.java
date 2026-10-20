package walksy.crosshairaddons.mixin;

import main.walksy.lib.api.hud.HudPlacement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.crosshairaddons.CrosshairAddons;
import walksy.crosshairaddons.config.CrosshairAddonsConfig;


@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsExtractorMixin {

    @Unique
    private boolean crosshairaddons$moving;

    @Shadow
    public abstract int guiWidth();

    @Shadow
    public abstract int guiHeight();

    @Shadow
    public abstract void blitSprite(ResourceLocation sprite, int x, int y, int width, int height, int color);

    @Shadow
    public abstract void blitSprite(ResourceLocation sprite, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height, int color);

    @Inject(method = "blitSprite(Lnet/minecraft/resources/ResourceLocation;IIIII)V", at = @At("HEAD"), cancellable = true)
    public void moveAttackIndicator(final ResourceLocation sprite, final int x, final int y, final int width, final int height, final int color, final CallbackInfo ci) {
        final int newY = this.crosshairaddons$movedY(sprite);
        if (newY == -1) {
            return;
        }
        ci.cancel();
        this.crosshairaddons$moving = true;
        try {
            this.blitSprite(sprite, x, newY, width, height, color);
        } finally {
            this.crosshairaddons$moving = false;
        }
    }

    @Inject(method = "blitSprite(Lnet/minecraft/resources/ResourceLocation;IIIIIIIII)V", at = @At("HEAD"), cancellable = true)
    public void moveAttackIndicatorProgress(final ResourceLocation sprite, final int textureWidth, final int textureHeight, final int u, final int v, final int x, final int y, final int width, final int height, final int color, final CallbackInfo ci) {
        final int newY = this.crosshairaddons$movedY(sprite);
        if (newY == -1) {
            return;
        }
        ci.cancel();
        this.crosshairaddons$moving = true;
        try {
            this.blitSprite(sprite, textureWidth, textureHeight, u, v, x, newY, width, height, color);
        } finally {
            this.crosshairaddons$moving = false;
        }
    }

    @Unique
    private int crosshairaddons$movedY(final ResourceLocation sprite) {
        if (this.crosshairaddons$moving || !sprite.getPath().contains("hud/crosshair_attack")) {
            return -1;
        }
        return this.crosshairaddons$attackIndicatorY();
    }

    @Unique
    private int crosshairaddons$attackIndicatorY() {
        final int w = this.guiWidth();
        final int h = this.guiHeight();

        final int ix = w / 2 - 8;
        int iy = h / 2 - 7 + 16;

        final int iL = ix;
        final int iR = ix + 16;
        int iT = iy;
        int iB = iy + 16;

        final int pad = 0;
        final int gap = CrosshairAddonsConfig.attackIndicatorGap;

        for (final HudPlacement space : CrosshairAddons.addonSpaces()) {
            if (!space.visible()) continue;

            final int aL = (int) (space.screenX() - pad);
            final int aR = (int) (space.screenX() + space.width() + pad);
            final int aT = (int) (space.screenY() - pad);
            final int aB = (int) (space.screenY() + space.height() + pad);

            if (aL < iR && aR > iL && aT < iB && aB > iT) {
                final int candidateIy = aB + gap;
                if (candidateIy > iy) {
                    iy = candidateIy;
                    iT = iy;
                    iB = iy + 16;
                }
            }
        }

        final int defaultIy = h / 2 - 7 + 16;
        return iy == defaultIy ? -1 : iy;
    }
}
