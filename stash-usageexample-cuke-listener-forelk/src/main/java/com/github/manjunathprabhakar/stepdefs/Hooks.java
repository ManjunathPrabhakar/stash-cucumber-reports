package com.github.manjunathprabhakar.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public void bf(Scenario s){
        System.out.println("s.getName() = " + s.getName());
        System.out.println("s.getId() = " + s.getId());
    }
}
