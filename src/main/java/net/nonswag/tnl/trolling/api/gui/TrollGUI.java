package net.nonswag.tnl.trolling.api.gui;

import net.nonswag.tnl.listener.api.gui.GUI;
import net.nonswag.tnl.listener.api.gui.GUIItem;
import net.nonswag.tnl.listener.api.gui.GUIOverflowException;
import net.nonswag.tnl.listener.api.gui.Interaction;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.trolling.api.troll.Troll;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class TrollGUI extends GUI {

    private TrollGUI(@Nonnull TNLPlayer victim) {
        super(5, "§8* §6§lTroll§e§lMenu");
        formatDefault();
        setItem(13, TNLItem.create(victim).setName("§8* §7Victim§8: §6" + victim.getName()));
        update(Troll.TROLLS, victim);
    }

    @Nonnull
    private TrollGUI update(@Nonnull List<Troll> trolls, @Nonnull TNLPlayer victim) {
        for (int i = 0; i < 2; i++) remove(10 + i);
        for (int i = 0; i < 2; i++) remove(15 + i);
        for (int i = 0; i < 3; i++) remove(19 + i);
        for (int i = 0; i < 3; i++) remove(23 + i);
        for (int i = 0; i < 5; i++) remove(29 + i);
        List<Troll> overflow = new ArrayList<>();
        trolls.forEach(troll -> {
            try {
                TNLItem item = TNLItem.create(troll.getIcon()).setName("§8* §7Troll§8: §6" + troll.getName());
                if (troll.getDescription() != null) item.withLore("§8* §7Description§8: §6" + troll.getDescription());
                if (troll.isToggleable()) item.addLore("§8* §7Active§8: §6" + troll.isVictim(victim));
                addItem(item.toGUIItem().addInteractions(new Interaction(player1 -> {
                    if (troll.isVictim(victim)) troll.removeVictim(victim);
                    else troll.addVictim(victim);
                    update(trolls, victim);
                })));
            } catch (GUIOverflowException e) {
                overflow.add(troll);
            }
        });
        if (overflow.isEmpty()) {
            for (int i = 0; i < getSize(); i++) {
                setItemIfAbsent(i, TNLItem.create(Material.RED_STAINED_GLASS_PANE).setName("§8* §7Troll§8: §7-§8/§7-"));
            }
            remove(39).remove(41).formatDefault();
        } else {
            remove(39).formatDefault();
            setItem(41, TNLItem.create(Material.TIPPED_ARROW).setName("§8* §3Load more trolls").
                    toGUIItem().addInteractions(new Interaction(player -> {
                        GUIItem[] items = getContents();
                        update(overflow, victim).setItem(39, TNLItem.create(Material.TIPPED_ARROW).
                                setName("§8* §3Load previous trolls").toGUIItem().
                                addInteractions(new Interaction(player1 ->
                                        update(overflow, victim).setContents(items))));
                    })));
        }
        return this;
    }

    @Nonnull
    public static TrollGUI create(@Nonnull TNLPlayer victim) {
        return new TrollGUI(victim);
    }
}
