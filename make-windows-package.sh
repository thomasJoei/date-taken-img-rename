# generate jar
mvn clean package

# package for distribution, use jdk 15
jpackage -i ./target/ --dest ./dist \
--main-class com.thojoeis.Main \
--icon src/main/resources/rename_box_icon.ico \
--main-jar image-rename-1.0-jar-with-dependencies.jar \
--name "Image Renamer" --win-menu --win-shortcut