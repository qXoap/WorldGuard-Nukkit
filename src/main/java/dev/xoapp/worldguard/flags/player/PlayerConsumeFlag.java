package dev.xoapp.worldguard.flags.player;

import dev.xoapp.worldguard.flags.ZoneFlag;

public class PlayerConsumeFlag extends ZoneFlag {

    private boolean flagValue;

    public PlayerConsumeFlag(boolean flagValue) {
        this.flagValue = flagValue;
    }

    @Override
    public String getFlagName() {
        return "playerConsume";
    }

    @Override
    public String getFlagBio() {
        return "Player Consume";
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
