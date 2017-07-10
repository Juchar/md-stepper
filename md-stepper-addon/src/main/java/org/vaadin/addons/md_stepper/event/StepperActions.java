package org.vaadin.addons.md_stepper.event;

import org.vaadin.addons.md_stepper.Stepper;

/**
 * Convenience methods for executing stepper actions on stepper events.
 */
public final class StepperActions {

  private StepperActions() {
    // Prevent instantiation
  }

  /**
   * Call {@link Stepper#back()} for the given event.
   *
   * @param event
   *     The event fo which the action should be executed
   */
  public static void back(StepperListener.StepperEvent event) {
    event.getSource().back();
  }

  /**
   * Call {@link Stepper#next()} for the given event.
   *
   * @param event
   *     The event fo which the action should be executed
   */
  public static void next(StepperListener.StepperEvent event) {
    event.getSource().next();
  }

  /**
   * Call {@link Stepper#skip()} for the given event.
   *
   * @param event
   *     The event fo which the action should be executed
   */
  public static void skip(StepperListener.StepperEvent event) {
    event.getSource().skip();
  }
}
