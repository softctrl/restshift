#!/bin/bash
cp .env/application_properties ./resthift/src/main/resources/application.properties && \
git add . && \
git commit -a && \
git push && \
echo "Done git repository update" && \
cp .env/application.properties ./resthift/src/main/resources/application.properties && \
echo "################# OK"
