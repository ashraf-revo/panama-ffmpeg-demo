# Java Panama FFMpeg Demo

This project demonstrates how to use Java's Foreign Function & Memory (FFM) API, also known as Project Panama, to interact with native libraries. Specifically, this demo uses `jextract` to generate Java bindings for the FFMpeg library and then uses those bindings to calculate the duration of a video file.

## Prerequisites

Before you begin, ensure you have the following installed:

*   **FFMpeg and development headers:** This project calls native FFMpeg libraries, so you need to have them installed on your system. On Debian-based Linux distributions, you can install them using `apt-get`:
    ```bash
    sudo apt-get update
    sudo apt-get install -y ffmpeg libavformat-dev
    ```

*   **`Java 25`**
*   **`jextract`**

## Installation

The easiest way to install Java 25 and `jextract` is by using [SDKMAN!](https://sdkman.io/).

1.  **Install Java 25:**
    ```bash
    sdk install java 25.0.1-amzn
    ```

2.  **Install jextract:**
    ```bash
    sdk install jextract
    ```

## Building the Project
The generation step for java binding for ffmpeg lib will look something like this:
```bash
jextract /usr/include/x86_64-linux-gnu/libavformat/avformat.h -t com.asrevo.ffmpeg -l avformat --output src/main/java
```

This command tells `jextract` to process the `avformat.h` header file, generate Java classes in the `com.asrevo.ffmpeg` package, link against the `avformat` library, and place the generated source files in `src/main/java`.

## To build the project and run test cases, run:

```bash
./gradlew build test
```