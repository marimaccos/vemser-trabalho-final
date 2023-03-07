package br.com.dbc.javamosdecolar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavamosDecolarApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(JavamosDecolarApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
