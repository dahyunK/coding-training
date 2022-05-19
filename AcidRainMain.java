
public class AcidRainMain {

	public static void main(String[] args) {
		AcidRain acidrain = new AcidRain();
		Thread rain = new Thread(acidrain.new Rain_Thread());
		rain.run();
	}

}
