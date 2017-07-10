package org.vaadin.addons.md_stepper.iterator;

/**
 * Listener that is triggered whenever an iterator ends.
 *
 * @param <E>
 *     The type of the elements of the iterator
 */
@FunctionalInterface
public interface EndListener<E> extends IteratorListener {

  /**
   * Triggered when the iterator ends.
   *
   * @param event
   *     Event that contains information about the iterator that ended
   */
  void onEnd(IteratorEvent<E> event);
}
