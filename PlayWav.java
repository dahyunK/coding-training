import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlayWav{
	public static void main(String[] args) {
		File Wav = new File("sample.wav");
		PlaySound(Wav);
		PlaySound(Wav);
		PlaySound(Wav);
		PlaySound(Wav);
	}
	
	static void PlaySound(File Sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
			
			Thread.sleep(clip.getMicrosecondLength()/1000);
			
		}catch(Exception e) {
			
		}
	}
}