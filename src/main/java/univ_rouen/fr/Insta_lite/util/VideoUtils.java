package univ_rouen.fr.Insta_lite.util;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import java.io.IOException;
import java.nio.file.Path;

public class VideoUtils {

    public static String getVideoDuration(Path filePath) throws IOException {

        FFprobe ffprobe = new FFprobe("C:/ffmpeg/bin/ffprobe");


        FFmpegProbeResult probeResult = ffprobe.probe(filePath.toString());


        double duration = probeResult.getFormat().duration;

        long hours = (long) (duration / 3600);
        long minutes = (long) ((duration % 3600) / 60);
        long seconds = (long) (duration % 60);


        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
