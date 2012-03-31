package com.cbp.PublicTrial;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrialCommand implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("Trial")){
			Player p = (Player) sender;
			if(args.length== 0){
				
			}else if(args.length==1){
				if(p.hasPermission("trial.detain")){
					if(p.getServer().getPlayer(args[0]) != null){
						Player targetPlayer = Bukkit.getPlayer(args[0]);
					}
				}else{
					p.sendMessage(ChatColor.RED + "You Don't have Permision!");
				}
				
			}else if(args[0].equalsIgnoreCase("Convict")){
				
			}else if(args[1].equalsIgnoreCase("Jail")){
				
			}else if(args[2].equalsIgnoreCase("Free")){
				
			}
		}
		return false;
		
	}

}
