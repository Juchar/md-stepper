package org.vaadin.addons.md_stepper.demo.steps;

import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import org.vaadin.addons.md_stepper.Step;

public class Step1 extends Step {

  public Step1() {
    super(true); // Use default actions

    VerticalLayout content = new VerticalLayout();
    content.setWidth(100, Sizeable.Unit.PERCENTAGE);
    content.setSpacing(true);
    content.setMargin(true);

    Label basicInformationTitle = new Label("Basic Information");
    basicInformationTitle.addStyleName(ValoTheme.LABEL_H2);
    Label basicInformationLabel = new Label("The stepper component can be used to iterate " +
                                            "through the single steps of a process. Depending on " +
                                            "whether the stepper is declared linear or not, " +
                                            "the provided steps have to be completed in order - " +
                                            "or not.");

    Label demoUsageTitle = new Label("Demo Usage");
    demoUsageTitle.addStyleName(ValoTheme.LABEL_H3);
    Label demoUsageLabel = new Label("You can use the panel on the left side to change and " +
                                     "try out different attributes of the stepper.<br>" +
                                     "Additionally, the demo will show the various possibilities " +
                                     "to use the stepper and its steps when you progress through " +
                                     "the single steps.", ContentMode.HTML);

    content.addComponent(basicInformationTitle);
    content.addComponent(basicInformationLabel);
    content.addComponent(demoUsageTitle);
    content.addComponent(demoUsageLabel);
    content.iterator().forEachRemaining(c -> c.setWidth(100, Unit.PERCENTAGE));

    setCaption("Step 1");
    setDescription("Basic Stepper Features");
    setContent(content);
  }
}
