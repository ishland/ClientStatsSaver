package com.ishland.ClientStatsSaver;

import java.io.File;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;

public class SaveTask implements Runnable {

    File dataFile = null;

    public SaveTask(@Nonnull File dataFile) {
	this.dataFile = dataFile;
    }

    @Override
    public void run() {
	Bukkit.getLogger().info("[ClientStatsSaver] Saving data...");
	try {
	    DataSaver.save();
	} catch (Exception e) {
	    Bukkit.getLogger().log(Level.WARNING,
		    "[ClientStatsSaver] Unable to save data", e);
	}
    }

}
