package org.vaadin.addons.md_stepper.iterator;

import java.util.Iterator;

/**
 * Listener that is triggered whenever an element is skipped.
 *
 * @param <E>
 *     The type of the elements of the iterator
 */
@FunctionalInterface
public interface SkipListener<E> extends IterationListener {

  /**
   * Triggered when an element is skipped.
   *
   * @param event
   *     Event that contains information about the skip
   */
  void onSkip(SkipEvent<E> event);

  /**
   * An event fired when an element is skipped.
   *
   * @param <E>
   *     The type of the element of the iterator
   */
  class SkipEvent<E> extends IterationEvent<E> {

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
    public SkipEvent(Iterator<E> source, E previous, E current) {
      super(source, previous, current);
    }
  }
}
