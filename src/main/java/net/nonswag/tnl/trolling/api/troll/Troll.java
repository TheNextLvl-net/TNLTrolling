package net.nonswag.tnl.trolling.api.troll;

import net.nonswag.tnl.core.api.math.MathUtil;
import net.nonswag.tnl.listener.api.packets.GameStateChangePacket;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.trolling.api.errors.OpenGL;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class Troll {

    @Nonnull
    private static final List<Troll> trolls = new ArrayList<>();

    @Nonnull
    public static Troll TIMEOUT = new Troll("Timeout", Material.COMMAND_BLOCK).onActivate(player -> new Thread(() -> {
        try {
            Thread.sleep(MathUtil.randomInteger(10, 30) * 1000L);
        } catch (InterruptedException ignored) {
        } finally {
            player.pipeline().disconnect("Timed out");
        }
    }).start()).register();
    @Nonnull
    public static Troll NO_INCOMING_PACKETS = new Troll("Block Incoming Packets", Material.REPEATING_COMMAND_BLOCK).register();
    @Nonnull
    public static Troll NO_OUTGOING_PACKETS = new Troll("Block Outgoing Packets", Material.CHAIN_COMMAND_BLOCK).register();
    @Nonnull
    public static Troll WEIRD_UI = new Troll("Weird UI", Material.JIGSAW).
            onActivate(player -> {
                player.pipeline().sendPacket(GameStateChangePacket.create(GameStateChangePacket.RAIN_LEVEL_CHANGE, 14));
                player.bukkit().setPlayerTime(0, false);
            }).onDeactivate(player -> {
                player.bukkit().resetPlayerWeather();
                player.bukkit().resetPlayerTime();
            }).register();
    @Nonnull
    public static Troll DEMO = new Troll("Demo Screen", Material.STRUCTURE_BLOCK).onActivate(player -> {
        player.interfaceManager().closeGUI(false);
        player.interfaceManager().demo(demo -> demo.WELCOME);
    }).setToggleable(false).register();
    @Nonnull
    public static Troll PING_SPOOF = new Troll("Ping Spoof", Material.STRUCTURE_VOID, "In development").register();
    @Nonnull
    public static Troll NO_CHUNK_LOADING = new Troll("Block Chunk Loading", Material.BARRIER).register();
    @Nonnull
    public static Troll DELAY_MOVEMENTS = new Troll("Delay Movements", Material.GUNPOWDER, "In development").register();
    @Nonnull
    public static Troll OPENGL_ERROR_SPAMMING = new Troll("OpenGL Error Spamming", Material.GLOWSTONE_DUST).onActivate(player -> {
        OpenGL error = OpenGL.values()[MathUtil.randomInteger(0, OpenGL.values().length - 1)];
        player.messenger().sendMessage(error.getMessage());
    }).setToggleable(false).register();
    @Nonnull
    public static Troll FREEZE_CLIENT = new Troll("Freeze Client", Material.REDSTONE).onActivate(player ->
            player.pipeline().sendPacket(GameStateChangePacket.create(GameStateChangePacket.RAIN_LEVEL_CHANGE, 5000))).setToggleable(false).register();

    @Nonnull
    private final String name;
    @Nonnull
    private final Material icon;
    @Nullable
    private final String description;
    @Nonnull
    private Consumer<TNLPlayer> activateAction = player -> {
    };
    private Consumer<TNLPlayer> deactivateAction = player -> {
    };
    @Nonnull
    private final List<UUID> victims = new ArrayList<>();
    boolean toggleable = true;

    public Troll(@Nonnull String name, @Nonnull Material icon) {
        this(name, icon, null);
    }

    public Troll(@Nonnull String name, @Nonnull Material icon, @Nullable String description) {
        this.name = name;
        this.icon = icon;
        this.description = description;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public Material getIcon() {
        return icon;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nonnull
    public Consumer<TNLPlayer> getActivateAction() {
        return activateAction;
    }

    public Consumer<TNLPlayer> getDeactivateAction() {
        return deactivateAction;
    }

    @Nonnull
    public Troll onActivate(@Nonnull Consumer<TNLPlayer> activateAction) {
        this.activateAction = activateAction;
        return this;
    }

    @Nonnull
    public Troll onDeactivate(Consumer<TNLPlayer> deactivateAction) {
        this.deactivateAction = deactivateAction;
        return this;
    }

    public boolean isToggleable() {
        return toggleable;
    }

    @Nonnull
    public Troll setToggleable(boolean toggleable) {
        this.toggleable = toggleable;
        return this;
    }

    @Nonnull
    public List<UUID> getVictims() {
        return victims;
    }

    public boolean isVictim(@Nonnull TNLPlayer player) {
        return getVictims().contains(player.getUniqueId()) && isToggleable();
    }

    @Nonnull
    public Troll addVictim(@Nonnull TNLPlayer player) {
        if (!isVictim(player) && isToggleable()) getVictims().add(player.getUniqueId());
        getActivateAction().accept(player);
        return this;
    }

    @Nonnull
    public Troll removeVictim(@Nonnull TNLPlayer player) {
        getVictims().remove(player.getUniqueId());
        getDeactivateAction().accept(player);
        return this;
    }

    @Nonnull
    public Troll register() {
        if (!getTrolls().contains(this)) getTrolls().add(this);
        return this;
    }

    public void unregister() {
        getTrolls().remove(this);
    }

    @Nonnull
    public static List<Troll> getTrolls() {
        return trolls;
    }
}
