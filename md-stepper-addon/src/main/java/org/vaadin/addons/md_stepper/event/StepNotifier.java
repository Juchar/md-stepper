package org.vaadin.addons.md_stepper.event;

/**
 * A notifier that notifies about step evet
 */
public interface StepNotifier {

  /**
   * Add the given listener that is triggered if a step is completed.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addStepCompleteListener(StepCompleteListener listener);

  /**
   * Remove the given listener.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeStepCompleteListener(StepCompleteListener listener);

  /**
   * Add the given listener that is triggered if the <b>back</b> action of a step is triggered.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addStepBackListener(StepBackListener listener);

  /**
   * Remove the given listener.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeStepBackListener(StepBackListener listener);

  /**
   * Add the given listener that is triggered if the <b>next</b> action of a step is triggered.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addStepNextListener(StepNextListener listener);

  /**
   * Remove the given listener.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeStepNextListener(StepNextListener listener);

  /**
   * Add the given listener that is triggered if the <b>skip</b> action of a step is triggered.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addStepSkipListener(StepSkipListener listener);

  /**
   * Remove the given listener.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeStepSkipListener(StepSkipListener listener);

  /**
   * Add the given listener that is triggered if a step is cancelled.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addStepCancelListener(StepCancelListener listener);

  /**
   * Remove the given listener.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeStepCancelListener(StepCancelListener listener);

  /**
   * Add the given listener that is triggered if a step is activated.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addStepActiveListener(StepActiveListener listener);

  /**
   * Remove the given listener.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeStepActiveListener(StepActiveListener listener);
}
