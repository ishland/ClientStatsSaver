package com.ishland.ClientStatsSaver;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.ishland.ClientStatsSaver.injector.CSInjector;
import com.ishland.ClientStatsSaver.injector.CSUtils;
import com.ishland.ClientStatsSaver.injector.exception.CSOperationException;

import fr.onecraft.clientstats.ClientStats;
import fr.onecraft.clientstats.common.core.AbstractAPI;

public class BukkitPlugin extends JavaPlugin implements Listener {

    private boolean isInited = false;
    private File dataFile = null;

    @Override
    public void onLoad() {
	this.getDataFolder().mkdirs();
	dataFile = new File(this.getDataFolder() + File.separator + "data.yml");
    }

    @Override
    public void onEnable() {
	Bukkit.getPluginManager().registerEvents(this, this);
	this.getLogger().info("Waiting for ClientStats...");
	if (Bukkit.getPluginManager().isPluginEnabled("ClientStats"))
	    this.inject();
    }

    @Override
    public void onDisable() {
	if (!this.isInited)
	    return;
	this.isInited = false;
	new SaveTask(dataFile).run();
    }

    @SuppressWarnings("deprecation")
    public void inject() {
	if (this.isInited)
	    return;
	this.getLogger().info("ClientStats enabled, injecting...");
	try {
	    CSInjector.init(AbstractAPI.class.cast(ClientStats.getApi()));
	} catch (CSOperationException e) {
	    this.getLogger().log(Level.SEVERE,
		    "Error while injecting ClientStats", e);
	    Bukkit.getPluginManager().disablePlugin(this, true);
	}
	DataSaver.init(dataFile);
	if (dataFile.length() == 0) {
	    try {
		dataFile.createNewFile();
		DataSaver.save();
	    } catch (Exception e) {
		this.getLogger().log(Level.SEVERE,
			"Error while preparing data storage", e);
		Bukkit.getPluginManager().disablePlugin(this, true);
	    }
	}
	try {
	    DataSaver.load();
	} catch (Exception e) {
	    this.getLogger().log(Level.SEVERE,
		    "Error while preparing data storage", e);
	    Bukkit.getPluginManager().disablePlugin(this, true);
	}
	try {
	    System.out.println(CSUtils.serialize());
	} catch (Exception e) {
	}
	this.isInited = true;
	Bukkit.getScheduler().scheduleAsyncRepeatingTask(this,
		new SaveTask(dataFile), 0, 60 * 20);
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
	if (!event.getPlugin().getName().equals("ClientStats"))
	    return;
	this.inject();
    }
}
