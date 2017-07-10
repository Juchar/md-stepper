package org.vaadin.addons.md_stepper.util;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Serializable supplier implementation.
 *
 * @param <T>
 *     the type of results supplied by this supplier
 */
@FunctionalInterface
public interface SerializableSupplier<T> extends Supplier<T>, Serializable {
}
