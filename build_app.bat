cd "C:\Program Files\Java\jdk-21\bin"
.\jpackage --input "C:\Users\xana\Desktop\jpack\runtime" ^
--type msi ^
--app-version "9.0.0" ^
--copy-right "\u00A9 2024 EVL lab" ^
--description "The Java Graphical Authorship Attribution Program" ^
--name "JGAAP" ^
--vendor "EVLLabs" ^
--main-jar "C:\Users\xana\Desktop\jpack\runtime\jgaap.jar" ^
--module-path "C:\Program Files\Java\jdk-21\bin" ^
--add-modules javafx.base,javafx.controls,javafx.graphics,java.base,java.desktop,java.logging,java.naming,java.rmi,java.xml ^
-d C:\Users\xana\Desktop\jpack\runtime


