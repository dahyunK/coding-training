
public class AcidRainPythonMain {

	public static void main(String[] args) {
		AcidRainPython acidrainPython = new AcidRainPython();
		Thread rain = new Thread(acidrainPython.new Rain_Thread());
		rain.run();
	}

}
