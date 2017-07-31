package org.vaadin.addons.md_stepper;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Stepper implementation that shows the steps in a vertical style.
 */
public class VerticalStepper extends AbstractStepper
    implements ElementAddListener<Step>, ElementRemoveListener<Step>, StepperCompleteListener {

  private static final String STYLE_ROOT_LAYOUT = "stepper-vertical";

  private final VerticalLayout rootLayout;
  private final Map<Step, RowLayout> rowMap;

  private Spacer spacer;

  /**
   * Create a new linear, vertical stepper for the given steps using a {@link StepIterator}.
   *
   * @param steps
   *     The steps to show
   */
  public VerticalStepper(List<Step> steps) {
    this(steps, true);
  }

  /**
   * Create a new vertical stepper for the given steps using a {@link StepIterator}.
   *
   * @param steps
   *     The steps to show
   * @param linear
   *     <code>true</code> if the state rule should be linear, <code>false</code> else
   */
  public VerticalStepper(List<Step> steps, boolean linear) {
    this(new StepIterator(steps, linear), StepLabel::new);
  }

  /**
   * Create a new vertical stepper fusing the given iterator.
   *
   * @param stepIterator
   *     The iterator that handles the iteration over the given steps
   * @param labelFactory
   *     The label factory to build step labels
   */
  private VerticalStepper(StepIterator stepIterator, SerializableSupplier<StepLabel> labelFactory) {
    this(stepIterator, new LabelProvider(stepIterator, labelFactory));
  }

  /**
   * Create a new vertical stepper using the given iterator and label change handler.
   *
   * @param stepIterator
   *     The iterator that handles the iteration over the given steps
   * @param labelProvider
   *     The handler that handles changes to labels
   */
  private VerticalStepper(StepIterator stepIterator, LabelProvider labelProvider) {
    super(stepIterator, labelProvider);

    addStepperCompleteListener(this);
    getStepIterator().addElementAddListener(this);
    getStepIterator().addElementRemoveListener(this);

    this.rowMap = new HashMap<>();

    this.rootLayout = new VerticalLayout();
    this.rootLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
    this.rootLayout.setSizeFull();

    setCompositionRoot(rootLayout);
    addStyleName(STYLE_ROOT_LAYOUT);
    setSizeFull();
    refreshLayout();
  }

  private void refreshLayout() {
    rootLayout.removeAllComponents();
    rowMap.clear();

    List<Step> steps = getSteps();
    for (Step singleStep : steps) {
      RowLayout layout = new RowLayout(singleStep);
      rowMap.put(singleStep, layout);
      rootLayout.addComponent(layout);
    }

    spacer = Spacer.addToLayout(rootLayout);
  }

  /**
   * Create a new linear, vertical stepper for the given steps using a {@link StepIterator}.
   *
   * @param steps
   *     The steps to show
   * @param labelFactory
   *     The factory used to create new labels for the steps
   */
  public VerticalStepper(List<Step> steps, SerializableSupplier<StepLabel> labelFactory) {
    this(steps, true, labelFactory);
  }

  /**
   * Create a new vertical stepper for the given steps using a {@link StepIterator}.
   *
   * @param steps
   *     The steps to show
   * @param linear
   *     <code>true</code> if the state rule should be linear, <code>false</code> else
   * @param labelFactory
   *     The factory used to create new labels for the steps
   */
  public VerticalStepper(List<Step> steps, boolean linear,
                         SerializableSupplier<StepLabel> labelFactory) {
    this(new StepIterator(steps, linear), labelFactory);
  }

  @Override
  public void onStepperComplete(StepperCompleteEvent event) {
    rowMap.values().forEach(r -> r.onStepperComplete(event));
  }

  @Override
  public void onElementAdd(ElementAddEvent<Step> event) {
    refresh();
  }

  @Override
  public void refresh() {
    super.refresh();
    refreshLayout();
    setActive(getCurrent(), getCurrent(), false);
  }

  @Override
  public void showFeedbackMessage(String message) {
    super.showFeedbackMessage(message);

    if (message == null) {
      setActive(getCurrent(), getCurrent(), false);
    }

    rowMap.values()
          .stream()
          .filter(RowLayout::isActive)
          .findFirst()
          .ifPresent(layout -> layout.showTransitionMessage(message));
  }

  @Override
  protected void setActive(Step step, Step previousStep, boolean fireEvent) {
    if (spacer != null) {
      rootLayout.setExpandRatio(spacer, step != null ? 0 : 1);
    }

    rowMap.entrySet().forEach(entry -> {
      RowLayout layout = entry.getValue();
      if (Objects.equals(entry.getKey(), step)) {
        layout.setActive(true);
        layout.setHeight(100, Unit.PERCENTAGE);
        rootLayout.setExpandRatio(layout, 1);
      } else {
        layout.setActive(false);
        layout.setHeightUndefined();
        rootLayout.setExpandRatio(layout, 0);
      }
    });

    super.setActive(step, previousStep, fireEvent);
  }

  @Override
  public void onElementRemove(ElementRemoveEvent<Step> event) {
    refresh();
  }

  /**
   * Styles for the vertical stepper.
   */
  public static final class Styles {

    /**
     * Show the stepper in a borderless style.
     */
    public static final String STEPPER_BORDERLESS = "borderless";

    private Styles() {
      // Prevent instantiation
    }
  }

  private class RowLayout extends CustomComponent implements StepperCompleteListener {

    private static final String STYLE_COMPONENT = "stepper-vertical-row";
    private static final String STYLE_DIVIDER = "label-divider";
    private static final String STYLE_CONTENT_CONTAINER = "content-container";
    private static final String STYLE_BUTTON_BAR = "button-bar";
    private static final String STYLE_FEEDBACK_MESSAGE = "feedback-message";

    private final GridLayout rootLayout;
    private final StepLabel label;
    private final CssLayout divider;
    private final Panel contentContainer;
    private final HorizontalLayout buttonBar;

    private final Step step;

    private boolean active;

    private RowLayout(Step step) {
      this.step = step;

      label = getLabelProvider().getStepLabel(step);

      divider = new CssLayout();
      divider.addStyleName(STYLE_DIVIDER);
      divider.setHeight(100, Unit.PERCENTAGE);

      contentContainer = new Panel();
      contentContainer.addStyleName(STYLE_CONTENT_CONTAINER);
      contentContainer.addStyleName(ValoTheme.PANEL_BORDERLESS);
      contentContainer.setSizeFull();

      buttonBar = new HorizontalLayout();
      buttonBar.addStyleName(STYLE_BUTTON_BAR);
      buttonBar.setSpacing(true);
      buttonBar.setWidth(100, Unit.PERCENTAGE);
      buttonBar.setMargin(new MarginInfo(false, false, !isLastStep(step), false));

      rootLayout = new GridLayout(2, 3);
      rootLayout.setSizeFull();
      rootLayout.setColumnExpandRatio(1, 1);
      rootLayout.setRowExpandRatio(1, 1);
      rootLayout.addComponent(label, 0, 0, 1, 0);
      rootLayout.addComponent(divider, 0, 1, 0, 2);
      rootLayout.addComponent(contentContainer, 1, 1, 1, 1);
      rootLayout.addComponent(buttonBar, 1, 2, 1, 2);

      setCompositionRoot(rootLayout);
      addStyleName(STYLE_COMPONENT);
      setWidth(100, Unit.PERCENTAGE);
      setActive(false);
    }

    private boolean isLastStep(Step step) {
      return getSteps().indexOf(step) == getSteps().size() - 1;
    }

    public boolean isActive() {
      return active;
    }

    public void setActive(boolean active) {
      this.active = active;

      buttonBar.setVisible(active);
      contentContainer.setVisible(active);

      buttonBar.removeAllComponents();
      contentContainer.setContent(null);

      divider.setHeight(isLastStep(step) ? 0 : -1, Unit.PIXELS);

      if (!active) {
        return;
      }

      if (!isLastStep(step)) {
        divider.setHeight(100, Unit.PERCENTAGE);
      }
      contentContainer.setContent(step.getContent());

      Button nextButton = step.getNextButton();
      Button skipButton = step.getSkipButton();
      Button cancelButton = step.getCancelButton();
      Button backButton = step.getBackButton();

      buttonBar.addComponent(nextButton);
      buttonBar.addComponent(skipButton);
      buttonBar.addComponent(cancelButton);
      Spacer.addToLayout(buttonBar);
      buttonBar.addComponent(backButton);

      nextButton.setVisible(!isComplete());
      cancelButton.setVisible(step.isCancellable());
      skipButton.setVisible(step.isOptional());
      backButton.setVisible(getStepIterator().hasPrevious());
    }

    public void showTransitionMessage(String message) {
      if (rootLayout.getComponent(1, 0) != null) {
        rootLayout.removeComponent(1, 0);
      }

      if (rootLayout.getComponent(0, 0) != null) {
        rootLayout.removeComponent(0, 0);
      }

      label.setCaptionVisible(message == null);
      label.setDescriptionVisible(message == null);

      if (message != null) {
        Label feedbackLabel = new Label(message);
        feedbackLabel.addStyleName(STYLE_FEEDBACK_MESSAGE);
        feedbackLabel.setWidth(100, Unit.PERCENTAGE);

        rootLayout.addComponent(label, 0, 0);
        rootLayout.addComponent(feedbackLabel, 1, 0);
        rootLayout.setComponentAlignment(feedbackLabel, Alignment.MIDDLE_LEFT);
        contentContainer.setContent(new CenteredLayout(new Spinner()));
        buttonBar.forEach(c -> c.setVisible(false));
      } else {
        rootLayout.addComponent(label, 0, 0, 1, 0);
        contentContainer.setContent(step);
      }
    }

    @Override
    public void onStepperComplete(StepperCompleteEvent event) {
      buttonBar.forEach(b -> b.setVisible(false));
    }
  }
}
