package com.cbp.PublicTrial;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class TrialListener implements Listener{

    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleMoveEvent(PlayerMoveEvent event) {
        if(isArrested(event.getPlayer())){
            event.setCancelled(true);
        }
    }
    
    public void handlePlayerChat(PlayerChatEvent event){
        if(isArrested(event.getPlayer())){
            event.setMessage(ChatColor.DARK_RED+"[ARRESTED]"+ChatColor.RESET+event.getMessage());
        }
    }
    
    
    public boolean isArrested(Player p){
        return PublicTrial.isArrested(p);
    }
    
    
    
    
    
    
    
    
 
}
