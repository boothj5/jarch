JArch
=====

A tool to verify the modularity and layering of Java source code.

In summary, a glorified package dependency analyser.

In more detail, it allows you to specify rules about the dependencies between
major components, functional areas and layers of your application, it will 
check them, and inform you of problems.

Its particulary suited to enterprise applications, built with Spring or JEE
where functional areas and layering are central.

It is based loosley on JAPAN (http://japan.sourceforge.net/), but the code is
written from scratch, with a focus on components, modules and layers.

Installation
------------

Download from the downloads page https://github.com/boothj5/jarch/downloads .
jdom-2.0.1 is also required http://www.jdom.org/downloads/index.html .

Put both in a directory available to your Ant build script or command line.

Running from the command line
-----------------------------

To run straight from the command line (pointing to the jars on your system):

    java -cp dist/jarch-0.1.2.jar:lib/jdom-2.0.1.jar com.boothj5.jarch.cli.Main <source-path> <config-file>

Where <source-path> is the path name to your java source code to be analysed, 
and <config-file> is the jarch configuration for your project, see below. 

Running using the Ant task
--------------------------

Include the following in build.xml:

```xml
<taskdef classname="com.boothj5.jarch.JArchTask" name="jarch" classpath="lib/build/jarch.jar:lib/build/jdom-2.0.1.jar"/>

<target name="jarch" >
    <jarch sourcePath="src/project/java" jarchConfigFile="jarch-config.xml" failBuild="false"/>
</target>
```

If the `failBuild` option is enabled JArch will consider any errors a build failure and Ant will exit,
otherwise messages are shown and Ant will continue.  

Configuration
-------------

```xml
<jarch-config>

    <layer-spec name="spring">
        <layer name="controller">
            <dependency on="facade"/>
        </layer>
        <layer name="facade">
            <dependency on="service"/>
        </layer>
        <layer name="service">
            <dependency on="repository"/>
        </layer>
        <layer name="repository">
            <dependency on="dao"/>
        </layer>
        <layer name="dao"/>
    </layer-spec>

    <rule-set name="application-module-dependencies" base-package="com.boothj5.jarchexample.application">
        <module name="common"/>
        <module name="address" layer-spec="spring">
            <dependency on="common"/>
        </module>
        <module name="telephonenumber" layer-spec="spring">
            <dependency on="common"/>
        </module>
        <module name="person" layer-spec="spring">
            <dependency on="common"/>
            <dependency on="address"/>
            <dependency on="telephonenumber"/>
        </module>
    </rule-set>

    <rule-set name="project-dependencies" base-package="com.boothj5.jarchexample">
        <module name="common"/>    
        <module name="dto">    
            <dependency on="common"/>
        </module>    
        <module name="configuration" layer-spec="spring">    
            <dependency on="common"/>
        </module>
        <module name="domain">    
            <dependency on="common"/>
            <dependency on="dto"/>
        </module>    
        <module name="application">
            <dependency on="common"/>    
            <dependency on="configuration"/>
            <dependency on="domain"/>
            <dependency on="dto"/>
        </module>    
    </rule-set>
            
</jarch-config>
```

The `layer-spec` defines a layering which can be referenced by modules.  If a
module references the layer spec it is validated against that spec.  I.e. JArch
will look for packages with names matching `layer` elements within the module.  
If it finds any it will check that your layers only call each other as you've 
specified in the dependencies of each layer.

A `rule-set` is a bunch of package dependency rules within a particular package.
It is a list of `module` elements representing sub-packages each with an 
optional `layer-spec` attribute, and a set of depdendencies to validate. 

If JArch finds that you've made a call to another module that isn't defined in the
caller module's dependencies, a message is ouput.

The config file is validated first and will show errors when a non exitent 
module or layer-spec is specified, and show Warnings when circular module 
dependencies are specified

Example output
--------------

    JArch - Java Architecture checker.
    
    Source path: /home/james/projects-git/jarch/test/resources/invalid
    Config file: /home/james/projects-git/jarch/test/resources/jarch-config.xml
    
    --> Analysing rule-set "application-module-dependencies".
    
    MODULE: "common" must not import from "person"
      -> com.boothj5.jarchexample.application.common.StringUtil:
             Line 5: import com.boothj5.jarchexample.application.person.service.PersonService;
    
    MODULE: "address" must not import from "person"
      -> com.boothj5.jarchexample.application.address.facade.AddressFacade:
             Line 7: import com.boothj5.jarchexample.application.person.service.PersonService;
    
    MODULE: "address" must not import from "telephonenumber"
      -> com.boothj5.jarchexample.application.address.facade.AddressFacade:
             Line 8: import com.boothj5.jarchexample.application.telephonenumber.service.TelephoneNumberService;
    
    LAYER: "controller" must not import from "service" in module "telephonenumber" according to layer-spec "spring"
      -> com.boothj5.jarchexample.application.telephonenumber.controller.TelephoneNumberController:
             Line 7: import com.boothj5.jarchexample.application.telephonenumber.service.TelephoneNumberService;
    
    LAYER: "dao" must not import from "controller" in module "telephonenumber" according to layer-spec "spring"
      -> com.boothj5.jarchexample.application.telephonenumber.dao.TelephoneNumberDAO:
             Line 6: import com.boothj5.jarchexample.application.telephonenumber.controller.TelephoneNumberController;
    
    LAYER: "controller" must not import from "service" in module "person" according to layer-spec "spring"
      -> com.boothj5.jarchexample.application.person.controller.PersonController:
             Line 7: import com.boothj5.jarchexample.application.person.service.PersonService;
    
    LAYER: "controller" must not import from "repository" in module "person" according to layer-spec "spring"
      -> com.boothj5.jarchexample.application.person.controller.PersonController:
             Line 8: import com.boothj5.jarchexample.application.person.repository.PersonRepository;
    
    LAYER: "controller" must not import from "dao" in module "person" according to layer-spec "spring"
      -> com.boothj5.jarchexample.application.person.controller.PersonController:
             Line 9: import com.boothj5.jarchexample.application.person.dao.PersonDAO;
    
    --> Analysing rule-set "project-dependencies".
    
    MODULE: "common" must not import from "application"
      -> com.boothj5.jarchexample.common.DateUtil:
             Line 5: import com.boothj5.jarchexample.application.common.StringUtil;
    
    MODULE: "common" must not import from "domain"
      -> com.boothj5.jarchexample.common.DateUtil:
             Line 6: import com.boothj5.jarchexample.domain.Person;
    
    MODULE: "common" must not import from "dto"
      -> com.boothj5.jarchexample.common.DateUtil:
             Line 7: import com.boothj5.jarchexample.dto.PersonDTO;
    
    MODULE: "common" must not import from "configuration"
      -> com.boothj5.jarchexample.common.DateUtil:
             Line 8: import com.boothj5.jarchexample.configuration.controller.ConfigurationController;
    
    MODULE: "dto" must not import from "application"
      -> com.boothj5.jarchexample.dto.PersonDTO:
             Line 4: import com.boothj5.jarchexample.application.common.StringUtil;
    
    MODULE: "dto" must not import from "domain"
      -> com.boothj5.jarchexample.dto.PersonDTO:
             Line 5: import com.boothj5.jarchexample.domain.Person;
    
    MODULE: "dto" must not import from "configuration"
      -> com.boothj5.jarchexample.dto.PersonDTO:
             Line 7: import com.boothj5.jarchexample.configuration.controller.ConfigurationController;
    
    LAYER: "controller" must not import from "service" in module "configuration" according to layer-spec "spring"
      -> com.boothj5.jarchexample.configuration.controller.ConfigurationController:
             Line 7: import com.boothj5.jarchexample.configuration.service.ConfigurationService;
    
    LAYER: "controller" must not import from "repository" in module "configuration" according to layer-spec "spring"
      -> com.boothj5.jarchexample.configuration.controller.ConfigurationController:
             Line 8: import com.boothj5.jarchexample.configuration.repository.ConfigurationRepository;
    
    LAYER: "controller" must not import from "dao" in module "configuration" according to layer-spec "spring"
      -> com.boothj5.jarchexample.configuration.controller.ConfigurationController:
             Line 9: import com.boothj5.jarchexample.configuration.dao.ConfigurationDAO;
    
    MODULE: "configuration" must not import from "application"
      -> com.boothj5.jarchexample.configuration.service.ConfigurationService:
             Line 7: import com.boothj5.jarchexample.application.common.StringUtil;
    
    MODULE: "configuration" must not import from "domain"
      -> com.boothj5.jarchexample.configuration.service.ConfigurationService:
             Line 8: import com.boothj5.jarchexample.domain.Person;
    
    MODULE: "configuration" must not import from "dto"
      -> com.boothj5.jarchexample.configuration.service.ConfigurationService:
             Line 9: import com.boothj5.jarchexample.dto.PersonDTO;
    
    MODULE: "domain" must not import from "application"
      -> com.boothj5.jarchexample.domain.Person:
             Line 6: import com.boothj5.jarchexample.application.common.StringUtil;
    
    MODULE: "domain" must not import from "configuration"
      -> com.boothj5.jarchexample.domain.Person:
             Line 7: import com.boothj5.jarchexample.configuration.controller.ConfigurationController;
    
    Module errors: 15
    Layer errors: 8
