package org.vaadin.addons.md_stepper.iterator;

import java.util.Iterator;

/**
 * Listener that is triggered whenever an iterator moves.
 *
 * @param <E>
 *     The type of the elements of the iterator
 */
@FunctionalInterface
public interface MoveToListener<E> extends PositionableIterationListener {

  /**
   * Triggered when the iterator is moved.
   *
   * @param event
   *     Event that contains information about the move
   */
  void onMoveTo(MoveToEvent<E> event);

  /**
   * An event fired when an iterator moves.
   *
   * @param <E>
   *     The type of the element of the iterator
   */
  class MoveToEvent<E> extends PositionableIterationEvent<E> {

    /**
     * Construct a new iteration event.
     *
     * @param source
     *     The iterator that causes the event
     * @param previous
     *     The current element <b>before</b> the iteration
     * @param current
     *     The current element <b>after</b> the iteration
     */
    public MoveToEvent(Iterator<E> source, E previous, E current) {
      super(source, previous, current);
    }
  }
}
