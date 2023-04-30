package com.driver.controllers;

import com.driver.model.Customer;
import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;


	// ---------------------------------------------------------------------------------------------------

	@PostMapping("/register")																// 1st API
	public ResponseEntity<Void> registerCustomer(@RequestBody Customer customer){

		customerService.register(customer);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/delete")																// 2nd API
	public void deleteCustomer(@RequestParam Integer customerId){

		customerService.deleteCustomer(customerId);
	}

	@PostMapping("/bookTrip")															    // 3rd API
	public ResponseEntity<Integer> bookTrip(@RequestParam Integer customerId, @RequestParam String fromLocation,
											@RequestParam String toLocation, @RequestParam Integer distanceInKm) throws Exception {

		TripBooking bookedTrip = customerService.bookTrip(customerId,fromLocation,toLocation,distanceInKm);

		return new ResponseEntity<>(bookedTrip.getTripBookingId(), HttpStatus.CREATED);
	}

	@DeleteMapping("/complete")																// 4th API
	public void completeTrip(@RequestParam Integer tripId) {

		customerService.completeTrip(tripId);
	}

	@DeleteMapping("/cancelTrip")															// 5th API
	public void cancelTrip(@RequestParam Integer tripId) {

		customerService.cancelTrip(tripId);
	}
}
