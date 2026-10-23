package walksy.crosshairaddons.config;

import main.walksy.lib.api.WalksyLibApi;
import main.walksy.lib.api.config.Config;
import main.walksy.lib.api.config.option.AvailabilityMode;
import main.walksy.lib.api.config.option.Category;
import main.walksy.lib.api.config.option.Option;
import main.walksy.lib.api.config.option.OptionGroup;
import main.walksy.lib.api.config.option.Options;
import main.walksy.lib.api.config.value.ListEntries;
import main.walksy.lib.api.config.value.PixelGrid;
import main.walksy.lib.api.config.value.PixelGridAnimation;
import main.walksy.lib.core.hud.HudEditScreen;
import main.walksy.lib.api.config.PathUtils;
import walksy.crosshairaddons.CrosshairAddons;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class CrosshairAddonsConfig implements WalksyLibApi {
    public static boolean modEnabled = true;
    public static int attackIndicatorGap = -2;

    public static boolean environmentBlend = true;
    public static boolean showInThirdPerson = false;

    public static boolean entityIndicatorAddonEnabled = true;
    public static boolean entityIndicatorShowAll = false;
    public static List<String> entityIndicatorEntityList = List.of("player");
    public static PixelGridAnimation entityIndicatorAddon = new PixelGridAnimation(PixelGrid.create()
        .set(4, 4).set(5, 4)
        .set(9, 4).set(10, 4)
        .set(4, 5)
        .set(10, 5)
        .set(4, 10).set(5, 10)
        .set(9, 10).set(10, 10)
        .set(4, 9)
        .set(10, 9)
        .build()).offset(0, 0);

    public static boolean elytraAddonEnabled = true;
    public static PixelGridAnimation elytraAddon = new PixelGridAnimation(PixelGrid.create()
        .set(1, 4).set(1, 5).set(1, 6).set(1, 7)
        .set(2, 3).set(2, 8).set(2, 9)
        .set(3, 3).set(3, 10).set(3, 11)
        .set(4, 2).set(4, 11)
        .set(5, 2).set(5, 10)
        .set(6, 2).set(6, 3).set(6, 4).set(6, 5).set(6, 6).set(6, 7).set(6, 8).set(6, 9)
        .set(7, 2).set(7, 3)
        .set(8, 2).set(8, 3).set(8, 4).set(8, 5).set(8, 6).set(8, 7).set(8, 8).set(8, 9)
        .set(9, 2).set(9, 10)
        .set(10, 2).set(10, 11)
        .set(11, 3).set(11, 10).set(11, 11)
        .set(12, 3).set(12, 8).set(12, 9)
        .set(13, 4).set(13, 5).set(13, 6).set(13, 7)
        .build()).offset(0, 11);

    public static boolean hitmarkerAddonEnabled = true;
    public static boolean hitmarkerToggleOnAttack = false;
    public static boolean hitmarkerStopOnAnimationEnd = true;
    public static int hitmarkerDuration = 15;
    public static PixelGridAnimation hitmarkerAddon = new PixelGridAnimation(PixelGrid.create()
        .set(3, 3).set(11, 3)
        .set(4, 4).set(10, 4)
        .set(5, 5).set(9, 5)
        .set(5, 9).set(9, 9)
        .set(4, 10).set(10, 10)
        .set(3, 11).set(11, 11)
        .build()).offset(0, 0);

    public static boolean shieldBreakAddonEnabled = false;
    public static boolean shieldIndicatorEnabled = false;
    public static boolean shieldBreakStopOnAnimationEnd = true;
    public static int shieldBreakDuration = 15;
    public static PixelGridAnimation shieldBreakAddon = new PixelGridAnimation(
        PixelGrid.create()
            .set(4, 5).set(5, 5).set(6, 5).set(7, 5).set(8, 5).set(9, 5).set(10, 5)
            .set(4, 6).set(10, 6)
            .set(4, 7).set(10, 7)
            .set(4, 8).set(10, 8)
            .set(4, 9).set(10, 9)
            .set(4, 10).set(10, 10)
            .set(5, 11).set(9, 11)
            .set(6, 12).set(7, 12).set(8, 12)
            .build(),

        PixelGrid.create()
            .set(3, 5).set(4, 5).set(5, 5).set(9, 5).set(10, 5).set(11, 5)
            .set(3, 6).set(11, 6)
            .set(3, 7).set(11, 7)
            .set(3, 8).set(11, 8)
            .set(3, 9).set(11, 9)
            .set(3, 10).set(11, 10)
            .set(4, 11).set(10, 11)
            .set(5, 12).set(9, 12).set(6, 12).set(8, 12)
            .build(),

        PixelGrid.create()
            .set(2, 5).set(4, 5).set(10, 5).set(11, 5)
            .set(2, 6).set(12, 6)
            .set(2, 7).set(12, 7)
            .set(12, 8)
            .set(2, 9).set(12, 9)
            .set(2, 10).set(12, 10)
            .set(3, 11)
            .set(10, 12)
            .build(),

        PixelGrid.create()
            .set(1, 5).set(3, 5).set(11, 5)
            .set(1, 7).set(13, 7)
            .set(1, 9).set(13, 9)
            .set(13, 10)
            .set(2, 11)
            .set(10, 12)
            .build(),

        PixelGrid.create()
            .set(2, 5)
            .set(0, 7).set(14, 7)
            .set(14, 9)
            .set(1, 11)
            .set(12, 12)
            .build()
    ).offset(0, 8.5).animationSpeed(17);

    public static PixelGridAnimation shieldIndicatorAddon = new PixelGridAnimation(PixelGrid.create()
        .set(4, 5).set(5, 5).set(6, 5).set(7, 5).set(8, 5).set(9, 5).set(10, 5)
        .set(4, 6).set(10, 6)
        .set(4, 7).set(10, 7)
        .set(4, 8).set(10, 8)
        .set(4, 9).set(10, 9)
        .set(4, 10).set(10, 10)
        .set(5, 11).set(9, 11)
        .set(6, 12).set(7, 12).set(8, 12)
        .build()).offset(0, 8.5);

    public static boolean isEditing() {
        return HudEditScreen.isOpen();
    }

    private static Option<Boolean> toggle(final String name, final String saveKey, final Supplier<Boolean> getter, final boolean defaultValue, final Consumer<Boolean> setter, final Supplier<Boolean> shown) {
        return toggle(name, saveKey, getter, defaultValue, setter, shown, null);
    }

    private static Option<Boolean> toggle(final String name, final String saveKey, final Supplier<Boolean> getter, final boolean defaultValue, final Consumer<Boolean> setter, final Supplier<Boolean> shown, final String description) {
        return Options.ofBoolean(name, getter, defaultValue, setter).saveKey(saveKey).availability(shown, AvailabilityMode.HIDE).description(description).build();
    }

    @Override
    public Config define() {
        return Config.builder()
            .path(PathUtils.ofConfigDir("crosshairaddons"))
            .onSave(CrosshairAddons.getStateManager()::updateCachedEntityTypes)
            .category(Category.builder("General")
                .group(OptionGroup.builder("General")
                    .option(toggle("Mod Enabled", "Mod Enabled", () -> modEnabled, true, value -> modEnabled = value, () -> true))
                    .option(Options.ofNumber("Attack Indicator Gap", () -> attackIndicatorGap, -2, value -> attackIndicatorGap = value)
                        .range(-5, 10, 1)
                        .saveKey("Vanilla Attack Indicator Gap")
                        .description("Space kept between an icon and the attack indicator below it")
                        .build())
                    .option(toggle("Environment Blend", "Environment Blend", () -> environmentBlend, true, value -> environmentBlend = value, () -> true, "Invert the icons against the background like the crosshair"))
                    .option(toggle("Show In Third Person", "Show In Third Person", () -> showInThirdPerson, false, value -> showInThirdPerson = value, () -> true))
                    .build())
                .group(OptionGroup.builder("Entity Indicator")
                    .option(toggle("Entity Indicator", "Entity Indicator Addon Enabled", () -> entityIndicatorAddonEnabled, true, value -> entityIndicatorAddonEnabled = value, () -> true, "Shows when your crosshair is on one of the chosen entities"))
                    .option(toggle("Show All Entities", "Entity Indicator Show All Entities", () -> entityIndicatorShowAll, false, value -> entityIndicatorShowAll = value, () -> entityIndicatorAddonEnabled, "Show for every entity, not just the chosen ones"))
                    .option(Options.ofSelectionList("Entities", () -> entityIndicatorEntityList, List.of("player"), value -> entityIndicatorEntityList = value, ListEntries.entities())
                        .saveKey("Entity Indicator Entity List")
                        .availability(() -> entityIndicatorAddonEnabled && !entityIndicatorShowAll, AvailabilityMode.HIDE)
                        .build())
                    .option(Options.ofPixelGridAnimation("Icon", () -> entityIndicatorAddon, entityIndicatorAddon, value -> entityIndicatorAddon = value)
                        .saveKey("Entity Indicator Addon Icon")
                        .availability(() -> entityIndicatorAddonEnabled, AvailabilityMode.HIDE)
                        .hudSpace("Entity Indicator", CrosshairAddons.addonHud(() -> entityIndicatorAddon, State.INDICATOR, "Entity Indicator Addon Icon"))
                        .build())
                    .build())
                .group(OptionGroup.builder("Elytra")
                    .option(toggle("Elytra", "Elytra Addon Enabled", () -> elytraAddonEnabled, true, value -> elytraAddonEnabled = value, () -> true, "Shows while you're wearing an elytra"))
                    .option(Options.ofPixelGridAnimation("Icon", () -> elytraAddon, elytraAddon, value -> elytraAddon = value)
                        .saveKey("Elytra Addon Icon")
                        .availability(() -> elytraAddonEnabled, AvailabilityMode.HIDE)
                        .hudSpace("Elytra", CrosshairAddons.addonHud(() -> elytraAddon, State.ELYTRA, "Elytra Addon Icon"))
                        .build())
                    .build())
                .group(OptionGroup.builder("Hitmarker")
                    .option(toggle("Hitmarker", "Hitmarker Addon Enabled", () -> hitmarkerAddonEnabled, true, value -> hitmarkerAddonEnabled = value, () -> true, "Shows when your arrows hit"))
                    .option(toggle("Play On Attack", "Toggle Hitmarker On Attack", () -> hitmarkerToggleOnAttack, false, value -> hitmarkerToggleOnAttack = value, () -> hitmarkerAddonEnabled, "Also show it when you hit a player with a weapon other than an arrow"))
                    .option(toggle("Stop When Animation Ends", "Hitmarker Stop On Animation End", () -> hitmarkerStopOnAnimationEnd, true, value -> hitmarkerStopOnAnimationEnd = value, () -> hitmarkerAddonEnabled, "Hide it after one play instead of after a set time"))
                    .option(Options.ofNumber("Duration", () -> hitmarkerDuration, 15, value -> hitmarkerDuration = value)
                        .range(0, 50, 1)
                        .saveKey("Hitmarker Duration")
                        .availability(() -> hitmarkerAddonEnabled && !hitmarkerStopOnAnimationEnd, AvailabilityMode.HIDE)
                        .description("How many ticks it stays on screen")
                        .build())
                    .option(Options.ofPixelGridAnimation("Icon", () -> hitmarkerAddon, hitmarkerAddon, value -> hitmarkerAddon = value)
                        .saveKey("Hitmarker Addon Icon")
                        .availability(() -> hitmarkerAddonEnabled, AvailabilityMode.HIDE)
                        .hudSpace("Hitmarker", CrosshairAddons.addonHud(() -> hitmarkerAddon, State.HITMARKER, "Hitmarker Addon Icon"))
                        .build())
                    .build())
                .group(OptionGroup.builder("Shield Indicator")
                    .option(toggle("Shield Indicator", "Shield Indicator Addon Enabled", () -> shieldIndicatorEnabled, false, value -> shieldIndicatorEnabled = value, () -> true, "Shows when the player you're aiming at is blocking"))
                    .option(Options.ofPixelGridAnimation("Icon", () -> shieldIndicatorAddon, shieldIndicatorAddon, value -> shieldIndicatorAddon = value)
                        .saveKey("Shield Indicator Addon Icon")
                        .availability(() -> shieldIndicatorEnabled, AvailabilityMode.HIDE)
                        .hudSpace("Shield Indicator", CrosshairAddons.addonHud(() -> shieldIndicatorAddon, State.SHIELD_INDICATOR, "Shield Indicator Addon Icon"))
                        .build())
                    .build())
                .group(OptionGroup.builder("Shield Break")
                    .option(toggle("Shield Break", "Shield Break Addon Enabled", () -> shieldBreakAddonEnabled, false, value -> shieldBreakAddonEnabled = value, () -> true, "Shows when you disable someone's shield"))
                    .option(toggle("Stop When Animation Ends", "Shield Break Stop On Animation End", () -> shieldBreakStopOnAnimationEnd, true, value -> shieldBreakStopOnAnimationEnd = value, () -> shieldBreakAddonEnabled, "Hide it after one play instead of after a set time"))
                    .option(Options.ofNumber("Duration", () -> shieldBreakDuration, 15, value -> shieldBreakDuration = value)
                        .range(0, 50, 1)
                        .saveKey("Shield Break Duration")
                        .availability(() -> shieldBreakAddonEnabled && !shieldBreakStopOnAnimationEnd, AvailabilityMode.HIDE)
                        .description("How many ticks it stays on screen")
                        .build())
                    .option(Options.ofPixelGridAnimation("Icon", () -> shieldBreakAddon, shieldBreakAddon, value -> shieldBreakAddon = value)
                        .saveKey("Shield Break Addon Icon")
                        .availability(() -> shieldBreakAddonEnabled, AvailabilityMode.HIDE)
                        .hudSpace("Shield Break", CrosshairAddons.addonHud(() -> shieldBreakAddon, State.SHIELD_BREAK, "Shield Break Addon Icon"))
                        .build())
                    .build())
                .build())
            .build();
    }
}
