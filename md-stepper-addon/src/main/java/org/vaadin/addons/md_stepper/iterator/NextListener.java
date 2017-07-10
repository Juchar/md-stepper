package org.vaadin.addons.md_stepper.iterator;

import java.util.Iterator;

/**
 * Listener that is triggered whenever an iterator is moved to the next element.
 *
 * @param <E>
 *     The type of the elements of the iterator
 */
@FunctionalInterface
public interface NextListener<E> extends IterationListener {

  /**
   * Triggered when the iterator is moved to the next element.
   *
   * @param event
   *     Event that contains information about the move to the next element
   */
  void onNext(NextEvent<E> event);

  /**
   * An event fired when an iterator moves to the next element.
   *
   * @param <E>
   *     The type of the element of the iterator
   */
  class NextEvent<E> extends IterationEvent<E> {

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
    public NextEvent(Iterator<E> source, E previous, E current) {
      super(source, previous, current);
    }
  }
}
