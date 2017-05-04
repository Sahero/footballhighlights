/**
 *HighlightController.java
 *
 *@author Sagar Shrestha 
 **/
package com.spring.elasticsearch.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.jaas.SecurityContextLoginModule;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.elasticsearch.model.Football;
import com.spring.elasticsearch.model.Search;
import com.spring.elasticsearch.service.HighlightService;

@Controller
//@RequestMapping("/highlights")
public class HighlightController {

	//static final Logger logger = LogManager.getLogger(HelloController.class.getName());
	@Value("#{'${video.sources}'.split(',')}")
	private List<String> videoSources;

	@Autowired
	private HighlightService highlightService;

	@Autowired
	private Environment environment;
	
	//@Autowired
	//private HttpSession httpSession;

	/*@PostMapping("/login")
	public String loginPage(HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().equals("ROLE_ADMIN")) {
			//httpSession.setAttribute("user", auth.getName());
			//httpSession.setAttribute("role", auth.getAuthorities());
			return "redirect:/admin?pg=1";
		}		
		else{
			return "login";
		}
	}*/

	@GetMapping("/login")
	public String getLoginPage(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		/*System.out.println(auth.getAuthorities());
		System.out.println(auth.getName());
		System.out.println(auth.getPrincipal());
		System.out.println(auth.getDetails());*/
		if(auth.getName().toLowerCase().equals("anonymoususer")){
			System.out.println("2");
			return "login";
		}	
		else{
			System.out.println("1");
			return "redirect:/admin?pg=1";
		}
	}

	@GetMapping("/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	/*@GetMapping("/admin")
	public String listHighlights(Model model) {
		return "redirect:/admin?pg=1";
	}*/
	@GetMapping("/admin")
	public String listHighlights(@RequestParam(value="pg",defaultValue="1") int pageNum,Model model) {
		model.addAttribute("user", getPrincipal());//httpSession.getAttribute("user"));
		List<Football> footballList = highlightService.getAllHighlights(pageNum);

		model.addAttribute("highlights", footballList);
		model.addAttribute("totalPages",getTotalPageNumber(highlightService.getTotalNumberOfRecords()));
		model.addAttribute("search",new Search());
		model.addAttribute("pg",pageNum);
		return "highlightsList";
	}

	@PostMapping(value={"/admin/searchHighlight","/searchHighlight"})
	public String searchHighlights(@ModelAttribute("search") Search theSearch, Model model) {
		model.addAttribute("user", getPrincipal());
		List<Football> footballList = highlightService.getHighlights(theSearch);
		model.addAttribute("highlights", footballList);
		model.addAttribute("totalPages",getTotalPageNumber(highlightService.getTotalNumberOfRecords()));
		return "highlightsList";
	}

	@GetMapping(value={"/admin/addNewHighlight","/addNewHighlight"})
	public String addNewHighlight(@RequestParam(value="pg",defaultValue="1") int pageNum,
			Model theModel){

		theModel.addAttribute("user", getPrincipal());
		Football theFootball = new Football();
		theModel.addAttribute("football",theFootball);
		theModel.addAttribute("pg",pageNum);
		theModel.addAttribute("videoSources",videoSources);
		return "footballForm";
	}

	@PostMapping(value={"/admin/saveHighlight","/saveHighlight"})
	public String saveHighlight(@RequestParam(value="pg",defaultValue="1") int pageNum,
			@Valid @ModelAttribute("football") Football theFootball,BindingResult result,Model theModel){
		//System.out.println(result.getErrorCount());
		//System.out.println(result.toString());
		if(result.hasErrors()){
			theModel.addAttribute("pg",pageNum);
			theModel.addAttribute("videoSources",videoSources);
			return "footballForm";
		}
		highlightService.saveHighlight(theFootball);
		return "redirect:/admin?pg="+pageNum;
	}



	@GetMapping(value={"/admin/updateHighlight","/updateHighlight"})
	public String updateHighlight(@RequestParam(value="pg",defaultValue="1") int pageNum,@RequestParam("highlightsId") String theId,
			Model theModel){
		theModel.addAttribute("user", getPrincipal());
		Football theFootball = highlightService.getHighlight(theId);		
		theModel.addAttribute("football",theFootball);		
		theModel.addAttribute("pg",pageNum);
		theModel.addAttribute("videoSources",videoSources);
		return "footballForm";									
	}

	@GetMapping(value={"/admin/deleteHighlight","/deleteHighlight"})
	public String deleteHighlight(@RequestParam(value="pg",defaultValue="1") int pageNum,@RequestParam("highlightsId") String theId){
		highlightService.deleteHighlight(theId);
		//return "redirect:/user/list";
		return "redirect:/admin?pg="+pageNum;
	}

	/*Non admin*/

	/**************************************************************************************************/
	@GetMapping("/home")
	public String listAllFootballHighlights(@RequestParam(value="pg",defaultValue="1") int pageNum,Model theModel){
		List<Football> allHighlights= highlightService.getAllHighlights(pageNum);
		theModel.addAttribute("footballHighlights",allHighlights);
		theModel.addAttribute("totalPages",getTotalPageNumber(highlightService.getTotalNumberOfRecords()));
		theModel.addAttribute("search",new Search());
		return "footballhighlights";
	}

	@PostMapping(value={"/home/search","/search"})
	public String search(@ModelAttribute("search") Search theSearch, Model model) {
		List<Football> footballList = highlightService.getHighlights(theSearch);
		model.addAttribute("footballHighlights", footballList);
		model.addAttribute("totalPages",getTotalPageNumber(highlightService.getTotalNumberOfRecords()));
		//model.addAttribute("search",new Search());
		return "footballhighlights";
	}
	@GetMapping("/contact")
	public String contactForm(Model theModel){
		//System.out.println("test");
		return "contactForm";
	}
	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	private long getTotalPageNumber(long totalRecords){
		long totalVideosToShow =Long.parseLong(environment.getProperty("number.of.videos.in.a.page"));		
		//return (totalRecords/totalVideosToShow) + 1;
		return (totalRecords+totalVideosToShow- 1)/totalVideosToShow;
	}
}