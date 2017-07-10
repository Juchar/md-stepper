package org.vaadin.addons.md_stepper;

import com.vaadin.event.LayoutEvents;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontIcon;
import com.vaadin.server.Resource;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import org.vaadin.addons.md_stepper.util.StringUtils;

import java.util.Objects;

/**
 * Label that shows the icon, caption and description of a step.
 * <p>
 * Additionally the label will update the icon depending on whether its set to
 * <ul>
 * <li><code>NEXTED</code></li>
 * <li><code>SKIPPED</code></li>
 * <li><code>REVIEWABLE</code></li>
 * <li><code>EDITABLE</code></li>
 * <li><code>ERROR</code></li>
 * </ul>
 */
public class StepLabel extends CustomComponent
    implements Component, LayoutEvents.LayoutClickNotifier {

  public static final FontIcon DEFAULT_ICON_NEXTED = VaadinIcons.CHECK;
  public static final FontIcon DEFAULT_ICON_SKIPPED = VaadinIcons.CHECK;
  public static final FontIcon DEFAULT_ICON_EDITABLE = VaadinIcons.PENCIL;
  public static final FontIcon DEFAULT_ICON_ERROR = VaadinIcons.WARNING;

  private static final String STYLE_ROOT_LAYOUT = "step-label";
  private static final String STYLE_STEP_ICON = "step-icon";
  private static final String STYLE_STEP_CAPTION = "step-caption";
  private static final String STYLE_STEP_DESCRIPTION = "step-description";
  private static final String STYLE_SINGLE_LABEL = "single-label";

  private final HorizontalLayout rootLayout;
  private final Label iconLabel;
  private final Label captionLabel;
  private final Label descriptionLabel;
  private final VerticalLayout captionWrapper;

  private FontIcon iconNexted;
  private FontIcon iconSkipped;
  private FontIcon iconEditable;

  private FontIcon iconError;
  private FontIcon icon;
  private String caption;
  private String description;
  private boolean active;
  private boolean nexted;
  private boolean skipped;
  private boolean editable;
  private boolean clickable;
  private Throwable error;

  /**
   * Construct a new label.
   */
  public StepLabel() {
    this(null, null, null);
  }


  /**
   * Construct a new label with the given caption, description and icon.
   *
   * @param caption
   *     The caption to show
   * @param description
   *     The description to show
   * @param icon
   *     The icon to show
   */
  public StepLabel(String caption, String description, FontIcon icon) {
    active = false;
    nexted = false;
    skipped = false;
    editable = false;
    clickable = false;

    iconLabel = new Label();
    iconLabel.setWidthUndefined();
    iconLabel.setContentMode(ContentMode.HTML);
    iconLabel.addStyleName(STYLE_STEP_ICON);

    captionLabel = new Label();
    captionLabel.setWidth(100, Unit.PERCENTAGE);
    captionLabel.addStyleName(STYLE_STEP_CAPTION);

    descriptionLabel = new Label();
    descriptionLabel.setWidth(100, Unit.PERCENTAGE);
    descriptionLabel.addStyleName(ValoTheme.LABEL_LIGHT);
    descriptionLabel.addStyleName(ValoTheme.LABEL_SMALL);
    descriptionLabel.addStyleName(STYLE_STEP_DESCRIPTION);

    captionWrapper = new VerticalLayout();
    captionWrapper.setSpacing(false);
    captionWrapper.setMargin(false);
    captionWrapper.setSizeFull();
    captionWrapper.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
    captionWrapper.addComponent(captionLabel);
    captionWrapper.addComponent(descriptionLabel);

    rootLayout = new HorizontalLayout();
    rootLayout.setSpacing(false);
    rootLayout.setMargin(false);
    rootLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
    rootLayout.setWidth(100, Unit.PERCENTAGE);
    rootLayout.addComponent(iconLabel);
    rootLayout.addComponent(captionWrapper);
    rootLayout.setExpandRatio(captionWrapper, 1);

    setCompositionRoot(rootLayout);
    addStyleName(STYLE_ROOT_LAYOUT);
    setIcon(icon);
    setCaption(caption);
    setDescription(description);

    setIconNexted(DEFAULT_ICON_NEXTED);
    setIconSkipped(DEFAULT_ICON_SKIPPED);
    setIconEditable(DEFAULT_ICON_EDITABLE);
    setIconError(DEFAULT_ICON_ERROR);
  }

  /**
   * Construct a new label with the given caption.
   *
   * @param caption
   *     The caption to show
   */
  public StepLabel(String caption) {
    this(caption, null, null);
  }

  /**
   * Construct a new label with the given caption and description.
   *
   * @param caption
   *     The caption to show
   * @param description
   *     The description to show
   */
  public StepLabel(String caption, String description) {
    this(caption, description, null);
  }

  @Override
  public Registration addLayoutClickListener(LayoutEvents.LayoutClickListener listener) {
    Objects.requireNonNull(listener, "listener may not be null");
    return rootLayout.addLayoutClickListener(listener);
  }

  @Deprecated
  @Override
  public void removeLayoutClickListener(LayoutEvents.LayoutClickListener listener) {
    Objects.requireNonNull(listener, "listener may not be null");
    rootLayout.removeLayoutClickListener(listener);
  }

  @Override
  public String getCaption() {
    return caption;
  }

  @Override
  public void setCaption(String caption) {
    this.caption = caption;
    markAsDirty();
  }

  @Override
  public FontIcon getIcon() {
    return icon;
  }

  @Override
  public void setIcon(Resource icon) {
    if (icon != null && !(icon instanceof FontIcon)) {
      throw new IllegalArgumentException("Only FontIcons are allowed");
    }

    this.icon = (FontIcon) icon;
    markAsDirty();
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
    markAsDirty();
  }

  @Override
  public void beforeClientResponse(boolean initial) {
    setupIcon();
    setupCaption();
    setupDescription();
    setupLabelPositions();
    setupStyles();
    super.beforeClientResponse(initial);
  }

  private void setupIcon() {
    iconLabel.setValue(icon != null ? icon.getHtml() : null);
    if (nexted) {
      iconLabel.setValue(getIconNexted().getHtml());
    }
    if (skipped) {
      iconLabel.setValue(getIconSkipped().getHtml());
    }
    if (editable) {
      iconLabel.setValue(getIconEditable().getHtml());
    }
    if (error != null) {
      iconLabel.setValue(getIconError().getHtml());
    }
    iconLabel.setVisible(iconLabel.getValue() != null && !"".equals(iconLabel.getValue()));
  }

  private void setupCaption() {
    captionLabel.setValue(caption);
    captionLabel.setVisible(!StringUtils.isBlank(captionLabel.getValue()));
  }

  private void setupDescription() {
    descriptionLabel.setValue(description);
    if (error != null) {
      descriptionLabel.setValue(error.getLocalizedMessage());
    }
    descriptionLabel.setVisible(!StringUtils.isBlank(descriptionLabel.getValue()));
  }

  private void setupLabelPositions() {
    boolean onlyCaption = captionLabel.isVisible() && !descriptionLabel.isVisible();
    boolean onlyDescription = descriptionLabel.isVisible() && !captionLabel.isVisible();
    setupSinglePosition(captionLabel, onlyCaption);
    setupSinglePosition(descriptionLabel, onlyDescription);
  }

  private void setupStyles() {
    toggleStyleName(this, Styles.NEXTED, nexted);
    toggleStyleName(this, Styles.SKIPPED, skipped);
    toggleStyleName(this, Styles.ACTIVE, active);
    toggleStyleName(this, Styles.EDITABLE, editable);
    toggleStyleName(this, Styles.CLICKABLE, clickable);
    toggleStyleName(this, Styles.ERROR, error != null);
  }

  /**
   * Get the icon for the label that is shown if it is <code><b>NEXTED</b></code>.
   *
   * @return The icon that is shown
   */
  public FontIcon getIconNexted() {
    return iconNexted;
  }

  /**
   * Set the icon that is shown if it is <code><b>NEXTED</b></code>.
   *
   * @param iconNexted
   *     The icon to be shown
   */
  public void setIconNexted(FontIcon iconNexted) {
    Objects.requireNonNull(iconNexted, "icon may not be null");
    this.iconNexted = iconNexted;
  }

  /**
   * Get the icon that is shown if it is <code><b>SKIPPED</b></code>.
   *
   * @return The icon that is shown
   */
  public FontIcon getIconSkipped() {
    return iconSkipped;
  }

  /**
   * Set the icon that is shown if it is <code><b>SKIPPED</b></code>.
   *
   * @param iconSkipped
   *     The icon to be shown
   */
  public void setIconSkipped(FontIcon iconSkipped) {
    Objects.requireNonNull(iconSkipped, "icon may not be null");
    this.iconSkipped = iconSkipped;
  }

  /**
   * Get the icon for the label that is shown if it is <code><b>EDITABLE</b></code>.
   *
   * @return The icon that is shown
   */
  public FontIcon getIconEditable() {
    return iconEditable;
  }

  /**
   * Set the icon that is shown if it is <code><b>EDITABLE</b></code>.
   *
   * @param iconEditable
   *     The icon to be shown
   */
  public void setIconEditable(FontIcon iconEditable) {
    Objects.requireNonNull(iconEditable, "icon may not be null");
    this.iconEditable = iconEditable;
  }

  /**
   * Get the icon for the label that is shown if it is <code><b>ERRONEOUS</b></code>.
   *
   * @return The icon that is shown
   */
  public FontIcon getIconError() {
    return iconError;
  }

  /**
   * Set the icon that is shown if it is <code><b>ERRONEOUS</b></code>.
   *
   * @param iconError
   *     The icon to be shown
   */
  public void setIconError(FontIcon iconError) {
    Objects.requireNonNull(iconError, "icon may not be null");
    this.iconError = iconError;
  }

  private void setupSinglePosition(Component component, boolean singlePosition) {
    Alignment alignment = singlePosition ? Alignment.MIDDLE_LEFT : Alignment.TOP_LEFT;
    captionWrapper.setComponentAlignment(component, alignment);
    toggleStyleName(component, STYLE_SINGLE_LABEL, singlePosition);
  }

  private void toggleStyleName(Component component, String styleName, boolean enable) {
    if (enable) {
      component.addStyleName(styleName);
    } else {
      component.removeStyleName(styleName);
    }
  }

  /**
   * Get the active state of the label.
   *
   * @return <code>true</code> if it is active, <code>false</code> else
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Get the active state of the label.
   *
   * @param active
   *     <code>true</code> if it is be active, <code>false</code> else
   */
  public void setActive(boolean active) {
    this.active = active;
    markAsDirty();
  }

  /**
   * Get the clickable state of the label.
   *
   * @return <code>true</code> if it is clickable, <code>false</code> else
   */
  public boolean isClickable() {
    return clickable;
  }

  /**
   * Get the clickable state of the label.
   *
   * @param clickable
   *     <code>true</code> if it is be clickable, <code>false</code> else
   */
  public void setClickable(boolean clickable) {
    this.clickable = clickable;
    markAsDirty();
  }

  /**
   * Get the current error of the label.
   *
   * @return The current error
   */
  public Throwable getError() {
    return error;
  }

  /**
   * Set the error to show.
   *
   * @param error
   *     The error to show
   */
  public void setError(Throwable error) {
    this.error = error;
    markAsDirty();
  }

  /**
   * Get the nexted state of the label.
   *
   * @return <code>true</code> if it is nexted, <code>false</code> else
   */
  public boolean isNexted() {
    return nexted;
  }

  /**
   * Get the nexted state of the label.
   *
   * @param nexted
   *     <code>true</code> if it is nexted, <code>false</code> else
   */
  public void setNexted(boolean nexted) {
    this.nexted = nexted;
    markAsDirty();
  }

  /**
   * Get the skipped state of the label.
   *
   * @return <code>true</code> if it is skipped, <code>false</code> else
   */
  public boolean isSkipped() {
    return skipped;
  }

  /**
   * Get the skipped state of the label.
   *
   * @param skipped
   *     <code>true</code> if it is skipped, <code>false</code> else
   */
  public void setSkipped(boolean skipped) {
    this.skipped = skipped;
    markAsDirty();
  }

  /**
   * Get the editable state of the label.
   *
   * @return <code>true</code> if it is editable, <code>false</code> else
   */
  public boolean isEditable() {
    return editable;
  }

  /**
   * Get the editable state of the label.
   *
   * @param editable
   *     <code>true</code> if it is editable, <code>false</code> else
   */
  public void setEditable(boolean editable) {
    this.editable = editable;
    markAsDirty();
  }

  /**
   * Set the visibility state of the caption label.
   *
   * @param visible
   *     <code>true</code> if it should be visible, <code>false</code> else
   */
  public void setCaptionVisible(boolean visible) {
    captionLabel.setVisible(visible);
    markAsDirty();
  }

  /**
   * Set the visibility state of the description label.
   *
   * @param visible
   *     <code>true</code> if it should be visible, <code>false</code> else
   */
  public void setDescriptionVisible(boolean visible) {
    descriptionLabel.setVisible(visible);
    markAsDirty();
  }

  /**
   * The styles for a {@link StepLabel}.
   */
  public static final class Styles {

    private static final String ACTIVE = "active";
    private static final String NEXTED = "nexted";
    private static final String SKIPPED = "skipped";
    private static final String EDITABLE = "editable";
    private static final String CLICKABLE = "clickable";
    private static final String ERROR = "error";

    private Styles() {
      // Prevent instantiation
    }
  }
}
