package com.ishland.ClientStatsSaver.injector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

public class CSUtils {
    public static Map<String, Object> serialize()
	    throws IllegalArgumentException, IllegalAccessException {
	Map<String, Object> map = new HashMap<>();

	map.put("joined", CSInjector.getJoined());
	map.put("totalJoined", CSInjector.getTotalJoined());
	map.put("totalNewPlayers", CSInjector.getTotalNewPlayers());
	map.put("maxOnlinePlayers", CSInjector.getMaxOnlinePlayers());
	map.put("maxOnlineDate", CSInjector.getMaxOnlineDate());
	map.put("startOfRecording", CSInjector.getStartOfRecording());
	map.put("averagePlaytime", CSInjector.getAveragePlaytime());
	map.put("playtimeRatio", CSInjector.getPlaytimeRatio());

	return map;
    }

    public static boolean isEmpty()
	    throws IllegalArgumentException, IllegalAccessException {
	return CSInjector.getJoined().size() == 0;
    }

    @SuppressWarnings("unchecked")
    public static boolean deserialize(boolean doOverWrite,
	    @Nonnull Map<String, Object> map)
	    throws IllegalArgumentException, IllegalAccessException {
	if (!isEmpty() && !doOverWrite)
	    return false;

	CSInjector.setJoined((Map<UUID, Integer>) map.get("joined"));
	CSInjector.setTotalJoined(Integer
		.valueOf(String.valueOf(map.get("totalJoined"))).intValue());
	CSInjector.setTotalNewPlayers(
		Integer.valueOf(String.valueOf(map.get("totalNewPlayers")))
			.intValue());
	CSInjector.setMaxOnlinePlayers(
		Integer.valueOf(String.valueOf(map.get("maxOnlinePlayers")))
			.intValue());
	CSInjector.setMaxOnlineDate(Long
		.valueOf(String.valueOf(map.get("maxOnlineDate"))).longValue());
	CSInjector.setStartOfRecording(
		Long.valueOf(String.valueOf(map.get("startOfRecording")))
			.longValue());
	CSInjector.setAveragePlaytime(
		Double.valueOf(String.valueOf(map.get("averagePlaytime")))
			.doubleValue());
	CSInjector.setPlaytimeRatio(Integer
		.valueOf(String.valueOf(map.get("playtimeRatio"))).intValue());

	return true;
    }
}
