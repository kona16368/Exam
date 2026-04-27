package scoremanager.main;

import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ===== パラメータ取得 =====
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String isAttendStr = req.getParameter("is_attend");

        int entYear = 0;
        boolean isAttend = false;

        if (entYearStr != null && !entYearStr.isEmpty()) {
            entYear = Integer.parseInt(entYearStr);
        }

        if (isAttendStr != null) {
            isAttend = true;
        }

        // ===== Studentにセット =====
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);

        // ===== DB更新 =====
        StudentDao dao = new StudentDao();
        dao.save(student);

        // ===== JSPへ =====
        req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
    }
}