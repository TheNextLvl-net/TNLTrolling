package net.nonswag.tnl.trolling;

import net.nonswag.tnl.listener.api.plugin.PluginUpdate;
import net.nonswag.tnl.listener.api.plugin.TNLPlugin;
import net.nonswag.tnl.listener.api.settings.Settings;
import net.nonswag.tnl.listener.api.version.Version;
import net.nonswag.tnl.trolling.commands.TrollCommand;
import net.nonswag.tnl.trolling.listeners.ConnectionListener;
import net.nonswag.tnl.trolling.listeners.PacketListener;

public class Trolling extends TNLPlugin {

    @Override
    public void enable() {
        getCommandManager().registerCommand(new TrollCommand());
        getEventManager().registerListener(Version.v1_16_4, PacketListener::new);
        getEventManager().registerListener(new ConnectionListener());
        async(() -> {
            if (Settings.AUTO_UPDATER.getValue()) new PluginUpdate(this).downloadUpdate();
        });
    }
}
