package com.vaadin.flow.component.select.data;

import com.vaadin.flow.component.select.entity.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamData {

    private static List<Team> teamList;

    {
        teamList = new ArrayList<>();
        teamList.add(new Team(1, "Flow", 1));
        teamList.add(new Team(2, "Components", 1));
        teamList.add(new Team(3, "Pro tools", 1));
        teamList.add(new Team(4, "Developers Journey and Onboarding", 1));
        teamList.add(new Team(5, "Experts", 2));
        teamList.add(new Team(6, "Incubator", 2));

    }

    public List<Team> getTeams() {
        return teamList;
    }
}
