package com.inmosty;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.parser.mp4.MP4Parser;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class VideoLengthFetcher {
    public static void main(String[] args) {
        //Tika tika = new Tika();
        MP4Parser parser = new MP4Parser();
        List<String> videoUrls = new ArrayList<>();
        videoUrls.add("https://naver.com");

        for (String videoUrl : videoUrls) {
            try (InputStream stream = new URL(videoUrl).openStream()) {
                Metadata metadata = new Metadata();
                parser.parse(stream, new BodyContentHandler(), metadata, new ParseContext());

/*                for (String name : metadata.names()) {
                    System.out.println(name + ": " + metadata.get(name));
                }*/

                // metadata에서 duration 가져오기
                String rawDuration = metadata.get("xmpDM:duration");
                log.info("rawDuration = {}", rawDuration);
                if (rawDuration != null && !rawDuration.isEmpty()) {
                    // duration을 초로 변환
                    double durationInSecondsRaw = Double.parseDouble(rawDuration);

                    // 초를 분과 초로 변환
                    int durationInSeconds = (int) (durationInSecondsRaw) % 60;
                    int durationInMinutes = (int) ((durationInSecondsRaw / 60) % 60);

                    log.info("==================start==============");
                    log.info("Video URL: " + videoUrl);
                    log.info("Duration: " + durationInMinutes + " minutes " + durationInSeconds + " seconds");
                    log.info("==================end==============");
                }
            } catch (Exception e) {
                System.err.println("Failed to fetch video length for URL: " + videoUrl);
                e.printStackTrace();
            }
        }
    }
}
