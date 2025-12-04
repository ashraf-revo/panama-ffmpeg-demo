#!/bin/bash
jextract /usr/include/x86_64-linux-gnu/libavformat/avformat.h \
    -t com.asrevo.ffmpeg \
    -l avformat \
    --output src/main/java
