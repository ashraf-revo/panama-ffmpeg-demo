package com.asrevo;

import com.asrevo.ffmpeg.AVFormatContext;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static com.asrevo.ffmpeg.avformat_h_1.*;

public record Video(String path) {
    public double duration() {
        try (var arena = Arena.ofConfined()) {
            // 1. Setup pointer and URL
            MemorySegment pFormatCtx_ptr = arena.allocate(ValueLayout.ADDRESS);
            MemorySegment cUrl = arena.allocateFrom(path);
            // 2. avformat_open_input()
            int ret = avformat_open_input(pFormatCtx_ptr, cUrl, MemorySegment.NULL, MemorySegment.NULL);
            if (ret < 0) {
                System.err.printf("❌ Failed to open input file '%s'. Return code: %d%n", path, ret);
                throw new RuntimeException("Failed to open input file");
            }
            // Extract the AVFormatContext*
            MemorySegment pFormatCtxAddress = pFormatCtx_ptr.get(ValueLayout.ADDRESS, 0);
            MemorySegment pFormatCtx = pFormatCtxAddress.reinterpret(AVFormatContext.layout().byteSize());
            // 3. REQUIRED: Read stream info
            ret = avformat_find_stream_info(pFormatCtx, MemorySegment.NULL);
            if (ret < 0) {
                System.err.println("❌ avformat_find_stream_info() failed");
                avformat_close_input(pFormatCtx_ptr);
                throw new RuntimeException("avformat_find_stream_info() failed");
            }
            // 4. Read duration
            long durationInMicroseconds = AVFormatContext.duration(pFormatCtx);
            if (durationInMicroseconds == Long.MIN_VALUE) {
                System.out.println("⚠️ Duration still unavailable (NO PTS)");
                avformat_close_input(pFormatCtx_ptr);
                throw new RuntimeException("Duration still unavailable (NO PTS)");
            }

            double durationInSeconds = durationInMicroseconds / 1_000_000.0;
            // Cleanup
            avformat_close_input(pFormatCtx_ptr);
            return durationInSeconds;
        }
    }
}
