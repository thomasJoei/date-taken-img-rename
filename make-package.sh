# generate jar
mvn clean package

# package for distribution, use jdk 15
jpackage -i ./target/ \
--main-class com.thojoeis.Main \
--icon src/main/resources/rename_box_icon.icns \
--main-jar image-rename-1.0-SNAPSHOT-jar-with-dependencies.jar \
--name "Image Renamer"



