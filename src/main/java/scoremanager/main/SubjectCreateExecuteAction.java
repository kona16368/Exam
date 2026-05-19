package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
	

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 変数
        String cd = "";
        String name = "";

        Subject subject = new Subject();
        SubjectDao subjectDao = new SubjectDao();

        Map<String, String> errors = new HashMap<>();

        // リクエストパラメータ取得
        cd = req.getParameter("cd").trim();
        name = req.getParameter("name").trim();
       
        // 入力チェック
        if (cd == null || cd.isEmpty()) {
            errors.put("1", "科目コードを入力してください");
        }else if(cd.length() !=3) {
        	errors.put("1","科目コード３文字で入力してください");
        }

        if (name == null || name.isEmpty()) {
            errors.put("2", "科目名を入力してください");
        }

        // 重複チェック
        if (errors.isEmpty()) {

        	if (subjectDao.get(cd, teacher.getSchool()) != null) {

                errors.put("3", "科目コードが重複しています");

            } else {

                // 科目情報セット
                subject.setCd(cd);
                subject.setName(name);
                subject.setSchool(teacher.getSchool());

                // DB保存
                subjectDao.save(subject);
            }
        }

        // 値保持
        req.setAttribute("cd", cd);
        req.setAttribute("name", name);

        // エラーセット
        req.setAttribute("errors", errors);

        // JSPへフォワード
        if (errors.isEmpty()) {

            req.getRequestDispatcher("subject_create_done.jsp")
               .forward(req, res);

        } else {

            req.getRequestDispatcher("subject_create.jsp")
               .forward(req, res);
        }
    }
}