package net.nonswag.tnl.trolling.commands;

import net.nonswag.tnl.listener.api.command.CommandSource;
import net.nonswag.tnl.listener.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.TNLCommand;
import net.nonswag.tnl.listener.api.language.MessageKey;
import net.nonswag.tnl.listener.api.message.Message;
import net.nonswag.tnl.listener.api.message.Placeholder;
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
            TNLPlayer player = source.player();
            if (args.length >= 1) {
                TNLPlayer victim = TNLPlayer.cast(args[0]);
                if (victim != null) player.openGUI(TrollGUI.create(victim));
                else player.sendMessage(MessageKey.PLAYER_NOT_ONLINE, new Placeholder("player", args[0]));
            } else player.sendMessage("%prefix% §c/troll §8[§6Victim§8]");
        } else source.sendMessage(Message.PLAYER_COMMAND_EN.getText());
    }

    @Nonnull
    @Override
    protected List<String> suggest(@Nonnull Invocation invocation) {
        List<String> suggestions = new ArrayList<>();
        for (Player all : Bukkit.getOnlinePlayers()) suggestions.add(all.getName());
        return suggestions;
    }
}
