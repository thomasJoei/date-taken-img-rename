FROM python:3.8.2-alpine

RUN apk --no-cache add \
    build-base \
    jpeg-dev \
    zlib-dev \
    && pip install pillow --no-cache-dir \
    && apk del build-base

COPY rename-img-to-date-taken.py /script/

WORKDIR /pictures

ENTRYPOINT ["python", "/script/rename-img-to-date-taken.py"]
