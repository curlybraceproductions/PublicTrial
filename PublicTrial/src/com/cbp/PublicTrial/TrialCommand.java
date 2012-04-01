package com.cbp.PublicTrial;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TrialCommand implements CommandExecutor {
   
    PublicTrial p;
    
    public TrialCommand(Plugin p){
        this.p = p;
    }
    
    public boolean onCommand(CommandSender sender1, Command cmd1, String commandLabel, String[] args){
        String cmd = cmd1.getName();
        Player sender = null;
        if (sender instanceof Player) {
            sender = (Player) sender1;
        }
        
        
        if(cmd.equals("ptrial") || cmd.equals("trial")){
            if(args[0]==null && args[1]==null){
                sender.sendMessage(ChatColor.RED+"Invalid Arguments");
                return false;
            }
            if(args[0].equals("arrest") && (sender.isOp() || sender.hasPermission("trial.detain"))){
                p.arrest(p.getServer().getPlayer(args[1]));
            }
            if(PublicTrial.arrested.size()>0)
                if(args[1].equals)
        }
    
    
    
        
        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("Trial")) {
            Player p = (Player) sender;
            if (args.length == 0) {

            } else if (args.length == 1) {

                if (args[0].equals("arrest") && p.hasPermission("trial.detain")) {
                    if (p.getServer().getPlayer(args[1]) != null) {
                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "You Don't have Permision!");
                }

            } else if (args[0].equalsIgnoreCase("Convict")) {

            } else if (args[1].equalsIgnoreCase("Jail")) {

            } else if (args[2].equalsIgnoreCase("Free")) {

            }
        }
        return false;

    }*/

}
