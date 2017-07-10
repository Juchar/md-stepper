package org.vaadin.addons.md_stepper.event;

import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.Stepper;

/**
 * Listener for events triggered by the skip of a step.
 */
public interface StepSkipListener extends StepListener {

  /**
   * Triggered if a step is skipped.
   *
   * @param event
   *     The event containing additional information
   */
  void onStepSkip(StepSkipEvent event);

  /**
   * Event that contains information about the skip of a step.
   */
  class StepSkipEvent extends StepEvent {

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
    public StepSkipEvent(Stepper source, Step step) {
      super(source, step);
    }
  }
}
