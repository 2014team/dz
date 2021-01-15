
package com.artcweb.test;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sun.glass.events.KeyEvent;

public class Shell {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//
		// Runtime.getRuntime().exec("C:\\Users\\Administrator\\Desktop\\开发工具\\phtoshopzxpjb\\pscc2017_gr\\PhotoshopPortable.exe");

		// Runtime.getRuntime().exec("C:/Windows/System32/cmd.exe /k start
		// C:/Users/Administrator/Desktop/test/1.html"); // 通过cmd命令打开一个网页
		// Runtime.getRuntime().exec("C:\\Users\\Administrator\\Desktop\\开发工具\\phtoshopzxpjb\\pscc2017_gr\\PhotoshopPortable.exe
		// /k start C:/Users/Administrator/Desktop/shell/pixel_art_group.atn");
		// // 通过cmd命令打开一个网页
		// Runtime runtime=Runtime.getRuntime();
		 Process p;  
		try {
			// runtime.exec("C:\\Users\\Administrator\\Desktop\\开发工具\\phtoshopzxpjb\\pscc2017_gr\\PhotoshopPortable.exe
			// /k start " + KeyEvent.VK_SHIFT +KeyEvent.VK_F12);
			 //runtime.exec("C:\\Users\\Administrator\\Desktop\\开发工具\\phtoshopzxpjb\\pscc2017_gr\\PhotoshopPortable.exe");
			 p = Runtime.getRuntime().exec("C:/Users/Administrator/Desktop/pst/AA.exe C:/Users/Administrator/Desktop/test/3.jpg");
			
			 
			 InputStream fis=p.getInputStream();    
	            //用一个读输出流类去读    
	             InputStreamReader isr=new InputStreamReader(fis);    
	            //用缓冲器读行    
	             BufferedReader br=new BufferedReader(isr);    
	             String line=null;    
	            //直到读完为止    
	            while((line=br.readLine())!=null)    
	             {    
	                 System.out.println(line);    
	             }    
			 // runtime.exec("C:/Windows/System32/cmd.exe /k " +
			// KeyEvent.VK_SHIFT +KeyEvent.VK_F12 );

			 //Runtime.getRuntime().exec("C:/Windows/System32/cmd.exe /c calc");

			

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
