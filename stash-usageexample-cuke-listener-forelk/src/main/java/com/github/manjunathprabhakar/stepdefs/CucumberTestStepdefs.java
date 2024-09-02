package com.github.manjunathprabhakar.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

public class CucumberTestStepdefs {

    private static Logger log = Logger.getLogger("This Class");

    @Given("today is Sunday")
    public void today_is_Sunday() {
        //today = "Sunday";
    }

    @When("I ask whether it's {string} yet")
    public void i_ask_whether_it_s_Friday_yet(String day) {
        System.out.println("day = " + day);
        log.info(day);
    }

    @Then("I should be told {string}")
    public void i_should_be_told(String expectedAnswer) {
        System.out.println("expectedAnswer = " + expectedAnswer);
    }

    static String isItFriday(String today) {
        return "nope";
    }

}
