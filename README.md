# Image Renamer - Rename image files by date taken
To rename all images in a folder with their "DateTimeOriginal" metadata.
Especially helpful if have images from multiple devices (camera, phone, friend's camera) with different naming and you want to merge and sort them in chronological order.

I personally use this script every time I come back from a travel and got photos on my camera, phone, and some sent by people I met on the way.

Note : If no metadata "DateTimeOriginal" is available the image won't be renamed, you will have to do it manually.

### Download and Installation
1. Mac OSX dmg file: [here](https://github.com/thomasJoei/date-taken-img-rename/raw/master/dist/Image%20Renamer-1.0.dmg)
1. Open the dmg file and place the app inside your Applications folder
1. Since this not an Apple signed app you will have to authorize it before running it.
    Open a terminal and run :
    ```
    sudo xattr -cr  /Applications/Image\ Renamer.app
    ```


### Generate jar and package for OSX distribution 
```
# generate jar
mvn clean package

# package for distribution, use jdk 15
jpackage -i ./target/ --dest ./dist \
--main-class com.thojoeis.Main \
--icon src/main/resources/rename_box_icon.icns \
--main-jar image-rename-1.0-jar-with-dependencies.jar \
--name "Image Renamer"

```


