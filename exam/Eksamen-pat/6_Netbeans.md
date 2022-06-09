# Exam Questions

## 1. What is wrong with the JAR file–based component model?

The physical and logical boundaries dissolve on a flat classpath. The packages and the implementations within have a precedence on the classpath. If package_a in Jar1 has Class2, and another package_b in Jar2 has Class 1, Class 2, and Class 3, and Jar1 is loaded before Jar 2, then Jar2 will be forced to use Jar1 -> package_a's Class2. This configuration happens at runtime and could cause unexpected behavior of the components.

> Root cause: The component boundary that keeps all related classes of the components together at development time is dissolving and dynamically changing at runtime

## 2. How does Netbeans overcome the problems of flat CLASSPATH?

There are three types of classloaders in the Netbeans Classloader System:

### Module Classloader

Each module registered in the module system has an instance of the module classloader, by means of which every module obtains its own namespace.

This classloader primarily loads classes from the module's JAR archive, but it may load from multiple archives, as often happens with library wrapper modules...

The original classloader is the parent classloader of every module classloader.

This multiparent module classloader enables classes to be loaded from other modules, while avoiding namespace conflicts. The loading of classes is delegated to the parent classloader, rather than the modules themselves.

### System Classloader

By default it is a multiparent classloader. It owns all the instantiated module classloader as its parent.

- Lets us load everything provided by a module with this class loader

It can be accessed in two different ways:

- Lookup
  - `ClassLoader cl = (ClassLoader) Lookup.getDefault().lookup(ClassLoader.class);`
- Context classloader of the current thread
  - `ClassLoader cl = Thread.currentThread().getContextClassLoader();`

### Original Classloader

The original (application) classloader is created by the launcher of the application.

- Loads classes and other resources on the original `CLASSPATH` and from the `lib` directories and their `ext` subdirectories

If a JAR archive is not recognized as a module (that is, the manifest entries are invalid), it is not transferred to the module system.

- Such resources are always found first
  - If those found here are also found in a module JAR, the ones in the module are ignored.

## 3. Are all Netbeans modules JAR files?

Yes, and no...
NBM contain jar-files and those contain a meta-data file also. But other than that, it is just a jar-file.

The manifest/meta-data file contain:

- `OpenIDE-Module`: This attribute defines a unique name for the module used for
  recognition as a module by the module system. Defining this attribute is
  obligatory.
- `OpenIDE-Module-Name`: This defines a displayable name of the module which is also displayed in the plugin manager.
- `OpenIDE-Module-Public-Packages`: To support encapsulation, accessing classes in other modules is denied by default. With this attribute, packages can be explicitly declared as public so other modules can access it. This is especially essential with libraries.
- `OpenIDE-Module-Friends`: If only certain modules can access the packages which
  are declared as public with the attribute OpenIDE-Module-Public-Packages then
  those may be stated here.
- And a bunch more. Those are not that important....

## 4. Are all JAR files Netbeans modules?

No, there are multiple other module systems and other uses of jar files.

## 5. How is the unique identity of a Netbeans module bundle defined?

In the manifest file. See question 3.

## 6. Do all Netbeans modules need to export one or more packages?

No. The core module usually don't export(public packages) anything, they just start all the other components.

## 7. How does the Netbeans runtime-container carry out module resolution?

The basis of the NetBeans Platform and its modular architecture is called _NetBeans Runtime Container_

It consists of the following five modules:

- _Bootstrap_: Executed initially
  - Executes all registered command-line handlers
  - Creates a boot classloader, which loads the startup module, and then executes it
  - Also responsible to start the platform-specific launcher (_for Windows, a .exe_), which identifies the Java Runtime Environm
- _Startup_: Deploys the application by initializing
  - the module system
  - the file system
- _Module System API_: Responsible for management of the modules and their settings and dependencies
- _File System API_: Provides a virtual file system which provides a plat-form independent access
  - Is mostly used for loading resources of the modules
- _Lookup & Utilties API_: Provides and important base component which is used for the intercommunication of the modules
  - Can be used independently of the NetBeans Platform

### Structure of a Module

A module is a simple JAR file which usually consists of the following parts

- Manifest file (manifest.mf)
  - Identifies a module.
- Layer file (layer.xml)
- Class files
- Resources

### Module Resolution

Starting the runtime container, it finds all available modules and creates an internal registry out of them.

- Usually, modules are just loaded when needed, at first they are just registered as "Existing"

A module can do tasks at startup, by using the Module Installer (Chapter 3). The runtime container also
facilitates dynamic loading, unloading, installing, and uninstalling of modules during runtime.

- Necessary for users when updating an application (with the auto update function).
  - Also necessary for deactivating unneeded modules within an application

## 8. What are the elements of a Netbeans service?

Same principle as OSGi. A SP, a SPI and some meta-data (manifest.mf-file). In NetBeans you can use annotations to mark a class as SP to a specific SPI. Otherwise you can make a file in the META-INF.services folder with the full path to the SPI as filename and the full path to the concrete implementation as the content.

## 9. How do you register a Netbeans service?

You can do it the old fashioned way mentioned in question 8. Or via annotations - also mantioned in question 8.
Annotation in the following way:

```java
@ServiceProvider(service = IEntityProcessingService.class)
```

## 10. How do you find a Netbeans module service?

You make use of the LookUp API. Lookup is a Service Locator from NetBeans.

```java
Lookup lookup = Lookup.getDefault();
private Lookup.Result<IGamePluginService> result;
result = lookup.lookupResult(IGamePluginService.class);
result.addLookupListener(lookupListener);
result.allItems();
 for (IGamePluginService plugin : result.allInstances()) {
    plugin.start(gameData, world);
    gamePlugins.add(plugin);
        }
```

## 11. Can a Netbeans module provide more than one interface? How?

Yes, by using the `@ServiceProviders` annotation, which is similar to the `@ServiceProvider` annotation. Using this, we can provide a list of service providers

```java
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),
    @ServiceProvider(service = someOtherService.class)
})
public class Enemy implements IEntityProcessingService, someOtherService {
    // Class body
}
```

## 12. Can a Netbeans module require more than one interface? How?

The core-module uses all services(interfaces) via the Lookup mentioned in question 10. It finds all service providers to the service provided interfaces.

```java
for (IEntityProcessingService entityProcessorService : lookup.lookupAll(IEntityProcessingService.class)) {
    entityProcessorService.process(gameData, world);
}

for (IPostEntityProcessingService postEntityProcessorService : lookup.lookupAll(IPostEntityProcessingService.class)) {
    postEntityProcessorService.process(gameData, world);
}
```
