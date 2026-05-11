package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		StudentDao studentDao = new StudentDao();

		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String subjectCd = req.getParameter("subject_cd");
		String noStr = req.getParameter("no");

		int entYear = 0;
		int no = 0;

		if (entYearStr != null && !entYearStr.equals("")) {
			entYear = Integer.parseInt(entYearStr);
		}

		if (noStr != null && !noStr.equals("")) {
			no = Integer.parseInt(noStr);
		}

		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();

		List<Integer> entYearSet = new ArrayList<>();

		for (int i = year - 10; i < year + 11; i++) {
			entYearSet.add(i);
		}

		List<String> classNumSet = classNumDao.filter(teacher.getSchool());
		List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());

		List<Student> students = new ArrayList<>();

		if (entYear != 0 && classNum != null && !classNum.equals("")) {
			students = studentDao.filter(teacher.getSchool(), entYear, classNum, true);
		}

		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("subject_set", subjectSet);

		req.setAttribute("ent_year", entYear);
		req.setAttribute("class_num", classNum);
		req.setAttribute("subject_cd", subjectCd);
		req.setAttribute("no", no);

		req.setAttribute("students", students);

		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
	}
}