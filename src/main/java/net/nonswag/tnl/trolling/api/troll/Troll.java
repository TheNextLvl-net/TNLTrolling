package net.nonswag.tnl.trolling.api.troll;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.math.MathUtil;
import net.nonswag.tnl.listener.api.packets.outgoing.DisconnectPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.ForgetLevelChunkPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.RemoveEntitiesPacket;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.trolling.Trolling;
import net.nonswag.tnl.trolling.api.errors.Internal;
import net.nonswag.tnl.trolling.api.errors.OpenGL;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
@Setter
@RequiredArgsConstructor
@FieldsAreNonnullByDefault
public class Troll {
    public static final List<Troll> TROLLS = new ArrayList<>();
    public static final Troll NO_INCOMING_PACKETS = new Troll("Block Incoming Packets", Material.REPEATING_COMMAND_BLOCK).register();
    public static final Troll NO_OUTGOING_PACKETS = new Troll("Block Outgoing Packets", Material.CHAIN_COMMAND_BLOCK).register();
    public static final Troll DEMO = new Troll("Demo Screen", Material.STRUCTURE_BLOCK).activate(player -> {
        player.interfaceManager().closeGUI(false);
        player.interfaceManager().demo(demo -> (float) demo.INTRO);
    }).setToggleable(false).register();
    public static final Troll NO_CHUNK_LOADING = new Troll("Block Chunk Loading", Material.BARRIER).register();
    public static final Troll OPENGL_ERROR_SPAMMING = new Troll("OpenGL Error Spamming", Material.GLOWSTONE_DUST, "Shows a random error").activate(player -> {
        OpenGL error = OpenGL.values()[MathUtil.randomInteger(0, OpenGL.values().length - 1)];
        player.messenger().sendMessage(error.getMessage());
    }).setToggleable(false).register();
    public static final Troll RANDOM_EXCEPTION = new Troll("Random Exception", Material.NAME_TAG, "Disconnects the player").activate(player -> {
        Internal error = Internal.values()[MathUtil.randomInteger(0, Internal.values().length - 1)];
        DisconnectPacket.create(Component.text(error.getMessage())).send(player);
    }).setToggleable(false).register();
    public static final Troll FREEZE_CLIENT = new Troll("Freeze Client", Material.SKELETON_SKULL, "Use with caution!").activate(player ->
            player.particleManager().spawn(Particle.ELECTRIC_SPARK, player.worldManager().getLocation(), Integer.MAX_VALUE)).setToggleable(false).register();
    public static final Troll HIDE_CHUNKS = new Troll("Hide Chunks", Material.ENDER_EYE).activate(player -> {
        int x = player.worldManager().getChunk().getX();
        int z = player.worldManager().getChunk().getZ();
        ForgetLevelChunkPacket.create(x - 1, z - 1).send(player);
        ForgetLevelChunkPacket.create(x + 1, z + 1).send(player);
        ForgetLevelChunkPacket.create(x - 1, z + 1).send(player);
        ForgetLevelChunkPacket.create(x + 1, z - 1).send(player);
        ForgetLevelChunkPacket.create(x + 2, z).send(player);
        ForgetLevelChunkPacket.create(x - 2, z).send(player);
        ForgetLevelChunkPacket.create(x, z + 2).send(player);
        ForgetLevelChunkPacket.create(x, z - 2).send(player);
    }).setToggleable(false).register();
    public static final Troll HIDE_ENTITIES = new Troll("Hide Entities", Material.ENDER_PEARL).sync(true).activate(player ->
            RemoveEntitiesPacket.create(player.worldManager().getWorld().getEntities().toArray(new Entity[]{})).send(player)).setToggleable(false).register();

    private final String name;
    private final Material icon;
    @Accessors(fluent = true, chain = true)
    private Boolean sync;
    @Nullable
    private final String description;
    @Accessors(fluent = true, chain = true)
    private Consumer<TNLPlayer> activate = player -> {
    };
    @Accessors(fluent = true, chain = true)
    private Consumer<TNLPlayer> deactivate = player -> {
    };
    private final List<UUID> victims = new ArrayList<>();
    @Accessors(chain = true)
    boolean toggleable = true;

    public Troll(@Nonnull String name, @Nonnull Material icon) {
        this(name, icon, null);
    }

    public boolean isVictim(@Nonnull TNLPlayer player) {
        return getVictims().contains(player.getUniqueId()) && isToggleable();
    }

    public Troll addVictim(@Nonnull TNLPlayer player) {
        if (!isVictim(player) && isToggleable()) getVictims().add(player.getUniqueId());
        if (sync() == null) activate().accept(player);
        else if (sync()) Trolling.getInstance().sync(() -> activate().accept(player));
        else Trolling.getInstance().async(() -> activate().accept(player));
        return this;
    }

    public Troll removeVictim(@Nonnull TNLPlayer player) {
        getVictims().remove(player.getUniqueId());
        deactivate().accept(player);
        return this;
    }

    public Troll register() {
        if (!TROLLS.contains(this)) TROLLS.add(this);
        return this;
    }

    public void unregister() {
        TROLLS.remove(this);
    }
}
