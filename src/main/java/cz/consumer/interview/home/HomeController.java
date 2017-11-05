package cz.consumer.interview.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.consumer.interview.Mappings;

@Controller
public class HomeController {
		
	@RequestMapping(Mappings.HOME)
	public ModelAndView index() {
		return new ModelAndView("setup");
	} 
	
	@RequestMapping(Mappings.PROJECTS_LIST)
	public ModelAndView projectsList() {
		return new ModelAndView("projects");
	} 
	
}
