package org.vaadin.addons.md_stepper.iterator;

/**
 * Notifier that triggers an event whenever an iterator starts.
 *
 * @param <E>
 *     The type of the iterated elements
 *
 * @see StartListener
 */
public interface StartNotifier<E> extends IteratorNotifier {

  /**
   * Add new listener for start events.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addStartListener(StartListener<E> listener);

  /**
   * Remove the given listener for start events.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeStartListener(StartListener<E> listener);
}
