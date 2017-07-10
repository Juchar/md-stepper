package org.vaadin.addons.md_stepper.iterator;

/**
 * Notifier that triggers an event whenever an element is skipped.
 *
 * @param <E>
 *     The type of the iterated elements
 *
 * @see SkipListener
 */
public interface SkipIterationNotifier<E> extends IterationNotifier<E> {

  /**
   * Add new listener for skip events.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addSkipListener(SkipListener<E> listener);

  /**
   * Remove the given listener for skip events.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeSkipListener(SkipListener<E> listener);
}
