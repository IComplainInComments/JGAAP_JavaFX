#!/bin/sh

jpackage --input java-runtime \
    --type dmg \
    --app-version "9.0.0" \
    --copy-right "\u00A9 2024 EVL lab" \
    --description "The Java Graphical Authorship Attribution Program" \
    --name "JGAAP" \
    --vendor "EVLLabs" \
    --main-jar jgaap.jar \
    --main-class com.jgaap.JGAAP \
    --module-path /Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home/jmods \
    --add-modules javafx.base,javafx.controls,javafx.graphics,java.base,java.desktop,java.logging,java.naming,java.rmi,java.xml \

