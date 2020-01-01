package com.ishland.ClientStatsSaver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import javax.annotation.Nonnull;

import com.ishland.ClientStatsSaver.injector.CSUtils;
import com.ishland.ClientStatsSaver.injector.exception.CSOperationException;

public class DataSaver {

    private static boolean isInited = false;
    private static File dataFile = null;

    public static void init(@Nonnull File dataFile) {
	DataSaver.dataFile = dataFile;
	isInited = true;
    }

    public static void save() throws IOException, IllegalArgumentException,
	    IllegalAccessException {
	if (!isInited)
	    return;
	FileOutputStream fileOut = new FileOutputStream(dataFile);
	ObjectOutputStream out = new ObjectOutputStream(fileOut);
	out.writeObject(CSUtils.serialize());
	out.close();
	fileOut.close();
    }

    @SuppressWarnings("unchecked")
    public static void load() throws IOException, IllegalArgumentException,
	    IllegalAccessException, CSOperationException,
	    ClassNotFoundException {
	if (!isInited)
	    return;
	FileInputStream fileIn = new FileInputStream(dataFile);
	ObjectInputStream in = new ObjectInputStream(fileIn);
	CSUtils.deserialize(true, (Map<String, Object>) in.readObject());
	in.close();
	fileIn.close();
    }
}
