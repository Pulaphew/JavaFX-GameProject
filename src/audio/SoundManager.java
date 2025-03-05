package audio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    private static MediaPlayer mediaPlayer;
    private static String currentFilePath = "";  

    public static void playBackgroundMusic(String filePath, double vol) {
        if (filePath.equals(currentFilePath) && mediaPlayer != null) {
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.STOPPED) {
                mediaPlayer.play();
            }
            return;
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        String resourcePath = extractResource(filePath);
        if (resourcePath == null) {
            System.err.println("cannot found sound: " + filePath);
            return;
        }

        new Thread(() -> Platform.runLater(() -> {
            try {
                // รอให้ไฟล์โหลดเสร็จก่อน
                Thread.sleep(100); 
                Media sound = new Media(new File(resourcePath).toURI().toString());
                mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.setVolume(vol);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.play();
                currentFilePath = filePath;
            } catch (Exception e) {
                e.printStackTrace();
            }
        })).start();
    }

    public static void stopBackgroundMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            currentFilePath = "";
        }
    }

    public static void playClickSound() {
        playSound("/buttonfx.mp3",1);
    }

    public static void playAttackSound() {
        playSound("/oraora.mp3",0.8);
    }

    public static void playDamageSound() {
        playSound("/hurt.mp3",0.8);
    }
    
    public static void playUltimateSound() {
        playSound("/ulti.mp3",1);
    }
    
    public static void playHealingSound() {
        playSound("/heal.mp3",1);
    }
    
    public static void playWinningSound() {
        playSound("/winning.mp3",1);
    }
    
    public static void playLoseSound() {
        playSound("/lose.mp3",1);
    }
    
    public static void playPoisonSound() {
        playSound("/takepoison.mp3",1);
    }
    
    public static void playImmortalSound() {
        playSound("/zawarudo.mp3",1);
    }
    
    public static void playDialogueSound() {
        playSound("/dialogue.mp3",0.8);
    }
    
    

    private static void playSound(String filePath ,double vol) {
        String resourcePath = extractResource(filePath);
        if (resourcePath == null) {
            System.err.println("ไม่พบไฟล์เสียง: " + filePath);
            return;
        }

        try {
            Thread.sleep(50);  // รอให้ไฟล์โหลดก่อน
            Media sound = new Media(new File(resourcePath).toURI().toString());
            MediaPlayer player = new MediaPlayer(sound);
            player.setVolume(vol);
            player.play();
            player.setOnEndOfMedia(() -> player.dispose());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String extractResource(String resourcePath) {
        try (InputStream input = SoundManager.class.getResourceAsStream(resourcePath)) {
            if (input == null) {
                System.err.println("Resource not found: " + resourcePath);
                return null;
            }

            File tempFile = Files.createTempFile("audio", ".mp3").toFile();
            tempFile.deleteOnExit();

            try (FileOutputStream output = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            }
            return tempFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
