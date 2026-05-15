package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.TestSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 検索条件
        String f1 =
                request.getParameter("f1");

        String f2 =
                request.getParameter("f2");

        String f3 =
                request.getParameter("f3");

        int entYear = 0;

        if (f1 != null &&
            !f1.isEmpty()) {

            entYear =
                    Integer.parseInt(f1);
        }
        if (f1 == null || f1.isEmpty()
                || f2 == null || f2.isEmpty()
                || f3 == null || f3.isEmpty()) {

            request.setAttribute(
                    "error",
                    "情報が存在しません");

            // 条件保持
            request.setAttribute("f1", f1);
            request.setAttribute("f2", f2);
            request.setAttribute("f3", f3);

            Teacher teacher =
                    (Teacher)request.getSession()
                            .getAttribute("user");

            // クラス一覧
            ClassNumDao classNumDao =
                    new ClassNumDao();

            request.setAttribute(
                    "class_num_set",
                    classNumDao.filter(
                            teacher.getSchool()));

            // 科目一覧
            SubjectDao subjectDao =
                    new SubjectDao();

            request.setAttribute(
                    "subject_set",
                    subjectDao.filter(
                            teacher.getSchool()));

            // 入学年度一覧
            TestListSubjectDao dao =
                    new TestListSubjectDao();

            request.setAttribute(
                    "ent_year_set",
                    dao.filterEntYear());

            request.getRequestDispatcher(
                    "test_list_subject.jsp")
                    .forward(request, response);

            return;
        }

        // 成績検索
        TestListSubjectDao dao =
                new TestListSubjectDao();

        List<TestSubject> list =
                dao.filter(
                        entYear,
                        f2,
                        f3);

        // 結果
        request.setAttribute(
                "list",
                list);

        // 条件保持
        request.setAttribute(
                "f1",
                f1);

        request.setAttribute(
                "f2",
                f2);

        request.setAttribute(
                "f3",
                f3);
        
        Teacher teacher =
        	    (Teacher)request.getSession()
        	        .getAttribute("user");

        // クラス一覧
        ClassNumDao classNumDao =
                new ClassNumDao();

        request.setAttribute(
                "class_num_set",
                classNumDao.filter(teacher.getSchool()));

        // 科目一覧
        SubjectDao subjectDao =
                new SubjectDao();

        List<Subject> subjectSet =
                subjectDao.filter(teacher.getSchool());

        request.setAttribute(
                "subject_set",
                subjectSet);

        // 入学年度一覧
        List<Integer> entYearSet =
                dao.filterEntYear();

        request.setAttribute(
                "ent_year_set",
                entYearSet);

        // JSP
        request.getRequestDispatcher(
                "test_list_subject.jsp")
                .forward(request, response);
    }
}