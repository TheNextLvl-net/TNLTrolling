package net.nonswag.tnl.trolling.commands;

import net.nonswag.tnl.core.api.command.CommandSource;
import net.nonswag.tnl.core.api.command.Invocation;
import net.nonswag.tnl.core.api.message.Placeholder;
import net.nonswag.tnl.core.api.message.key.MessageKey;
import net.nonswag.tnl.listener.api.command.TNLCommand;
import net.nonswag.tnl.listener.api.command.exceptions.SourceMismatchException;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.trolling.api.gui.TrollGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TrollCommand extends TNLCommand {

    public TrollCommand() {
        super("troll", "tnl.troll");
    }

    @Override
    protected void execute(@Nonnull Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (source.isPlayer()) {
            TNLPlayer player = (TNLPlayer) source.player();
            if (args.length >= 1) {
                TNLPlayer victim = TNLPlayer.cast(args[0]);
                if (victim != null) player.interfaceManager().openGUI(TrollGUI.create(victim));
                else player.messenger().sendMessage(MessageKey.PLAYER_NOT_ONLINE, new Placeholder("player", args[0]));
            } else player.messenger().sendMessage("%prefix% §c/troll §8[§6Victim§8]");
        } else throw new SourceMismatchException();
    }

    @Nonnull
    @Override
    protected List<String> suggest(@Nonnull Invocation invocation) {
        List<String> suggestions = new ArrayList<>();
        for (Player all : Bukkit.getOnlinePlayers()) suggestions.add(all.getName());
        return suggestions;
    }
}
