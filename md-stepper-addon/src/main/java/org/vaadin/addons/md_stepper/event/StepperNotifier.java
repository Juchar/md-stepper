package org.vaadin.addons.md_stepper.event;

/**
 * A notifier that notifies about stepper related events.
 */
public interface StepperNotifier {

  /**
   * Add a listener that is triggered if the stepper is complete.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addStepperCompleteListener(StepperCompleteListener listener);

  /**
   * Remove the given listener.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeStepperCompleteListener(StepperCompleteListener listener);

  /**
   * Add the given listener that is triggered if an error is shown.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addStepperErrorListener(StepperErrorListener listener);

  /**
   * Remove the given listener.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeStepperErrorListener(StepperErrorListener listener);

  /**
   * Add the given listener that is triggered if feedback is shown.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addStepperFeedbackListener(StepperFeedbackListener listener);

  /**
   * Remove the given listener.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeStepperFeedbackListener(StepperFeedbackListener listener);
}
