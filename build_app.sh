#!/bin/sh

NAME="JGAAP"
VENDOR="EVLLabs"

mv build/JGAAP_JavaFX.jar jgaap-app-build/java-runtime

jpackage --input "jgaap-app-build/java-runtime" \
    --type dmg \
    --app-version "9.0.0" \
    --copyright "\u00A9 2024 EVL lab" \
    --description "The Java Graphical Authorship Attribution Program" \
    --name $NAME \
    --vendor $VENDOR \
    --main-jar "JGAAP_JavaFX.jar" \
    --main-class "com.jgaap.JGAAP" \
    --module-path "/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home/jmods" \
    --add-modules "javafx.base,javafx.controls,javafx.graphics,java.base,java.desktop,java.logging,java.naming,java.rmi,java.xml" \
    -d .

