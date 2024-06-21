#!/bin/sh

jpackage --input java-runtime \
 --name JGAAP \
 --main-jar jgaap.jar \
 --main-class com.jgaap.JGAAP \ 
 --module-path /Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home/jmods \ 
 --add-modules javafx.base,javafx.controls,javafx.graphics,java.base,java.desktop,java.logging,java.naming,java.rmi,java.xml \
 --type dmg
