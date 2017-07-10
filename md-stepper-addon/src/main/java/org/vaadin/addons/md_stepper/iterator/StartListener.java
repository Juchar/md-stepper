package org.vaadin.addons.md_stepper.iterator;

/**
 * Listener that is triggered whenever an iterator starts.
 *
 * @param <E>
 *     The type of the elements of the iterator
 */
@FunctionalInterface
public interface StartListener<E> extends IteratorListener {

  /**
   * Triggered when the iterator starts.
   *
   * @param event
   *     Event that contains information about the iterator that started
   */
  void onStart(IteratorEvent<E> event);

}
