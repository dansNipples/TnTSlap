package com.Nips.TnTSlap.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Nips.TnTSlap.TnTSlap;

public class SetLobbyCommand implements CommandExecutor {
	private TnTSlap plugin;
	public SetLobbyCommand(TnTSlap plugin) {
		this.plugin = plugin;
	}
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            Player player = (Player) sender;

            // plugin.lobby_join = player.getLocation();;
            if(player.isOp() == true){
                if (args.length == 0) {
                		int i = plugin.getArenaConfig().getInt("Arenas.ArenaCount");
                        plugin.getArenaConfig().set("Arenas." + i, "");
                        plugin.saveArenaConfig();
                        player.sendMessage(ChatColor.YELLOW + "X: " + Math.round(player.getLocation().getX()));
                        player.sendMessage(ChatColor.YELLOW + "Y: " + Math.round(player.getLocation().getY()));
                        player.sendMessage(ChatColor.YELLOW + "Z: " + Math.round(player.getLocation().getZ()));
                        player.sendMessage(ChatColor.YELLOW + "Yaw: " + player.getLocation().getYaw());
                        player.sendMessage(ChatColor.YELLOW + "Pitch: " + player.getLocation().getPitch());
                        player.sendMessage(ChatColor.RED + "Arena: " + ChatColor.YELLOW + args[0] + ChatColor.RED + " has been set!");
                        return true;
                }
                if (args.length > 0) {
                		if(args.length == 2){
                			try {
	                            int j = plugin.getArenaConfig().getInt("Arenas.MaxArenaPoints");
	                            int i = Integer.parseInt(args[1]);
	                            if (i <= j) {
		                            if(plugin.getArenaConfig().contains(args[0])){
		                                plugin.getArenaConfig().set("Arenas." + args[0] + "." + args[1] + ".X", player.getLocation().getX());
		                                plugin.getArenaConfig().set("Arenas." + args[0] + "." + args[1] + ".Y", player.getLocation().getY());
		                                plugin.getArenaConfig().set("Arenas." + args[0] + "." + args[1] + ".Z", player.getLocation().getZ());
		                                plugin.getArenaConfig().set("Arenas." + args[0] + "." + args[1] + ".Yaw", player.getLocation().getYaw());
		                                plugin.getArenaConfig().set("Arenas." + args[0] + "." + args[1] + ".Pitch", player.getLocation().getPitch());
		                                plugin.saveArenaConfig();
		                                player.sendMessage(ChatColor.YELLOW + "X: " + Math.round(player.getLocation().getX()));
		                                player.sendMessage(ChatColor.YELLOW + "Y: " + Math.round(player.getLocation().getY()));
		                                player.sendMessage(ChatColor.YELLOW + "Z: " + Math.round(player.getLocation().getZ()));
		                                player.sendMessage(ChatColor.YELLOW + "Yaw: " + player.getLocation().getYaw());
		                                player.sendMessage(ChatColor.YELLOW + "Pitch: " + player.getLocation().getPitch());
		                                player.sendMessage(ChatColor.RED + "Arena: " + ChatColor.YELLOW + args[0] + ChatColor.RED + " has been set!");
	                            	} else
	                            		player.sendMessage(ChatColor.RED + "There is no such Arena!");
	                            } else {
	                                player.sendMessage("Value too high! Max is: " + plugin.getArenaConfig().getInt("Arenas.MaxArenaPoints"));
	                            }
                			}
                			catch(Exception e){
                				player.sendMessage("Argument 3 must be a variable!");
                			}
                		}
                        if (args[0].equalsIgnoreCase("Max")) {
                                if (args.length > 1) {
                                        int total = Integer.parseInt(args[1]);
                                        plugin.getArenaConfig().set("Arenas.MaxArenaPoints", (int) total);
                                        plugin.saveArenaConfig();
                                        player.sendMessage("Total available points to set: " + total);
                                } else {
                                    player.sendMessage("Enter a value to set max amount of spawn points to set");
                                }
                        }
 
                }
        } else 
        	player.sendMessage("No permission!");
            return true;
    }
}
