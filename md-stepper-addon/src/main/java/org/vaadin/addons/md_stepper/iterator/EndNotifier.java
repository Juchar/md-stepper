package org.vaadin.addons.md_stepper.iterator;

/**
 * Notifier that triggers an event whenever an iterator ends.
 *
 * @param <E>
 *     The type of the iterated elements
 *
 * @see EndListener
 */
public interface EndNotifier<E> extends IteratorNotifier {

  /**
   * Add new listener for end events.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addEndListener(EndListener<E> listener);

  /**
   * Remove the given listener for end events.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeEndListener(EndListener<E> listener);
}
