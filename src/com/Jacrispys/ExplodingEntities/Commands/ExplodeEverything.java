package com.Jacrispys.ExplodingEntities.Commands;

import com.Jacrispys.ExplodingEntities.ExplodingEntitiesMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

import static com.Jacrispys.ExplodingEntities.Utils.Chat.chat;

public class ExplodeEverything implements CommandExecutor, Listener {

    private final ExplodingEntitiesMain plugin;

    public ExplodeEverything(ExplodingEntitiesMain plugin) {
        this.plugin = plugin;

        plugin.getCommand("ExplodeEverything").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final HashMap<String, Boolean> enableExplode = new HashMap<>();
    private final HashMap<Entity, Long> explodingEntities = new HashMap<>();

    private void Explosions(Entity entity, Player p) {
        if (enableExplode.get("enabled").equals(true)) {
            if (explodingEntities.get(entity) == null) {
                explodingEntities.put(entity, System.currentTimeMillis());
            }
        } else return;
            BukkitRunnable boomboom = new BukkitRunnable() {
                @Override
                public void run() {
                    Location loc = entity.getLocation();
                    if (enableExplode.get("enabled").equals(true)) {
                        if ((System.currentTimeMillis() - explodingEntities.get(entity)) / 1000 >= 3) {
                            for (int i = 0; i <= 0; i++) {
                                p.getWorld().createExplosion(loc, 2.5F, false, true);
                            }
                            this.cancel();
                        } else {
                            loc = entity.getLocation();
                            if (entity.isDead()) {
                                this.cancel();
                            } else if (loc == null) {
                                this.cancel();
                            } else {
                                long time = (((System.currentTimeMillis() - explodingEntities.get(entity)) / 100L));
                                double time2 = time / 10F;
                                String name = ("&a" + (float) (3F - time2));
                                if ((3F - time2) >= 2.1) {
                                    name = ("&a" + (float) (3F - time2));
                                } else if ((3F - time2) >= 1.1) {
                                    name = ("&6" + (float) (3F - time2));
                                } else if ((3F - time2) >= 0) {
                                    String time3 = Float.toString((float) (3F - time2));
                                    name = ("&c" + (time3));
                                }
                                entity.setCustomName(chat(name));
                                entity.setCustomNameVisible(true);
                            }
                        }
                    } else if(enableExplode.get("enabled").equals(false)) { this.cancel();}
                }

            };
            boomboom.runTaskTimer(plugin,0L,0L);


            }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("ExplodeEverything")) {
           if(args.length == 0) {
               sender.sendMessage(chat("&6&lUsages:" +
                       "\n&a/ExplodeEverything on" +
                       "\n&c/ExplodeEverything off"));
           } else if(args.length == 1) {
               if(args[0].equalsIgnoreCase("on")) {
                   enableExplode.put("enabled", true);
                   sender.sendMessage(chat("&3ExplodeEverything: &aEnabled"));
                   Player p = (Player) sender;
                   if(enableExplode.get("enabled").equals(true)) {
                       Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                           for(Entity entity : p.getNearbyEntities(3, 3, 3)) {
                               Explosions(entity, p);
                           }
                       },0L, 0L);
                   } else return false;
               } else if (args[0].equalsIgnoreCase("off")) { enableExplode.put("enabled", false); sender.sendMessage(chat("&3ExplodeEverything: &cDisabled"));} else return false;
           } else return false;
        } else return false;
        return false;
    }
}
