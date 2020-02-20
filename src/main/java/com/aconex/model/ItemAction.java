package com.aconex.model;

public enum ItemAction {
    COMMUNICATION_OVER_HEAD("communication overhead per command", 1),

    FUEL_USAGE("fuel usage",1),

    UNCLEARED_SQUARES("uncleared squares",3),

    DESTRUCTION_OF_PROTECTED_TREE("destruction of protected tree",10),

    PAINT_DAMAGE_TO_BULLDOZER("paint damage to bulldozer",2);

    private String actionName;

    private int credit;

    ItemAction(String actionName, int credit) {
        this.actionName = actionName;
        this.credit = credit;
    }

    public String getActionName() {
        return actionName;
    }

    public int getCredit() {
        return credit;
    }
}
