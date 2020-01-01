package com.ishland.ClientStatsSaver;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Nonnull;

import org.yaml.snakeyaml.Yaml;

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
	FileWriter writer = new FileWriter(dataFile);
	new Yaml().dump(CSUtils.serialize(), writer);
    }

    @SuppressWarnings("unchecked")
    public static void load() throws IOException, IllegalArgumentException,
	    IllegalAccessException, CSOperationException {
	if (!isInited)
	    return;
	FileReader reader = new FileReader(dataFile);
	try {
	    if (!CSUtils.deserialize(true,
		    (Map<String, Object>) new Yaml().load(reader)))
		throw new CSOperationException();
	} catch (ClassCastException e) {
	    throw new IllegalArgumentException(e);
	}
	reader.close();
    }
}
