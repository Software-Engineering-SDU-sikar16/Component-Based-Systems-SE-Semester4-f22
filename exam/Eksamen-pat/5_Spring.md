# Exam Questions

## What is a Spring container?

The Spring container is the core of the Spring framework, and has the following responsibilities:

- instantiating components
- configuring properties of components
- wiring them together as per dependency injection information specified
- completely managing the life cycle of components from their creationg through destruction

The container depends on a configuration to decide on which components to insantiate and how to wire.

- Is built on the principle of IoC
- Uses dependency injection to manage the components deployed in them

> The Spring container is of two types

### Spring `BeanFactory` container

The simplest container, an implementation of the Factory design pattern.

- Provides basic support for dependency injection
- Is defined by an interface called the `BeanFactory` interface in the Spring API

> Preferred for simple lightweight applications

### Spring `ApplicationContext` container

Enhanced container that includes all the functionality of the `BeanFactory` container, provides additional enterprise functionality.

Provides several classes for loading bean definitions from the configuration file, through:

- The File system: `FileSystemXmlApplicationContext`
- The CLASSPATH: `ClassPathXmlApplicationContext`
- A Web application: `WebXmlApplicationContext`

## What is a Spring bean?

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

## What is Spring configuration and what options exists?

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

## What is the two types of Spring beans?

- Singleton (default)
  - Only a single instance of the bean is created per Spring container
  - Cached and used for subsequent requests
- Prototype
  - Any number of instances can be created based on the requests

## How are two Spring beans wired automatically by the container?

### ApplicationContext Container

Enhanced container that includes all the functionality of the `BeanFactory` container, provides additional enterprise functionality.

Provides several classes for loading bean definitions from the configuration file, through:

- The File system: `FileSystemXmlApplicationContext`
- The CLASSPATH: `ClassPathXmlApplicationContext`
- A Web application: `WebXmlApplicationContext`

```java
ApplicationContext context = new ClassPathXmlApplicationContext(“Beans.xml”);
MyBean myBean = (MyBean) context.getBean(“myBean”);
```

## How are the business components in Spring represented and identified?

### Beans.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection -->
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!-- Player Beans -->
    <bean id="PlayerBean" class="dk.sdu.mmmi.cbse.playersystem.Player" />
    <bean id="PlayerControlSystemBean" class="dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem"/>
    <bean id="PlayerPlugin" class="dk.sdu.mmmi.cbse.playersystem.PlayerPlugin"/>

    <!-- Asteroid Beans -->
    <bean id="AsteroidControlSystemBean" class="dk.sdu.mmmi.cbse.asteroid.AsteroidControlSystem" />
    <bean id="AsteroidPluginBean" class="dk.sdu.mmmi.cbse.asteroid.AsteroidPlugin" />

    <!-- Collision Beans -->
    <bean id="ColliderBean" class="dk.sdu.mmmi.cbse.collision.Collider" />
</beans>
```
