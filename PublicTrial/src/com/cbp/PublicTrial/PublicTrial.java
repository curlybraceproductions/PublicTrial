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


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
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
    public static ArrayList<Player>jailed   = new ArrayList<Player>();
    public static HashSet<String>jailedList = new HashSet<String>();
    private HashSet<Player>voted   = new HashSet<Player>();
    public static int guilty = 0;
    public static int notguilty = 0;
    private boolean voteRunning = false;
    private long trialtime = 0;
    private double guiltypercent = .6;
    public static  Location jail;
    private File f ;


    public void onEnable(){
        f = new File(this.getDataFolder()+"\\jailedplayers.dat");
        log = this.getLogger();
        log.info("PublicTrial has Been Enabled!");		
        this.getServer().getPluginManager().registerEvents(new TrialListener(),this);


        try{
            if(!f.exists())
                f.createNewFile();

            Scanner in = new Scanner(f);
           
            while(in.hasNext()){
                jailedList.add(in.next());
            }

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


      




        CommandExecutor exec = new TrialCommand(this);
        getCommand("ptrial").setExecutor(exec);
        getCommand("arrest").setExecutor(exec);
        getCommand("guilty").setExecutor(exec);
        getCommand("notguilty").setExecutor(exec);
        getCommand("unjail").setExecutor(exec);

        for(Player p: this.getServer().getOnlinePlayers()){
            isJailed(p);
        }

    }

    public void onDisable(){
        try{
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(f));
            for(Player p:jailed){
                out.write(p.getName());
            }
            for(String s:jailedList){
                out.write(s);
            }
            out.flush();
            out.close();
        }catch(Exception e){e.printStackTrace();}
        log.info("PublicTrial has Been Disabled!");
        jailed.clear();
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

    public static boolean unJail(Player p){
        if(jailed.contains(p)){
            jailed.remove(p);
            p.teleport(new Location(jail.getWorld(),0,64,0));
            return true;
        }
        return false;

    }


    public static Boolean isArrested(Player p){
        return arrested.contains(p);
    }
    public static Boolean isJailed(Player p){
        for(String s:jailedList){
            if(p.getName().equals(s) && !jailed.contains(p)){
                jailed.add(p);
                jailedList.remove(s);
            }
        }
        return jailed.contains(p);
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
            this.getServer().broadcastMessage("Vote for "+arrested.get(0).getName()+" is starting! use /guilty or /notguilty");

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
