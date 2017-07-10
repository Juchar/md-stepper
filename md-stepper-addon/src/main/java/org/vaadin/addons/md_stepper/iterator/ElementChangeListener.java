package org.vaadin.addons.md_stepper.iterator;

/**
 * Listener for element changes.
 *
 * @param <E>
 *     The type of the element
 */
@FunctionalInterface
public interface ElementChangeListener<E> extends IterationListener {

  /**
   * Triggered when the element changed.
   *
   * @param event
   *     The event that caused the element change
   */
  void onElementChange(IterationEvent<E> event);
}
