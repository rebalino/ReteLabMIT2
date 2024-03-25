package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

import com.google.common.collect.Table;
import com.google.common.collect.HashBasedTable;
import java.time.LocalTime;

public class TrainSensorImpl implements TrainSensor {

	private TrainController controller;
	private TrainUser user;
	private int speedLimit = 5;
	private Table<LocalTime, Integer, Integer> table = HashBasedTable.create();

	public TrainSensorImpl(TrainController controller, TrainUser user) {
		this.controller = controller;
		this.user = user;
	}

	@Override
	public int getSpeedLimit() {
		return speedLimit;
	}

	@Override
	public void overrideSpeedLimit(int speedLimit) {
		if((speedLimit < 0 || speedLimit > 500) || (speedLimit < getReferenceSpeed() / 2)){
			user.setAlarmState(true);
		}
		else{
			user.setAlarmState(false);
		}
		this.speedLimit = speedLimit;
		controller.setSpeedLimit(speedLimit);
	}

	@Override
	public LocalTime getCurrentTime(){
		return LocalTime.now();
	}

	@Override
	public int getJoystickPosition(){
		return user.getJoystickPosition();
	}

	@Override
	public int getReferenceSpeed(){
		return controller.getReferenceSpeed();
	}

	@Override
	public void addToTable(){
		table.put(getCurrentTime(), getJoystickPosition(), getReferenceSpeed());
	}

	@Override
	public int getTableSize(){
		return table.size();
	}
	
}
