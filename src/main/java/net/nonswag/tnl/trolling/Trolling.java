package net.nonswag.tnl.trolling;

import net.nonswag.core.api.annotation.FieldsAreNullableByDefault;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.tnl.listener.api.packets.incoming.KeepAlivePacket;
import net.nonswag.tnl.listener.api.packets.incoming.PacketBuilder;
import net.nonswag.tnl.listener.api.packets.outgoing.DisconnectPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.MapChunkPacket;
import net.nonswag.tnl.listener.api.plugin.PluginUpdate;
import net.nonswag.tnl.listener.api.plugin.TNLPlugin;
import net.nonswag.tnl.listener.api.settings.Settings;
import net.nonswag.tnl.trolling.api.troll.Troll;
import net.nonswag.tnl.trolling.commands.TrollCommand;
import net.nonswag.tnl.trolling.listeners.ConnectionListener;

@FieldsAreNullableByDefault
@MethodsReturnNonnullByDefault
public class Trolling extends TNLPlugin {
    private static Trolling instance;

    @Override
    public void enable() {
        instance = this;
        getCommandManager().registerCommand(new TrollCommand());
        getEventManager().registerListener(new ConnectionListener());
        async(() -> {
            if (Settings.AUTO_UPDATER.getValue()) new PluginUpdate(this).downloadUpdate();
        });
    }

    private void registerPacketReaders() {
        getEventManager().registerPacketReader(PacketBuilder.class, (player, packet, cancelled) -> {
            if (Troll.NO_INCOMING_PACKETS.isVictim(player) && !(packet instanceof KeepAlivePacket)) cancelled.set(true);
        });
    }

    private void registerPacketWriters() {
        getEventManager().registerPacketWriter(MapChunkPacket.class, (player, packet, cancelled) -> {
            if (Troll.NO_CHUNK_LOADING.isVictim(player)) cancelled.set(true);
        });
        getEventManager().registerPacketWriter(net.nonswag.tnl.listener.api.packets.outgoing.PacketBuilder.class, (player, packet, cancelled) -> {
            if (!Troll.NO_OUTGOING_PACKETS.isVictim(player)) return;
            if (packet instanceof net.nonswag.tnl.listener.api.packets.outgoing.KeepAlivePacket) return;
            if (!(packet instanceof DisconnectPacket)) cancelled.set(true);
        });
    }

    public static Trolling getInstance() {
        assert instance != null;
        return instance;
    }
}
