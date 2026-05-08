package scoremanager.main;

import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 科目コード取得
        String cd = req.getParameter("cd");

        // DAO生成
        SubjectDao dao = new SubjectDao();

        // 削除実行
        dao.delete(cd);

        // 完了画面へ
        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}