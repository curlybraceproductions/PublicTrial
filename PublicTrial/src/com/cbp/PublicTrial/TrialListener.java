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

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class TrialListener implements Listener{

    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleMoveEvent(PlayerMoveEvent event) {
        if(isArrested(event.getPlayer())){
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)

    public void handlePlayerChat(PlayerChatEvent event){
        if(isArrested(event.getPlayer())){
            event.setMessage(ChatColor.DARK_RED+"[ARRESTED]"+ChatColor.RESET+event.getMessage());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleCommand(PlayerCommandPreprocessEvent e){
        if(isJailed(e.getPlayer())){
            if(e.getMessage().startsWith("/")){
                e.getPlayer().sendMessage(ChatColor.RED+"Cannot send command while jailed!");
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleRespawn(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
        if(isJailed((Player) e.getEntity())){
            e.setCancelled(true);
        }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlejoin(PlayerJoinEvent e){
        if(isJailed(e.getPlayer())){
            e.getPlayer().teleport(PublicTrial.jail);
        }
    }
    
    
    
    public boolean isArrested(Player p){
        return PublicTrial.isArrested(p);
    }
    
    
    public boolean isJailed(Player p){
        return PublicTrial.isJailed(p);
    }
    
    
    
    
    
 
}
