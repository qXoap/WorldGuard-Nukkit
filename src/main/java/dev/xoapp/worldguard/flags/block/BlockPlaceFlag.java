package dev.xoapp.worldguard.flags.block;

import dev.xoapp.worldguard.flags.ZoneFlag;

public class BlockPlaceFlag extends ZoneFlag {

    private boolean flagValue;

    public BlockPlaceFlag(boolean flagValue) {
        this.flagValue = flagValue;
    }

    @Override
    public String getFlagName() {
        return "blockPlace";
    }

    @Override
    public String getFlagBio() {
        return "Block Place";
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
