package utils;

import java.util.Comparator;

public class SortFlight implements Comparator<FlightDetails> {

	@Override
	public int compare(FlightDetails flightOne, FlightDetails flightTwo) {		
		return Integer.compare((int) flightOne.getDurationInMinutes(),
				(int) flightTwo.getDurationInMinutes());
	}

}
