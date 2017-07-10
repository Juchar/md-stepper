package org.vaadin.addons.md_stepper.event;

import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.Stepper;

/**
 * Basic listener for all listeners related to steps.
 */
public interface StepListener extends StepperListener {

  /**
   * Base event class for all events related to steps.
   */
  class StepEvent extends StepperListener.StepperEvent {

    private final Step step;

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
    public StepEvent(Stepper source, Step step) {
      super(source);
      this.step = step;
    }

    /**
     * Get the step related to the event.
     *
     * @return The step
     */
    public Step getStep() {
      return step;
    }
  }
}
