package org.vaadin.addons.md_stepper.demo;

import com.vaadin.server.Page;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.util.ReflectTools;

import org.vaadin.addons.md_stepper.AbstractStepper;
import org.vaadin.addons.md_stepper.HorizontalStepper;
import org.vaadin.addons.md_stepper.VerticalStepper;
import org.vaadin.addons.md_stepper.demo.StepperPropertiesLayout.StepperCreateListener
           .StepperCreateEvent;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class StepperPropertiesLayout extends CustomComponent {

  private final ComboBox themeChooserBox;
  private final ComboBox stepperTypeBox;
  private final ComboBox iconStyleBox;
  private final Slider dividerRatioSlider;
  private final CheckBox linearStepperBox;
  private final CheckBox borderlessStepperBox;
  private final CheckBox dividerBox;
  private final Label hintsLabel;
  private AbstractStepper stepper;

  public StepperPropertiesLayout() {
    VerticalLayout rootLayout = new VerticalLayout();
    rootLayout.setMargin(true);
    rootLayout.setSpacing(true);
    rootLayout.setWidth(100, Unit.PERCENTAGE);

    themeChooserBox = createThemeChooserBox();
    stepperTypeBox = createStepperTypeBox();
    iconStyleBox = createIconStyleBox();
    dividerRatioSlider = createDividerRatioSlider();
    linearStepperBox = createLinearStepperBox();
    borderlessStepperBox = createBorderlessStepperBox();
    dividerBox = createDividerBox();
    hintsLabel = createHintsLabel();

    rootLayout.addComponent(themeChooserBox);
    rootLayout.addComponent(stepperTypeBox);
    rootLayout.addComponent(iconStyleBox);
    rootLayout.addComponent(dividerRatioSlider);
    rootLayout.addComponent(linearStepperBox);
    rootLayout.addComponent(borderlessStepperBox);
    rootLayout.addComponent(dividerBox);
    rootLayout.addComponent(hintsLabel);

    setWidth(100, Unit.PERCENTAGE);
    setCompositionRoot(rootLayout);
  }

  public void start() {
    createStepper();
    fireEvent(new StepperCreateEvent(StepperPropertiesLayout.this, stepper));
  }

  private void createStepper() {
    String stepperType = String.valueOf(stepperTypeBox.getValue());
    boolean linear = linearStepperBox.getValue();

    stepper = null;
    switch (stepperType) {
      case "Horizontal":
        stepper = new HorizontalStepper(Data.getSteps(), linear);
        break;

      case "Vertical":
        stepper = new VerticalStepper(Data.getSteps(), linear);
        break;
      default:
        throw new UnsupportedOperationException("Only \"Horizontal\" or \"Vertical\" is supported");
    }

    if (stepper != null) {
      stepper.addStepperCompleteListener(event -> {
        Notification notification = new Notification("Success",
                                                     "Congratulations, you finished the stepper.",
                                                     Notification.Type.TRAY_NOTIFICATION);
        notification.show(Page.getCurrent());
      });
    }

    updateStepperIconStyles();
    updateStepperStyles();
    updateDividerExpandRatio();

    dividerRatioSlider.setEnabled(stepper instanceof HorizontalStepper);
    dividerBox.setEnabled(stepper instanceof HorizontalStepper);
    stepper.start();
  }

  private void updateStepperIconStyles() {
    Object value = iconStyleBox.getValue();
    String iconStyle = value != null ? String.valueOf(value) : "";

    stepper.removeStyleName(AbstractStepper.Styles.LABEL_ICONS_SQUARE);
    stepper.removeStyleName(AbstractStepper.Styles.LABEL_ICONS_CIRCULAR);

    switch (iconStyle) {
      case "Square":
        stepper.addStyleName(AbstractStepper.Styles.LABEL_ICONS_SQUARE);
        break;
      case "Circular":
        stepper.addStyleName(AbstractStepper.Styles.LABEL_ICONS_CIRCULAR);
        break;
      default:
        throw new UnsupportedOperationException("Only \"Square\" or \"Circular\" is supported");
    }
  }

  private void updateStepperStyles() {
    String stepperType = String.valueOf(stepperTypeBox.getValue());
    boolean borderless = borderlessStepperBox.getValue();
    boolean noDivider = dividerBox.getValue();

    switch (stepperType) {
      case "Horizontal":
        stepper.removeStyleName(HorizontalStepper.Styles.STEPPER_BORDERLESS);
        stepper.removeStyleName(HorizontalStepper.Styles.STEPPER_NO_DIVIDER);

        if (borderless) {
          stepper.addStyleName(HorizontalStepper.Styles.STEPPER_BORDERLESS);
        }
        if (noDivider) {
          stepper.addStyleName(HorizontalStepper.Styles.STEPPER_NO_DIVIDER);
        }
        break;

      case "Vertical":
        stepper.removeStyleName(VerticalStepper.Styles.STEPPER_BORDERLESS);

        if (borderless) {
          stepper.addStyleName(VerticalStepper.Styles.STEPPER_BORDERLESS);
        }
        break;
      default:
        throw new UnsupportedOperationException("Only \"Horizontal\" or \"Vertical\" is supported");
    }
  }

  private void updateDividerExpandRatio() {
    if (stepper instanceof HorizontalStepper) {
      Double value = dividerRatioSlider.getValue();
      ((HorizontalStepper) stepper).setDividerExpandRatio(value.floatValue());
    }
  }

  private ComboBox createThemeChooserBox() {
    List<String> themes = Arrays.asList("Blueprint", "Dark", "Default", "Facebook", "Flat",
                                        "Flat-Dark", "Light", "Metro");

    ComboBox comboBox = new ComboBox("Choose theme", themes);
    comboBox.setWidth(100, Unit.PERCENTAGE);
    comboBox.setValue("Default");
    comboBox.addValueChangeListener(e -> {
      Object value = e.getValue();
      String theme = value != null ? String.valueOf(value) : "";
      if (!"".equals(theme.trim())) {
        getUI().setTheme(theme.toLowerCase());
      }
    });

    return comboBox;
  }

  private ComboBox createStepperTypeBox() {
    List<String> stepperTypes = Arrays.asList("Horizontal", "Vertical");

    ComboBox comboBox = new ComboBox("Stepper Type *", stepperTypes);
    comboBox.setWidth(100, Unit.PERCENTAGE);
    comboBox.setValue(stepperTypes.get(0));
    comboBox.addValueChangeListener(event -> {
      createStepper();
      fireEvent(new StepperCreateEvent(StepperPropertiesLayout.this, stepper));
    });
    return comboBox;
  }

  private ComboBox createIconStyleBox() {
    List<String> iconStyles = Arrays.asList("Square", "Circular");
    ComboBox comboBox = new ComboBox("Choose Icon Style", iconStyles);
    comboBox.setWidth(100, Unit.PERCENTAGE);
    comboBox.setValue("Circular");
    comboBox.addValueChangeListener(event -> updateStepperIconStyles());
    return comboBox;
  }

  private Slider createDividerRatioSlider() {
    Slider slider = new Slider("Divider ratio", 0, 5);
    slider.setWidth(100, Unit.PERCENTAGE);
    slider.setResolution(2);
    slider.setValue(0.75);
    slider.addValueChangeListener(event -> updateDividerExpandRatio());
    return slider;
  }

  private CheckBox createLinearStepperBox() {
    CheckBox checkBox = new CheckBox("Linear Stepper *");
    checkBox.setWidth(100, Unit.PERCENTAGE);
    checkBox.addValueChangeListener(event -> {
      createStepper();
      fireEvent(new StepperCreateEvent(StepperPropertiesLayout.this, stepper));
    });
    return checkBox;
  }

  private CheckBox createBorderlessStepperBox() {
    CheckBox checkBox = new CheckBox("Borderless");
    checkBox.setWidth(100, Unit.PERCENTAGE);
    checkBox.addValueChangeListener(event -> updateStepperStyles());
    return checkBox;
  }

  private CheckBox createDividerBox() {
    CheckBox checkBox = new CheckBox("No Divider");
    checkBox.setWidth(100, Unit.PERCENTAGE);
    checkBox.addValueChangeListener(event -> updateStepperStyles());
    return checkBox;
  }

  private Label createHintsLabel() {
    Label label
        = new Label("* Changing the stepper type or the linearity will recreate the stepper.");
    label.setWidth(100, Unit.PERCENTAGE);
    label.addStyleName(ValoTheme.LABEL_LIGHT);
    label.addStyleName(ValoTheme.LABEL_SMALL);
    return label;
  }

  public void addStepperCreateListener(StepperCreateListener listener) {
    addListener(StepperCreateEvent.class, listener,
                StepperCreateListener.STEPPER_CREATE_METHOD);
  }

  public interface StepperCreateListener {

    Method STEPPER_CREATE_METHOD = ReflectTools.findMethod(StepperCreateListener.class,
                                                           "onStepperCreate",
                                                           StepperCreateEvent.class);

    void onStepperCreate(StepperCreateEvent event);

    final class StepperCreateEvent extends Component.Event {

      private final AbstractStepper stepper;

      public StepperCreateEvent(Component source, AbstractStepper stepper) {
        super(source);
        this.stepper = stepper;
      }

      public AbstractStepper getStepper() {
        return stepper;
      }
    }
  }
}
