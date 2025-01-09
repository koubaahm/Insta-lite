package univ_rouen.fr.Insta_lite.services;

import java.io.IOException;
import java.nio.file.Path;

public interface VideoProcessingService {

    void convertVideoToCompatibleFormat(Path inputPath, Path outputPath) throws IOException, InterruptedException;
    void trimVideo(Path inputPath, Path outputPath, String startTime, String duration) throws IOException, InterruptedException;
    void resizeVideo(Path inputPath, Path outputPath, int width, int height) throws IOException, InterruptedException;
}
