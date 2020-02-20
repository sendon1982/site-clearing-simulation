package com.aconex.model;

import java.util.Arrays;

public enum StepEnum {
    ADVANCE("a", "advance"),
    LEFT("l", "turn left"),
    RIGHT("r", "turn right"),
    QUIT("q", "quit");

    private String code;

    private String name;

    StepEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static StepEnum parse(String code) {
        return Arrays.stream(StepEnum.values())
                .filter(stepEnum -> stepEnum.getCode().equals(code))
                .findFirst()
                .orElse(null);

    }
}
