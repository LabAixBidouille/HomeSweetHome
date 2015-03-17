package org.labaix.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping("/")
public class HomeController {

	private Logger log = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		log.warn("print welcome");
		return "hello";
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("postConstruct");
	}

	@Autowired
	public void a() {
		System.out.println("a");
	}

	@Autowired
	public void d() {
		System.out.println("d");
	}

	@Autowired
	public void c() {
		System.out.println("c");
	}

	@Autowired
	public void b() {
		System.out.println("b");
	}

}