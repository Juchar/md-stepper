package org.vaadin.addons.md_stepper;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import org.vaadin.addons.md_stepper.event.StepActiveListener;
import org.vaadin.addons.md_stepper.event.StepBackListener;
import org.vaadin.addons.md_stepper.event.StepCancelListener;
import org.vaadin.addons.md_stepper.event.StepCompleteListener;
import org.vaadin.addons.md_stepper.event.StepNextListener;
import org.vaadin.addons.md_stepper.event.StepSkipListener;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Builder that conveniently allows to build steps.
 */
public final class StepBuilder {

  private final Collection<StepActiveListener> stepActiveListeners;
  private final Collection<StepCompleteListener> stepCompleteListeners;
  private final Collection<StepBackListener> stepBackListeners;
  private final Collection<StepNextListener> stepNextListeners;
  private final Collection<StepSkipListener> stepSkipListeners;
  private final Collection<StepCancelListener> stepCancelListeners;

  private Resource icon;
  private String caption;
  private String description;
  private Component content;

  private boolean optional;
  private boolean editable;
  private boolean cancellable;
  private boolean resetOnResubmit;
  private boolean defaultActions;

  private Button backButton;
  private Button nextButton;
  private Button skipButton;
  private Button cancelButton;

  public StepBuilder() {
    this.stepActiveListeners = new HashSet<>();
    this.stepCompleteListeners = new HashSet<>();
    this.stepBackListeners = new HashSet<>();
    this.stepNextListeners = new HashSet<>();
    this.stepSkipListeners = new HashSet<>();
    this.stepCancelListeners = new HashSet<>();
  }

  public StepBuilder withCaption(String caption) {
    this.caption = caption;
    return this;
  }

  public StepBuilder withDescription(String description) {
    this.description = description;
    return this;
  }

  public StepBuilder withContent(Component content) {
    this.content = content;
    return this;
  }

  public StepBuilder withIcon(Resource icon) {
    this.icon = icon;
    return this;
  }

  public StepBuilder withOptional(boolean optional) {
    this.optional = optional;
    return this;
  }

  public StepBuilder withEditable(boolean editable) {
    this.editable = editable;
    return this;
  }

  public StepBuilder withResetOnResubmit(boolean resetOnResubmit) {
    this.resetOnResubmit = resetOnResubmit;
    return this;
  }

  public StepBuilder withCancellable(boolean cancellable) {
    this.cancellable = cancellable;
    return this;
  }

  public StepBuilder withBackButton(Button backButton) {
    this.backButton = backButton;
    return this;
  }

  public StepBuilder withNextButton(Button nextButton) {
    this.nextButton = nextButton;
    return this;
  }

  public StepBuilder withSkipButton(Button skipButton) {
    this.skipButton = skipButton;
    return this;
  }

  public StepBuilder withCancelButton(Button cancelButton) {
    this.cancelButton = cancelButton;
    return this;
  }

  public StepBuilder withDefaultActions(boolean defaultActions) {
    this.defaultActions = defaultActions;
    return this;
  }

  public StepBuilder withStepCompleteListener(StepCompleteListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    stepCompleteListeners.add(listener);
    return this;
  }

  public StepBuilder withStepBackListener(StepBackListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    stepBackListeners.add(listener);
    return this;
  }

  public StepBuilder withStepNextListener(StepNextListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    stepNextListeners.add(listener);
    return this;
  }

  public StepBuilder withStepSkipListener(StepSkipListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    stepSkipListeners.add(listener);
    return this;
  }

  public StepBuilder withStepCancelListener(StepCancelListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    stepCancelListeners.add(listener);
    return this;
  }

  public StepBuilder withStepActiveListener(StepActiveListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    stepActiveListeners.add(listener);
    return this;
  }

  public Step build() {
    Step step = new Step();

    step.setCaption(caption);
    step.setDescription(description);
    step.setContent(content);
    step.setIcon(icon);
    step.setOptional(optional);
    step.setEditable(editable);
    step.setResetOnResubmit(resetOnResubmit);
    step.setCancellable(cancellable);
    step.setBackButton(backButton);
    step.setNextButton(nextButton);
    step.setSkipButton(skipButton);
    step.setCancelButton(cancelButton);
    step.setDefaultActions(defaultActions);

    stepActiveListeners.forEach(step::addStepActiveListener);
    stepCompleteListeners.forEach(step::addStepCompleteListener);
    stepBackListeners.forEach(step::addStepBackListener);
    stepNextListeners.forEach(step::addStepNextListener);
    stepSkipListeners.forEach(step::addStepSkipListener);
    stepCancelListeners.forEach(step::addStepCancelListener);

    return step;
  }
}
