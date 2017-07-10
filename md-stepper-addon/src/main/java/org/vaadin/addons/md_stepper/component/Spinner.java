package org.vaadin.addons.md_stepper.component;

import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Convenience component to create a spinner.
 */
public class Spinner extends Label {

  /**
   * Create a new spinner.
   */
  public Spinner() {
    addStyleName(ValoTheme.LABEL_SPINNER);
    setSizeUndefined();
  }
}
