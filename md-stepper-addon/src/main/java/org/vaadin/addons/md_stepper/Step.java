package org.vaadin.addons.md_stepper;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.declarative.DesignContext;
import com.vaadin.ui.themes.ValoTheme;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.vaadin.addons.md_stepper.event.StepActiveListener;
import org.vaadin.addons.md_stepper.event.StepActiveListener.StepActiveEvent;
import org.vaadin.addons.md_stepper.event.StepBackListener;
import org.vaadin.addons.md_stepper.event.StepBackListener.StepBackEvent;
import org.vaadin.addons.md_stepper.event.StepCancelListener;
import org.vaadin.addons.md_stepper.event.StepCancelListener.StepCancelEvent;
import org.vaadin.addons.md_stepper.event.StepCompleteListener;
import org.vaadin.addons.md_stepper.event.StepCompleteListener.StepCompleteEvent;
import org.vaadin.addons.md_stepper.event.StepNextListener;
import org.vaadin.addons.md_stepper.event.StepNextListener.StepNextEvent;
import org.vaadin.addons.md_stepper.event.StepNotifier;
import org.vaadin.addons.md_stepper.event.StepSkipListener;
import org.vaadin.addons.md_stepper.event.StepSkipListener.StepSkipEvent;
import org.vaadin.addons.md_stepper.event.StepperActions;
import org.vaadin.addons.md_stepper.event.StepperListener;
import org.vaadin.addons.md_stepper.iterator.SkippableElement;
import org.vaadin.addons.md_stepper.state.StatefulElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Step extends CustomComponent
    implements StepNotifier, StatefulElement, SkippableElement {

  private static final String DESIGN_ATTRIBUTE_STEP_ACTION = "step-action";
  private static final String DESIGN_TAG_CONTENT = "content";
  private static final String DESIGN_TAG_BUTTONS = "buttons";
  private static final List<String> ALLOWED_CHILDREN = Arrays.asList(DESIGN_TAG_CONTENT,
                                                                     DESIGN_TAG_BUTTONS);

  private final Collection<StepActiveListener> stepActiveListeners;
  private final Collection<StepCompleteListener> stepCompleteListeners;
  private final Collection<StepBackListener> stepBackListeners;
  private final Collection<StepNextListener> stepNextListeners;
  private final Collection<StepSkipListener> stepSkipListeners;
  private final Collection<StepCancelListener> stepCancelListeners;

  private final StepBackListener stepperActionBack;
  private final StepNextListener stepperActionNext;
  private final StepSkipListener stepperActionSkip;

  private String caption;
  private String description;
  private Component content;
  private Resource icon;

  private boolean optional;
  private boolean editable;
  private boolean cancellable;

  private Button backButton;
  private Button nextButton;
  private Button skipButton;
  private Button cancelButton;

  /**
   * Create a new step.
   */
  public Step() {
    this(false);
  }

  /**
   * Create a new step and add the default actions if requested.
   * <p>
   * Default actions are:
   * <ul> <li>call {@link StepperActions#next(StepperListener.StepperEvent)} if a {@link
   * StepNextEvent} occurs</li> <li>call {@link StepperActions#skip(StepperListener.StepperEvent)}
   * if a {@link StepSkipEvent} occurs</li> <li>call
   * {@link StepperActions#back(StepperListener.StepperEvent)}
   * if a {@link StepBackEvent} occurs</li> </ul>
   *
   * @param defaultActions
   *     <code>true</code> if the default actions should be added, <code>false</code> else
   */
  public Step(boolean defaultActions) {
    this.stepActiveListeners = new HashSet<>();
    this.stepCompleteListeners = new HashSet<>();
    this.stepBackListeners = new HashSet<>();
    this.stepNextListeners = new HashSet<>();
    this.stepSkipListeners = new HashSet<>();
    this.stepCancelListeners = new HashSet<>();

    this.stepperActionBack = StepperActions::back;
    this.stepperActionNext = StepperActions::next;
    this.stepperActionSkip = StepperActions::skip;

    this.caption = "";
    this.description = "";
    this.content = null;
    this.icon = null;

    this.optional = false;
    this.editable = false;
    this.cancellable = false;

    this.backButton = createBackButton();
    this.nextButton = createNextButton();
    this.skipButton = createSkipButton();
    this.cancelButton = createCancelButton();

    setDefaultActions(defaultActions);
  }

  /**
   * Create a new back button.
   *
   * @return The back button
   */
  protected Button createBackButton() {
    return new Button("Back");
  }

  /**
   * Create a new next button.
   *
   * @return The next button
   */
  protected Button createNextButton() {
    Button button = new Button("Next");
    button.addStyleName(ValoTheme.BUTTON_PRIMARY);
    return button;
  }

  /**
   * Create a new skip button.
   *
   * @return The skip button
   */
  protected Button createSkipButton() {
    return new Button("Skip");
  }

  /**
   * Create a new cancel button.
   *
   * @return The cancel button
   */
  protected Button createCancelButton() {
    return new Button("Cancel");
  }

  /**
   * Create a new step with the given caption and content and no default actions.
   *
   * @param caption
   *     The caption for the step
   * @param content
   *     The description for the step
   */
  public Step(String caption, Component content) {
    this(false, caption, content);
  }

  /**
   * Create a new step with the given caption and content and add default actions if wished.
   *
   * @param defaultActions
   *     <code>true</code> if default actions should be used for <b><code>back</code>,
   *     <code>next</code></b> and <b><code>skip</code></b> - <code>false</code> else
   * @param caption
   *     The caption for the step
   * @param content
   *     The description for the step
   */
  public Step(boolean defaultActions, String caption, Component content) {
    this(defaultActions);
    setCaption(caption);
    setContent(content);
  }

  /**
   * Create a new step with the given caption, description and content and no default actions.
   *
   * @param caption
   *     The caption for the step
   * @param description
   *     The description for the step
   * @param content
   *     The description for the step
   */
  public Step(String caption, String description, Component content) {
    this(false, caption, description, content);
  }

  /**
   * Create a new step with the given caption, description and content and add default actions
   * if wished.
   *
   * @param defaultActions
   *     <code>true</code> if default actions should be used for <b><code>back</code>,
   *     <code>next</code></b> and <b><code>skip</code></b> - <code>false</code> else
   * @param caption
   *     The caption for the step
   * @param description
   *     The description for the step
   * @param content
   *     The description for the step
   */
  public Step(boolean defaultActions, String caption, String description, Component content) {
    this(defaultActions);
    setCaption(caption);
    setDescription(description);
    setContent(content);
  }

  /**
   * Check whether the default actions are used.
   *
   * @return <code>true</code> if the default actions are used, <code>false</code> else
   */
  public boolean isDefaultActions() {
    return stepBackListeners.contains(stepperActionBack) &&
           stepNextListeners.contains(stepperActionNext) &&
           stepSkipListeners.contains(stepperActionSkip);
  }

  /**
   * Set whether the default actions should be used on button clicks or not.
   * <p>
   * This includes:
   * <ul>
   * <li>back</li>
   * <li>next</li>
   * <li>skip</li>
   * </ul>
   *
   * @param defaultActions
   *     <code>true</code> if the default actions should be used, <code>false</code> else
   */
  public void setDefaultActions(boolean defaultActions) {
    if (defaultActions) {
      addStepBackListener(stepperActionBack);
      addStepNextListener(stepperActionNext);
      addStepSkipListener(stepperActionSkip);
    } else {
      removeStepBackListener(stepperActionBack);
      removeStepNextListener(stepperActionNext);
      removeStepSkipListener(stepperActionSkip);
    }
  }

  @Override
  public boolean addStepCompleteListener(StepCompleteListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepCompleteListeners.add(listener);
  }

  @Override
  public boolean removeStepCompleteListener(StepCompleteListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepCompleteListeners.remove(listener);
  }

  @Override
  public boolean addStepBackListener(StepBackListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepBackListeners.add(listener);
  }

  @Override
  public boolean removeStepBackListener(StepBackListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepBackListeners.remove(listener);
  }

  @Override
  public boolean addStepNextListener(StepNextListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepNextListeners.add(listener);
  }

  @Override
  public boolean removeStepNextListener(StepNextListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepNextListeners.remove(listener);
  }

  @Override
  public boolean addStepSkipListener(StepSkipListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepSkipListeners.add(listener);
  }

  @Override
  public boolean removeStepSkipListener(StepSkipListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepSkipListeners.remove(listener);
  }

  @Override
  public boolean addStepCancelListener(StepCancelListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepCancelListeners.add(listener);
  }

  @Override
  public boolean removeStepCancelListener(StepCancelListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepCancelListeners.remove(listener);
  }

  @Override
  public boolean addStepActiveListener(StepActiveListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepActiveListeners.add(listener);
  }

  @Override
  public boolean removeStepActiveListener(StepActiveListener listener) {
    Objects.requireNonNull(listener, "Listener may not be null");
    return stepActiveListeners.remove(listener);
  }

  public void notifyActive(Stepper stepper) {
    Objects.requireNonNull(stepper, "Stepper may not be null");

    StepActiveEvent activeEvent = new StepActiveEvent(stepper, this);
    stepActiveListeners.forEach(l -> l.onStepActive(activeEvent));
  }

  public void notifyComplete(Stepper stepper) {
    Objects.requireNonNull(stepper, "Stepper may not be null");

    StepCompleteEvent event = new StepCompleteEvent(stepper, this);
    stepCompleteListeners.forEach(l -> l.onStepComplete(event));
  }

  public void notifyBack(Stepper stepper) {
    Objects.requireNonNull(stepper, "Stepper may not be null");

    StepBackEvent event = new StepBackEvent(stepper, this);
    stepBackListeners.forEach(l -> l.onStepBack(event));
  }

  public void notifyNext(Stepper stepper) {
    Objects.requireNonNull(stepper, "Stepper may not be null");

    StepNextEvent event = new StepNextEvent(stepper, this);
    stepNextListeners.forEach(l -> l.onStepNext(event));
  }

  public void notifySkip(Stepper stepper) {
    Objects.requireNonNull(stepper, "Stepper may not be null");

    StepSkipEvent event = new StepSkipEvent(stepper, this);
    stepSkipListeners.forEach(l -> l.onStepSkip(event));
  }

  public void notifyCancel(Stepper stepper) {
    Objects.requireNonNull(stepper, "Stepper may not be null");

    StepCancelEvent event = new StepCancelEvent(stepper, this);
    stepCancelListeners.forEach(l -> l.onStepCancel(event));
  }

  /**
   * Get the caption of the step.
   *
   * @return The caption of the step
   */
  @Override
  public String getCaption() {
    return caption;
  }

  @Override
  public void setCaption(String caption) {
    this.caption = caption;
  }

  /**
   * Get the icon of the step.
   *
   * @return The icon of the step
   */
  @Override
  public Resource getIcon() {
    return icon;
  }

  @Override
  public void setIcon(Resource icon) {
    this.icon = icon;
  }

  /**
   * Get the description of the step.
   *
   * @return The description of the step
   */
  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public void readDesign(Element design, DesignContext designContext) {
    new StepDesignHandler(design, designContext).readDesign();
  }

  @Override
  public void writeDesign(Element design, DesignContext designContext) {
    new StepDesignHandler(design, designContext).writeDesign();
  }

  /**
   * Get the content of the step.
   *
   * @return The content of the step
   */
  public Component getContent() {
    return content;
  }

  public void setContent(Component content) {
    this.content = content;
  }

  /**
   * Get the back button of the step.
   *
   * @return The back button of the step
   */
  public Button getBackButton() {
    return backButton;
  }

  public void setBackButton(Button backButton) {
    this.backButton = backButton;
  }

  /**
   * Get the next button of the step.
   *
   * @return The next button of the step
   */
  public Button getNextButton() {
    return nextButton;
  }

  public void setNextButton(Button nextButton) {
    this.nextButton = nextButton;
  }

  /**
   * Get the skip button of the step.
   *
   * @return The skip button of the step
   */
  public Button getSkipButton() {
    return skipButton;
  }

  public void setSkipButton(Button skipButton) {
    this.skipButton = skipButton;
  }

  /**
   * Get the cancel button of the step.
   *
   * @return The cancel button of the step
   */
  public Button getCancelButton() {
    return cancelButton;
  }

  public void setCancelButton(Button cancelButton) {
    this.cancelButton = cancelButton;
  }

  @Override
  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  @Override
  public boolean isOptional() {
    return optional;
  }

  public void setOptional(boolean optional) {
    this.optional = optional;
  }

  /**
   * Get the cancellable state of the step.
   *
   * @return <code>true</code> if the step is cancellable, <code>false</code> else
   */
  public boolean isCancellable() {
    return cancellable;
  }

  public void setCancellable(boolean cancellable) {
    this.cancellable = cancellable;
  }

  enum StepAction {
    BACK, NEXT, SKIP, CANCEL;

    public static boolean exists(String action) {
      return Arrays.stream(values()).anyMatch(a -> a.toString().equalsIgnoreCase(action));
    }

    public static StepAction from(String action) {
      return Arrays.stream(values())
                   .filter(a -> a.toString().equalsIgnoreCase(action))
                   .findFirst()
                   .orElse(null);
    }
  }

  private class StepDesignHandler {

    private final Element design;
    private final DesignContext designContext;

    private StepDesignHandler(Element design, DesignContext designContext) {
      this.design = design;
      this.designContext = designContext;
    }

    public void readDesign() {
      Step.super.readDesign(design, designContext);

      Elements elements = design.children();

      assertOnlyAllowedChildren(elements);
      assertNoDuplicateAllowedChildren(elements);

      readStepContent(elements);
      readStepButtons(elements);
    }

    private void assertOnlyAllowedChildren(Elements elements) {
      elements.forEach(element -> {
        if (!ALLOWED_CHILDREN.contains(element.tagName())) {
          throw new IllegalArgumentException("Illegal tag found: " + element.tagName() +
                                             ". Allowed tags: " +
                                             ALLOWED_CHILDREN.stream()
                                                             .collect(Collectors.joining(", ")));
        }
      });
    }

    private void assertNoDuplicateAllowedChildren(Elements elements) {
      ALLOWED_CHILDREN.forEach(allowedTag -> {
        if (elements.select(allowedTag).size() > 1) {
          throw new IllegalArgumentException("Multiple tags found for: " + allowedTag);
        }
      });
    }

    private void readStepContent(Elements elements) {
      Elements contentElements = elements.select(DESIGN_TAG_CONTENT);
      if (!contentElements.isEmpty()) {
        Element contentElement = contentElements.get(0);
        Elements children = contentElement.children();

        if (children.size() > 1) {
          String msg = "Only one child allowed for tag <" + DESIGN_TAG_CONTENT + ">";
          throw new IllegalArgumentException(msg);
        }

        Component component = designContext.readDesign(children.first());
        setContent(component);
      }
    }

    private void readStepButtons(Elements elements) {
      Elements buttonsElements = elements.select(DESIGN_TAG_BUTTONS);
      if (!buttonsElements.isEmpty()) {
        Element buttonsElement = buttonsElements.get(0);
        Elements children = buttonsElement.children();

        if (children.size() > 4) {
          String msg = "A maximum of 4 children allowed for tag <" + DESIGN_TAG_BUTTONS + ">";
          throw new IllegalArgumentException(msg);
        }

        List<StepAction> stepActions = new ArrayList<>();
        for (Element buttonElement : children) {
          StepAction stepAction = readStepButton(buttonElement);
          if (stepActions.contains(stepAction)) {
            throw new IllegalArgumentException("Duplicate step action found: " + stepAction);
          }
          stepActions.add(stepAction);
        }
      }
    }

    private StepAction readStepButton(Element buttonElement) {
      Component component = designContext.readDesign(buttonElement);
      if (!(component instanceof Button)) {
        throw new IllegalArgumentException("Only implementations of " + Button.class.getName() +
                                           " are allowed as children of <" + DESIGN_TAG_BUTTONS +
                                           ">");
      }

      Button button = (Button) component;
      if (!buttonElement.hasAttr(DESIGN_ATTRIBUTE_STEP_ACTION)) {
        throw new IllegalArgumentException("Please specify the step action for the button " +
                                           "using the attribute \"" + DESIGN_ATTRIBUTE_STEP_ACTION +
                                           "\"");
      }

      String stepActionAttr = buttonElement.attr(DESIGN_ATTRIBUTE_STEP_ACTION);
      if (!StepAction.exists(stepActionAttr)) {
        throw new IllegalArgumentException("Only the following step actions are allowed: " +
                                           Arrays.stream(StepAction.values())
                                                 .map(StepAction::toString)
                                                 .collect(Collectors.joining(", ")));
      }

      StepAction stepAction = StepAction.from(stepActionAttr);
      switch (stepAction) {
        case BACK:
          setBackButton(button);
          break;
        case NEXT:
          setNextButton(button);
          break;
        case SKIP:
          setSkipButton(button);
          break;
        case CANCEL:
          setCancelButton(button);
          break;
      }

      return stepAction;
    }

    public void writeDesign() {
      Step.super.writeDesign(design, designContext);
      Component contentComponent = getContent();

      if (contentComponent != null) {
        Element contentElement = design.appendElement(DESIGN_TAG_CONTENT);
        contentElement.appendChild(designContext.createElement(contentComponent));
      }

      Element buttonsElement = design.appendElement(DESIGN_TAG_BUTTONS);
      writeButton(buttonsElement, getBackButton(), StepAction.BACK);
      writeButton(buttonsElement, getNextButton(), StepAction.NEXT);
      writeButton(buttonsElement, getSkipButton(), StepAction.SKIP);
      writeButton(buttonsElement, getCancelButton(), StepAction.CANCEL);
    }

    private void writeButton(Element parent, Button button, StepAction stepAction) {
      if (button != null) {
        Element element = designContext.createElement(button);
        element.attr(DESIGN_ATTRIBUTE_STEP_ACTION, stepAction.toString().toLowerCase());
        parent.appendChild(element);
      }
    }
  }
}
