package org.vaadin.addons.md_stepper.event;

import org.vaadin.addons.md_stepper.Stepper;

/**
 * Listener for events triggered by the <b>error</b> action of a stepper.
 */
public interface StepperErrorListener extends StepperListener {

  /**
   * Triggered if the <b>error</b> action of a stepper is triggered.
   *
   * @param event
   *     The event containing additional information
   */
  void onStepperError(StepperErrorEvent event);

  /**
   * Event that contains information about the <b>error</b> action of a stepper.
   */
  class StepperErrorEvent extends StepperEvent {

    private final Throwable error;

    /**
     * Constructs a prototypical Event.
     *
     * @param source
     *     The object on which the Event initially occurred.
     *
     * @throws IllegalArgumentException
     *     if source is null.
     */
    public StepperErrorEvent(Stepper source, Throwable error) {
      super(source);
      this.error = error;
    }

    /**
     * Get the error of the stepper.
     *
     * @return The error
     */
    public Throwable getError() {
      return error;
    }
  }
}
