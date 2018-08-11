package com.example.mcbot.model;

public class BotSkill {
    private String skill;
    private Boolean use;

    public BotSkill(String skill, Boolean use) {
        this.skill = skill;
        this.use = use;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Boolean getUse() {
        return use;
    }

    public void setUse(Boolean use) {
        this.use = use;
    }
}
