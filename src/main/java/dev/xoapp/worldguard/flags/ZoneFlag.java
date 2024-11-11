package dev.xoapp.worldguard.flags;

public abstract class ZoneFlag {

    abstract public String getFlagName();

    abstract public String getFlagBio();

    abstract public Boolean getFlagValue();

    abstract public void setFlagValue(Boolean flagValue);

}
