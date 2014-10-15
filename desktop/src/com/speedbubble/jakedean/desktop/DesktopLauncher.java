package com.speedbubble.jakedean.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.speedbubble.jakedean.SpeedBubble;

public class DesktopLauncher{
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "SPEED BUBBLES";
		config.width = 1280;
		config.height = 720;
		
		new LwjglApplication(new SpeedBubble(new DesktopActionResolver()), config);
	}

}
