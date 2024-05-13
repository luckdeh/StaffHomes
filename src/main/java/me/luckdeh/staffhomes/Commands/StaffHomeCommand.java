package me.luckdeh.staffhomes.Commands;

import me.luckdeh.staffhomes.StaffHomes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffHomeCommand implements CommandExecutor {

    StaffHomes plugin;

    public StaffHomeCommand(StaffHomes plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player p){

            if (plugin.getConfig().getBoolean("enable")){
                if (args.length == 1 && args[0].equalsIgnoreCase("set")){
                    if(plugin.getConfig().isConfigurationSection("savedlocations." + p.getName())){
                        p.sendMessage(ChatColor.GREEN + "Overriding current home at " + ChatColor.GRAY + Math.round(plugin.getConfig().getDouble("savedlocations." + p.getName() + ".x")) + " " + Math.round(plugin.getConfig().getDouble("savedlocations." + p.getName() + ".y")) + " " + Math.round(plugin.getConfig().getDouble("savedlocations." + p.getName() + ".z")));
                       saveLocation(p);
                    }else{
                        saveLocation(p);
                    }

                }else if(args.length == 1 && args[0].equalsIgnoreCase("return")){
                    if (plugin.getConfig().isConfigurationSection("savedlocations." + p.getName())){
                        Location return_location = new Location(p.getWorld(),plugin.getConfig().getDouble("savedlocations." + p.getName() + ".x"), plugin.getConfig().getDouble("savedlocations." + p.getName() + ".y"), plugin.getConfig().getDouble("savedlocations." + p.getName() + ".z"));
                        p.teleport(return_location);
                        p.sendMessage(ChatColor.GREEN + "Returned to former location.");
                        plugin.getConfig().set("savedlocations." + p.getName(), null);
                        plugin.saveConfig();
                    }else{
                        p.sendMessage(ChatColor.RED + "You never set a staffhome!");
                    }
                }else if (args.length == 1 && args[0].equalsIgnoreCase("reload")){
                    plugin.reloadConfig();
                    p.sendMessage(ChatColor.GRAY + "Staffhomes Config has successfully reloaded.");
                }
            }else if (args.length == 1){
                Player t = Bukkit.getPlayer(args[0]);
                if (!(t == null)){
                    if (plugin.getConfig().isConfigurationSection("savedlocations." + t.getName())){
                        p.sendMessage(ChatColor.GREEN + "Teleporting to temp StaffHome(" + t.getName() + ") @: " + ChatColor.GRAY + Math.round(plugin.getConfig().getDouble("savedlocations." + t.getName() + ".x")) + Math.round( plugin.getConfig().getDouble("savedlocations." + t.getName() + ".y")) + Math.round(plugin.getConfig().getDouble("savedlocations." + t.getName() + ".z")));
                        Location return_Location = new Location(t.getWorld(), Math.round(plugin.getConfig().getDouble("savedlocations." + t.getName() + ".x")), Math.round(plugin.getConfig().getDouble("savedlocations." + t.getName() + ".y")) , Math.round(plugin.getConfig().getDouble("savedlocations." + t.getName() + ".z")));
                        p.teleport(return_Location);
                    }else {
                        p.sendMessage(ChatColor.DARK_RED + "That player does not have an home set.");
                    }
                }
            }else if(args.length == 0){
                p.sendMessage(ChatColor.GRAY + "=======" + ChatColor.GREEN + "" + ChatColor.BOLD + "StaffHomes" + ChatColor.GRAY + "by Luckdeh =======");
                p.sendMessage(ChatColor.GOLD + "/staffhome set" + ChatColor.GRAY + "-" + ChatColor.DARK_BLUE + "Set a temp StaffHome");
                p.sendMessage(ChatColor.GOLD + "/staffhome return" + ChatColor.GRAY + "-" + ChatColor.DARK_BLUE + "Return you to your last temp StaffHome and remove it");
                p.sendMessage(ChatColor.GOLD + "/staffhome [name]" + ChatColor.GRAY + "-" + ChatColor.DARK_BLUE + "Teleport to a temp home");
                p.sendMessage(ChatColor.GOLD + "/staffhome reload" + ChatColor.GRAY + "-" + ChatColor.DARK_BLUE + "Reload StaffHomes Config");

            }

        }


        return true;
    }

    public void saveLocation(Player p){
        Location l = p.getLocation();
        plugin.getConfig().createSection("savedlocations." + p.getName());
        plugin.getConfig().set("savedlocations." + p.getName() + ".x", l.getX());
        plugin.getConfig().set("savedlocations." + p.getName() + ".y", l.getY());
        plugin.getConfig().set("savedlocations." + p.getName() + ".z", l.getZ());
        plugin.saveConfig();
        p.sendMessage(ChatColor.GREEN + "Setting a temp staff home at: " + ChatColor.GRAY + Math.round(plugin.getConfig().getDouble("savedlocations." + p.getName() + ".x")) + " " + Math.round(plugin.getConfig().getDouble("savedlocations." + p.getName() + ".y")) + " " + Math.round(plugin.getConfig().getDouble("savedlocations." + p.getName() + ".z")));
    }
}
