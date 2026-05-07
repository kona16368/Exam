package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		SubjectDao dao = new SubjectDao();

		// 科目一覧取得（学校コードで絞る）
		List<Subject> subjects = dao.filter(teacher.getSchool());

		// JSPへ渡す
		req.setAttribute("subjects", subjects);

		// 画面遷移
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
	}
}