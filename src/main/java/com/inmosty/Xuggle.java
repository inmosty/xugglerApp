package com.inmosty;

import com.xuggle.xuggler.IContainer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Xuggle {
    public static void main(String[] args) {
        log.info("Hello World!");
        String url = "naver.com";
        IContainer container = IContainer.make();

        if (container.open(url, IContainer.Type.READ, null) < 0)
            throw new IllegalArgumentException("Could not open file: " + url);

        long duration = container.getDuration() / 1000000;
        log.info("Video length: " + duration + " seconds.");
    }
}
