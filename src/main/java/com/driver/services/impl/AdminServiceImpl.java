package com.driver.services.impl;

import java.util.List;

import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.AdminRepository;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository1;

	@Autowired
	DriverRepository driverRepository1;

	@Autowired
	CustomerRepository customerRepository1;



	// ------------------------------------------------------------------------------------------------

	@Override                                                                                // 1st API - done
	public void adminRegister(Admin admin) {
		//Save the admin in the database

		adminRepository1.save(admin);
	}

	@Override                                                                                // 2nd API - done
	public Admin updatePassword(Integer adminId, String password) {
		//Update the password of admin with given id

//		Admin admin;
//
//		try {
//			admin = adminRepository1.findById(adminId).get();
//		} catch (Exception e) {
//			throw new Exception("Admin does not Exist in the database ");
//		}

		Admin admin = adminRepository1.findById(adminId).get();
		admin.setPassword(password);
		adminRepository1.save(admin);
		return admin;
	}

	@Override                                                                                // 3rd - done
	public void deleteAdmin(int adminId)  {
		// Delete admin without using deleteById function

//		Admin admin;
//
//		try {
//			admin = adminRepository1.findById(adminId).get();
//		} catch (Exception e) {
//			throw new Exception("Admin does not Exist in the database ");
//		}

		Admin admin = adminRepository1.findById(adminId).get();
		adminRepository1.deleteById(adminId);
	}

	@Override                                                                                // 4th API - done
	public List<Driver> getListOfDrivers() {
		//Find the list of all drivers

		List<Driver> list = driverRepository1.findAll();
		return list;
	}

	@Override                                                                                // 5th API - done
	public List<Customer> getListOfCustomers() {
		//Find the list of all customers

		List<Customer> list = customerRepository1.findAll();
		return list;
	}
}


