package com.chrisali.javaflightsim.utilities.plotting;

import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

public class PlotUtilities {
	private static HashMap<PlotType, XYPlot> plotLists = new HashMap<>();
	
	static void makePlotLists(ArrayList<Double[]> logsOut) {
		
		// Create XY series for each set of data
		
		XYSeries uData        = new XYSeries("u");
		XYSeries vData        = new XYSeries("v");
		XYSeries wData        = new XYSeries("w");
		
		XYSeries posData      = new XYSeries("Position");
		XYSeries altData      = new XYSeries("Altitude");
		
		XYSeries altDotData   = new XYSeries("Alt Dot");
		
		XYSeries phiData      = new XYSeries("Phi");
		XYSeries thetaData    = new XYSeries("Theta");
		XYSeries psiData      = new XYSeries("Psi");
		
		XYSeries pData        = new XYSeries("p");
		XYSeries qData        = new XYSeries("q");
		XYSeries rData        = new XYSeries("r");
				
		XYSeries axData       = new XYSeries("a_x");
		XYSeries ayData       = new XYSeries("a_y");
		XYSeries azData       = new XYSeries("a_z");		
		
		XYSeries lData        = new XYSeries("L");
		XYSeries mData        = new XYSeries("M");
		XYSeries nData        = new XYSeries("N");
		
		XYSeries tasData      = new XYSeries("TAS");
		
		XYSeries betaData     = new XYSeries("Beta");
		XYSeries alphaData    = new XYSeries("Alpha");
		
		XYSeries elevData     = new XYSeries("Elevator");
		XYSeries ailData      = new XYSeries("Aileron");
		XYSeries rudData      = new XYSeries("Rudder");
		XYSeries throtData    = new XYSeries("Throttle");
		XYSeries flapData     = new XYSeries("Flaps");
		
		XYSeries alphaDotData = new XYSeries("Alpha Dot");
		XYSeries machData     = new XYSeries("Mach");
		
		// Add data from logsOut to each XYSeries
		
		for (Double[] y : logsOut) {
			uData.add(y[0],y[1]);           // u
			vData.add(y[0],y[2]);           // v
			wData.add(y[0],y[3]);           // w
			
			posData.add(y[5],y[4]);         // NE Position
			altData.add(y[0],y[6]);         // Altitude
			
			altDotData.add(y[0], y[27]);    // Alt Dot
			
			phiData.add(y[0],y[7]);         // phi
			thetaData.add(y[0],y[8]);       // theta
			psiData.add(y[0],y[9]);         // psi
			
			pData.add(y[0],y[10]);          // p
			qData.add(y[0],y[11]);          // q
			rData.add(y[0],y[12]);          // r
			
			axData.add(y[0],y[22]);         // a_x
			ayData.add(y[0],y[23]);         // a_y
			azData.add(y[0],y[24]);         // a_z
			
			lData.add(y[0],y[19]);          // L
			mData.add(y[0],y[20]);          // M
			nData.add(y[0],y[21]);          // N
			
			tasData.add(y[0], y[13]);       // TAS
			
			betaData.add(y[0],y[14]);       // beta
			alphaData.add(y[0],y[15]);      // alpha
			
			elevData.add(y[0], y[37]);	    // elevator
			ailData.add(y[0], y[38]);	    // aileron
			rudData.add(y[0], y[39]);	    // rudder
			throtData.add(y[0], y[40]);	    // throttle
			flapData.add(y[0], y[41]);	    // flaps
			
			alphaDotData.add(y[0], y[42]);  // alphaDot
			machData.add(y[0], y[43]);		// mach
		}
		
		// Create XYSeriesCollections for each desired plot and add series to them
		
		XYSeriesCollection linearVelSeries    = new XYSeriesCollection();
		linearVelSeries.addSeries(uData);
		linearVelSeries.addSeries(vData);
		linearVelSeries.addSeries(wData);
		
		XYSeriesCollection positionSeries     = new XYSeriesCollection();
		positionSeries.addSeries(posData);
		
		XYSeriesCollection altitudeSeries     = new XYSeriesCollection();
		altitudeSeries.addSeries(altData);
		
		XYSeriesCollection altDotSeries       = new XYSeriesCollection();
		altDotSeries.addSeries(altDotData);
		
		XYSeriesCollection headingSeries      = new XYSeriesCollection();
		headingSeries.addSeries(psiData);
		
		XYSeriesCollection eulerAnglesSeries  = new XYSeriesCollection();
		eulerAnglesSeries.addSeries(phiData);
		eulerAnglesSeries.addSeries(thetaData);
		
		XYSeriesCollection angularRatesSeries = new XYSeriesCollection();
		angularRatesSeries.addSeries(pData);
		angularRatesSeries.addSeries(qData);
		angularRatesSeries.addSeries(rData);
		
		XYSeriesCollection linearAccelSeries  = new XYSeriesCollection();
		linearAccelSeries.addSeries(axData);
		linearAccelSeries.addSeries(ayData);
		linearAccelSeries.addSeries(azData);
		
		XYSeriesCollection totalMomentSeries  = new XYSeriesCollection();
		totalMomentSeries.addSeries(lData);
		totalMomentSeries.addSeries(mData);
		totalMomentSeries.addSeries(nData);
		
		XYSeriesCollection tasSeries          = new XYSeriesCollection();
		tasSeries.addSeries(tasData);
		
		XYSeriesCollection windParamSeries    = new XYSeriesCollection();
		windParamSeries.addSeries(betaData);
		windParamSeries.addSeries(alphaData);
		
		XYSeriesCollection elevSeries		  = new XYSeriesCollection();
		elevSeries.addSeries(elevData);
		
		XYSeriesCollection ailSeries		  = new XYSeriesCollection();
		ailSeries.addSeries(ailData);
		
		XYSeriesCollection rudSeries		  = new XYSeriesCollection();
		rudSeries.addSeries(rudData);
		
		XYSeriesCollection throtSeries		  = new XYSeriesCollection();
		throtSeries.addSeries(throtData);
		
		XYSeriesCollection flapSeries		  = new XYSeriesCollection();
		flapSeries.addSeries(flapData);
		
		XYSeriesCollection alphaDotSeries	  = new XYSeriesCollection();
		alphaDotSeries.addSeries(alphaDotData);
		
		XYSeriesCollection machSeries		  = new XYSeriesCollection();
		machSeries.addSeries(machData);
		
		// Create plots, add series collections to them and put the plots into a HashMap with an enum key
		
		XYPlot linearVelPlot    = new XYPlot(linearVelSeries,    
											 null,
											 new NumberAxis("Velocity [ft/sec]"), 
									 		 new StandardXYItemRenderer()); 
		
		plotLists.put(PlotType.VELOCITY, linearVelPlot);
		
		XYPlot positionPlot     = new XYPlot(positionSeries, 
											 new NumberAxis("East [ft]"), 
											 new NumberAxis("North [ft]"), 
											 new StandardXYItemRenderer());
		
		plotLists.put(PlotType.POSITION, positionPlot);
		
		XYPlot altitudePlot     = new XYPlot(altitudeSeries, 
											 null, 
											 new NumberAxis("Altitude [ft]"), 
											 new StandardXYItemRenderer());

		plotLists.put(PlotType.ALTITUDE, altitudePlot);
		
		XYPlot altDotPlot       = new XYPlot(altDotSeries, 
										     null, 
										     new NumberAxis("Vertical Speed [ft/sec]"), 
										     new StandardXYItemRenderer());

		plotLists.put(PlotType.VERT_SPEED, altDotPlot);
		
		XYPlot headingPlot      = new XYPlot(headingSeries, 
											 null, 
											 new NumberAxis("Heading [rad]"), 
											 new StandardXYItemRenderer());
							
		plotLists.put(PlotType.HEADING, headingPlot);
		
		XYPlot eulerAnglesPlot  = new XYPlot(eulerAnglesSeries, 
											 null, 
											 new NumberAxis("Angle [rad]"), 
											 new StandardXYItemRenderer());
		
		plotLists.put(PlotType.EULER_ANGLES, eulerAnglesPlot);
		
		XYPlot angularRatesPlot = new XYPlot(angularRatesSeries, 
											 null, 
									     	 new NumberAxis("Rate [rad/sec]"), 
									     	 new StandardXYItemRenderer());
		
		plotLists.put(PlotType.ANGULAR_RATE, angularRatesPlot);
		
		XYPlot linearAccelPlot  = new XYPlot(linearAccelSeries, 
										     null, 
											 new NumberAxis("Acceleration [g]"), 
											 new StandardXYItemRenderer());
		
		plotLists.put(PlotType.ACCELERATION, linearAccelPlot);
		
		XYPlot totalMomentPlot  = new XYPlot(totalMomentSeries, 
											 null, 
											 new NumberAxis("Moment [ft*lb]"), 
											 new StandardXYItemRenderer()); 
		
		plotLists.put(PlotType.MOMENT, totalMomentPlot);
		
		XYPlot tasPlot          = new XYPlot(tasSeries, 
										     null, 
										     new NumberAxis("True Airspeed [ft/sec]"), 
										     new StandardXYItemRenderer());

		plotLists.put(PlotType.TAS, tasPlot);
		
		XYPlot windParamPlot    = new XYPlot(windParamSeries, 
											 null, 
											 new NumberAxis("Angle [rad]"), 
											 new StandardXYItemRenderer());
		
		plotLists.put(PlotType.WIND_PARAM, windParamPlot);
		
		XYPlot elevPlot    		= new XYPlot(elevSeries, 
											 null, 
											 new NumberAxis("Deflection [rad]"), 
											 new StandardXYItemRenderer());

		plotLists.put(PlotType.ELEVATOR, elevPlot);
		
		XYPlot ailPlot		    = new XYPlot(ailSeries, 
											 null, 
											 new NumberAxis("Deflection [rad]"), 
											 new StandardXYItemRenderer());
		
		plotLists.put(PlotType.AILERON, ailPlot);
		
		XYPlot rudPlot    		= new XYPlot(rudSeries, 
											 null, 
											 new NumberAxis("Deflection [rad]"), 
											 new StandardXYItemRenderer());
		
		plotLists.put(PlotType.RUDDER, rudPlot);
		
		XYPlot throtPlot        = new XYPlot(throtSeries, 
											 null, 
											 new NumberAxis("Position [norm]"), 
											 new StandardXYItemRenderer());
		
		plotLists.put(PlotType.THROTTLE, throtPlot);
		
		XYPlot flapPlot         = new XYPlot(flapSeries, 
											 null, 
											 new NumberAxis("Deflection [rad]"), 
											 new StandardXYItemRenderer());
		
		plotLists.put(PlotType.FLAPS, flapPlot);
		
		XYPlot alphaDotPlot     = new XYPlot(alphaDotSeries, 
											 null, 
											 new NumberAxis("Rate [rad/sec]"), 
											 new StandardXYItemRenderer());
							
		plotLists.put(PlotType.ALPHA_DOT, alphaDotPlot);

		XYPlot machPlot         = new XYPlot(machSeries, 
											 null, 
											 new NumberAxis("Mach Number"), 
											 new StandardXYItemRenderer());

		plotLists.put(PlotType.MACH, machPlot);
	}
	
	public static HashMap<PlotType, XYPlot> getPlotLists() {return plotLists;}
	
	static void generatePlotWindows(SimulationPlots simPlots) {
		simPlots.pack();
		RefineryUtilities.centerFrameOnScreen(simPlots);
		simPlots.setVisible(true);
	}
}