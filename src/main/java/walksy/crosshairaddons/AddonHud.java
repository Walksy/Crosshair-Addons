package walksy.crosshairaddons;

import main.walksy.lib.api.hud.HudElement;
import main.walksy.lib.core.render.WLGraphics2D;

import java.util.List;

public final class AddonHud implements HudElement {
    private static final String ENVIRONMENT_BLEND = "Environment Blend";

    private final HudElement element;
    private final List<String> options;

    AddonHud(final HudElement element, final String iconKey) {
        this.element = element;
        this.options = List.of(iconKey, ENVIRONMENT_BLEND);
    }

    @Override
    public void render(final WLGraphics2D graphics, final float x, final float y, final float scale, final boolean editing) {
        this.element.render(graphics, x, y, scale, editing);
    }

    @Override
    public float width(final float scale) {
        return this.element.width(scale);
    }

    @Override
    public float height(final float scale) {
        return this.element.height(scale);
    }

    @Override
    public float scaleStep() {
        return this.element.scaleStep();
    }

    public boolean snapScale() {
        return true;
    }

    public List<String> hudOptions() {
        return this.options;
    }

    @Override
    public boolean visible() {
        return this.element.visible();
    }

    @Override
    public float defaultOffsetX() {
        return this.element.defaultOffsetX();
    }

    @Override
    public float defaultOffsetY() {
        return this.element.defaultOffsetY();
    }
}
