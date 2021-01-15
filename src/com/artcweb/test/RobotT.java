package com.artcweb.test;

import java.awt.Robot;
import java.awt.event.KeyEvent;


public class RobotT {

	
	public static void main(String[] args) throws Exception {
		
		 Runtime runtime=Runtime.getRuntime();
		 //runtime.exec("C:\\Users\\Administrator\\Desktop\\开发工具\\phtoshopzxpjb\\pscc2017_gr\\PhotoshopPortable.exe");
		 
		Robot robot = new Robot();
		robot.delay(3000); //运行代码后，暂停三秒，留够时间去打开电脑自带的画板，并点击形状里面的椭圆形
		robot.keyPress(KeyEvent.VK_SHIFT); //按下空格键
		robot.keyPress(KeyEvent.VK_F2); //按下空格键
		
	}
}
