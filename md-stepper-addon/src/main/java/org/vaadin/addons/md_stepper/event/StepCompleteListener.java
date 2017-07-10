package org.vaadin.addons.md_stepper.event;

import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.Stepper;

/**
 * Listener for events triggered by the completion of a step.
 */
public interface StepCompleteListener extends StepListener {

  /**
   * Triggered if the completion of a step is triggered.
   *
   * @param event
   *     The event containing additional information
   */
  void onStepComplete(StepCompleteEvent event);

  /**
   * Event that contains information about the completion of a step.
   */
  class StepCompleteEvent extends StepEvent {

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
    public StepCompleteEvent(Stepper source, Step step) {
      super(source, step);
    }
  }
}
