package com.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;

import com.server.Database;
import com.server.HashTableDatabase;

public class MainF {

	private static Logs log = new MainLogs();

	public static void main(String[] args) {

		log.debug("Hello world!");

		try {
			Database db = new HashTableDatabase(log);
			db.createTable();
			fileList(null, db);
			db.getTable();
//			db.dropTable();
			db.closeConnection();
		} catch (Exception e) {
			log.debug(e);
		}

	}

	private static int count = 0;

	public static void fileList(String name, Database db) {
		if (name == null) {
			Scanner sc = new Scanner(System.in);
			name = sc.next();
		}
		log.debug(name);

		Path path = Paths.get(name);
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for (Path file : stream) {
				if (!Files.isDirectory(file)) {
					log.info(file.getFileName());
					long size = Files.size(file);
					log.info(size + " bytes");
					count++;
					db.insertTable(count, file.getFileName().toString(), name,
							size);
				} else
					fileList(file.toString(), db);
			}
		} catch (IOException ex) {
			log.error(ex);
		}
	}
}
