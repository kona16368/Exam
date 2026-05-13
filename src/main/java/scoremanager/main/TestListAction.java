package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(
            HttpServletRequest req,
            HttpServletResponse res
    ) throws Exception {

        // セッション取得
        HttpSession session = req.getSession();

        // ログインユーザー取得
        Teacher teacher =
                (Teacher) session.getAttribute("user");

        // DAO
        ClassNumDao classNumDao =
                new ClassNumDao();

        SubjectDao subjectDao =
                new SubjectDao();

        // クラス一覧取得
        List<String> classNumSet =
                classNumDao.filter(
                        teacher.getSchool()
                );

        // 科目一覧取得
        List<Subject> subjectSet =
                subjectDao.filter(
                        teacher.getSchool()
                );

        // 現在年取得
        LocalDate todaysDate =
                LocalDate.now();

        int year =
                todaysDate.getYear();

        // 入学年度リスト生成
        List<Integer> entYearSet =
                new ArrayList<>();

        for (int i = year - 10;
                i < year + 1;
                i++) {

            entYearSet.add(i);
        }

        // requestへ設定
        req.setAttribute(
                "class_num_set",
                classNumSet
        );

        req.setAttribute(
                "subject_set",
                subjectSet
        );

        req.setAttribute(
                "ent_year_set",
                entYearSet
        );

        // JSPへ
        req.getRequestDispatcher(
                "test_list.jsp"
        ).forward(req, res);
    }
}