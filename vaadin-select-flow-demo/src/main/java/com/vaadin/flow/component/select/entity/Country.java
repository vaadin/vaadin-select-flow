package com.vaadin.flow.component.select.entity;

import java.util.Objects;

public class Country {
    private String name;
    private String continent;
    private String capital;

    public Country(String name, String continent, String capital) {
        this.name = name;
        this.continent = continent;
        this.capital = capital;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    public String getCapital() {
        return capital;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Country country = (Country) o;
        return name.equals(country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
