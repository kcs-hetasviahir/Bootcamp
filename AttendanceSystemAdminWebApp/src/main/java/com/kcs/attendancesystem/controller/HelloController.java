package com.kcs.attendancesystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
//	@Autowired
//	private TeacherRepository teacherRepository;
//	
//	@Autowired
//	private NotificationService notificationService;
	
	@GetMapping("/demoGet")
	public String demoGet() {
		return "Hello from Demo GET API";
	}
	
	@PostMapping("/demoPost") 
	public String demoPost() {
		return "Hello from Demo POST API";
	}
	
//	@GetMapping("/sendmail") 
//	public String sendMAil() {
//		Optional<Teacher> teacherOpt = teacherRepository.findById(36l);
//		
//		if (teacherOpt.isPresent()) {
//			notificationService.sendMail(teacherOpt.get());
//		}
//		return "Hello from Demo POST API";
//	}
}
