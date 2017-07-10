package org.vaadin.addons.md_stepper.collection;

import java.util.Collection;

public interface ElementRemoveListener<E> extends CollectionChangeListener {

  void onElementRemove(ElementRemoveEvent<E> event);

  class ElementRemoveEvent<E> extends CollectionChangeListener.CollectionChangeEvent<E> {

    public ElementRemoveEvent(Collection<E> source, E element) {
      super(source, element);
    }
  }
}
