#!/bin/sh

javadoc com.jgaap \
 -private \
 --module-path "/Library/Java/Extensions/JavaFX_21/javafx-sdk-21.0.3/lib" \
 --add-modules=javafx.base,javafx.graphics,javafx.controls \
 -d Documentation/JavaDoc \
 -classpath "lib/external/" \
