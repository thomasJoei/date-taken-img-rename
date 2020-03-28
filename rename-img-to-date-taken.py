import os
import sys
import PIL.Image
from PIL.ExifTags import TAGS
import re
import mimetypes

def getDateTaken(filename):
    ret = {}
    image = PIL.Image.open(filename)
    exifinfo = None
    
    try :
        exifinfo = image._getexif()
    except AttributeError:
        print("{} file does not contain METADATA. Skipping...".format(filename))
        return None
    
    if exifinfo is not None:
        for tag, value in exifinfo.items():
            decoded = TAGS.get(tag, tag)
            ret[decoded] = value
    return ret.get('DateTimeOriginal', None)


mypath = os.getcwd()
allowedTypes = ['image/png', 'image/jpeg']

files = [f for f in os.listdir(mypath) if (os.path.isfile(f) and mimetypes.guess_type(f)[0] in allowedTypes)]
agree = input("Are you sure you want to rename all {} files inside {} directory ? (y/n)".format(len(files), mypath))

if (agree == 'y'):
    for f in files:
        dateTaken = getDateTaken(f)
        
        if dateTaken is not None:
            dateTaken = re.sub(r':', '', dateTaken)
            dateTaken = re.sub(r' ', '-', dateTaken)
            filename, file_extension = os.path.splitext(f)
            newName = dateTaken + file_extension

            print("Renaming {} => {}".format(f, newName))
            os.rename(f, newName)
else:
    print('Aborted')


