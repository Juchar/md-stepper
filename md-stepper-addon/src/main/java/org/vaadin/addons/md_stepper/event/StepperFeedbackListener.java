package org.vaadin.addons.md_stepper.event;

import org.vaadin.addons.md_stepper.Stepper;

/**
 * Listener for events triggered by the <b>feedback</b> action of a stepper.
 */
public interface StepperFeedbackListener extends StepperListener {

  /**
   * Triggered if the <b>feedback</b> action of a stepper is triggered.
   *
   * @param event
   *     The event containing additional information
   */
  void onStepperFeedback(StepperFeedbackEvent event);

  /**
   * Event that contains information about the <b>feedback</b> action of a stepper.
   */
  class StepperFeedbackEvent extends StepperEvent {

    private final String feedbackMessage;

    /**
     * Constructs a prototypical Event.
     *
     * @param source
     *     The object on which the Event initially occurred.
     * @param feedbackMessage
     *     The feedback message
     *
     * @throws IllegalArgumentException
     *     if source is null.
     */
    public StepperFeedbackEvent(Stepper source, String feedbackMessage) {
      super(source);
      this.feedbackMessage = feedbackMessage;
    }

    /**
     * Get the feedback message.
     *
     * @return The message
     */
    public String getFeedbackMessage() {
      return feedbackMessage;
    }
  }
}
