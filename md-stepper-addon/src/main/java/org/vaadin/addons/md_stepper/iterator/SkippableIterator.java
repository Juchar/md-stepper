package org.vaadin.addons.md_stepper.iterator;

import java.util.Iterator;

public interface SkippableIterator<E> extends Iterator<E> {

  boolean hasSkip();

  E skip();

}
