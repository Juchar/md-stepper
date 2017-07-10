package org.vaadin.addons.md_stepper.demo;

import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.demo.steps.Step1;
import org.vaadin.addons.md_stepper.demo.steps.Step2;
import org.vaadin.addons.md_stepper.demo.steps.Step3;
import org.vaadin.addons.md_stepper.demo.steps.Step4;

import java.util.Arrays;
import java.util.List;

public final class Data {

  private Data() {
    // Prevent instantiation
  }

  public static List<Step> getSteps() {
    return Arrays.asList(new Step1(), new Step2(), new Step3(), new Step4());
  }
}
