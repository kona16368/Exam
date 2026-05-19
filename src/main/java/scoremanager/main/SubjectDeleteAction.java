// SubjectDeleteAction.java
package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	
    	HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 科目コード取得
        String cd = req.getParameter("cd");

        // DAO生成
        SubjectDao subjectDao = new SubjectDao();

        // 科目情報取得
        Subject subject = subjectDao.get(cd, teacher.getSchool());

        // 科目が存在しない場合
        if (subject == null) {

            req.setAttribute("error", "科目情報が存在しません");

            req.getRequestDispatcher("error.jsp").forward(req, res);

            return;
        }

        // JSPへデータセット
        req.setAttribute("subject", subject);
        req.setAttribute("cd", subject.getCd());
        req.setAttribute("name", subject.getName());

        // 削除確認画面へ
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}