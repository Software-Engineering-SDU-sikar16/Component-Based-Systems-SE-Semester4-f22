# Exam Questions

## What are the different types of component models?

### Dependency injection-based component models

Each component declares the set of interfaces provided by it and the set of interfaces required by it.

- The component framework analyzes this information on all the deployed components and injects the providing component references to requiring components

### Publish and consume whiteboard query-based component models

Each component registers the set of provided interfaces with a whiteboard registry.

- Any component requiring an interface queries the whiteboard registry to obtain a reference to the providing component.

## What is tight coupling?

Coupling is the measuure of interdependence between modules.

Tight coupling means a large amount of interdependence between modules.

The lowest amount of coupling is _data coupling_, meaning that the dependency between modules is only data passed between them.

The worst amount if _content coupling_, where one module can modify another.

## Why is tight coupling not advisable?

If we have tight coupling, our system is harder to understand, maintain and reuse.

1. High resistance to change in implementation.

## How do we measure the amount of coupling present in a design?

By assessing the class's dependency depth.

> A class that has no dependency has a dependency depth of 0.
>
> Any other class that depends on a class with a dependency depth of 0 has a dependency depth of 1.
>
> Any class that depends on a class with a dependency depth of 1 would have a dependency depth of 2, and so on.
>
> Higher dependency depth implies higher tight coupling

## What design strategies are used to lower coupling?

- Low coupling design principle
- Multiple Layer Architecture
- MVC?

## Why should the business entity objects not be offered as dynamic component services?

They are considered data carrying objects and are shared by all the components. It is considered a library, and does not provide any services.

- They can be instantiated by other components
- They can be passed around by other components to pass data values between them

## Why is coupling on a stable entity preferred over coupling on an unstable entity?

It improves the quality of dependencies, stable meaning we are depending on an _interface_.

- Easier to make changes

## Should componentization of multiple layer applications preserve the original layering structure?

## When changes are requested, is it advisable to change the provided and required interface definitions?

No. It should be Open for extension, Closed for modification
