
public class AcidRainMain {

	public static void main(String[] args) {
		AcidRain acidrain = new AcidRain();
		Thread rain = new Thread(acidrain.new Rain_Thread());
		Thread rain1 = new Thread(acidrain.new PlayWav());
		rain.start();
		rain1.start();
	}

}
