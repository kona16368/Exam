package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistDoneAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		int entYear = Integer.parseInt(req.getParameter("ent_year"));
		String classNum = req.getParameter("class_num");
		String subjectCd = req.getParameter("subject_cd");
		int no = Integer.parseInt(req.getParameter("no"));

		StudentDao studentDao = new StudentDao();
		TestDao testDao = new TestDao();

		List<Student> students = studentDao.filter(
				teacher.getSchool(),
				entYear,
				classNum,
				true);

		for (Student student : students) {

			String pointStr = req.getParameter("point_" + student.getNo());

			if (pointStr != null && !pointStr.equals("")) {

				int point = Integer.parseInt(pointStr);
				
				if (point < 0 || point > 100) {
				    req.setAttribute("error", "点数は0～100で入力してください");

				    req.getRequestDispatcher("test_regist.jsp")
				       .forward(req, res);

				    return;
				}

				Test test = new Test();

				test.setStudent_no(student.getNo());
				test.setSubject_cd(subjectCd);
				test.setSchool_cd(teacher.getSchool().getCd());
				test.setNo(no);
				test.setPoint(point);
				test.setClass_num(classNum);
				test.setEnt_year(entYear);

				testDao.save(test);
			}
		}

		req.getRequestDispatcher("test_regist_done.jsp")
		   .forward(req, res);
	}
}