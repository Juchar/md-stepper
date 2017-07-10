package org.vaadin.addons.md_stepper.iterator;

/**
 * Element that can be skipped.
 */
public interface SkippableElement {

  /**
   * Get information about whether the element is optional or not.
   *
   * @return <code>true</code> if the element is optional, <code>false</code> else
   */
  boolean isOptional();
}
