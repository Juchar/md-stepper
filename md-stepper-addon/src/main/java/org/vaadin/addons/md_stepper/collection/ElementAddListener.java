package org.vaadin.addons.md_stepper.collection;

import java.util.Collection;

public interface ElementAddListener<E> extends CollectionChangeListener {

  void onElementAdd(ElementAddEvent<E> event);

  class ElementAddEvent<E> extends CollectionChangeListener.CollectionChangeEvent<E> {

    public ElementAddEvent(Collection<E> source, E element) {
      super(source, element);
    }
  }
}
