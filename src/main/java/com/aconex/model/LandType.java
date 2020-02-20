package com.aconex.model;

import java.util.Arrays;
import java.util.function.Supplier;

public enum LandType {
    PLAIN(1, "o"),
    ROCKY(2, "r"),
    TREE(2, "t"),
    RESERVED(-1, "T");

    private int fuel;

    private String code;

    LandType(int fuel, String code) {
        this.fuel = fuel;
        this.code = code;
    }

    public int getFuel() {
        return fuel;
    }

    public String getCode() {
        return code;
    }

    public static int getFuelByLandType(LandType landType) {
        return landType.getFuel();
    }

    public static String getCodeByLandType(LandType landType) {
        return landType.getCode();
    }

    public static LandType parse(String code) {
        return Arrays.stream(LandType.values())
                .filter(landType -> landType.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Input param code " + code + "is invalid"));
    }
}
