JArch
=====

A tool to verify the modularity and layering of Java source code.

In summary, its a glorified packagae dependency analyser.

In more detail, it allows you to specify rules about the dependencies between
functional areas and layers of your application, will check them, and inform
you of problems.

Its particulary suited to enterprise applications, built with Spring or JEE
where functional areas and layering are central.

It is based loosley on JAPAN (http://japan.sourceforge.net/), but the code is
written from scratch, with a focus on modules and layers.

Specify rules in a config file
------------------------------

An application "project" has the functional areas:
    
    com.company.project.configuration
    com.company.project.person
    com.company.project.address

And some common code:

    com.company.project.common

It is written using spring, with the following layers 
in each functional area:

    com.company.project.<functionalarea>.controller
    com.company.project.<functionalarea>.facade
    com.company.project.<functionalarea>.service
    com.company.project.<functionalarea>.repository
    com.company.project.<functionalarea>.dao
    com.company.project.<functionalarea>.dto
    com.company.project.<functionalarea>.domain
    
A config file is created:

jarch-config.xml:

```xml
<jarch-config>

    <base-package>com.company.project</base-package>

    <layer-spec name="spring">
        <layer name="controller">
            <dependency on="facade"/>
            <dependency on="domain"/>
        </layer>
        <layer name="facade">
            <dependency on="service"/>
            <dependency on="domain"/>
        </layer>
        <layer name="service">
            <dependency on="repository"/>
            <dependency on="domain"/>
        </layer>
        <layer name="repository">
            <dependency on="dao"/>
            <dependency on="domain"/>
            <dependency on="dto"/>
        </layer>
        <layer name="dao">
            <dependency on="dto"/>
        </layer>
        <layer name="domain">
            <dependency on="dto"/>
        </layer>
        <layer name="dto">
        </layer>
    </layer-spec>

    <module name="common"/>

    <module name="configuration" layer-spec="spring">
        <dependency on="common"/>
    </module>

    <module name="person" layer-spec="spring">
        <dependency on="common"/>
        <dependency on="configuration"/>
        <dependency on="address"/>
    </module>

    <module name="address" layer-spec="spring">
        <dependency on="common"/>
        <dependency on="configuration"/>
    </module>

</jarch-config>
```

There is a module element per functional area.  If that area is layered a
layer-spec attribute is set referencing the layer-spec.  If no layering
is used (the common code) no layer-spec is specified.

The layer-spec defines how the layers of each functional area may communicate,
for example the repository layer cannot call the controller layer.

Running the Ant task
--------------------

Include the following in build.xml:

```xml
<taskdef classname="com.boothj5.jarch.JArchTask" name="jarch" classpath="lib/build/jarch.jar:lib/build/jdom-2.0.1.jar"/>

<target name="jarch" >
    <jarch sourcePath="src/project/java" jarchConfigFile="jarch-config.xml" />
</target>
```

And run:

    ant jarch
