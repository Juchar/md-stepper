package org.vaadin.addons.md_stepper.event;

import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.Stepper;

/**
 * Listener for events triggered if a sep is activated.
 */
public interface StepActiveListener extends StepListener {

  /**
   * Triggered if a step is activated.
   *
   * @param event
   *     The event containing additional information
   */
  void onStepActive(StepActiveEvent event);

  /**
   * Event that contains information about the activation of a step.
   */
  class StepActiveEvent extends StepEvent {

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
    public StepActiveEvent(Stepper source, Step step) {
      super(source, step);
    }
  }
}
