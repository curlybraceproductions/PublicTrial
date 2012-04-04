package com.cbp.PublicTrial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PublicTrial extends JavaPlugin{
    Logger log;
    public static ArrayList<Player>arrested = new ArrayList<Player>();
    public static HashSet<Player>jailed   = new HashSet<Player>();
    private HashSet<Player>voted   = new HashSet<Player>();
    public static int guilty = 0;
    public static int notguilty = 0;
    private boolean voteRunning = false;
    private long trialtime = 0;
    private double guiltypercent = .6;
    private Location jail;
    private File f ;


    public void onEnable(){
        f = new File(this.getDataFolder()+"\\jailedplayers.dat");
        log = this.getLogger();
        log.info("PublicTrial has Been Enabled!");		
        this.getServer().getPluginManager().registerEvents(new TrialListener(),this);

        
        try{
           if(!f.exists())
               f.createNewFile();
           
           jailed =   (HashSet<Player>) new ObjectInputStream( new FileInputStream(f)).readObject();
           
        }catch(Exception e){e.printStackTrace();}
        
        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        trialtime = config.getLong("settings.trialtime") * 20;
        System.out.println(trialtime);
        guiltypercent = config.getDouble("settings.guiltpercent");

        jail = new Location(this.getServer().getWorld(config.getString("jail.world")), 
                config.getDouble("jail.x"),
                config.getDouble("jail.y"),
                config.getDouble("jail.z"));


        for(Player p:jailed){
            p.teleport(jail);
        }
            
        
        
        
        CommandExecutor exec = new TrialCommand(this);
        getCommand("ptrial").setExecutor(exec);
        getCommand("arrest").setExecutor(exec);
        getCommand("guilty").setExecutor(exec);
        getCommand("notguilty").setExecutor(exec);


    }

    public void onDisable(){
        try{
            new ObjectOutputStream(new FileOutputStream(f)).writeObject(jailed);
        }catch(Exception e){e.printStackTrace();}
        log.info("PublicTrial has Been Disabled!");

        this.getServer().getScheduler().cancelAllTasks();

    }


    public CommandExecutor TrialCommand;


    
    

    public void arrest(Player p){
        String s =  p.getDisplayName() +" has been arrested! ";
        if(arrested.size()>0){
            s = s + " Voting will begin shortly. "+arrested.size()+" in queue";
        }

        arrested.add(p);
        this.getServer().broadcastMessage(s);
        checkVoteQueue();
    }

    public static Boolean isArrested(Player p){
        return arrested.contains(p);
    }

    public String vote(boolean b){
        if(!voteRunning){
            return "There are currently no trials to vote in";
        }
        else{
            if(b){
                guilty++;
                return "Voted Guilty!";
            }
            else
                notguilty++;
            return "Voted Not Guilty!";
        }

    }


    public void checkVoteQueue(){
        if(!voteRunning && arrested.size()>0){
            guilty = 0;
            notguilty = 0;
            voteRunning = true;
            voted.clear();
            this.getServer().broadcastMessage("Vote for "+arrested.get(0)+" is starting! use /guilty or /notguilty");

            this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

                public void run() {
                    finalizeVote();
                }
            }, trialtime);
        }

    }


    public void finalizeVote(){
        voteRunning = false;
        double guilt = (guilty)/((notguilty + guilty) +0.0);
        if((guilt)>guiltypercent){
            Player p  = arrested.remove(0);
            jailed.add(p);
            p.teleport(jail);
            this.getServer().broadcastMessage("Player "+p.getDisplayName()+" Was Voted "+ ChatColor.DARK_RED+"Guilty"+ChatColor.RESET+"with "+(int)(guilt*100)+"% voting guilty");
        }
        else{
            Player p  = arrested.remove(0);
            this.getServer().broadcastMessage("Player "+p.getDisplayName()+" Was Voted "+ ChatColor.GREEN+"Not Guilty"+ChatColor.RESET+"with "+(int)(guilt*100)+"% voting guilty");

        }
        checkVoteQueue();
    }


    public void setJail(Location l){
        jail = l;
        FileConfiguration config = this.getConfig();
        config.set("jail.world", l.getWorld().getName());
        config.set("jail.x", l.getX());
        config.set("jail.y", l.getY());
        config.set("jail.z", l.getZ());
        this.saveConfig();
    }

}
