package com.cbp.PublicTrial;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PublicTrial extends JavaPlugin{
	Logger log;
	public static ArrayList<Player>arrested = new ArrayList<Player>();
	public static HashSet<Player>jailed   = new HashSet<Player>();
	
	
	
	
	
	
	
	public void onEnable(){
		log = this.getLogger();
		log.info("PublicTrial has Been Enabled!");		
        this.getServer().getPluginManager().registerEvents(new TrialListener(), this);

	}
	public void onDisable(){
		log.info("PublicTrial has Been Disabled!");
	}
	public CommandExecutor TrialCommand;
	
	
	
	public void arrest(Player p){
	    String s =  p.getDisplayName() +" has been arrested! ";
	    if(arrested.size()>0){
	        s = s + " Voting will begin shortly. "+arrested.size()+" in queue";
	    }
	    
	    if(arrested.size() == 0){
	        s = s + " Voting starts now!";
	    }
	    
	    this.getServer().broadcastMessage(s);
	    this.getServer().broadcastMessage("To vote for "+p.getDisplayName()+" fate, use /trial guilty or /trial notguilty");
	    
	}
	
	
	
}
