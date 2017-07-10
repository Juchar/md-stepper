package org.vaadin.addons.md_stepper.iterator;

/**
 * Base interface for all notifiers regarding list iteration
 *
 * @param <E>
 *     The type of the iterated elements
 *
 * @see ListIterationListener
 */
public interface ListIterationNotifier<E> extends IterationNotifier<E> {

  /**
   * Add new listener for previous events.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addPreviousListener(PreviousListener<E> listener);

  /**
   * Remove the given listener for previous events.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removePreviousListener(PreviousListener<E> listener);
}
