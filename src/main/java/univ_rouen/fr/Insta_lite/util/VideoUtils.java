package univ_rouen.fr.Insta_lite.util;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import java.io.IOException;
import java.nio.file.Path;

public class VideoUtils {

    public static String getVideoDuration(Path filePath) throws IOException {
        // Assurez-vous que le chemin vers ffprobe est correct
        FFprobe ffprobe = new FFprobe("C:/ffmpeg/bin/ffprobe");

        // Obtenez les informations de la vidéo
        FFmpegProbeResult probeResult = ffprobe.probe(filePath.toString());

        // Récupérez la durée en secondes
        double duration = probeResult.getFormat().duration;

        // Convertissez la durée en heures, minutes, et secondes
        long hours = (long) (duration / 3600);
        long minutes = (long) ((duration % 3600) / 60);
        long seconds = (long) (duration % 60);

        // Retournez la durée formatée
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
