package org.vaadin.addons.md_stepper;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.declarative.DesignAttributeHandler;
import com.vaadin.ui.declarative.DesignContext;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.vaadin.addons.md_stepper.event.StepperCompleteListener;
import org.vaadin.addons.md_stepper.event.StepperCompleteListener.StepperCompleteEvent;
import org.vaadin.addons.md_stepper.event.StepperErrorListener;
import org.vaadin.addons.md_stepper.event.StepperErrorListener.StepperErrorEvent;
import org.vaadin.addons.md_stepper.event.StepperFeedbackListener;
import org.vaadin.addons.md_stepper.event.StepperFeedbackListener.StepperFeedbackEvent;
import org.vaadin.addons.md_stepper.event.StepperNotifier;
import org.vaadin.addons.md_stepper.iterator.ElementChangeListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Abstract base class for stepper implementations.
 * <p>
 * This class takes care about handling element changes of the iterator and updating the labels
 * accordingly.
 */
public abstract class AbstractStepper extends CustomComponent
    implements Stepper, StepperNotifier, ElementChangeListener<Step> {

  private static final String DESIGN_ATTRIBUTE_LINEAR = "linear";

  private final Collection<StepperCompleteListener> stepperCompleteListeners;
  private final Collection<StepperErrorListener> stepperErrorListeners;
  private final Collection<StepperFeedbackListener> stepperFeedbackListeners;

  private final StepIterator stepIterator;
  private final LabelProvider labelProvider;
  private final Map<Step, Throwable> errorMap;

  private final Button.ClickListener onBackClicked;
  private final Button.ClickListener onNextClicked;
  private final Button.ClickListener onSkipClicked;
  private final Button.ClickListener onCancelClicked;

  private String feedbackMessage;

  /**
   * Construct a new instance of the stepper.
   *
   * @param stepIterator
   *     The iterator that handles the iteration over the steps
   * @param labelProvider
   *     The handler that handles changes to labels
   */
  protected AbstractStepper(StepIterator stepIterator, LabelProvider labelProvider) {
    Objects.requireNonNull(stepIterator, "Step iterator may not be null");
    Objects.requireNonNull(labelProvider, "Label provider may not be null");

    this.stepperCompleteListeners = new HashSet<>();
    this.stepperErrorListeners = new HashSet<>();
    this.stepperFeedbackListeners = new HashSet<>();
    this.errorMap = new HashMap<>();
    this.labelProvider = labelProvider;

    this.onBackClicked = e -> getCurrent().notifyBack(this);
    this.onNextClicked = e -> getCurrent().notifyNext(this);
    this.onSkipClicked = e -> getCurrent().notifySkip(this);
    this.onCancelClicked = e -> getCurrent().notifyCancel(this);

    addStepperCompleteListener(labelProvider);
    addStepperErrorListener(labelProvider);
    addStepperFeedbackListener(labelProvider);

    this.stepIterator = stepIterator;
    this.stepIterator.addElementChangeListener(this);
  }

  @Override
  public boolean addStepperCompleteListener(StepperCompleteListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepperCompleteListeners.add(listener);
  }

  @Override
  public boolean removeStepperCompleteListener(StepperCompleteListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepperCompleteListeners.remove(listener);
  }

  @Override
  public boolean addStepperErrorListener(StepperErrorListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepperErrorListeners.add(listener);
  }

  @Override
  public boolean removeStepperErrorListener(StepperErrorListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepperErrorListeners.remove(listener);
  }

  @Override
  public boolean addStepperFeedbackListener(StepperFeedbackListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepperFeedbackListeners.add(listener);
  }

  @Override
  public boolean removeStepperFeedbackListener(StepperFeedbackListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepperFeedbackListeners.remove(listener);
  }

  @Override
  public void refresh() {
    labelProvider.refresh();
  }

  @Override
  public List<Step> getSteps() {
    return stepIterator.getSteps();
  }

  @Override
  public Step getCurrent() {
    return stepIterator.getCurrent();
  }

  @Override
  public boolean isComplete() {
    return stepIterator.isComplete();
  }

  @Override
  public boolean isStepComplete(Step step) {
    return stepIterator.isStepComplete(step);
  }

  @Override
  public void start() {
    stepIterator.moveTo(null);
    stepIterator.next();
  }

  @Override
  public void back() {
    Objects.requireNonNull(getCurrent(), "No current step specified");

    if (stepIterator.hasPrevious()) {
      stepIterator.previous();
    } else {
      notifyStepperComplete();
    }
  }

  @Override
  public void next() {
    Step current = getCurrent();
    Objects.requireNonNull(current, "No current step specified");

    labelProvider.setCompleted(current, true);
    current.notifyComplete(this);

    resetStepsIfNeeded(current);

    if (stepIterator.hasNext()) {
      stepIterator.next();
    } else {
      notifyStepperComplete();
    }
  }

  private void resetStepsIfNeeded(Step current) {
    if (stepIterator.isLinear() && current.isResetOnResubmit()) {
      List<Step> steps = stepIterator.getSteps();
      steps.stream()
           .skip(steps.indexOf(current) + 1)
           .filter(stepIterator::isStepComplete)
           .forEach(step -> {
             labelProvider.setCompleted(step, false);
             step.notifyReset(this);
           });
    }
  }

  @Override
  public void skip() {
    Step current = getCurrent();
    Objects.requireNonNull(current, "No current step specified");

    labelProvider.setSkipped(current, true);
    current.notifyComplete(this);

    resetStepsIfNeeded(current);

    if (stepIterator.hasSkip()) {
      stepIterator.skip();
    } else {
      notifyStepperComplete();
    }
  }

  @Override
  public void showError(Throwable throwable) {
    showError(getCurrent(), throwable);
  }

  @Override
  public void showError(Step step, Throwable throwable) {
    Objects.requireNonNull(step, "No current step specified");

    if (throwable != null) {
      errorMap.put(step, throwable);
    } else {
      errorMap.remove(step);
    }

    notifyStepperError(step, throwable);
  }

  @Override
  public void hideError() {
    hideError(getCurrent());
  }

  @Override
  public void hideError(Step step) {
    showError(step, null);
  }

  @Override
  public Throwable getError() {
    return getError(getCurrent());
  }

  @Override
  public Throwable getError(Step step) {
    return errorMap.get(step);
  }

  @Override
  public void showFeedbackMessage(String message) {
    feedbackMessage = message;

    notifyStepperFeedback(message);
  }

  @Override
  public void hideFeedbackMessage() {
    showFeedbackMessage(null);
  }

  private void notifyStepperFeedback(String message) {
    StepperFeedbackEvent feedbackEvent = new StepperFeedbackEvent(this, message);
    stepperFeedbackListeners.forEach(l -> l.onStepperFeedback(feedbackEvent));
  }

  @Override
  public String getFeedbackMessage() {
    return feedbackMessage;
  }

  private void notifyStepperError(Step step, Throwable throwable) {
    StepperErrorEvent errorEvent = new StepperErrorEvent(this, step, throwable);
    stepperErrorListeners.forEach(l -> l.onStepperError(errorEvent));
  }

  private void notifyStepperComplete() {
    StepperCompleteEvent stepperCompleteEvent = new StepperCompleteEvent(this);
    stepperCompleteListeners.forEach(l -> l.onStepperComplete(stepperCompleteEvent));
  }

  /**
   * Get the step iterator of this stepper.
   *
   * @return the step iterator
   */
  protected StepIterator getStepIterator() {
    return stepIterator;
  }

  /**
   * Get the label provider that handles changes to labels.
   *
   * @return The provider
   */
  protected LabelProvider getLabelProvider() {
    return labelProvider;
  }

  @Override
  public void onElementChange(IterationEvent<Step> event) {
    setActive(event.getCurrent(), event.getPrevious());
  }

  /**
   * Set the given step to be the active step.
   *
   * @param step
   *     The step to be active
   */
  protected void setActive(Step step, Step previousStep) {
    setActive(step, previousStep, true);
  }

  /**
   * Set the given step to be the active step.
   *
   * @param step
   *     The step to be active
   * @param fireEvent
   *     <code>true</code> if an event should be fired, <code>false</code> else
   */
  protected void setActive(Step step, Step previousStep, boolean fireEvent) {
    if (step != null) {
      step.getBackButton().addClickListener(onBackClicked);
      step.getNextButton().addClickListener(onNextClicked);
      step.getSkipButton().addClickListener(onSkipClicked);
      step.getCancelButton().addClickListener(onCancelClicked);
      labelProvider.setActive(step);

      if (fireEvent) {
        step.notifyActive(this, previousStep);
      }
    }
  }

  @Override
  public void readDesign(Element design, DesignContext designContext) {
    super.readDesign(design, designContext);

    for (Element child : design.children()) {
      Component childComponent = designContext.readDesign(child);
      if (!(childComponent instanceof Step)) {
        throw new IllegalArgumentException("Only implementations of " + Step.class.getName() +
                                           " are allowed as children of " + getClass().getName());
      }

      stepIterator.add(((Step) childComponent));
    }

    boolean linear = false;

    Attributes attributes = design.attributes();
    if (attributes.hasKey(DESIGN_ATTRIBUTE_LINEAR)) {
      linear = DesignAttributeHandler.getFormatter()
                                     .parse(design.attr(DESIGN_ATTRIBUTE_LINEAR), Boolean.class);
    }

    stepIterator.setLinear(linear);
  }

  @Override
  public void writeDesign(Element design, DesignContext designContext) {
    super.writeDesign(design, designContext);
    design.attr(DESIGN_ATTRIBUTE_LINEAR, stepIterator.isLinear());

    List<Step> steps = stepIterator.getSteps();
    for (Step step : steps) {
      Element childElement = designContext.createElement(step);
      design.appendChild(childElement);
    }
  }

  /**
   * Styles for the stepper.
   */
  public static final class Styles {

    /**
     * Show square icons on the labels.
     */
    public static final String LABEL_ICONS_SQUARE = "label-icons-square";
    /**
     * Show circle icons on the labels.
     */
    public static final String LABEL_ICONS_CIRCULAR = "label-icons-circular";

    private Styles() {
      // Prevent instantiation
    }
  }
}
