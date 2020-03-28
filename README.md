# Rename image files by date taken
To rename all images in a folder with their "DateTimeOriginal" metadata.
Especially helpful if have images from multiple devices (camera, phone, friend's camera) with different naming and you want to merge and sort them in chronological order.

I personally use this script every time I come back from a travel and got photos on my camera, phone, and some sent by people I met on the way.

Note : If no metadata "DateTimeOriginal" is available the image won't be renamed, you will have to do it manually.

### To run
### Easiest way, through Docker
If you have docker installed.
```
# inside th pictures folder, run :
docker run --rm --mount type=bind,source="$(pwd)",target=/pictures -it thojoeis/img-rename
```

### Directly using the Python 3 script
Check the Dockerfile to know which dependencies you need to have installed on your computer.
```
# inside th pictures folder, run :
python <path-to-this-script>
```


## Docker documentation
Build Docker image and run :
```
# inside this folder, run :
docker image build --tag thojoeis/img-rename .

# inside th pictures folder, run :
docker run --rm --mount type=bind,source="$(pwd)",target=/pictures -it thojoeis/img-rename
```


Create container from `python:3.8.2-alpine`, install dependencies and run script, in one Docker command:
```
docker run --rm -it \
--mount type=bind,source="$(pwd)",target=/pictures \
--mount type=bind,source="<path-to-this-script>",target=/script/rename-img-to-date-taken.py \
--workdir="/pictures" \
python:3.8.2-alpine /bin/ash -c 'apk --no-cache add build-base \
                       jpeg-dev \
                       zlib-dev; \
pip install image;
python /script/rename-img-to-date-taken.py;'
```

