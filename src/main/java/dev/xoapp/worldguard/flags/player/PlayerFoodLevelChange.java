package dev.xoapp.worldguard.flags.player;

import dev.xoapp.worldguard.flags.ZoneFlag;

public class PlayerFoodLevelChange extends ZoneFlag {

    private boolean flagValue;

    public PlayerFoodLevelChange(boolean flagValue) {
        this.flagValue = flagValue;
    }

    @Override
    public String getFlagName() {
        return "playerFoodLevelChange";
    }

    @Override
    public String getFlagBio() {
        return "Player Food Level Change";
    }

    @Override
    public Boolean getFlagValue() {
        return flagValue;
    }

    @Override
    public void setFlagValue(Boolean flagValue) {
        this.flagValue = flagValue;
    }
}