package clb.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.stereotype.Component;

import clb.business.SimpleService;
import clb.database.entities.DataLogger;

@Component
@ViewScoped
@ManagedBean
public class GraphicBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{simpleService}")
	private SimpleService simpleService;

	private LineChartModel lineModel;

	private static final int NUMBER_OF_HOURS = 24;

	private List<String> hours ;

	private List<Number> numbers;


	@PostConstruct
	public void init(){

		if(lineModel == null){
			lineModel = new LineChartModel();
		}
		
		hours = new ArrayList<String>();
		numbers = new ArrayList<Number>();


		lineModel.setTitle("Energy Variation");
		lineModel.setLegendPosition("e");

		lineModel.getAxes().put(AxisType.X, new CategoryAxis("Hours"));

		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setLabel("Power");
		yAxis.setMin(0);
		yAxis.setMax(100);

		LineChartSeries series = new LineChartSeries();
		series.setLabel("Variable Variation");

		Random r = new Random();
		
		for(int i=0;i<NUMBER_OF_HOURS;i++){
			Integer numberGenerated = r.nextInt(100);
			String hoursStr = i+":00";

			if(i < 10)
				hoursStr = "0" + hoursStr;

			numbers.add(i,numberGenerated);
			hours.add(i,hoursStr);
			series.set(hoursStr,numberGenerated);
		}

		lineModel.addSeries(series);
	}


	public void createDataLogger(){

		DataLogger dataLogger = new DataLogger();

		simpleService.createDataLogger(dataLogger);

		System.out.println( "Data Logger created successfully!" );
	}

	public void updateChartValues(){

	}

	public SimpleService getSimpleService() {
		return simpleService;
	}

	public void setSimpleService( SimpleService simpleService ) {
		this.simpleService = simpleService;
	}

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}


}
