package org.vaadin.addons.md_stepper.component;

import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Label;

import java.util.Objects;

/**
 * Convenience component that represents an expandable spacer usable inside ordered layouts.
 */
public class Spacer extends Label {

  public Spacer() {
    setSizeFull();
  }

  /**
   * Add a spacer to the given layout that will expand automatically.
   *
   * @param layout
   *     The layout to add the spacer to
   */
  public static Spacer addToLayout(AbstractOrderedLayout layout) {
    Objects.requireNonNull(layout, "Layout may not be null");

    Spacer spacer = new Spacer();
    layout.addComponent(spacer);
    layout.setExpandRatio(spacer, 1);
    return spacer;
  }
}
