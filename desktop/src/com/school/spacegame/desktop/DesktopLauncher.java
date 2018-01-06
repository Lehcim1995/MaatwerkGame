package com.school.spacegame.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.school.spacegame.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//TODO set settings
        // fullscreen
        // Game name
        // etc

        config.title = "SpaceGame";
//        config.useGL30 = true;
        config.vSyncEnabled = false;
        config.foregroundFPS = 60;
//        config.fullscreen = true;
        config.samples = 8;
        config.preferencesFileType = Files.FileType.Internal;

		new LwjglApplication(new Main(), config);
	}
}
