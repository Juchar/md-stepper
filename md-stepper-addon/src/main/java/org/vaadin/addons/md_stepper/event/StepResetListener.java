package org.vaadin.addons.md_stepper.event;

import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.Stepper;

/**
 * Listener for events triggered by the reset of a step.
 */
public interface StepResetListener extends StepListener {

  /**
   * Triggered if the reset of a step is triggered.
   *
   * @param event
   *     The event containing additional information
   */
  void onStepReset(StepResetEvent event);

  /**
   * Event that contains information about the reset of a step.
   */
  class StepResetEvent extends StepEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source
     *     The object on which the Event initially occurred.
     * @param step
     *     The step related to this event
     *
     * @throws IllegalArgumentException
     *     if source is null.
     */
    public StepResetEvent(Stepper source, Step step) {
      super(source, step);
    }
  }
}
