package cn.ms.frame;

public class BootstrapTest {

	public static void main(String[] args) {
		try {
			Bootstrap.INSTANCE.init();
			System.out.println(Bootstrap.INSTANCE.pluginComponentMap);
			System.out.println(Bootstrap.INSTANCE.extensionMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
