package dev.xoapp.worldguard.flags.block;

import dev.xoapp.worldguard.flags.ZoneFlag;

public class BlockBreakFlag extends ZoneFlag {

    private boolean flagValue;

    public BlockBreakFlag(boolean flagValue) {
        this.flagValue = flagValue;
    }

    @Override
    public String getFlagName() {
        return "blockBreak";
    }

    @Override
    public String getFlagBio() {
        return "Block Break";
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
