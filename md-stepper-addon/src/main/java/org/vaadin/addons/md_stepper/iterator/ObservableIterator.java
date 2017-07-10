package org.vaadin.addons.md_stepper.iterator;

import java.util.Iterator;

/**
 * An iterator that provides the possibility to watch its moves.
 *
 * @param <E>
 *     The type of the iterated elements
 */
public interface ObservableIterator<E>
    extends Iterator<E>, StartNotifier<E>, EndNotifier<E>, ElementChangeNotifier<E>,
            PositionableIterator<E>, PositionableIterationNotifier<E>,
            SkippableIterator<E>, SkipIterationNotifier<E> {
}
