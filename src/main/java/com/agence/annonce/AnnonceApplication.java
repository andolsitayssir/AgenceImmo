package com.agence.annonce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



 
@SpringBootApplication
public class AnnonceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AnnonceApplication.class, args);
    }

		private static final Logger LOGGER = LoggerFactory.getLogger(AnnonceApplication.class);
		@Override
		public void run(String...  args) throws Exception{
			LOGGER.trace("This is TRACE");
			LOGGER.debug("This is DEBUG");
			LOGGER.info("This is INFO");
			LOGGER.warn("This is WARN");
			LOGGER.error("This is Error");
		};
	}


