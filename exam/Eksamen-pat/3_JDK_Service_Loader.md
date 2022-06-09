# Exam Questions

## What are the basic component structural constructs in Java SE platform?
The Java platform, Standard Edition (Java SE) consists of the following elements:
* Java Programming Language
* Java Software Development Kit (JDK)
* Java SE Application Programming Interface (API)
* Java Runtime Environment (JRE)

For example, we refer to Java SE API to invoke
prebuilt platform provided features. When we invoke any class defined in the Java
SE API, we are referring to classes external to our application’s code. Where does
this external code come from? The Java SE API specifications are implemented and
packaged inside the JRE runtime library called **rt.jar.**(Windows) or **classes.jar.**(Mac)
 We get visibility and access
to this external code from within our source code by including the rt.jar file in the
build path (during development time) and in the class path (during runtime). 

* **TL;DR** : rt.jar contains all of the compiled class files for the base Java Runtime  environment RT.

## What are the pros and cons of basic component constructs provided by Java SE platform?
Pros (IMO)
 - java.lang is already imported by the compiler. No need to import classes like System.
 - If we need java classes, we just import them and the rt.jar has them built in.
 - While the JAR file provides a physical component boundary, the Java package
construct provides a logical component boundary across components. the rt.jar file includes many packages such as java.lang, java.util, and so forth. This helps the same physical component to carry multiple logical components. **Example** We have the class **Scanner**. Both the Java class and a credit card scanner. Those could exist in the same package due to logical packages.

Cons(IMO)
 - I guess there are no cons? They don't mention any in the book I guess. They mention cons about components.

## Why is a component interface important?
The use of interfaces eliminates the tight coupling between components during the build time.
If you import a component w/o the use of interfaces, you depend on the component you imported.
This means that the application component could not be developed in isolation from the
business component. This is an impediment to independent component fabrication.
To solve the build time dependency problem, we can make use of Java interface construct.

## What is the primary reason for introducing a component interface?
To remove build time dependency between the application and business components using the interface. See above.

## What is glue code? Why is it required?
Glue code is needed to make **new** instances of a interface with the type of class needed.
Example: InterfaceType instance = **new** ConcreteImp;

## What is the alternative to using glue code?
Automate the component assembly using a component model and component framework.


## What is a component model?
The functionality done by the glue code can be automated, provided all the components that participate in the assembly process follow common standards and conventions. Such a set of standards that enables automated component assembly is known as a component model. The component model is supported by a component framework, which is usually a runtime program that wires the components deployed.
Component models can be divided into two major categories based on how automation of component assembly is achieved:
1. Dependency injection-based component models
2. Publish and consume whiteboard query-based component models

## What are the different types of component models?
Dependency injection and thw whiteboard model. Explained in other file.

## What is the relationship between a component model and a component framework?
**From above**:
The functionality done by the glue code can be automated, provided all the components that participate in the assembly process follow common standards and conventions. Such a set of standards that enables automated component assembly is known as a component model. The component model is supported by a component framework, which is usually a runtime program that wires the components deployed.


## How are components wired in a whiteboard component framework?
I don't understand this quuestion.... but:
The component has a meta-data file (usually in a META-INF folder), telling the Service Locator that it has a concrete implementation for a SPI. (a service provider).
The Service Locator can now registrate the SP on a list of SP's. The assambler can then look up all SP's of a certain SPI.


## Can a component with the provided interface take the responsibility of registering it with a whiteboard component registry?
It is the Service Locator's job. The component has to have a meta-data file in order for the Service Locator to see it tho.
