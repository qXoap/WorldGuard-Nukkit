package dev.xoapp.worldguard.flags.entity;

import dev.xoapp.worldguard.flags.ZoneFlag;

public class EntityDamageFlag extends ZoneFlag {

    private boolean flagValue;

    public EntityDamageFlag(boolean flagValue) {
        this.flagValue = flagValue;
    }

    @Override
    public String getFlagName() {
        return "entityDamage";
    }

    @Override
    public String getFlagBio() {
        return "Entity Damage";
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
