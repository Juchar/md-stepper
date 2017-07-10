package org.vaadin.addons.md_stepper.iterator;

import java.util.Iterator;

/**
 * Basic listener for listening to iteration events.
 */
public interface IterationListener extends IteratorListener {

  /**
   * Basic class for iteration events.
   *
   * @param <E>
   *     The type of the iterated elements
   */
  class IterationEvent<E> extends IteratorEvent<E> {

    private final transient E previous;
    private final transient E current;

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
    public IterationEvent(Iterator<E> source, E previous, E current) {
      super(source);
      this.previous = previous;
      this.current = current;
    }

    /**
     * Get the current element <b>before</b> the iteration.
     *
     * @return The element
     */
    public E getPrevious() {
      return previous;
    }

    /**
     * Get the current element <b>after</b> the iteration.
     *
     * @return The element
     */
    public E getCurrent() {
      return current;
    }
  }
}
