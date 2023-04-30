package com.driver.controllers;

import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/driver")
public class DriverController {

	@Autowired
	DriverService driverService;

	@PostMapping(value = "/register")															// 1st API
	public ResponseEntity<Void> registerDriver(@RequestParam String mobile, @RequestParam String password){

		driverService.register(mobile,password);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete")															// 2nd API
	public void deleteDriver(@RequestParam Integer driverId){

		driverService.removeDriver(driverId);
	}

	@PutMapping("/status")																		// 3rd API
	public void updateStatus(@RequestParam Integer driverId)  {

		driverService.updateStatus(driverId);
	}
}
