package dev.xoapp.worldguard.flags.player;

import dev.xoapp.worldguard.flags.ZoneFlag;

public class PlayerDropFlag extends ZoneFlag {

    private boolean flagValue;

    public PlayerDropFlag(boolean flagValue) {
        this.flagValue = flagValue;
    }

    @Override
    public String getFlagName() {
        return "playerDrop";
    }

    @Override
    public String getFlagBio() {
        return "Player Drop Items";
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