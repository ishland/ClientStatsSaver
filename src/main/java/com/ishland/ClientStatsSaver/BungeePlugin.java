package com.ishland.ClientStatsSaver;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.bstats.bungeecord.Metrics;

import com.ishland.ClientStatsSaver.injector.CSInjector;
import com.ishland.ClientStatsSaver.injector.CSUtils;
import com.ishland.ClientStatsSaver.injector.exception.CSOperationException;

import fr.onecraft.clientstats.ClientStats;
import fr.onecraft.clientstats.common.core.AbstractAPI;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePlugin extends Plugin {

    private boolean isInited = false;
    private File dataFile = null;
    private Metrics metric = null;

    @Override
    public void onLoad() {
	this.getDataFolder().mkdirs();
	dataFile = new File(this.getDataFolder() + File.separator + "data.dat");
    }

    @Override
    public void onEnable() {
	this.setMetric(new Metrics(this));
	new Timer().schedule(new TimerTask() {
	    @Override
	    public void run() {
		getLogger().info("Waiting for ClientStats...");
		if (ClientStats.getApi() != null) {
		    inject();
		    this.cancel();
		}
	    }
	}, 0, 1000);
    }

    public void inject() {
	if (this.isInited)
	    return;
	this.getLogger().info("ClientStats enabled, injecting...");
	try {
	    CSInjector.init(AbstractAPI.class.cast(ClientStats.getApi()));
	} catch (CSOperationException e) {
	    this.getLogger().log(Level.SEVERE,
		    "Error while injecting ClientStats", e);
	    this.onDisable();
	}
	DataSaver.init(dataFile);
	if (dataFile.length() == 0) {
	    try {
		dataFile.createNewFile();
		DataSaver.save();
	    } catch (Exception e) {
		this.getLogger().log(Level.SEVERE,
			"Error while preparing data storage", e);
		this.onDisable();
	    }
	}
	try {
	    DataSaver.load();
	} catch (Exception e) {
	    this.getLogger().log(Level.SEVERE,
		    "Error while preparing data storage", e);
	    this.onDisable();
	}
	try {
	    System.out.println(CSUtils.serialize());
	} catch (Exception e) {
	}
	this.isInited = true;
	this.getProxy().getScheduler().schedule(this, new SaveTask(dataFile), 0,
		60, TimeUnit.SECONDS);
    }

    @Override
    public void onDisable() {
	if (!this.isInited)
	    return;
	this.isInited = false;
	new SaveTask(dataFile).run();
    }

    /**
     * @return the metric
     */
    public Metrics getMetric() {
	return metric;
    }

    /**
     * @param metric the metric to set
     */
    public void setMetric(Metrics metric) {
	this.metric = metric;
    }

}
