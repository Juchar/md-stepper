package org.vaadin.addons.md_stepper.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;

public interface CollectionChangeListener extends EventListener, Serializable {

  class CollectionChangeEvent<E> extends EventObject {

    private final transient E element;

    /**
     * Constructs a prototypical Event.
     *
     * @param source
     *     The object on which the Event initially occurred.
     *
     * @throws IllegalArgumentException
     *     if source is null.
     */
    public CollectionChangeEvent(Collection<E> source, E element) {
      super(source);
      this.element = element;
    }

    public Collection<E> getCollection() {
      return (Collection<E>) getSource();
    }

    public E getElement() {
      return element;
    }
  }
}
