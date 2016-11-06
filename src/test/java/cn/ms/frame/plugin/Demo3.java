package cn.ms.frame.plugin;

import cn.ms.frame.common.annotation.Plugin;

@Plugin(id="demo2", order=10)
public class Demo3 implements IPlugin {

	public void init() throws Exception {
		System.out.println(getClass().getSimpleName()+"-IPlugin-init");		
	}

	public void start() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
