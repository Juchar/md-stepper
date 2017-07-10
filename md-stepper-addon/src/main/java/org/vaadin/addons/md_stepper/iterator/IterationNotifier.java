package org.vaadin.addons.md_stepper.iterator;

/**
 * Base interface for all notifiers regarding iteration
 *
 * @param <E>
 *     The type of the iterated elements
 *
 * @see IterationListener
 */
public interface IterationNotifier<E> extends IteratorNotifier {

  /**
   * Add new listener for next events.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addNextListener(NextListener<E> listener);

  /**
   * Remove the given listener for next events.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeNextListener(NextListener<E> listener);
}
