package com.app.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Greeting;
import com.app.services.ApnsClient;

@RestController("sendController")
@RequestMapping("/send")
public class SendController {

	private static final String template = "Sent: %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	ApnsClient apnsClient;
	
/*	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public 
	Greeting sayHello(
			@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template,
				name));
	}*/
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public 
	Greeting sendAPns(
			@RequestParam(value = "message", required = false, defaultValue = "Hello") String message,
			@RequestParam(value = "deviceId", required = false, defaultValue = "3eaa5eae3663036c34f155939ae84c8d8a3a1194d50f57f1e73e5c8c94ededf9") String deviceId) {
		//1cf0d2d578acd9f3e59866e0c8c240307880ec209bbd71031056287176e93f73: myapp,mydevice
		//7c89e49d7a1a0a2f35807daad9a8f606820bd271c378bd2e94e5708eaee03a15 photostory,mydevice
		apnsClient.send(deviceId, message);
		return new Greeting(counter.incrementAndGet(), String.format(template,
				message));
	}
	
}
