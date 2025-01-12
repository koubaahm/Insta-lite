package univ_rouen.fr.Insta_lite.util;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
@Component
public class VideoUtils {
    @Value("${ffmpeg.ffprobe-path}")
    private  String ffprobePath;
    public String getVideoDuration(Path filePath) throws IOException {
        // Utiliser la variable d'instance ffprobePath
        FFprobe ffprobe = new FFprobe(ffprobePath);

        FFmpegProbeResult probeResult = ffprobe.probe(filePath.toString());

        double duration = probeResult.getFormat().duration;

        long hours = (long) (duration / 3600);
        long minutes = (long) ((duration % 3600) / 60);
        long seconds = (long) (duration % 60);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
