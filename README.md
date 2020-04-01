# Image Renamer

```
# generate jar
mvn clean package

# package for distribution, you need jdk 14 to have jpackage
jpackage -i ./target/ --main-class com.thojoeis.ImageRenamer --main-jar image-rename-1.0-SNAPSHOT-jar-with-dependencies.jar

jpackage -i ./target/ --name Image\ Renamer --icon src/main/resources/rename_box_icon.icns --main-class com.thojoeis.Main --main-jar image-rename-1.0-SNAPSHOT-jar-with-dependencies.jar
```

