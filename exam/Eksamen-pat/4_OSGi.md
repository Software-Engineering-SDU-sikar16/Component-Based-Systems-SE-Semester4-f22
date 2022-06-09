# Exam Questions

## 1. What is wrong with the JAR file-based component model?

The physical and logical boundaries dissolve on a flat classpath. The packages and the implementations within have a precedence on the classpath. If package_a in Jar1 has Class2, and another package_b in Jar2 has Class 1, Class 2, and Class 3, and Jar1 is loaded before Jar 2, then Jar2 will be forced to use Jar1 -> package_a's Class2. This configuration happens at runtime and could cause unexpected behavior of the components.

> Root cause: The component boundary that keeps all related classes of the components together at development time is dissolving and dynamically changing at runtime

## 2. How does OSGi overcome the problems of flat CLASSPATH?

It wraps a _module layer_ around the JAR file. A module/bundle is the unit of deployment in OSGI.

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

## 3. Are all OSGi bundles JAR files?

Yes

It is:

- a regular JAR file that contains
  - class files
  - resource files
  - metadata
- special because
  - it carries additional metadata relevant to OSGi in the manifest of the JAR file
    - `META-INF/MANIFEST.MF`

## 4. Are all JAR files OSGi bundles?

No, because a JAR file does not neccessarily contain the OSGi relevant metadata.

## 5. How is the unique identity of an OSGi bundle defined?

The symbolic name and version together uniquely identify a bundle

```
Bundle-SymbolicName: org.package.uniqueName
Bundle-Version: x.y.z.qualifier
```

> Two bundles can only share the `Bundle-SymbolicName` if their `Bundle-Version` differs.

## 6. Do all OSGi bundles need to export one or more packages?

By default, they export nothing, and none of the code is visible from other bundles.

> To export: `Export-Package: org.project.package` in `MANIFEST.MF`.
>
> Note: exporting a package does not expose sub-packages.

## 7. Can an OSGi bundle transition its state from installed to active?

No, there are more states inbetween

### Life Cycle Layer

- **Installed**
  - A bundle can be installed by another bundle. Once it is, an object of type _Bundle_ is returned by the framework runtime. All life cycle operations such as astarting, stopping, or uninstalling of the bundle can be performed only through this object
- **Resolved**
  - Once installed, the framework analyzes its manifest and determines the dependencies of the bundle.
    - Resolves the import-package dependencies of the bundle by looking for other bundles that export the required packages. If the bundles dependencies are resolved, the state changes to _Resolved_.
- **Starting**
  - A bundle in _Resolved_ state can be started by calling `start()` on the `Bundle` object.
    - While the bundle transitions from _Resolved_ to _Active_, it is in the _Starting_ state.
- **Active**
  - After a bundle has received a `start()` command, it gets activated after a transition state of _Starting_
    - Just before activation, the framework checks if there is a `BundleActivator` defined in the manifest. If there is, the framework calls the `start()` method on the defined bundle activator.
      - The `BundleActivator` provides a means for the bundle dev. to interfere with the bundle life cycle events.
- **Stopping**
  - An active bundle can be stopped by calling the `stop()` method on the `Bundle` object. The bundle transitions through the _Stopping_ state to reach the _Resolved_ state.
    - If a `BundleActivator` is defined in the manifest, the framework calls the `stop()` method on this.
      - This intervention provides and opportunity for the bundle developer to clean up any resources being used by the bundle before it stops.
- **Uninstalled**
  - A bundle can be uninstalled by calling the `uninstall()` method on the `Bundle` object.
    - When a bundle is uninstalled, the framework notifies all the other bundles. If the bundle had exported any package and it is currently in use by other bundles, then the exported packages would continue to be available until the framework is stopped in spite of the uninstallation of the bundle.

## 8. How does the OSGi framework carry out bundle resolution? Is the same resolution mechanism applicable to the OSGi service layer?

### Module layer bundle resolution

The OSGi framework matches the exports and imports of deployed bundles to dynamically wire (compose) the entire application. This is called bundle resolution and ensures consistency among the different bundles in terms of versions and other constraints.

### Service Layer

The service layer provides a publish, find, and bind model of collaboration.

1. OSGi bundles _publish_ a set of services through the OSGi service registery.
2. Other Bundles query the OSGi to _locate_ and _bind_ with the services that they require
3. The service registry checks the list of registered services and returns a service reference that matches the query
4. The requiring bundle binds with the service reference and consumes its services.

> Instead of querying the registry, the bundles can opt to be notified by the OSGi service registry whenever a registration or unregistration event happens. Once the notification arrives, the bundles can bind or unbind with an appropriate service

## 9. What are the elements of an OSGi service?

A service consists of a _service interface_ and a _service object_.

### Service Interface (Service Provider Interface)

- Akin to a provided interface of a component
- Defines a set of API operators
- Represents a conceptual contract between provider and consumer

### Service Object (Service Provider)

- Akin to an internal component implementation of the provided interface
- Provides implementation of the service

## 10. How do you register an OSGi service?

When a bundle registers a service with the service registry:

- it registers a service object against a service interface
- it owns the service object

When the bundle is stopped:

- the service and service object are unregistered

### Registering a service using the BundleContext API

```java
public class myBundleActivator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        context.registerService(SomeServiceInterface.class, new ServiceObject(), null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }
}
```

When registered, the service functionality is made available to other bundles under the runtime framework's control

## 11. How do you find an OSGi service? + 12. How do you bind to an OSGi service?

The service layer provides a procedural service model. Publishing bundles take the responsibility of publishing services, and the consuming bundle takes the responsibility of finding and binding to the service.

A consumer can use the service registry to search for providers of a particular service:

```java
public IService getService() {
    ServiceReference<IService> serviceRef = bundleContext.getServiceReference(IService.class);
    return bundleContext.getService(serviceRef)
}
```

Once it finds a service provider, it can bind and use the service. A dependency exists between the bundle owning the service and the bundle consuming it. To decouple this dependency, we use the `ServiceReference` API object. This contains properties and metadata information the consuming bundle can examine before making use of the service.

## 13. Why do we need declarative services?

Sits on top of the service layer.

Lets the bundle developers publish and consume services declaratively through the OSGi service layer. In short: it automates the registration and lookup of services, making dev-life easier.

### Declarative Services model

- A service provided by a bundle is described by a _service component_.
  - the service component description is interpreted at runtime to create and destroy service objects as neccessasry
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

## 14. Can an OSGi component provide more than one interface?

Yes, the Service Component dscriptor can simply have multiple implementation elements

## 15. Can an OSGi component require more than one interface?

Yes, the Service component descriptor can simply have multiple reference elements

## 16. Do declarative services provide auto-wiring of required and provided interfaces?

yup, that's why we use it. No need for manual lookup and binding in the bundle activator class.

## 17. In what way does declarative services specification enhance upon the service layer specification?

It automates registration and lookup of services, making the developers life easier.
