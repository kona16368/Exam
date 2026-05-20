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

		// パラメータ取得
		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String subjectCd = req.getParameter("subject_cd");
		String noStr = req.getParameter("no");

		int entYear = 0;
		int no = 0;

		// 空チェック
		if (entYearStr != null && !entYearStr.isEmpty()) {
			entYear = Integer.parseInt(entYearStr);
		}

		if (noStr != null && !noStr.isEmpty()) {
			no = Integer.parseInt(noStr);
		}

		StudentDao studentDao = new StudentDao();
		TestDao testDao = new TestDao();

		List<Student> students = studentDao.filter(
				teacher.getSchool(),
				entYear,
				classNum,
				true);

		for (Student student : students) {

			String pointStr = req.getParameter("point_" + student.getNo());

			// 未入力ならスキップ
			if (pointStr != null && !pointStr.isEmpty()) {

				try {

					int point = Integer.parseInt(pointStr);

					// 点数範囲チェック
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

				} catch (NumberFormatException e) {

					req.setAttribute("error", "点数は数字で入力してください");

					req.getRequestDispatcher("test_regist.jsp")
							.forward(req, res);

					return;
				}
			}
		}

		req.getRequestDispatcher("test_regist_done.jsp")
				.forward(req, res);
	}
}