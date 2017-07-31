package org.vaadin.addons.md_stepper;

import java.util.List;

/**
 * Allows to step through items.
 */
public interface Stepper {

  /**
   * Refresh the stepper (update labels, etc.).
   * <p>
   * Use this method if a step has changed.
   */
  void refresh();

  /**
   * Get the steps of this stepper.
   *
   * @return The steps
   */
  List<Step> getSteps();

  /**
   * Get the currently active step of this stepper.
   *
   * @return The step
   */
  Step getCurrent();

  /**
   * Check if the stepper is complete.
   *
   * @return <code>true</code> if the stepper is complete, <code>false</code> else
   */
  boolean isComplete();

  /**
   * Check if the given step is complete.
   *
   * @param step
   *     The step to check
   *
   * @return <code>true</code> if the step is complete, <code>false</code> else
   */
  boolean isStepComplete(Step step);

  /**
   * Start the stepper.
   */
  void start();

  /**
   * Move a step back.
   */
  void back();

  /**
   * Move a step forward.
   */
  void next();

  /**
   * Skip the current step and move a step forward.
   */
  void skip();

  /**
   * Show the given error for the currently active step.
   *
   * @param throwable
   *     The error to show
   */
  void showError(Throwable throwable);

  /**
   * Show the given error for the specified step.
   *
   * @param step
   *     The step to show the error for
   * @param throwable
   *     The error to show
   */
  void showError(Step step, Throwable throwable);

  /**
   * Hide the error for the current step.
   * <p>
   * Convenience method for calling {@link #showError(Throwable)} with <code>null</code> as
   * parameter.
   */
  void hideError();

  /**
   * Hide the error for the specified step.
   * <p>
   * Convenience method for calling {@link #showError(Step, Throwable)} with <code>null</code> as
   * parameter.
   */
  void hideError(Step step);

  /**
   * Get the error for the current step.
   *
   * @return The error
   */
  Throwable getError();

  /**
   * Get the error for the specified step.
   *
   * @return The error
   */
  Throwable getError(Step step);

  /**
   * Show the given feedback message.
   *
   * @param message
   *     The message to show
   */
  void showFeedbackMessage(String message);

  /**
   * Hide the currently visible feedback message.
   * <p>
   * Convenience method for calling {@link #showFeedbackMessage(String)} with <code>null</code> as
   * parameter.
   */
  void hideFeedbackMessage();

  /**
   * Get the current feedback message.
   *
   * @return The message
   */
  String getFeedbackMessage();
}
