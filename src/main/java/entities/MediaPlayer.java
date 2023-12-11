package entities;

import javafx.scene.media.Media;

public class MediaPlayer {
    private static MediaPlayer instance;
    private javafx.scene.media.MediaPlayer mediaPlayer;

    private MediaPlayer() {
        // Load the media file
        Media media = new Media(getClass().getResource("/com/example/stickhero/sb_indreams(chosic.com).mp3").toString());

        // Create the MediaPlayer
        mediaPlayer = new javafx.scene.media.MediaPlayer(media);

        // Set volume and cycle count
        mediaPlayer.setVolume(0.5);
        mediaPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
    }

    public static MediaPlayer getInstance() {
        if (instance == null) {
            synchronized (MediaPlayer.class) {
                if (instance == null) {
                    instance = new MediaPlayer();
                }
            }
        }
        return instance;
    }

    public void play() {
        if (!mediaPlayer.getStatus().equals(javafx.scene.media.MediaPlayer.Status.PLAYING)) {
            // Create a new thread for playing the music
            Thread musicThread = new Thread(mediaPlayer::play);
            musicThread.setDaemon(true);
            musicThread.start();
        }
    }

    public javafx.scene.media.MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}