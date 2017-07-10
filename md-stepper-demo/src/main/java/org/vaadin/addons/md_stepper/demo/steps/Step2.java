package org.vaadin.addons.md_stepper.demo.steps;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import org.vaadin.addons.md_stepper.Step;

public class Step2 extends Step {

  public Step2() {
    super(true); // Use default actions

    VerticalLayout content = new VerticalLayout();
    content.setWidth(100, Sizeable.Unit.PERCENTAGE);
    content.setSpacing(true);
    content.setMargin(true);

    Label stepAttributesTitle = new Label("Step Attributes");
    stepAttributesTitle.addStyleName(ValoTheme.LABEL_H2);
    Label stepAttributesLabel = new Label("You can change various attributes on a single step:" +
                                          "<ul>" +
                                          "<li>caption</li>" +
                                          "<li>description</li>" +
                                          "<li>icon</li>" +
                                          "<li>optional (to be able to skip it)</li>" +
                                          "<li>editable (come back if skipped or next, show edit " +
                                          "icon)" +
                                          "</li>" +
                                          "</ul>", ContentMode.HTML);

    content.addComponent(stepAttributesTitle);
    content.addComponent(stepAttributesLabel);
    content.iterator().forEachRemaining(c -> c.setWidth(100, Unit.PERCENTAGE));

    setCaption("Step 2");
    setDescription("Step Attributes");
    setContent(content);
    setIcon(VaadinIcons.BAR_CHART);
    setOptional(true);
    setEditable(true);
  }
}
