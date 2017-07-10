package org.vaadin.addons.md_stepper.iterator;

import java.io.Serializable;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Iterator;

/**
 * Base listener for listening to iterator events.
 */
public interface IteratorListener extends EventListener, Serializable {

  /**
   * Base class for iterator events.
   *
   * @param <E>
   *     The type of the iterated elements
   */
  class IteratorEvent<E> extends EventObject {

    /**
     * Construct a new iterator event
     *
     * @param source
     *     The iterator that causes the event
     */
    public IteratorEvent(Iterator<E> source) {
      super(source);
    }

    /**
     * Get the iterator that caused the event.
     *
     * @return The iterator
     */
    public Iterator<E> getIterator() {
      return (Iterator<E>) getSource();
    }
  }
}
