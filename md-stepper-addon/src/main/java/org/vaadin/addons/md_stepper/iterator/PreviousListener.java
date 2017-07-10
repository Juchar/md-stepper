package org.vaadin.addons.md_stepper.iterator;

import java.util.Iterator;

@FunctionalInterface
public interface PreviousListener<E> extends ListIterationListener {

  void onPrevious(PreviousEvent<E> event);

  class PreviousEvent<E> extends ListIterationEvent<E> {

    public PreviousEvent(Iterator<E> source, E oldElement, E newElement) {
      super(source, oldElement, newElement);
    }
  }
}
