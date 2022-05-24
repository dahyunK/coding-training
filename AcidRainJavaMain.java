
public class AcidRainJavaMain {

	public static void main(String[] args) {
		AcidRainJava acidrainJava = new AcidRainJava();
		Thread rain = new Thread(acidrainJava.new Rain_Thread());
		Thread rain1 = new Thread(acidrainJava.new PlayWav());
		rain.start();
		rain1.start();
	}

}
