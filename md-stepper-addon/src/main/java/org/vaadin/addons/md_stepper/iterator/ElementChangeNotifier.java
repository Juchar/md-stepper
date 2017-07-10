package org.vaadin.addons.md_stepper.iterator;

/**
 * Notifier that triggers an event whenever an element changes.
 *
 * @param <E>
 *     The type of the element
 *
 * @see ElementChangeListener
 */
public interface ElementChangeNotifier<E> extends IterationNotifier<E> {

  /**
   * Add new listener for element changes.
   *
   * @param listener
   *     The listener to add
   *
   * @return <code>true</code> if the listener was successfully added, <code>false</code> else
   */
  boolean addElementChangeListener(ElementChangeListener<E> listener);

  /**
   * Remove the given listener for element changes.
   *
   * @param listener
   *     The listener to remove
   *
   * @return <code>true</code> if the listener was successfully removed, <code>false</code> else
   */
  boolean removeElementChangeListener(ElementChangeListener<E> listener);
}
