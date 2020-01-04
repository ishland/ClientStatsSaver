package com.ishland.ClientStatsSaver;

import java.io.File;

import javax.annotation.Nonnull;

public class SaveTask implements Runnable {

    File dataFile = null;

    public SaveTask(@Nonnull File dataFile) {
	this.dataFile = dataFile;
    }

    @Override
    public void run() {
	try {
	    DataSaver.save();
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

}
