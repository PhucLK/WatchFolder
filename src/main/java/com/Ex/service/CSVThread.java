package com.Ex.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.Ex.constant.Constant;
import com.Ex.model.Person;
import com.opencsv.bean.CsvToBeanBuilder;

@Component
public class CSVThread extends Thread {

	final String filename = System.getProperty("user.dir") + Constant.CSV_FOLDER;
	final String url = "http://localhost:8080/person/list";

	private List<Person> listPersons = new ArrayList<>();

	public CSVThread(String name) {
		super(name);
	}

	public CSVThread() {
		// TODO Auto-generated constructor stub
	}

//	public void processThread() {
//		watchFolder(filename);
//	}

	public void run() {

		Path path = Paths.get(filename);
		try {
			WatchService watchService = FileSystems.getDefault().newWatchService();
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
			WatchKey key;
			int i = 0;
			Path dir = Paths.get(filename);
			try {
				if (i == 0) {
					try {
						Files.walk(dir).forEach(p -> {
							if (p.toFile().isFile() && p.toFile().getAbsolutePath().contains(Constant.CSV_STRING)) {

								List<Person> listPersons2 = new ArrayList<>();
								try {
									listPersons2 = new CsvToBeanBuilder<Person>(
											new FileReader(p.toFile().getAbsolutePath())).withType(Person.class)
													.withSkipLines(1).build().parse();
								} catch (IllegalStateException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								this.listPersons = Stream.concat(this.listPersons.stream(), listPersons2.stream())
										.collect(Collectors.toList());
							}
						});

						// listPersons.forEach(p -> System.out.println(p.toString()));

						listPersons.forEach(p -> {
							RestTemplate restTemplate = new RestTemplate();
							restTemplate.getMessageConverters().add(0,
									new StringHttpMessageConverter(StandardCharsets.UTF_8));

							HttpHeaders headers = new HttpHeaders();
							headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

							// headers.s

							MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
							map.add("name", p.getName());
							map.add("age", p.getAge());

							HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
									map, headers);

							// ResponseEntity<String> response =
							restTemplate.postForEntity(url, request, String.class);
						});

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				while ((key = watchService.take()) != null) {
					i = i + 1;
					for (WatchEvent<?> event : key.pollEvents()) {
						System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");

//						Thread.getAllStackTraces().keySet().forEach((t) -> System.out
//								.println(t.getName() + "\nIs Daemon " + t.isDaemon() + "\nIs Alive " + t.isAlive()));

						try {
							Files.walk(dir).forEach(p -> {
								if (p.toFile().isFile() && p.toFile().getAbsolutePath().contains(Constant.CSV_STRING)) {

									List<Person> listPersons2 = new ArrayList<>();
									try {
										listPersons2 = new CsvToBeanBuilder<Person>(
												new FileReader(p.toFile().getAbsolutePath())).withType(Person.class)
														.withSkipLines(1).build().parse();
									} catch (IllegalStateException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (FileNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									this.listPersons = Stream.concat(this.listPersons.stream(), listPersons2.stream())
											.collect(Collectors.toList());
								}
							});

							// listPersons.forEach(p -> System.out.println(p.toString()));

							listPersons.forEach(p -> {
								RestTemplate restTemplate = new RestTemplate();
								restTemplate.getMessageConverters().add(0,
										new StringHttpMessageConverter(StandardCharsets.UTF_8));

								HttpHeaders headers = new HttpHeaders();
								headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

								MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
								map.add("name", p.getName());
								map.add("age", p.getAge());

								HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
										map, headers);

								// ResponseEntity<String> response =
								restTemplate.postForEntity(url, request, String.class);
							});

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						key.reset();
					}

				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(getName() + " is Daemon thread");
		// watchFolder();
//		System.out.println("after");
//		this.listPersons.forEach(p -> System.out.println(p.toString()));
//
//		// Checking whether the thread is Daemon or not
//		if (Thread.currentThread().isDaemon()) {
//			// Thread thread = Thread.currentThread();
//			System.out.println("before");
//			System.out.println(getName() + " is Daemon thread");
//			watchFolder();
//			System.out.println("after");
//			this.listPersons.forEach(p -> System.out.println(p.toString()));
//			// loopThroughAllFilesInFolder(filename);
////			watchFolder();
////			try {
////				thread.join();
////				watchFolder();
////				System.out.println("after");
////				this.listPersons.forEach(p -> System.out.println(p.toString()));
////			} catch (InterruptedException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//
//		}
//
//		else {
//			System.out.println(getName() + " is User thread");
//		}

	}

	public void loopThroughAllFilesInFolder() {

		Path dir = Paths.get(filename);
		try {
			Files.walk(dir).forEach(path -> readFile(path.toFile()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void readFile(File file) {

		if (file.isFile() && file.getAbsolutePath().contains(Constant.CSV_STRING)) {
//			List<Person> listPersons2 = new ArrayList<>();
//			listPersons2 = getListPersonFromCSVFile(file.getAbsolutePath());
			this.listPersons = Stream
					.concat(this.listPersons.stream(), getListPersonFromCSVFile(file.getAbsolutePath()).stream())
					.collect(Collectors.toList());
		}
	}

	public List<Person> getListPersons() {
		return listPersons;
	}

	public void setListPersons(List<Person> listPersons) {
		this.listPersons = listPersons;
	}

	public List<Person> getListPersonFromCSVFile(String fileName) {

		List<Person> listPersons2 = new ArrayList<>();
		try {
			listPersons2 = new CsvToBeanBuilder<Person>(new FileReader(fileName)).withType(Person.class)
					.withSkipLines(1).build().parse();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listPersons2;
	}

	public void watchFolder() {
		Path path = Paths.get(filename);
		try {
			WatchService watchService = FileSystems.getDefault().newWatchService();
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
			WatchKey key;
			int i = 0;
			try {
				if (i == 0) {
					loopThroughAllFilesInFolder();
				}
				while ((key = watchService.take()) != null) {
					i = i + 1;
					for (WatchEvent<?> event : key.pollEvents()) {
						System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");

//						Thread.getAllStackTraces().keySet().forEach((t) -> System.out
//								.println(t.getName() + "\nIs Daemon " + t.isDaemon() + "\nIs Alive " + t.isAlive()));

						loopThroughAllFilesInFolder();
						key.reset();
					}

				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
