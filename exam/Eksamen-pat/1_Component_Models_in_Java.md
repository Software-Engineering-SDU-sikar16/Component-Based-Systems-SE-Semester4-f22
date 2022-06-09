# 1. Component Models in Java Exam Questions

## What are the different types of component models?

A component model is a definition of properties that components must satisfy, methods and mechanisms for the composition of components

### Dependency injection-based component models

Each component declares the set of interfaces provided by it and the set of interfaces required by it.

- The component framework analyzes this information on all the deployed components and injects the providing component references to requiring components

### Publish and consume whiteboard query-based component models

Each component registers the set of provided interfaces with a whiteboard registry.

- Any component requiring an interface queries the whiteboard registry to obtain a reference to the providing component.

## What is the Spring framework?

A modular framework that supports Inversion of Control (IoC) features in the form of dependency injection.

- Lets an object obtain of a reference of another object without using a `new` operator
  - the heart of the Spring framework
- provides the required APIs for development and deployment of the components

Springs differs from other components model because there is no concept of interface

- Components are used directly, the framework is composed of _Spring Beans_ (the "Component") which are POJOs deployed in a _Spring container_

> The spring container instantiates the bean components using a configuration mechanism that is usually specified through an _XML file_.

## What are Spring beans and the Spring container?

### Spring container

The Spring container is the core of the Spring framework, and has the following responsibilities:

- instantiating components
- configuring properties of components
- wiring them together as per dependency injection information specified
- completely managing the life cycle of components from their creationg through destruction

The container depends on a configuration to decide on which components to insantiate and how to wire.

- Is built on the principle of IoC
- Uses dependency injection to manage the components deployed in them

> The Spring container is of two types

#### Spring `BeanFactory` container

The simplest container, an implementation of the Factory design pattern.

- Provides basic support for dependency injection
- Is defined by an interface called the `BeanFactory` interface in the Spring API

> Preferred for simple lightweight applications

#### Spring `ApplicationContext` container

Enhanced container that includes all the functionality of the `BeanFactory` container, provides additional enterprise functionality.

Provides several classes for loading bean definitions from the configuration file, through:

- The File system: `FileSystemXmlApplicationContext`
- The CLASSPATH: `ClassPathXmlApplicationContext`
- A Web application: `WebXmlApplicationContext`

### Spring beans

Spring components are referred to as _Spring beans_ and play the role of business components in the Spring framework

Developed and deployed in the Spring Container, which manages their life cycles.
Managed by the container with the help of the configuration file

Spring beans are POJOs and are categorized into two types only at the time of instantiation:

- Singleton (default)
  - Only a single instance of the bean is created per Spring container
  - Cached and used for subsequent requests
- Prototype
  - Any number of instances can be created based on the requests

> Bean type is specified in the bean definition configuration file

## What is the significance of the Spring configuration?

It's critical for managing bean instances

Spring provides three configurations:

- XML-based configuration file (most popular)
  - Can have any name, but is usally named `Beans.xml`
  - Contains configuratino metadata used by the container creating a bean instance, managing the bean's life cycle methods, and managing the bean's dependencies
- Annotation-based configuration
- Java-based configuration

Some of the important metadata that are part of the configuration file:

- **id**
  - id for the bean
- **class**
  - Specifies the bean class
- **name**
  - alias name for the Id of the bean
- **scope**
  - scope of the bean objects: singleton, prototype
- **lazy-init**
  - bean will be initialized only during the first request
- **init-method**
  - method invoked after initialization of the bean
- **destory-method**
  - method invoked before the destruction of the bean instance
- **constructor-arg**
  - used for injecting dependencies
- **properties**
  - used for injecting dependencies
- **autowire**
  - used for injecting dependencies

## What is OSGi?

It defines a dynamic module system for java, fixing Java's modularity problem by:

- giving better control of the code structure
- giving better life cycle management of objects
- giving loosely couplely coupled modular architecture

A specification consisting of two parts:

- **OSGi Framework**
  - Runtime environment that provides all the functionality as per the specifications. Apps are developed and executed within this.
  - Provides the API for the development
  - Multiple framework implementations
    - Eclipse Equinox
    - Apache Felix
    - Knoplerfish
