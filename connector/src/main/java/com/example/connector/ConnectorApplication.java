package com.example.connector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.net.Socket;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
@RestController
public class ConnectorApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConnectorApplication.class, args);
	}

	@GetMapping("/socket")
	public String socket(@RequestParam(value = "host", defaultValue = "127.0.0.1") String host,
			@RequestParam(value = "port", defaultValue = "9090") int port) {
		String jsonPayload = "{\"socketPort\":\"" + port + "\"}";

		String response = sendToTargetServerSocket(jsonPayload, host, port);

		return String.format("socket %s! %s! response: %s!", host, port, response);
	}

	private String sendToTargetServerSocket(String payload, String host, int port) {
		try (Socket socket = new Socket(host, port);
				OutputStream outputStream = socket.getOutputStream();
				InputStream inputStream = socket.getInputStream()) {

			// Convert the payload to bytes and send it through the socket
			byte[] payloadBytes = payload.getBytes();
			outputStream.write(payloadBytes);
			outputStream.flush();
			outputStream.write(System.lineSeparator().getBytes());
			outputStream.flush();

			// Read the response from the server
			byte[] buffer = new byte[1024];
			int bytesRead;
			StringBuilder responseBuilder = new StringBuilder();
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				String chunk = new String(buffer, 0, bytesRead);
				responseBuilder.append(chunk);
			}
			return responseBuilder.toString();

		} catch (IOException e) {
			e.printStackTrace();
			// Handle the exception
		}
		return "error";
	}
}