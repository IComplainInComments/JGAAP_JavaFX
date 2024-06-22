jpackage --input "jgaap-app-build\java-runtime" ^
--type msi ^
--app-version "9.0.0" ^
--copyright "\u00A9 2024 EVL lab" ^
--description "The Java Graphical Authorship Attribution Program" ^
--name "JGAAP" ^
--vendor "EVLLabs" ^
--main-jar "JGAAP_JavaFX.jar" ^
--module-path "C:\Program Files\Java\jdk-21\bin" ^
--add-modules javafx.base,javafx.controls,javafx.graphics,java.base,java.desktop,java.logging,java.naming,java.rmi,java.xml ^
-d "%USERPROFILE%\Desktop"