- **OSGi standard services**
  - Defines reusable services that should be provided as part of the development platform implementation. These are in three conceptual layers:
    - Module Layer
      - Responsible for packaging and sharing code
    - Life cycle layer
      - Responsible for managing the life cycle of a deployed module during runtime
    - Service layer
      - Responsible for dynamic service publication, searching and binding

## What is a bundle in OSGi?”

OSGi wraps a _module layer_ around the JAR file. A module/bundle is the unit of deployment in OSGI.

It is:

- a regular JAR file that contains
  - class files
  - resource files
  - metadata
- special because
  - it carries additional metadata relevant to OSGi in the manifest of the JAR file
    - `META-INF/MANIFEST.MF`

The OSGi framework provides **stronger boundaries** around the bundle by:

- having bundles explicitly export packages
  - Classes that could usually lead to dissolving boundaries are loaded within the class loading context of a bundle, and are not visible outside the bundle (assuming they arent exported).

As a result, our boundary is consistent irrespective of CLASSPATH definitions in the environment.

> Finer control around physical and logical boundaries of a component using OSGi bundles,
> because a bundle needs to explicitly define what portion of its internal code is externally visible

A bundle must also explicitly declare any external dependencies that is has with the code exposed by other bundles.
The OSGi framework matches the exports and imports of deployed bundles to dynamically wire (compose) the entire application. This is called bundle resolution and ensures consistency among the different bundles in terms of versions and other constraints.

> All OSGi bundles are JAR files, but not all JAR files are OSGi bundles.

It is:

- a regular JAR file that contains
  - class files
  - resource files
  - metadata
- special because
  - it carries additional metadata relevant to OSGi in the manifest of the JAR file
    - `META-INF/MANIFEST.MF`

## What is the importance of the OSGi service registry?

It lets bundles provide and consume services dynamically at runtime.

The service layer provides a publish, find, and bind model of collaboration.

1. OSGi bundles _publish_ a set of services through the OSGi service registery.
2. Other Bundles query the OSGi to _locate_ and _bind_ with the services that they require
3. The service registry checks the list of registered services and returns a service reference that matches the query
4. The requiring bundle binds with the service reference and consumes its services.

## What is MANIFEST.MF in OSGi?

Metadata relevant to OSGi

Provides **stronger boundaries** around the bundle by:

- having bundles explicitly export packages
- having bundles explicitly import packages (declare external dependencies to other bundles)

> Finer control around physical and logical boundaries of a component using OSGi bundles,
> because a bundle needs to explicitly define what portion of its internal code is externally visible

The OSGi framework matches the exports and imports of deployed bundles to dynamically wire (compose) the entire application.
This is called bundle resolution and ensures consistency among the different bundles in terms of versions and other constraints.

## What is component.xml in OSGi?

Configuration file for Declaratives Services.

The declarative services layer sits on top of the service layer.

Lets the bundle developers publish and consume services declaratively through the OSGi service layer. In short: it automates the registration and lookup of services, making dev-life easier.

### Declarative Services model

- A service provided by a bundle is described by a _service component_.
  - the service component description is interpreted at runtime to create and destroy service objects as neccessary
  - is declared in a component description file (XML file inside the bundle)
    - which is read and acted upon by the Service Component Runtime (SCR)
  - a component declares the services that it provides and the dependencies it has ton other components through _references_

#### Declaring a component:

Requires three things:

- An XML file that contains the component description
- A pointer to the component description file location using the bundle manifest (`MANIFEST.MF`) header `Service-Component: META-INF/component.xml`
- An implementation class as defined by the component description

```xml
<?xml version = "1.0" encoding = "UTF-8"?>
<scr:component xmlns:scr = "http://www.osgi.org/xmlns/scr/v1.1.0"
name = "org.project.package">
<implementation class = "org.project.package.MyService"/>
<service>
 <provide interface = "org.project.package.IMyService"/>
</service>
</scr:component>
```

## "How are the components created using Netbeans, Spring, OSGi and invoked by an application client?” and ”Explain the configuration mechanism available in the component models”.
