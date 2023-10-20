package projetJEE.ProjetEE.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import projetJEE.ProjetEE.Models.Student;

@Controller
class StudentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	@GetMapping(path = "/students")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<String> groups = new ArrayList<>();
		groups.add("ING1");
		groups.add("ING2");
		groups.add("ING3");
		groups.add("ING4");
		request.setAttribute("groups", groups);
		RequestDispatcher view = request.getRequestDispatcher("showForm.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String group = request.getParameter("group");
		
		if(group.equals("none")) {
			request.getSession().setAttribute("error", "please select a group");
			response.sendRedirect("/td1/students");
			return;
		}
		request.getSession().removeAttribute("error");
		
		////////////////////////////////
		//should be found in database
		List<Student> students = new ArrayList<>();
		Student remy = new Student();
		remy.setFirstName("Rémy");
		remy.setLastName("Brousset");
		students.add(remy);
		////////////////////////////////
		
		request.setAttribute("students", students);

		RequestDispatcher view = request.getRequestDispatcher("showStudents.jsp");
		view.forward(request, response);
	}

}
