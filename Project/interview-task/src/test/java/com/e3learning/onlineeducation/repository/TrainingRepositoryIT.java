package com.e3learning.onlineeducation.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import testsuits.IntegrationTest;

import com.e3learning.onlineeducation.model.Account;
import com.e3learning.onlineeducation.model.AccountStatus;
import com.e3learning.onlineeducation.model.Address;
import com.e3learning.onlineeducation.model.Course;
import com.e3learning.onlineeducation.model.Training;

@Category(IntegrationTest.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TrainingRepositoryIT {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	CountryRepository countryRepository;
	
	@Autowired
	TrainingRepository trainingRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Before
	public void setUp() {
		for (int i = 1; i <= 20; i++) {
			Account account = new Account();
			
			Address address = new Address();			
			address.setCountry(countryRepository.findOne(1));
			address.setState("state");
			address.setStreetName("streetName");
			address.setSuburb("suburb");
			address = addressRepository.save(address);
			
			account.setAddress(address);
			account.setEmail("email@domain.com");
			account.setFirstName("firstName" + i );
			account.setLastName("lastName" + i);
			account.setStatus(AccountStatus.ACTIVE);
			account = accountRepository.save(account);
			
			Course course = new Course();
			course.setTitle("newcourse" + i);
			course = courseRepository.save(course);
			
			Training training = new Training();
			training.setAccount(account);
			training.setCourse(course);
			training.setStartDate(new Date());
			training = trainingRepository.save(training);
		}
		accountRepository.flush();
	}

	@Test
	public void testFindByAccount() {
		for (int i = 1; i <= 20; i++) {
			List<Account> account = accountRepository.findByFirstNameLike("firstName" + i);
			Page<Training> trainings = trainingRepository.findByAccount(account.get(0), new PageRequest(0, 5));
			assertEquals(1,trainings.getContent().size());
			assertEquals(trainings.getContent().get(0).getCourse().getTitle(),"newcourse" + i);
		}
	}
	
	
}
