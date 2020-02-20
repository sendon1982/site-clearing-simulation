package com.aconex.model;

public class Block {

    private Position position;

    private LandType landType;

    private boolean clearedBefore;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public LandType getLandType() {
        return landType;
    }

    public void setLandType(LandType landType) {
        this.landType = landType;
    }

    public boolean isClearedBefore() {
        return clearedBefore;
    }

    public void setClearedBefore(boolean clearedBefore) {
        this.clearedBefore = clearedBefore;
    }

    @Override
    public String toString() {
        return landType.getCode();
    }
}
