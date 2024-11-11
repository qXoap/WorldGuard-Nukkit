package dev.xoapp.worldguard.flags.entity;

import dev.xoapp.worldguard.flags.ZoneFlag;

public class EntityTeleportFlag extends ZoneFlag {

    private boolean flagValue;

    public EntityTeleportFlag(boolean flagValue) {
        this.flagValue = flagValue;
    }

    @Override
    public String getFlagName() {
        return "entityTeleport";
    }

    @Override
    public String getFlagBio() {
        return "Entity Teleport";
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
