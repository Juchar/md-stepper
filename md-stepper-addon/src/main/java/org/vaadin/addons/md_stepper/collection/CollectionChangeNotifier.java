package org.vaadin.addons.md_stepper.collection;

public interface CollectionChangeNotifier<E> {

  boolean addElementAddListener(ElementAddListener<E> listener);

  boolean removeElementAddListener(ElementAddListener<E> listener);

  boolean addElementRemoveListener(ElementRemoveListener<E> listener);

  boolean removeElementRemoveListener(ElementRemoveListener<E> listener);
}
