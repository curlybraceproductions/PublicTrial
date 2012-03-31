package com.cbp.PublicTrial;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class PublicTrial extends JavaPlugin{
	Logger log;

	public void onEnable(){
		log = this.getLogger();
		log.info("PublicTrial has Been Enabled!");		
	}
	public void onDisable(){
		log.info("PublicTrial has Been Disabled!");
	}
}
