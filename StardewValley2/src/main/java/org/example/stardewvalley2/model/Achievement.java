package org.example.stardewvalley2.model;

public class Achievement implements Comparable<Achievement> {
    private String achievementName;
    private String achievementDescription;

    public Achievement(String achievementName, String achievementDescription) {
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public String getAchievementDescription() {
        return achievementDescription;
    }

    public void setAchievementDescription(String achievementDescription) {
        this.achievementDescription = achievementDescription;
    }

    @Override
    public int compareTo(Achievement o) {
        return achievementName.compareTo(o.getAchievementName());
    }

    @Override
    public String toString() {
        return String.format("%s: \n %s", achievementName, achievementDescription);
    }
}
