package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    // 変更リンク押下時に実行される処理
HttpSession session = req.getSession();
Teacher teacher = (Teacher) session.getAttribute("user");
// 変数定義
String no = "";          // 学生番号
String name = "";        // 氏名
int ent_year = 0;        // 入学年度
String class_num = "";   // クラス番号
boolean isAttend = false;// 在学フラグ
Student student = new Student();
StudentDao studentDao = new StudentDao();
ClassNumDao classNumDao = new ClassNumDao();
// リクエストパラメータ取得（学生番号）
no = req.getParameter("no");
// DBから学生情報取得
student = studentDao.get(no);
// クラス一覧取得
List<String> class_num_set = classNumDao.filter(teacher.getSchool());
// 学生情報を変数へ格納
ent_year = student.getEntYear();
name = student.getName();
class_num = student.getClassNum();
isAttend = student.isAttend();
// リクエストスコープへセット（JSPへ渡す）
req.setAttribute("ent_year", ent_year);
req.setAttribute("no", no);
req.setAttribute("name", name);
req.setAttribute("class_num", class_num);
req.setAttribute("class_num_set", class_num_set);
req.setAttribute("isattend", isAttend);

		// JSPへフォワード 7
		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}

}
