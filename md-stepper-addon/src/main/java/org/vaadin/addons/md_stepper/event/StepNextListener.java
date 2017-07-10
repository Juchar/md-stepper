package org.vaadin.addons.md_stepper.event;

import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.Stepper;

/**
 * Listener for events triggered by the <b>next</b> action of a step.
 */
public interface StepNextListener extends StepListener {

  /**
   * Triggered if the <b>next</b> action of a step is triggered.
   *
   * @param event
   *     The event containing additional information
   */
  void onStepNext(StepNextEvent event);

  /**
   * Event that contains information about the <b>next</b> action of a step.
   */
  class StepNextEvent extends StepEvent {

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
    public StepNextEvent(Stepper source, Step step) {
      super(source, step);
    }
  }
}
