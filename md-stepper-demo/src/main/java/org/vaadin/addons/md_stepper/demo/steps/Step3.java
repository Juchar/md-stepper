package org.vaadin.addons.md_stepper.demo.steps;

import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.Stepper;
import org.vaadin.addons.md_stepper.event.StepperActions;

import java.util.Timer;
import java.util.TimerTask;

public class Step3 extends Step {

  public Step3() {
    VerticalLayout content = new VerticalLayout();
    content.setWidth(100, Sizeable.Unit.PERCENTAGE);
    content.setSpacing(true);
    content.setMargin(true);

    Label feedbackTitle = new Label("Step Feedback");
    feedbackTitle.addStyleName(ValoTheme.LABEL_H2);
    Label stepFeedbackLabel = new Label("The stepper provides the possibility to show a " +
                                        "feedback message for long running operations.<br>Just " +
                                        "click next to see an example.", ContentMode.HTML);

    content.addComponent(feedbackTitle);
    content.addComponent(stepFeedbackLabel);

    addStepBackListener(StepperActions::back);
    addStepNextListener(event -> {
      Stepper stepper = event.getSource();
      stepper.showFeedbackMessage("Long loading operation is being performed");

      new Timer().schedule(new TimerTask() {

        @Override
        public void run() {
          UI.getCurrent().access(() -> {
            stepper.hideFeedbackMessage();
            stepper.next();
          });
        }
      }, 2000);
    });

    setCaption("Step 3");
    setDescription("Long running Operations");
    setContent(content);
  }
}
