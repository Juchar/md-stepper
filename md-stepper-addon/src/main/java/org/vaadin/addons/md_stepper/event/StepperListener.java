package org.vaadin.addons.md_stepper.event;

import org.vaadin.addons.md_stepper.Stepper;

import java.io.Serializable;
import java.util.EventListener;
import java.util.EventObject;

/**
 * Base listener for all events regarding steppers.
 */
public interface StepperListener extends EventListener, Serializable {

  /**
   * Base event class for all events related to steppers.
   */
  class StepperEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source
     *     The stepper on which the Event initially occurred.
     *
     * @throws IllegalArgumentException
     *     if source is null.
     */
    public StepperEvent(Stepper source) {
      super(source);
    }

    /**
     * Get the stepper that produced the event.
     *
     * @return The stepper
     */
    @Override
    public Stepper getSource() {
      return (Stepper) super.getSource();
    }
  }
}
