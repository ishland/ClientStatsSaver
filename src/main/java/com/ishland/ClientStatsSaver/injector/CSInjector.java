package com.ishland.ClientStatsSaver.injector;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.ishland.ClientStatsSaver.injector.exception.CSNotFoundException;
import com.ishland.ClientStatsSaver.injector.exception.CSOperationException;

import fr.onecraft.clientstats.common.core.AbstractAPI;

public class CSInjector {

    private static boolean isInited = false;

    // ClientStats API Class
    private static Class<?> CSAPI = null;

    // ClientStats API Instance
    private static AbstractAPI instance = null;

    // ClientStats Fields
    private static Field joined = null; // Map<UUID, Integer>
    private static Field totalJoined = null; // int
    private static Field totalNewPlayers = null; // int
    private static Field maxOnlinePlayers = null; // int
    private static Field maxOnlineDate = null; // long
    private static Field startOfRecording = null; // long
    private static Field averagePlaytime = null; // double
    private static Field playtimeRatio = null; // int

    public static void init(@Nonnull AbstractAPI api)
	    throws CSOperationException {
	instance = api;
	// Try to find ClientStats API Class
	try {
	    CSAPI = Class
		    .forName("fr.onecraft.clientstats.common.core.AbstractAPI");
	} catch (ClassNotFoundException e) {
	    throw new CSNotFoundException(e);
	}

	try {
	    initReflectionAccess();
	} catch (NoSuchFieldException e) {
	    throw new CSOperationException("Unable to access field "
		    + "(incompatible ClientStats version)", e);
	} catch (SecurityException e) {
	    throw new CSOperationException("Unable to access field "
		    + "(Java security manager blocked our access)", e);
	} catch (IllegalArgumentException e) {
	    throw new CSOperationException("Unable to access field "
		    + "(Java security manager blocked our access)", e);
	} catch (IllegalAccessException e) {
	    throw new CSOperationException("Unable to access field "
		    + "(incompatible ClientStats version)", e);
	}

	isInited = true;
    }

    private static void initReflectionAccess()
	    throws CSNotFoundException, NoSuchFieldException, SecurityException,
	    IllegalArgumentException, IllegalAccessException {
	if (CSAPI == null)
	    throw new CSNotFoundException();

	// Try to disable Java Security
	try {
	    Class.forName("java.lang.System").getDeclaredField("security")
		    .setAccessible(true);
	    Class.forName("java.lang.System").getDeclaredField("security")
		    .set(null, null);
	} catch (Exception e) {
	    System.out.println(
		    "[ClientStatsSaver] Error while disabling Java Security "
			    + "(This does not break this plugin)");
	    e.printStackTrace();
	}

	joined = CSAPI.getDeclaredField("joined");
	joined.setAccessible(true);
	// Disable final fields
	Field joinedField = Field.class.getDeclaredField("modifiers");
	joinedField.setAccessible(true);
	joinedField.setInt(joined, joined.getModifiers() & ~Modifier.FINAL);

	totalJoined = CSAPI.getDeclaredField("totalJoined");
	totalJoined.setAccessible(true);

	totalNewPlayers = CSAPI.getDeclaredField("totalNewPlayers");
	totalNewPlayers.setAccessible(true);

	maxOnlinePlayers = CSAPI.getDeclaredField("maxOnlinePlayers");
	maxOnlinePlayers.setAccessible(true);

	maxOnlineDate = CSAPI.getDeclaredField("maxOnlineDate");
	maxOnlineDate.setAccessible(true);

	startOfRecording = CSAPI.getDeclaredField("startOfRecording");
	startOfRecording.setAccessible(true);

	averagePlaytime = CSAPI.getDeclaredField("averagePlaytime");
	averagePlaytime.setAccessible(true);

	playtimeRatio = CSAPI.getDeclaredField("playtimeRatio");
	playtimeRatio.setAccessible(true);
    }

    public static boolean isCSAvailable() throws CSOperationException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	try {
	    Class.forName("fr.onecraft.clientstats.common.core.AbstractAPI");
	} catch (ClassNotFoundException e) {
	    return false;
	}
	return true;
    }

    @SuppressWarnings("unchecked")
    public static Map<UUID, Integer> getJoined()
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	return (Map<UUID, Integer>) joined.get(instance);
    }

    public static void setJoined(Map<UUID, Integer> joined)
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	CSInjector.joined.set(instance, joined);
    }

    public static int getTotalJoined()
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	return totalJoined.getInt(instance);
    }

    public static void setTotalJoined(int totalJoined)
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	CSInjector.totalJoined.setInt(instance, totalJoined);
    }

    public static int getTotalNewPlayers()
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	return totalNewPlayers.getInt(instance);
    }

    public static void setTotalNewPlayers(int totalNewPlayers)
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	CSInjector.totalNewPlayers.setInt(instance, totalNewPlayers);
    }

    public static int getMaxOnlinePlayers()
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	return maxOnlinePlayers.getInt(instance);
    }

    public static void setMaxOnlinePlayers(int maxOnlinePlayers)
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	CSInjector.maxOnlinePlayers.setInt(instance, maxOnlinePlayers);
    }

    public static long getMaxOnlineDate()
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	return maxOnlineDate.getLong(instance);
    }

    public static void setMaxOnlineDate(long maxOnlineDate)
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	CSInjector.maxOnlineDate.setLong(instance, maxOnlineDate);
    }

    public static long getStartOfRecording()
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	return startOfRecording.getLong(instance);
    }

    public static void setStartOfRecording(long startOfRecording)
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	CSInjector.startOfRecording.setLong(instance, startOfRecording);
    }

    public static double getAveragePlaytime()
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	return averagePlaytime.getDouble(instance);
    }

    public static void setAveragePlaytime(double averagePlaytime)
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	CSInjector.averagePlaytime.setDouble(instance, averagePlaytime);
    }

    public static int getPlaytimeRatio()
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	return playtimeRatio.getInt(instance);
    }

    public static void setPlaytimeRatio(int playtimeRatio)
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isInited)
	    throw new IllegalStateException(
		    "Please initialize the injector first");
	CSInjector.playtimeRatio.setInt(instance, playtimeRatio);
    }

}
