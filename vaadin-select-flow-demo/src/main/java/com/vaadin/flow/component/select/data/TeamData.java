package com.vaadin.flow.component.select.data;

import com.vaadin.flow.component.select.entity.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamData {

    private final List<Team> TEAM_LIST = new ArrayList<>();

    {
//        departmentList = new Department[]{new Department(1, "PD", new Team[] {new Team("Flow"), new Team("Components"), new Team("Pro tools")}), new Department(2, new Team("Services"), new Team[]{new Team("Experts"), new Team("Incubator")})}
        TEAM_LIST.add(new Team(1, "Flow", 1));
        TEAM_LIST.add(new Team(2, "Components", 1));
        TEAM_LIST.add(new Team(3, "Pro tools", 1));
        TEAM_LIST.add(new Team(4, "Developers Journey and Onboarding", 1));
        TEAM_LIST.add(new Team(5, "Experts", 2));
        TEAM_LIST.add(new Team(6, "Incubator", 2));

    }

    public List<Team> getTeams() {
        return TEAM_LIST;
    }
}
