# Stepper Add-on for Vaadin 7

Material Design Stepper is a UI component add-on for Vaadin 7 that implements a Stepper following the [Material Design Specification](https://material.io/guidelines/components/steppers.html#). 

## Online demo

Try the add-on demo at [https://md-stepper-demo.herokuapp.com/](https://md-stepper-demo.herokuapp.com/).

## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to <http://vaadin.com/addon/md-stepper>.

## Building and running demo

git clone https://github.com/Juchar/md-stepper  
mvn clean install  
cd demo  
mvn jetty:run  

To see the demo, navigate to http://localhost:8080/

## Development with Eclipse IDE

For further development of this add-on, the following tool-chain is recommended:
- Eclipse IDE
- m2e wtp plug-in (install it from Eclipse Marketplace)
- Vaadin Eclipse plug-in (install it from Eclipse Marketplace)
- JRebel Eclipse plug-in (install it from Eclipse Marketplace)
- Chrome browser

### Importing project

Choose File > Import... > Existing Maven Projects

Note that Eclipse may give "Plugin execution not covered by lifecycle configuration" errors for pom.xml. Use "Permanently mark goal resources in pom.xml as ignored in Eclipse build" quick-fix to mark these errors as permanently ignored in your project. Do not worry, the project still works fine. 

### Debugging server-side

If you have not already compiled the widgetset, do it now by running vaadin:install Maven target for stepper-root project.

If you have a JRebel license, it makes on the fly code changes faster. Just add JRebel nature to your stepper-demo project by clicking project with right mouse button and choosing JRebel > Add JRebel Nature

To debug project and make code modifications on the fly in the server-side, right-click the stepper-demo project and choose Debug As > Debug on Server. Navigate to http://localhost:8080/stepper-demo/ to see the application.

### Debugging client-side

Debugging client side code in the stepper-demo project:
  - run "mvn vaadin:run-codeserver" on a separate console while the application is running
  - activate Super Dev Mode in the debug window of the application or by adding ?superdevmode to the URL
  - You can access Java-sources and set breakpoints inside Chrome if you enable source maps from inspector settings.
 
## Release notes

### Version 1.0
- Initial Release with basic features

## Roadmap

This component is developed as a hobby with no public roadmap or any guarantees of upcoming releases. That said, the following features are planned for upcoming releases:
- Currently nothing planned

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:
- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

Material Design Stepper is written by Julien Charpenel

# Developer Guide

## Getting started

Here is a simple example on how to try out the add-on component:

```java
Step step1 = new Step(true, "Caption", "Description", new Label("Content"));
Step step2 = new StepBuilder()
                 .withDefaultActions(true)
                 .withCaption("Caption")
                 .withDescription("Description")
                 .withContent(new Label("Content"))
                 .build();

HorizontalStepper stepper = new HorizontalStepper(Arrays.asList(step1, step2), false);
stepper.setSizeFull();
stepper.start();

addComponent(stepper);
```

For a more comprehensive example, see **md-stepper-demo/src/main/org/vaadin/addons/md_stepper/demo/DemoUI.java**

## Features
### Stepper
#### Horizontal & Vertical Stepper

You have the choice to use a `HorizontalStepper` or a `VerticalStepper` that have a layout corresponding to the material design specification.

#### Linearity

The steppers can be set to be linear or non-linear. Please be aware that this is only possible during the creation of the stepper using a constructor parameter:
```java
// Create a new NON-LINEAR stepper by setting the parameter to false
HorizontalStepper stepper = new HorizontalStepper(stepList, false);
```

**Note:**  
This behaviour is implemented **ON PURPOSE** as it would produce unexpected behaviour if the linearity is changed during the process of going through the steps.

#### Step List

The list of steps is provided during the creation of the stepper and is not modifiable afterwards.

**Note:**  
This behaviour is implemented **ON PURPOSE** as it would produce unexpected behaviour if the list is changed during the process of going through the steps.

#### Navigation
The buttons for moving between the steps are automatically shown depending on the configuration of the stepper and the steps.

A click on those buttons wll trigger the according events.

To react on those events and actually move, please use the methods provided by the stepper:
 - `Stepper#next()`
 - `Stepper#back()`
 - `Stepper#skip()`
 
##### Default Navigation
If you want the stepper to automatically call those methods as a reaction to the events, you can use `Step#setDefaultActions(boolean)` or pass in `true` to the constructor of a step for the according parameter.
 
#### Validation
The stepper provides the methods `Stepper#showError(Throwable)` and `Stepper#hideError()` that can be used to show e.g. validation errors:

```java
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
```

For a more detailed example, see **md-stepper-demo/src/main/org/vaadin/addons/md_stepper/demo/steps/Step4.java**

#### Feedback
The stepper provides the methods `Stepper#showFeedbackMessage(String)` and `Stepper#hideFeedbackMessage()` that gives the possibility to show a feedback message for long running operations:

```java
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
```

For a more detailed example, see **md-stepper-demo/src/main/org/vaadin/addons/md_stepper/demo/steps/Step3.java**

#### Custom Step Label Icons
You can provide a custom label factory to the stepper to change the icons for step labels:
```java
SerializableSupplier<StepLabel> labelFactory = () -> {
  StepLabel stepLabel = new StepLabel();
  stepLabel.setIconEditable(FontAwesome.BEER);
  return stepLabel;
};

HorizontalStepper stepper = new HorizontalStepper(stepList, labelFactory);
```

### Step
#### Icons
You can specify custom icons for a step by using `Step#setIcon(Resource)`.  

**ATTENTION:**  
Please be aware that currently only `com.vaadin.server.FontIcon` is supported as an icon for a step.

#### Optional
You can specify a step to be optional by using `Step#setOptional(boolean)`.

If a step is marked as optional, the stepper will show a button to skip the step.

#### Cancellable
You can specify a step to be cancellable by using `Step#setCancellable(boolean)`.

If a step is marked as cancellable, the stepper will show a button to cancel the step.

#### Editable
You can specify a step to be editable by using `Step#setEditable(boolean)`.

If a step is marked as editable, it is possible for the user to come back to this step event it has already been completed (either by using `Stepper#skip()` or `Stepper#next()`).

#### Changing the step after stepper start
If you want to change an attribute after the stepper has already been started you have to call `Stepper#refresh()` to see the changes reflected.

### Events

#### Step
The step class provides various possibilities to listen to events.

##### StepActiveEvent
Called whenever a step is activated and shown by the stepper.

##### StepCompleteEvent
Called whenever a step is completed (either by using `Stepper#skip()` or `Stepper#next()`).

##### StepBackEvent
Called whenever the **back**-button of a step is clicked.

##### StepNextEvent
Called whenever the **next**-button of a step is clicked.

##### StepSkipEvent
Called whenever the **skip**-button of a step is clicked.

##### StepCancelEvent
Called whenever the **cancel**-button of a step is clicked.

#### Stepper
The stepper class provides various possibilities to listen to events.

##### StepperCompleteEvent
Called if all steps have been completed.

##### StepperErrorEvent
Called if the stepper is requested to show an error.

##### StepperFeedbackEvent
Called if the stepper is requested to show a feedback message.

### Declarative Support
The addon provides declarative support to create a stepper and its steps.
Use 
 - `<content/>` to define the content of a step
 - `<buttons/>` to define the buttons of a step
   - `step-action` attribute on a button to define for which action the button should be used
 
**Example:**
```html
<vaadin-vertical-layout width="100%" height="100%">
  <my-horizontal-stepper _id="stepper" linear divider-expand-ratio="0.75"
                         width="1000px" height="500px" :middle :center>
    <my-step caption="Step Caption 1" description="Step Description 1"
             optional editable cancellable default-actions>
      <content>
        <v-vertical-layout width="100%" spacing>
          <v-label style-name="h2">
            Declarative Support
          </v-label>
          <v-label>
            The stepper also supports reading & writing declarative layouts.
            You can set attributes of the stepper itself (e.g. linear) as well as define the single
            steps (including content & buttons customization).
          </v-label>
        </v-vertical-layout>
      </content>
      <buttons>
        <v-button step-action="skip">Skip</v-button>
        <v-button style-name="primary" step-action="next">Finish</v-button>
        <v-button style-name="danger" step-action="cancel">Cancel</v-button>
      </buttons>
    </my-step>
  </my-horizontal-stepper>
</vaadin-vertical-layout>
```
