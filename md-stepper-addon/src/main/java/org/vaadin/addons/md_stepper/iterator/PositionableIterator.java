package org.vaadin.addons.md_stepper.iterator;

import java.util.ListIterator;

/**
 * Iterator that allows repositioning the cursor to a certain element.
 *
 * @param <E>
 *     The type of the iterated elements
 */
public interface PositionableIterator<E> extends ListIterator<E> {

  /**
   * Move to the given element.
   *
   * @param element
   *     The element to move the iterator to
   */
  void moveTo(E element);

  /**
   * Check if a move to the given element is possible.
   *
   * @param element
   *     The element to check the move for
   *
   * @return <code>true</code> if the move is possible, <code>false</code> else
   */
  boolean hasMoveTo(E element);
}
