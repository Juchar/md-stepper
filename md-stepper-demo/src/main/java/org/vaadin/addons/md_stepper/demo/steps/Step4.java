package org.vaadin.addons.md_stepper.demo.steps;

import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.Stepper;
import org.vaadin.addons.md_stepper.event.StepperActions;
import org.vaadin.addons.md_stepper.util.StringUtils;

public class Step4 extends Step {

  public Step4() {
    VerticalLayout content = new VerticalLayout();
    content.setWidth(100, Sizeable.Unit.PERCENTAGE);
    content.setSpacing(true);
    content.setMargin(true);

    Label errorTitle = new Label("Step Validation");
    errorTitle.addStyleName(ValoTheme.LABEL_H2);
    Label errorLabel = new Label("You can validate the contents of your step and show an " +
                                 "error message.<br>Try it out using the text field below " +
                                 "(it should not be empty).", ContentMode.HTML);

    TextField textField = new TextField("Please enter a value");

    content.addComponent(errorTitle);
    content.addComponent(errorLabel);
    content.iterator().forEachRemaining(c -> c.setWidth(100, Unit.PERCENTAGE));
    content.addComponent(textField);

    addStepBackListener(StepperActions::back);
    addStepNextListener(event -> {
      Stepper stepper = event.getSource();
      stepper.hideError();

      String value = textField.getValue();
      if (StringUtils.isBlank(value)) {
        stepper.showError(new RuntimeException("Field should not be empty"));
      } else {
        stepper.next();
      }
    });

    setCaption("Step 4");
    setDescription("Step Validation");
    setContent(content);
  }
}
