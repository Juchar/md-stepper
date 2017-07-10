package org.vaadin.addons.md_stepper.demo;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import org.vaadin.addons.md_stepper.component.Spacer;

import javax.servlet.annotation.WebServlet;

@Theme("default")
@Title("Stepper Add-on Demo")
@Push(PushMode.MANUAL)
@SuppressWarnings("serial")
public class DemoUI extends UI implements StepperPropertiesLayout.StepperCreateListener {

  private GridLayout rootLayout;

  @Override
  protected void init(VaadinRequest request) {
    rootLayout = getRootLayout();
    setContent(rootLayout);

    Label title = getTitleLabel();

    StepperPropertiesLayout layout = new StepperPropertiesLayout();
    layout.addStepperCreateListener(this);
    layout.setWidth(300, Unit.PIXELS);

    Spacer spacer = new Spacer();
    spacer.setWidth(100, Unit.PIXELS);

    rootLayout.addComponent(title, 0, 0, 2, 0);
    rootLayout.addComponent(spacer, 1, 1);
    rootLayout.addComponent(layout, 0, 1);
    rootLayout.setComponentAlignment(title, Alignment.MIDDLE_CENTER);

    layout.start();
  }

  private GridLayout getRootLayout() {
    GridLayout layout = new GridLayout(3, 2);
    layout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
    layout.setMargin(new MarginInfo(false, true, true, true));
    layout.setSpacing(true);
    layout.setSizeFull();
    layout.setRowExpandRatio(1, 1);
    layout.setColumnExpandRatio(2, 1);
    return layout;
  }

  private Label getTitleLabel() {
    Label label = new Label("Material Design Stepper Add-on Demo");
    label.addStyleName(ValoTheme.LABEL_H1);
    label.setWidthUndefined();
    return label;
  }

  @Override
  public void onStepperCreate(StepperCreateEvent event) {
    rootLayout.removeComponent(2, 1);
    rootLayout.addComponent(event.getStepper(), 2, 1);
  }

  @WebServlet(value = "/*", asyncSupported = true)
  @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
  public static class Servlet extends VaadinServlet {
  }
}
