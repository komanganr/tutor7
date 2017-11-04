package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.example.model.CourseModel;
import com.example.service.CourseService;

@Controller
public class CourseController {
	@Autowired
	CourseService courseDAO;

	@RequestMapping("/course/view/{id_course}")
	public String viewCourse(Model model,@PathVariable(value = "id_course") String id_course) {
		CourseModel course = courseDAO.selectCourse(id_course);

		if (course != null) {
			model.addAttribute("course", course);
			model.addAttribute("title","View Course");
			return "view-course";
		} else {
			model.addAttribute("id_course", id_course);
			return "not-found";
		}
	}
}
