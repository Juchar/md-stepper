package org.vaadin.addons.md_stepper;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import org.vaadin.addons.md_stepper.collection.ElementAddListener;
import org.vaadin.addons.md_stepper.collection.ElementRemoveListener;
import org.vaadin.addons.md_stepper.component.CenteredLayout;
import org.vaadin.addons.md_stepper.component.Spacer;
import org.vaadin.addons.md_stepper.component.Spinner;
import org.vaadin.addons.md_stepper.event.StepperCompleteListener;
import org.vaadin.addons.md_stepper.util.SerializableSupplier;

import java.util.ArrayList;
import java.util.List;

/**
 * Stepper implementation that show th steps in a horizontal style.
 */
public class HorizontalStepper extends AbstractStepper
    implements ElementAddListener<Step>, ElementRemoveListener<Step>, StepperCompleteListener {

  public static final float DEFAULT_EXPAND_RATIO_DIVIDER = 0.75F;

  private static final String STYLE_ROOT_LAYOUT = "stepper-horizontal";
  private static final String STYLE_LABEL_BAR = "label-bar";
  private static final String STYLE_DIVIDER = "label-divider";
  private static final String STYLE_FEEDBACK_MESSAGE = "feedback-message";
  private static final String STYLE_CONTENT_CONTAINER = "content-container";
  private static final String STYLE_BUTTON_BAR = "button-bar";

  private final HorizontalLayout labelBar;
  private final HorizontalLayout buttonBar;
  private final Panel stepContent;

  private float dividerExpandRatio;

  /**
   * Create a new horizontal stepper.
   * <p>
   * <b>ATTENTION:</b><br>
   * This constructor is used for the declarative layout!
   */
  public HorizontalStepper() {
    this(new ArrayList<>());
  }

  /**
   * Create a new linear, horizontal stepper for the given steps using a {@link StepIterator}.
   *
   * @param steps
   *     The steps to show
   */
  public HorizontalStepper(List<Step> steps) {
    this(steps, true);
  }

  /**
   * Create a new horizontal stepper for the given steps using a {@link StepIterator}.
   *
   * @param steps
   *     The steps to show
   * @param linear
   *     <code>true</code> if the state rule should be linear, <code>false</code> else
   */
  public HorizontalStepper(List<Step> steps, boolean linear) {
    this(new StepIterator(steps, linear), StepLabel::new);
  }

  /**
   * Create a new horizontal stepper using the given iterator.
   *
   * @param stepIterator
   *     The iterator that handles the iteration over the given steps
   * @param labelFactory
   *     The label factory used to create step labels
   */
  private HorizontalStepper(StepIterator stepIterator,
                            SerializableSupplier<StepLabel> labelFactory) {
    this(stepIterator, new LabelProvider(stepIterator, labelFactory));
  }

  /**
   * Create a new horizontal stepper using the given iterator and label change handler.
   *
   * @param stepIterator
   *     The iterator that handles the iteration over the given steps
   * @param labelProvider
   *     The handler that handles changes to labels
   */
  private HorizontalStepper(StepIterator stepIterator, LabelProvider labelProvider) {
    super(stepIterator, labelProvider);

    addStepperCompleteListener(this);
    getStepIterator().addElementAddListener(this);
    getStepIterator().addElementRemoveListener(this);

    this.labelBar = new HorizontalLayout();
    this.labelBar.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
    this.labelBar.setWidth(100, Unit.PERCENTAGE);
    this.labelBar.addStyleName(STYLE_LABEL_BAR);
    this.labelBar.setMargin(false);
    this.labelBar.setSpacing(false);

    this.buttonBar = new HorizontalLayout();
    this.buttonBar.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
    this.buttonBar.addStyleName(STYLE_BUTTON_BAR);
    this.buttonBar.setWidth(100, Unit.PERCENTAGE);
    this.buttonBar.setMargin(false);
    this.buttonBar.setSpacing(true);

    this.stepContent = new Panel();
    this.stepContent.addStyleName(ValoTheme.PANEL_BORDERLESS);
    this.stepContent.addStyleName(STYLE_CONTENT_CONTAINER);
    this.stepContent.setSizeFull();

    this.dividerExpandRatio = DEFAULT_EXPAND_RATIO_DIVIDER;

    VerticalLayout rootLayout = new VerticalLayout();
    rootLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
    rootLayout.setSizeFull();
    rootLayout.setMargin(false);
    rootLayout.setSpacing(true);
    rootLayout.addComponent(labelBar);
    rootLayout.addComponent(stepContent);
    rootLayout.addComponent(buttonBar);
    rootLayout.setExpandRatio(stepContent, 1);

    setCompositionRoot(rootLayout);
    addStyleName(STYLE_ROOT_LAYOUT);
    setSizeFull();
    refreshLabelBar();
  }

  private void refreshLabelBar() {
    labelBar.removeAllComponents();

    List<Step> steps = getSteps();
    for (int i = 0; i < steps.size(); i++) {
      addStepLabel(steps.get(i));
      if (i < steps.size() - 1) {
        addStepLabelDivider();
      }
    }

    labelBar.iterator().forEachRemaining(c -> c.setWidth(100, Unit.PERCENTAGE));
  }

  private void addStepLabel(Step step) {
    Component stepLabel = getLabelProvider().getStepLabel(step);

    labelBar.addComponent(stepLabel);
    labelBar.setExpandRatio(stepLabel, 1);
  }

  private void addStepLabelDivider() {
    CssLayout divider = new CssLayout();
    divider.addStyleName(STYLE_DIVIDER);

    labelBar.addComponent(divider);
    labelBar.setExpandRatio(divider, getDividerExpandRatio());
  }

  /**
   * Get the expand ratio for the divider between the labels.
   *
   * @return The expand ratio
   */
  public float getDividerExpandRatio() {
    return dividerExpandRatio;
  }

  /**
   * Set the expand ratio of the dividers between the labels (labels have an expand ratio of
   * <code><b>1</b></code>).
   *
   * @param dividerExpandRatio
   *     The expand ratio for the dividers
   */
  public void setDividerExpandRatio(float dividerExpandRatio) {
    this.dividerExpandRatio = dividerExpandRatio;
    refreshLabelBar();
  }

  /**
   * Create a new linear, horizontal stepper for the given steps using a {@link StepIterator}.
   *
   * @param steps
   *     The steps to show
   * @param labelFactory
   *     The factory used to create new labels for the steps
   */
  public HorizontalStepper(List<Step> steps, SerializableSupplier<StepLabel> labelFactory) {
    this(steps, true, labelFactory);
  }

  /**
   * Create a new horizontal stepper for the given steps using a {@link StepIterator}.
   *
   * @param steps
   *     The steps to show
   * @param linear
   *     <code>true</code> if the state rule should be linear, <code>false</code> else
   * @param labelFactory
   *     The factory used to create new labels for the steps
   */
  public HorizontalStepper(List<Step> steps, boolean linear,
                           SerializableSupplier<StepLabel> labelFactory) {
    this(new StepIterator(steps, linear), labelFactory);
  }

  @Override
  public void onStepperComplete(StepperCompleteEvent event) {
    buttonBar.forEach(b -> b.setVisible(false));
  }

  @Override
  public void onElementAdd(ElementAddEvent<Step> event) {
    refresh();
  }

  @Override
  public void refresh() {
    super.refresh();
    refreshLabelBar();
    setActive(getCurrent(), getCurrent(), false);
  }

  @Override
  public void showFeedbackMessage(String message) {
    super.showFeedbackMessage(message);

    if (message == null) {
      refreshLabelBar();
      setActive(getCurrent(), getCurrent(), false);
    } else {
      buttonBar.forEach(button -> button.setVisible(false));
      showTransitionLabel(message);
      showSpinner();
    }
  }

  private void showTransitionLabel(String message) {
    Label feedbackLabel = new Label(message);
    feedbackLabel.addStyleName(STYLE_FEEDBACK_MESSAGE);

    labelBar.removeAllComponents();
    labelBar.addComponent(feedbackLabel);
  }

  private void showSpinner() {
    stepContent.setContent(new CenteredLayout(new Spinner()));
  }

  @Override
  protected void setActive(Step step, Step previousStep, boolean fireEvent) {
    stepContent.setContent(step.getContent());
    refreshButtonBar(step);

    super.setActive(step, previousStep, fireEvent);
  }

  private void refreshButtonBar(Step step) {
    buttonBar.removeAllComponents();
    buttonBar.setVisible(true);

    if (step == null) {
      return;
    }

    Button backButton = step.getBackButton();
    Button cancelButton = step.getCancelButton();
    Button skipButton = step.getSkipButton();
    Button nextButton = step.getNextButton();

    backButton.setVisible(getStepIterator().hasPrevious());
    cancelButton.setVisible(step.isCancellable());
    skipButton.setVisible(step.isOptional());
    nextButton.setVisible(!isComplete());

    buttonBar.addComponent(backButton);
    Spacer.addToLayout(buttonBar);
    buttonBar.addComponent(cancelButton);
    buttonBar.addComponent(skipButton);
    buttonBar.addComponent(nextButton);
  }

  @Override
  public void onElementRemove(ElementRemoveEvent<Step> event) {
    refresh();
  }

  /**
   * Styles for the horizontal stepper.
   */
  public static final class Styles {

    /**
     * Show the stepper in a borderless style.
     */
    public static final String STEPPER_BORDERLESS = "borderless";
    /**
     * Do not show the divider between the label bar and the content
     */
    public static final String STEPPER_NO_DIVIDER = "no-divider";

    private Styles() {
      // Prevent instantiation
    }
  }
}
