package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.time.LocalTime;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainSensorTest {
    private TrainController mockController;
    private TrainUser mockUser;
    private TrainSensor sensor;

    @Before
    public void init(){
        mockController = mock(TrainController.class);
        mockUser = mock(TrainUser.class);
        sensor = new TrainSensorImpl(mockController, mockUser);
    }

    @Test
    public void OverAbsoluteSpeedLimit(){
        sensor.overrideSpeedLimit(600);
        verify(mockUser, times(1)).setAlarmState(true);
    }

    @Test
    public void InAbsoluteSpeedLimit(){
        sensor.overrideSpeedLimit(250);
        verify(mockUser, times(1)).setAlarmState(false);
    }
    
    @Test
    public void OverRelativeSpeedLimit(){
        when(mockController.getReferenceSpeed()).thenReturn(150);
        sensor.overrideSpeedLimit(50);
        verify(mockUser, times(1)).setAlarmState(true);
    }

    @Test
    public void InRelativeSpeedLimit(){
        when(mockController.getReferenceSpeed()).thenReturn(150);
        sensor.overrideSpeedLimit(100);
        verify(mockUser, times(1)).setAlarmState(false);
    }
}
