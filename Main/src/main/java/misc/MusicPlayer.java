package misc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {
	public ArrayList<MediaPlayer> songs = new ArrayList<>();
	public int index = 0;
	
	public MusicPlayer() {
		songLoad();
	}
	public void songLoad() {
		try (Stream<Path> paths = Files.walk(Paths.get("src/res/songs"))) {
		    paths.filter(Files::isRegularFile).forEach(u -> songs.add(new MediaPlayer(new Media(u.toUri().toString()))));
		} catch(IOException e) {
			e.printStackTrace();
		}
		for(MediaPlayer mp : songs) {
			mp.setCycleCount(MediaPlayer.INDEFINITE);
			mp.setOnRepeat(() -> {
				mp.pause();
				songs.get(index = ++index % songs.size()).play();
			});
		}
		songs.get(0).play();
	}
}
