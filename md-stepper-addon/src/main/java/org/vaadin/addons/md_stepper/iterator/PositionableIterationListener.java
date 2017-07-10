package org.vaadin.addons.md_stepper.iterator;

import java.util.Iterator;

/**
 * Basic listener for listening to position iteration events.
 */
public interface PositionableIterationListener extends ListIterationListener {

  /**
   * Basic class for position iteration events.
   *
   * @param <E>
   *     The type of the iterated elements
   */
  class PositionableIterationEvent<E> extends ListIterationEvent<E> {

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
    public PositionableIterationEvent(Iterator<E> source, E previous, E current) {
      super(source, previous, current);
    }
  }
}
