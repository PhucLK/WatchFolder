package com.Ex;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.Ex.constant.Constant;
import com.Ex.model.Person;
import com.Ex.service.PersonService;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		String filename = System.getProperty("user.dir") + Constant.CSV_FOLDER;
		Path path = Paths.get(filename);
		int i = 0;
		try {
			WatchService watchService = FileSystems.getDefault().newWatchService();
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
			WatchKey key;// = watchService.poll(10, TimeUnit.SECONDS);
			// DaemonThread daemonThread = new DaemonThread();
			try {

				while ((key = watchService.take()) != null) {
					i = i + 1;
					for (WatchEvent<?> event : key.pollEvents()) {
						System.out.println(Thread.currentThread().getName());
						// Thread.
//						System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
//						Thread.getAllStackTraces().keySet().forEach((t) -> t.getName().equals(Constant.WATCH_SERVICE_THREAD_NAME) ? ()
//							DaemonThread daemonThread = new DaemonThread("daemonThread");
//							daemonThread.setDaemon(true);
//							daemonThread.start();
//							daemonThread.join();
//						) : System.out.println(Thread.currentThread().getName());  );
//						if (Thread.currentThread().getName().equals(Constant.WATCH_SERVICE_THREAD_NAME)) {
//							System.out.println(Thread.currentThread().getName());
//							DaemonThread daemonThread = new DaemonThread("daemonThread");
//							daemonThread.setDaemon(true);
//							daemonThread.start();
//							daemonThread.join();
//						}
//						DaemonThread daemonThread = new DaemonThread("daemonThread");
//						daemonThread.setDaemon(true);
//						daemonThread.start();
						key.reset();
					}
					// System.out.println("Event kind:" + event.kind() + ". File affected: " +
					// event.context() + ".");
//					System.out.println(filename);
//
//					daemonThread.setDaemon(true);
//					daemonThread.start();
//					if (key != null) {
//						key.reset();
//					}

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

class DaemonThread extends Thread {
	public DaemonThread(String name) {
		super(name);
	}

	public void run() {
		String filename = System.getProperty("user.dir") + Constant.CSV_FOLDER;
		// Checking whether the thread is Daemon or not
		if (Thread.currentThread().isDaemon()) {
			System.out.println(getName() + " is Daemon thread");

//			PersonService personService = new PersonService();
//
//			List<Person> listPersons = new ArrayList<>();
//			personService.loopThroughAllFilesInFolder(filename);
//			listPersons = personService.getListPersons();
//			// listPersons = personService.getListPersonFromCSVFile(filename);
//
//			System.out.println(listPersons.size());
//			listPersons.forEach(System.out::println);

		}

//		else {
//			System.out.println(getName() + " is User thread");
//		}

	}

}
