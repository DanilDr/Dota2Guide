package ru.russianbravedev.dotamania;

public class ConverterSteamID {
	final Long steamacckey;

	public ConverterSteamID() {
		steamacckey = new Long(76561197960265728L);
	}
	
	public Long Steam32To64(Long Steam32) {
		return Steam32 * 2 + steamacckey;
	}

	public Long Steam64To32(Long Steam64) {
		return (Steam64 - steamacckey) / 2;
	}
}
