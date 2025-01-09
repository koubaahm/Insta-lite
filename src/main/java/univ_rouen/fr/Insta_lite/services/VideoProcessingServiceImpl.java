package univ_rouen.fr.Insta_lite.services;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class VideoProcessingServiceImpl implements VideoProcessingService {

    @Override
    public void convertVideoToCompatibleFormat(Path inputPath, Path outputPath) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg", "-i", inputPath.toString(), "-c:v", "libx264", "-c:a", "aac", outputPath.toString());
        Process process = builder.start();
        process.waitFor();
    }

    @Override
    public void trimVideo(Path inputPath, Path outputPath, String startTime, String duration) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg", "-i", inputPath.toString(), "-ss", startTime, "-t", duration, "-c", "copy", outputPath.toString());
        Process process = builder.start();
        process.waitFor();
    }

    @Override
    public void resizeVideo(Path inputPath, Path outputPath, int width, int height) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg", "-i", inputPath.toString(), "-vf", String.format("scale=%d:%d", width, height), outputPath.toString());
        Process process = builder.start();
        process.waitFor();
    }
}
