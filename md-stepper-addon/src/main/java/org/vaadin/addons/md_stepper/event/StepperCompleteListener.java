package org.vaadin.addons.md_stepper.event;

import org.vaadin.addons.md_stepper.Stepper;

public interface StepperCompleteListener extends StepperListener {

  void onStepperComplete(StepperCompleteEvent event);

  class StepperCompleteEvent extends StepperEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source
     *     The stepper on which the Event initially occurred.
     *
     * @throws IllegalArgumentException
     *     if source is null.
     */
    public StepperCompleteEvent(Stepper source) {
      super(source);
    }
  }
}
