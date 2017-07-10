package org.vaadin.addons.md_stepper.iterator;

import java.util.Iterator;

/**
 * Basic listener for listening to list iteration events.
 */
public interface ListIterationListener extends IterationListener {

  /**
   * Basic class for list iteration events.
   *
   * @param <E>
   *     The type of the iterated elements
   */
  class ListIterationEvent<E> extends IterationEvent<E> {

    /**
     * Construct a new iteration event.
     *
     * @param source
     *     The iterator that causes the event
     * @param previous
     *     The current element <b>before</b> the iteration
     */
    public ListIterationEvent(Iterator<E> source, E previous, E current) {
      super(source, previous, current);
    }
  }
}
