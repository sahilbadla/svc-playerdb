package com.intuit.playerdb;

import com.intuit.playerdb.logging.SimpleLogger;
import com.intuit.playerdb.model.Player;
import com.intuit.playerdb.repository.PlayerRepository;
import com.intuit.playerdb.util.CsvLoaderUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class PlayerdbApplication {
	protected SimpleLogger LOGGER = new SimpleLogger(LoggerFactory.getLogger(getClass()));

	public static void main(String[] args) {
		SpringApplication.run(PlayerdbApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(PlayerRepository playerRepository) {
		return args -> {
			LOGGER.log("Preloading from csv file");
			playerRepository.saveAll(CsvLoaderUtils.read(Player.class, "classpath:People.csv"));
			LOGGER.log("finished loading from csv data");
		};
	}

	@Bean
	public HttpTraceRepository httpTraceRepository()
	{
		return new InMemoryHttpTraceRepository();
	}

}
