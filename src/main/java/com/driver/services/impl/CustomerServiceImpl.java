package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;


	// ------------------------------------------------------------------------------------------------

	@Override																			// 1st API - done
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override																			// 2nd API - done
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function

		Customer customer = customerRepository2.findById(customerId).get();

//		List<TripBooking> tripBookingList = customer.getTripBookingList();

//		for(TripBooking tripBooking : tripBookingList){
//			if(tripBooking.getStatus() == TripStatus.CONFIRMED){
//				tripBooking.setStatus(TripStatus.CANCELED);
//			}
//		}

		customerRepository2.deleteById(customerId);
	}

	@Override																			// 3rd API
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query

		Driver driver = null;
		List<Driver> driverList = driverRepository2.findAll();

		for(Driver st : driverList){
			if(st.getCab().getAvailable() == true) {

				if(driver == null || driver.getDriverId() > st.getDriverId())
					driver = st;
			}
		}

		// if no driver is available
		if(driver == null) {
			throw new Exception("No cab available!");
		}

		Customer customer = customerRepository2.findById(customerId).get();

		TripBooking tripBooking = new TripBooking();
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setStatus(TripStatus.CONFIRMED);

		tripBooking.setDriver(driver);					   // driver booked for that trip
		tripBooking.setCustomer(customer);                 // customer added for that trip


		// If cab is booked than cab , Driver and customer not available
		customer.getTripBookingList().add(tripBooking);
		driver.getTripBookingList().add(tripBooking);

		// Driver cab not available
		driver.getCab().setAvailable(false);

		// save the history in Repository
		driverRepository2.save(driver);
		customerRepository2.save(customer);

		return tripBooking;
	}

	@Override																			// 4th API - done
	public void cancelTrip(Integer tripId) {
		//Cancel the trip having given trip-Id and update TripBooking attributes accordingly

//		TripBooking tripBooking;
//
//		try{
//			tripBooking = tripBookingRepository2.findById(tripId).get();
//		}
//		catch (Exception e){
//			throw new Exception("TripId Does not exist in the database");
//		}

		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);                                // Bill become o because trip is cancelled
		tripBooking.getDriver().getCab().setAvailable(true);   // known cab is available

		tripBookingRepository2.save(tripBooking);
//		customerRepository2.save(tripBooking.getCustomer());    // add customer history in customer repo
//		driverRepository2.save(tripBooking.getDriver());		// add driver history in driver repo
	}

	@Override																			// 5th API - done
	public void completeTrip(Integer tripId) {
		//Complete the trip having given trip-Id and update TripBooking attributes accordingly

//		TripBooking tripBooking;
//
//		try{
//			tripBooking = tripBookingRepository2.findById(tripId).get();
//		}
//		catch (Exception e){
//			throw new Exception("TripId Does not exist in the database");
//		}

		// Check whether trip is already completed or cancelled

//		if(tripBooking.getStatus().compareTo(TripStatus.CANCELED) == 0 ||
//									tripBooking.getStatus().compareTo(TripStatus.COMPLETED) == 0)
//			return ;

		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.COMPLETED);
		tripBooking.getDriver().getCab().setAvailable(true);

		int distance = tripBooking.getDistanceInKm();
		Driver driver = tripBooking.getDriver();
		Cab cab = driver.getCab();
		int rate = cab.getPerKmRate();
		tripBooking.setBill(distance * rate);

		tripBookingRepository2.save(tripBooking);
//		driverRepository2.save(tripBooking.getDriver());
//		customerRepository2.save(tripBooking.getCustomer());
	}
}
