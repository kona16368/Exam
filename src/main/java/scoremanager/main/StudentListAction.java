package scoremanager.main;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
 
public class StudentListAction extends Action {
 
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
 
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
 
		// ローカル変数の指定 
		String entYearStr = ""; // 入力された入学年度
		String classNum = ""; // 入力されたクラス番号
		String isAttendStr = ""; // 入力された在学フラグ
		int entYear = 0; // 入学年度
		boolean isAttend = false; // 在学フラグ
		List<Student> students = null; // 学生リスト
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		StudentDao studentDao = new StudentDao();
		ClassNumDao classNumDao = new ClassNumDao();
		Map<String, String> errors = new HashMap<>();
 
		// リクエストパラメーターの取得 
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		isAttendStr = req.getParameter("f3");
 
		// ビジネスロジック 
		if (entYearStr != null && !entYearStr.equals("")) {
			entYear = Integer.parseInt(entYearStr);
		}
		if (isAttendStr != null) {
			isAttend = true;
		}
 
		// 入学年度リスト作成
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 11; i++) {
			entYearSet.add(i);
		}
 
		// DBからデータ取得 
		List<String> list = classNumDao.filter(teacher.getSchool());
 
		if (entYear != 0 && classNum != null && !classNum.equals("0")) {
			// 入学年度とクラス番号を指定
			students = studentDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
 
		} else if (entYear != 0 && (classNum == null || classNum.equals("0"))) {
			// 入学年度のみ指定
			students = studentDao.filter(teacher.getSchool(), entYear, isAttend);
 
		} else if (entYear == 0 && (classNum == null || classNum.equals("0"))) {
			// 指定なし
			students = studentDao.filter(teacher.getSchool(), isAttend);
 
		} else {
			//error の表示設定
			errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
			req.setAttribute("errors", errors);
			students = studentDao.filter(teacher.getSchool(), isAttend);
		}
 
		// レスポンス値をセット 
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
 
		if (isAttendStr != null) {
			req.setAttribute("f3", isAttendStr);
		}
		req.setAttribute("students", students);
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
 
		// JSPへフォワード 
		req.getRequestDispatcher("student_list.jsp").forward(req, res);
	}
}