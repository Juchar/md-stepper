package org.vaadin.addons.md_stepper.event;

import org.vaadin.addons.md_stepper.Step;
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

    private final Step step;
    private final Throwable error;

    /**
     * Constructs a prototypical Event.
     *
     * @param source
     *     The object on which the Event initially occurred.
     * @param step
     *     The step the error is shown for
     * @param error
     *     The error that is shown
     *
     * @throws IllegalArgumentException
     *     if source is null.
     */
    public StepperErrorEvent(Stepper source, Step step, Throwable error) {
      super(source);
      this.step = step;
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

    /**
     * The step the error is shown for.
     *
     * @return The step
     */
    public Step getStep() {
      return step;
    }
  }
}
