package com.cbp.PublicTrial;

/*
 * @copy 2012 Curly Brace Productions
 * 
 * Public Trial
 * 
 * 
 * @authors
 * Double0negative [http://github.com/double0negative]
 * 
 * 
 * Open Soruce, do what you want but you must leave credits 
 * to both me and Curly Brace Productions.
 * 
 * 
 */

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TrialCommand implements CommandExecutor {

    PublicTrial p;

    public TrialCommand(PublicTrial p){
        this.p = p;
    }

    public boolean onCommand(CommandSender sender1, Command cmd1, String commandLabel, String[] args){
        String cmd = cmd1.getName();
        Player sender = null;

        if (sender1 instanceof Player) {
            sender = (Player) sender1;
        }


        if(cmd.equals("ptrial") || cmd.equals("trial")){
            if(args.length==0){

            }
            if(args[0].equalsIgnoreCase("setjail")){
                p.setJail(sender.getLocation());
                sender.sendMessage("Jail location set");
            }
            if(args[0].equalsIgnoreCase("jailed")){
               String s = "";
               String t="";
               for(Player p: PublicTrial.jailed){
                   try{t = p.getName();}catch(Exception e){t = "";}

                   s = s+t+", ";
               }
               for(String str:PublicTrial.jailedList){
                   s = s+str+", ";
               }
               sender.sendMessage(s);

            }
            return true;
        }
        if((cmd.equalsIgnoreCase("arrest")
                && (sender.isOp() 
                        || sender.hasPermission("trial.detain")))){
            p.arrest(p.getServer().getPlayer((args.length==1)?args[0]:args[1]));
            return true;
        }

        if((cmd.equalsIgnoreCase("unjail")
                && (sender.isOp() 
                        || sender.hasPermission("trial.detain")))){
           sender.sendMessage(  (p.unJail(p.getServer().getPlayer(args[0])))?"Player Unjailed":"Player wasent jailed!");
            return true;
        }

        if(cmd.equalsIgnoreCase("guilty")){
            sender.sendMessage(p.vote(true)); 
        }
        if(cmd.equalsIgnoreCase("notguilty")){
            sender.sendMessage(p.vote(false)); 
        }




        return true; //TODO: make this actually return what its suppose to
    }

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


