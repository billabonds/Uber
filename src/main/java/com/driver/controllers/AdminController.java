package com.driver.controllers;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@PostMapping("/register")																		// 1st API
	public ResponseEntity<Void> registerAdmin(@RequestBody Admin admin){

		adminService.adminRegister(admin);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/update")																			// 2nd API
	public ResponseEntity<Admin> updateAdminPassword(@RequestParam Integer adminId, @RequestParam String password) {

		Admin updatedAdmin = adminService.updatePassword(adminId,password);
		return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
	}

	@DeleteMapping("/delete")																		// 3rd API
	public void deleteAdmin(@RequestParam Integer adminId) {

		adminService.deleteAdmin(adminId);
	}

	@GetMapping("/listOfCustomers")																	// 4th API
	public List<Customer> listOfCustomers() {

		List<Customer> listOfCustomers = adminService.getListOfCustomers();
		return listOfCustomers;
	}

	@GetMapping("/listOfDrivers")																	// 5th API
	public List<Driver> listOfDrivers() {

		List<Driver> listOfDrivers = adminService.getListOfDrivers();
		return listOfDrivers;
	}
}



