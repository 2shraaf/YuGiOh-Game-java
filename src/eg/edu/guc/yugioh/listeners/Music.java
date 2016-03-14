package eg.edu.guc.yugioh.listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Music {
	private AudioInputStream stream;
	private AudioFormat format;
	private DataLine.Info info;
	private Clip clip;

	public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public void close() {
		clip.close();
	}

	public Music() {

		try {
			stream = AudioSystem.getAudioInputStream(new File("src/yugi.wav"));
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			// clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}

}
