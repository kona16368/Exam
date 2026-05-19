package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ===== セッション =====
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ===== パラメータ取得 =====
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        // ===== DAO =====
        SubjectDao dao = new SubjectDao();

        // ===== 存在確認 =====
        Subject old = dao.get(cd, teacher.getSchool());

        // ===== 既に削除されている場合 =====
        if (old == null) {

            req.setAttribute("error", "既に削除された科目です");

            req.setAttribute("cd", cd);
            req.setAttribute("name", name);

            req.getRequestDispatcher("subject_update.jsp")
               .forward(req, res);

            return;
        }

        // ===== Subjectにセット =====
        Subject subject = new Subject();

        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        // ===== DB更新 =====
        dao.save(subject);

        // ===== 完了画面 =====
        req.getRequestDispatcher("subject_update_done.jsp")
           .forward(req, res);
    }
}