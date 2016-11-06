package cn.ms.frame.component;

import cn.ms.frame.common.annotation.Component;

@Component(id="demo2", order=10,plugin=cn.ms.frame.plugin.Demo1.class)
public class Demo3 implements IComponent {

	public void init() throws Exception {
		System.out.println(getClass().getSimpleName()+"-IComponent-init");
	}

	public void start() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
