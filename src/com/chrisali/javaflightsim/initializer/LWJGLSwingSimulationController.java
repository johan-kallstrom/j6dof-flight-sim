/*******************************************************************************
 * Copyright (C) 2016-2020 Christopher Ali
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
package com.chrisali.javaflightsim.initializer;

import java.util.EnumSet;

import com.chrisali.javaflightsim.lwjgl.LWJGLWorld;
import com.chrisali.javaflightsim.simulation.SimulationRunner;
import com.chrisali.javaflightsim.simulation.datatransfer.SimulationEventListener;
import com.chrisali.javaflightsim.simulation.integration.Integrate6DOFEquations;
import com.chrisali.javaflightsim.simulation.setup.Options;
import com.chrisali.javaflightsim.simulation.setup.SimulationConfiguration;
import com.chrisali.javaflightsim.simulation.setup.Trimming;
import com.chrisali.javaflightsim.simulation.utilities.FileUtilities;
import com.chrisali.javaflightsim.swing.GuiFrame;
import com.chrisali.javaflightsim.swing.consoletable.ConsoleTablePanel;
import com.chrisali.javaflightsim.swing.plotting.PlotWindow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controls the configuration and running of processes supporting the simulation component of JavaFlightSim. This consists of: 
 * <p>The simulation engine that integrates the 6DOF equations ({@link Integrate6DOFEquations})</p>
 * <p>Initialization and control of the LWJGL out the window (OTW) world ({@link LWJGLWorld})</p>
 * <p>Initializing the Swing GUI menus</p>
 * <p>Plotting of the simulation states and data ({@link PlotWindow})</p>
 * <p>Raw data display of simulation states ({@link ConsoleTablePanel})</p>
 * 
 * @author Christopher Ali
 *
 */
public class LWJGLSwingSimulationController implements SimulationEventListener {
	
	//Logging
	private static final Logger logger = LogManager.getLogger(LWJGLSwingSimulationController.class);
	
	// Configuration
	private SimulationConfiguration configuration;
	private EnumSet<Options> options;
			
	// Simulation and Threads
	private SimulationRunner runner;
	private Thread runnerThread;
	
	// Menus
	private GuiFrame guiFrame;
	
	// Plotting
	private PlotWindow plotWindow;
	
	// Raw Data Console
	private ConsoleTablePanel consoleTablePanel;

	private boolean wasReset = false;
		
	/**
	 * Initializes initial settings, configurations and conditions to be edited through menu options
	 */
	public LWJGLSwingSimulationController(SimulationConfiguration configuration) {
		this.configuration = configuration;
		guiFrame = new GuiFrame(configuration);
		guiFrame.addSimulationEventListener(this);
	}
	
	/**
	 * Initializes, trims and starts the flight controls, simulation (and flight and environment data, if selected) threads.
	 * Depending on options specified, a console panel and/or plot window will also be initialized and opened 
	 */
	@Override
	public boolean onStartSimulation() {
		if (runner != null && runner.isRunning()) {
			logger.warn("Simulation is already running! Please wait until it has finished");
			return false;
		}
		
		configuration = FileUtilities.readSimulationConfiguration();
		options = configuration.getSimulationOptions();
			
		logger.info("Starting simulation...");
		
		logger.info("Trimming aircraft...");
		Trimming.trimSim(configuration, false);
		
		logger.info("Initializing simulation runner...");
		runner = new SimulationRunner(configuration);
		runner.addSimulationEventListener(this);

		logger.info("Initializaing and starting simulation runner thread...");
		runnerThread = new Thread(runner);
		runnerThread.start();
				
		if (options.contains(Options.CONSOLE_DISPLAY))
			onInitializeConsole();

		return false;
	}
	
	/**
	 * Stops simulation and console table refresh if running, calls generate plots event if in Analysis Mode, 
	 * and opens main menus again if not visible
	 */
	@Override
	public void onStopSimulation() {
		if (runner.isRunning()) {
			logger.info("Stopping simulation...");
			runner.setRunning(false);
		}

		if (options.contains(Options.ANALYSIS_MODE))
			onPlotSimulation();
		
		if (!guiFrame.isVisible()) {
			logger.info("Returning to menus...");
			guiFrame.setVisible(true);
		}
	}
	
	/**
	 * Pauses and unpauses the simulation 
	 */
	@Override
	public void onPauseUnpauseSimulation() {
		if(!options.contains(Options.PAUSED)) {
			options.add(Options.PAUSED);
		} else {
			options.remove(Options.PAUSED);
			options.remove(Options.RESET);
			wasReset = false;
		} 
	}

	/**
	 * When the simulation is paused, it can be reset back to initial conditions once per pause 
	 */
	@Override
	public void onResetSimulation() {
		if(options.contains(Options.PAUSED) && !options.contains(Options.RESET) && !wasReset) {
			options.add(Options.RESET);
			logger.debug("Resetting simulation to initial conditions...");
			wasReset = true;
		}
	}

	/**
	 * Initializes the plot window if not already initialized, otherwise refreshes the window and sets it visible again
	 */
	@Override
	public void onPlotSimulation() {
		logger.info("Plotting simulation results...");
		
		try {
			if(plotWindow != null)
				plotWindow.setVisible(false);
				
			plotWindow = new PlotWindow(configuration.getSelectedAircraft(), runner.getLogsOut());
		} catch (Exception e) {
			logger.error("An error occurred while generating plots!", e);
		}
	}

	/**
	 * Initializes the raw data console window
	 */
	@Override
	public void onInitializeConsole() {
		try {
			logger.info("Starting flight data console...");
			
			if(consoleTablePanel != null)
				consoleTablePanel.setVisible(false);
			
			consoleTablePanel = new ConsoleTablePanel(runner.getLogsOut());			
		} catch (Exception e) {
			logger.error("An error occurred while starting the console panel!", e);
		}
	}
}
