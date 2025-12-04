package com.asrevo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VideoTest {
    @Test
    void shouldReturnActualVideoDuration() {
        Video input = new Video("input.mp4");
        assertEquals(30526667L, input.duration());
    }
}