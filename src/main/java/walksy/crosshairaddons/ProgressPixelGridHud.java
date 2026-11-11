package walksy.crosshairaddons;

import main.walksy.lib.api.config.value.PixelGrid;
import main.walksy.lib.api.config.value.PixelGridAnimation;
import main.walksy.lib.api.hud.HudElement;
import main.walksy.lib.api.hud.HudElements;
import main.walksy.lib.core.render.WLGraphics2D;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/**
 * A pixel-grid icon that is cut off from the right as {@code progress} falls from 1 to 0.
 * Size, offsets and scaling come from the regular pixel-grid element so it lays out exactly like the other addons.
 */
final class ProgressPixelGridHud implements HudElement {
    private final Supplier<PixelGridAnimation> animation;
    private final BooleanSupplier blend;
    private final DoubleSupplier progress;
    private final HudElement layout;

    ProgressPixelGridHud(final Supplier<PixelGridAnimation> animation, final BooleanSupplier blend, final DoubleSupplier progress) {
        this.animation = animation;
        this.blend = blend;
        this.progress = progress;
        this.layout = HudElements.pixelGrid(animation, blend);
    }

    @Override
    public void render(final WLGraphics2D graphics, final float x, final float y, final float scale, final boolean editing) {
        final PixelGridAnimation value = this.animation.get();
        final PixelGrid frame = value == null ? null : value.getCurrentFrame();
        if (frame == null) {
            return;
        }
        final int cellPixels = Math.max(1, Math.round(value.getSize() * scale));
        final PixelGrid cropped = editing ? frame : crop(frame, (float) this.progress.getAsDouble());
        if (cropped != null) {
            cropped.render(graphics, x, y, cellPixels, this.blend.getAsBoolean());
        }
    }

    private static PixelGrid crop(final PixelGrid frame, final float progress) {
        int minX = Integer.MAX_VALUE;
        int maxX = -1;
        for (int gy = 0; gy < frame.getHeight(); gy++) {
            for (int gx = 0; gx < frame.getWidth(); gx++) {
                if (frame.getColor(gx, gy) != 0) {
                    minX = Math.min(minX, gx);
                    maxX = Math.max(maxX, gx);
                }
            }
        }
        if (maxX < 0) {
            return null;
        }
        final int span = maxX - minX + 1;
        // Keep at least one column while the indicator is showing so it never vanishes before the rocket does.
        final int columns = Math.max(1, (int) Math.ceil(Math.max(0f, Math.min(1f, progress)) * span));
        if (columns >= span) {
            return frame;
        }
        final int cutoff = minX + columns;
        final int[][] source = frame.getColors();
        final int[][] colors = new int[frame.getHeight()][frame.getWidth()];
        for (int gy = 0; gy < colors.length; gy++) {
            System.arraycopy(source[gy], 0, colors[gy], 0, cutoff);
        }
        return new PixelGrid(frame.getResolution(), colors);
    }

    @Override
    public float width(final float scale) {
        return this.layout.width(scale);
    }

    @Override
    public float height(final float scale) {
        return this.layout.height(scale);
    }

    @Override
    public float scaleStep() {
        return this.layout.scaleStep();
    }

    @Override
    public float defaultOffsetX() {
        return this.layout.defaultOffsetX();
    }

    @Override
    public float defaultOffsetY() {
        return this.layout.defaultOffsetY();
    }
}
