package net.nonswag.tnl.trolling.commands;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.TNLCommand;
import net.nonswag.tnl.listener.api.command.exceptions.InvalidUseException;
import net.nonswag.tnl.listener.api.command.exceptions.PlayerNotOnlineException;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.trolling.api.gui.TrollGUI;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TrollCommand extends TNLCommand {

    public TrollCommand() {
        super("troll", "tnl.troll");
        setUsage("%prefix% §c/troll §8[§6Victim§8]");
    }

    @Override
    protected void execute(@Nonnull Invocation invocation) {
        TNLPlayer player = (TNLPlayer) invocation.source();
        String[] args = invocation.arguments();
        if (args.length < 1) throw new InvalidUseException(this);
        TNLPlayer victim = TNLPlayer.cast(args[0]);
        if (victim == null) throw new PlayerNotOnlineException(args[0]);
        player.interfaceManager().openGUI(TrollGUI.create(victim));
    }

    @Nonnull
    @Override
    protected List<String> suggest(@Nonnull Invocation invocation) {
        List<String> suggestions = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(all -> suggestions.add(all.getName()));
        return suggestions;
    }

    @Override
    public boolean canUse(@Nonnull CommandSource source) {
        return source instanceof TNLPlayer;
    }
}
