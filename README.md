# Figma2Compose

Automatically generate Compose code from Figma designs.

Go from directly from Figma design screens:

![Figma design screen](/media/ecran_figma.PNG)

To Jetpack Compose Android files:

![Jetpack Compose screen](/media/fragment_cod_generat.PNG)

We propose a tag based approach to generate native Android components from Figma natives (a tag looks like *[example-tag]* used in the name of a Figma structure*). A project can contain any number of screens, each with their own tagged components or nested components. 

## Usage

To use the project, clone and build the project, and run the generator using the *loadFromApi* commands, which requires the id of the Figma project (found in the project url) and the personal Figma API token.

Example command line arguments: ```loadFromApi fakeProjectIdABC123 personalApiToken```

Use the [following example project](https://www.figma.com/file/93U47JGLTRlnHIDebtR0El/app_demo?type=design&node-id=0-1&mode=design&t=4YIuDKVZo2o8Py9g-0) to see how you can annotate your own project for generation. You can check out the generated code of this project in /examples

### Custom components

There are several custom components that can be implemented. They use the tags approach seen above. Currently the following are implemented:

* **Button** (tag: [button]) - generates a button with the text of the Figma component. It needs to have a text component as a child.

* **Text field** (tag: [text-field]) - generates a text field with the text of the Figma component. It needs to have a text component as a child.

* **Row** (tag: [row]) - generates a row with the children of the Figma component. It needs to have at least one child.

* **Column** (tag: [column]) - generates a column with the children of the Figma component. It needs to have at least one child.

### Material Design

The project was intended to be used with Material Design. Several components are already implemented (buttons, text fields, switches, checkboxes, lists and list items). You can directly fetch the annotated components [at this link](https://www.figma.com/file/SkEzDc177BQaCA3rlwPbtJ/Material-3-Design-Kit-(figma-to-compose-annotated)?type=design&node-id=47909-2&mode=design&t=2PjVUBNsT2q5AHQu-0). The Material Design tags have the following structure: *[m3:component-name]*.

