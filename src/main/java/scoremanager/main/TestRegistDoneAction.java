package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistDoneAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		HttpSession session = req.getSession();

		Teacher teacher =
				(Teacher) session.getAttribute("user");

		int entYear =
				Integer.parseInt(
						req.getParameter("ent_year"));

		String classNum =
				req.getParameter("class_num");

		String subjectCd =
				req.getParameter("subject_cd");

		int no =
				Integer.parseInt(
						req.getParameter("no"));

		StudentDao studentDao =
				new StudentDao();

		TestDao testDao =
				new TestDao();

		ClassNumDao classNumDao =
				new ClassNumDao();

		SubjectDao subjectDao =
				new SubjectDao();

		// 入学年度セット
		int year =
				LocalDate.now().getYear();

		List<Integer> entYearSet =
				new ArrayList<>();

		for (int i = year - 10; i < year + 11; i++) {

			entYearSet.add(i);
		}

		// クラス・科目セット
		List<String> classNumSet =
				classNumDao.filter(
						teacher.getSchool());

		List<Subject> subjectSet =
				subjectDao.filter(
						teacher.getSchool());

		List<Student> students =
				studentDao.filter(
						teacher.getSchool(),
						entYear,
						classNum,
						true);

		for (Student student : students) {

			String pointStr =
					req.getParameter(
							"point_" + student.getNo());

			// 未入力ならスキップ
			if (pointStr != null &&
				!pointStr.trim().isEmpty()) {

				// 数字チェック
				if (!pointStr.matches("[0-9]+")) {

					req.setAttribute(
							"errorMessage",
							"数字で入力してください");
					
					req.setAttribute(
							"errorStudent",
							student.getNo());

					req.setAttribute(
							"ent_year",
							entYear);

					req.setAttribute(
							"class_num",
							classNum);

					req.setAttribute(
							"subject_cd",
							subjectCd);

					req.setAttribute(
							"no",
							no);

					req.setAttribute(
							"students",
							students);

					req.setAttribute(
							"ent_year_set",
							entYearSet);

					req.setAttribute(
							"class_num_set",
							classNumSet);

					req.setAttribute(
							"subject_set",
							subjectSet);

					req.getRequestDispatcher(
							"/scoremanager/main/test_regist.jsp")
							.forward(req, res);

					return;
				}

				long pointLong =
						Long.parseLong(pointStr);

				// 範囲チェック
				if (pointLong < 0 ||
					pointLong > 100) {

					req.setAttribute(
							"errorMessage",
							"0～100の範囲で入力してください");
					
					req.setAttribute("errorStudent",student.getNo());

					req.setAttribute(
							"ent_year",
							entYear);

					req.setAttribute(
							"class_num",
							classNum);

					req.setAttribute(
							"subject_cd",
							subjectCd);

					req.setAttribute(
							"no",
							no);

					req.setAttribute(
							"students",
							students);

					req.setAttribute(
							"ent_year_set",
							entYearSet);

					req.setAttribute(
							"class_num_set",
							classNumSet);

					req.setAttribute(
							"subject_set",
							subjectSet);

					req.getRequestDispatcher(
							"/scoremanager/main/test_regist.jsp")
							.forward(req, res);

					return;
				}

				int point = (int) pointLong;

				// 入力値保持
				student.setPoint(point);

				Test test = new Test();

				test.setStudent_no(
						student.getNo());

				test.setSubject_cd(
						subjectCd);

				test.setSchool_cd(
						teacher.getSchool().getCd());

				test.setNo(no);

				test.setPoint(point);

				test.setClass_num(
						classNum);

				test.setEnt_year(
						entYear);

				testDao.save(test);
			}
		}

		req.getRequestDispatcher(
				"test_regist_done.jsp")
				.forward(req, res);
	}
}