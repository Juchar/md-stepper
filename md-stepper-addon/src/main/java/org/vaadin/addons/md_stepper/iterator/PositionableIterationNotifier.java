package org.vaadin.addons.md_stepper.iterator;

/**
 * Base interface for all notifiers regarding position iteration
 *
 * @param <E>
 *     The type of the iterated elements
 *
 * @see PositionableIterationListener
 */
public interface PositionableIterationNotifier<E> extends ListIterationNotifier<E> {

  /**
   * Add new listener for move to events.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addMoveToListener(MoveToListener<E> listener);

  /**
   * Remove the given listener for move to events.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeMoveToListener(MoveToListener<E> listener);
}
