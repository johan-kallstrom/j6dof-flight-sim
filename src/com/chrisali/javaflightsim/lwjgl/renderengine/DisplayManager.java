/*******************************************************************************
 * Copyright (C) 2016-2017 Christopher Ali
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *  If you have any questions about this project, you can visit
 *  the project's GitHub repository at: http://github.com/chris-ali/j6dof-flight-sim/
 ******************************************************************************/
package com.chrisali.javaflightsim.lwjgl.renderengine;

import java.awt.Canvas;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import com.chrisali.javaflightsim.swing.GuiFrame;
import com.chrisali.javaflightsim.swing.SimulationWindow;

/**
 * Handles the creation, running and closing of the OTW display window 
 * 
 * @author Christopher Ali
 *
 */
public class DisplayManager {
	private static final int FPS_CAP = 60;
	
	private static int height = 900;
	private static int width = 1440;
	
	private static int aaSamples = 0;
	
	private static int colorDepth = 24;
	
	private static long lastFrameTime;
	private static float delta;
	
	/**
	 * Commands the creation of the OpenGL display; {@link SimulationWindow} object
	 * reference passed from {@link GuiFrame} used to set the {@link Canvas} parent object,
	 * allowing the display to be embedded within the simulation window
	 * 
	 * @param canvas
	 */
	public static void createDisplay(SimulationWindow simulationWindow) {
		try {
			ContextAttribs attribs = new ContextAttribs(3,3)
										.withForwardCompatible(true)
										.withProfileCore(true);
			Thread.sleep(500); // Slight pause to allow time for simulation window to initialize itself
			
			// AWT Canvas object that Display uses to set its parent to
			Canvas canvas = simulationWindow.getOutTheWindowCanvas();
			height = canvas.getHeight();
			width = canvas.getWidth();
					
			Display.setParent(canvas);
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create(new PixelFormat().withSamples(aaSamples).withDepthBits(colorDepth),attribs);
		} catch (LWJGLException | InterruptedException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, width, height);
		lastFrameTime = getCurrentTime();
	}
	
	/**
	 * Commands the creation of the OpenGL display in its own window
	 */
	public static void createDisplay() {
		try {
			ContextAttribs attribs = new ContextAttribs(3,3)
										.withForwardCompatible(true)
										.withProfileCore(true);
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle("Java Flight Simulator");
			Display.create(new PixelFormat().withSamples(aaSamples).withDepthBits(colorDepth),attribs);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, width, height);
		lastFrameTime = getCurrentTime();
	}
	
	/**
	 * Updates the display by rendering one frame based on the frame rate defined in {@link DisplayManager}
	 */
	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime= getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;	
		lastFrameTime = currentFrameTime;
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	public static void closeDisplay() {
		Display.destroy();
	}
	
	private static long getCurrentTime() {
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	public static int getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		DisplayManager.height = height;
	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		DisplayManager.width = width;
	}

	public static void setAaSamples(int aaSamples) {
		DisplayManager.aaSamples = aaSamples;
	}

	public static void setColorDepth(int colorDepth) {
		DisplayManager.colorDepth = colorDepth;
	}
}