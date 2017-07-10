package org.vaadin.addons.md_stepper;

import com.vaadin.server.Resource;
import com.vaadin.shared.MouseEventDetails;

import org.vaadin.addons.md_stepper.collection.ElementAddListener;
import org.vaadin.addons.md_stepper.collection.ElementRemoveListener;
import org.vaadin.addons.md_stepper.component.TextIcon;
import org.vaadin.addons.md_stepper.event.StepperCompleteListener;
import org.vaadin.addons.md_stepper.event.StepperErrorListener;
import org.vaadin.addons.md_stepper.event.StepperFeedbackListener;
import org.vaadin.addons.md_stepper.util.SerializableSupplier;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Provides labels for steps and handles the changes on them.
 */
public class LabelProvider
    implements ElementAddListener<Step>, ElementRemoveListener<Step>,
               StepperErrorListener, StepperFeedbackListener, StepperCompleteListener {

  private final Map<Step, StepLabel> labels;
  private final StepIterator stepIterator;
  private final SerializableSupplier<StepLabel> labelFactory;

  /**
   * Create a new label provider for the given step iterator using the provided label factory.
   *
   * @param stepIterator
   *     The iterator used to iterate over the steps
   * @param labelFactory
   *     The factory to create new labels
   */
  public LabelProvider(StepIterator stepIterator,
                       SerializableSupplier<StepLabel> labelFactory) {
    Objects.requireNonNull(stepIterator, "Step iterator may not be null");
    Objects.requireNonNull(labelFactory, "Label factory may not be null");

    this.labels = new HashMap<>();
    this.stepIterator = stepIterator;
    this.labelFactory = labelFactory;

    this.stepIterator.addElementAddListener(this);
    this.stepIterator.addElementRemoveListener(this);
  }

  /**
   * Update the labels to show the given step as skipped.
   *
   * @param step
   *     The step to show as skipped
   * @param skipped
   *     <code>true</code> if the step should be shown as skipped, <code>false</code> else
   */
  protected void setSkipped(Step step, boolean skipped) {
    Objects.requireNonNull(step, "Step may not be null");

    StepLabel label = getStepLabel(step);
    label.setNexted(false);
    label.setSkipped(skipped);
    label.setEditable(skipped && step.isEditable());
  }

  /**
   * Get the label for the given step.
   *
   * @param step
   *     The step to get the label for
   *
   * @return The label for the step
   */
  public StepLabel getStepLabel(Step step) {
    Objects.requireNonNull(step, "Step may not be null");

    if (!stepIterator.getSteps().contains(step)) {
      throw new NoSuchElementException("No such step");
    }

    if (!labels.containsKey(step)) {
      labels.put(step, buildStepLabel(step));
    }

    return labels.get(step);
  }

  /**
   * Build a new label for the given step.
   *
   * @param step
   *     The step to build the label for
   *
   * @return The label for the step
   */
  protected StepLabel buildStepLabel(Step step) {
    StepLabel stepLabel = labelFactory.get();
    stepLabel.setIcon(buildStepLabelIcon(step));
    stepLabel.setCaption(step.getCaption());
    stepLabel.setDescription(step.getDescription());
    stepLabel.addLayoutClickListener(event -> {
      boolean isLeftClick = event.getButton() == MouseEventDetails.MouseButton.LEFT;
      if (isLeftClick && stepLabel.isClickable()) {
        stepIterator.moveTo(step);
      }
    });
    return stepLabel;
  }

  /**
   * Build the icon for the given step.
   *
   * @param step
   *     The step to build the icon for
   *
   * @return The icon for the step
   */
  protected Resource buildStepLabelIcon(Step step) {
    Resource icon = step.getIcon();
    return icon != null
               ? icon
               : new TextIcon(String.valueOf(stepIterator.getSteps().indexOf(step) + 1));
  }

  /**
   * Update the labels to show the given step as complete.
   *
   * @param step
   *     The step to show as complete
   * @param completed
   *     <code>true</code> if the step should be shown as complete, <code>false</code> else
   */
  protected void setCompleted(Step step, boolean completed) {
    Objects.requireNonNull(step, "Step may not be null");

    StepLabel label = getStepLabel(step);
    label.setSkipped(false);
    label.setNexted(completed);
    label.setEditable(completed && step.isEditable());
  }

  @Override
  public void onStepperError(StepperErrorEvent event) {
    setError(event.getSource().getCurrent(), event.getError());
  }

  /**
   * Update the labels to show the given step as erroneous.
   *
   * @param step
   *     The step to show as erroneous
   * @param error
   *     The error to show
   */
  protected void setError(Step step, Throwable error) {
    Objects.requireNonNull(step, "Step may not be null");
    getStepLabel(step).setError(error);
  }

  @Override
  public void onStepperFeedback(StepperFeedbackEvent event) {
    if (event.getFeedbackMessage() != null) {
      stepIterator.getSteps().forEach(step -> setClickable(step, false));
    }
  }

  /**
   * Set the clickable state of the given steps label.
   *
   * @param step
   *     The step to update the label for
   * @param clickable
   *     The clickable state
   */
  protected void setClickable(Step step, boolean clickable) {
    getStepLabel(step).setClickable(clickable);
  }

  @Override
  public void onStepperComplete(StepperCompleteEvent event) {
    labels.values().forEach(l -> l.setEditable(false));
    setActive(null);
  }

  /**
   * Update the labels to show the given step as active.
   *
   * @param step
   *     The step to show as active
   */
  protected void setActive(Step step) {
    stepIterator.getSteps().forEach(s -> {
      StepLabel stepLabel = getStepLabel(s);
      stepLabel.setActive(false);
      stepLabel.setClickable(step != null && stepIterator.hasMoveTo(s));
    });

    if (step != null) {
      getStepLabel(step).setActive(true);
    }
  }

  @Override
  public void onElementRemove(ElementRemoveEvent<Step> event) {
    labels.remove(event.getElement());
    refresh();
  }

  public void refresh() {
    labels.entrySet().forEach(e -> {
      Step step = e.getKey();
      StepLabel stepLabel = e.getValue();
      stepLabel.setIcon(buildStepLabelIcon(step));
      stepLabel.setCaption(step.getCaption());
      stepLabel.setDescription(step.getDescription());
    });
  }

  @Override
  public void onElementAdd(ElementAddEvent<Step> event) {
    refresh();
  }
}
