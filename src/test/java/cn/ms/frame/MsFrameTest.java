package cn.ms.frame;

public class MsFrameTest {

	public static void main(String[] args) {
		try {
			Bootstrap.INSTANCE.init();
			System.out.println(Bootstrap.INSTANCE.pluginMap);
			System.out.println(Bootstrap.INSTANCE.componentMap);
			System.out.println(Bootstrap.INSTANCE.extensionMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
