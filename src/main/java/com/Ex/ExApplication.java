package com.Ex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import com.Ex.model.Person;
import com.Ex.service.CSVThread;
import com.Ex.service.PersonService;

@SpringBootApplication(scanBasePackages = { "com.Ex" })
public class ExApplication extends SpringBootServletInitializer {

	@Autowired
	private static PersonService personService;

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ExApplication.class, args);

//		Thread t = new Thread();
//		t.start();
//		t.join(1000);
		System.out.println("-------------------------------------");
		List<Person> listPersons = new ArrayList<>();
		CSVThread csvThread = new CSVThread();
		//csvThread.setDaemon(true);
		csvThread.run();
		// csvThread.join();
//		listPersons = csvThread.getListPersons();
//		System.out.println(listPersons.size());
//		// listPersons.forEach(p -> System.out.println(p.toString()));
//		// save to database
//		if (listPersons.size() > 0) {
//			personService.saveAll(listPersons);
//		}

	}

}
