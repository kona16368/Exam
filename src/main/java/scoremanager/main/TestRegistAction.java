package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();

		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();

		List<String> classNumSet = classNumDao.filter(teacher.getSchool());
		List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());

		List<Integer> entYearSet = new ArrayList<>();

		for (int i = year - 10; i < year + 11; i++) {
			entYearSet.add(i);
		}

		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("subject_set", subjectSet);

		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}