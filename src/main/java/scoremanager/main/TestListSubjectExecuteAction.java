package scoremanager.main;

import java.util.List;

import bean.TestSubject;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        int entYear =
                Integer.parseInt(
                        request.getParameter("ent_year"));

        String classNum =
                request.getParameter("class_num");

        String subjectCd =
                request.getParameter("subject_cd");

        TestListSubjectDao dao =
                new TestListSubjectDao();

        List<TestSubject> list =
                dao.filter(
                        entYear,
                        classNum,
                        subjectCd);

        request.setAttribute(
                "list",
                list);

        request.getRequestDispatcher(
                "test_list.jsp")
                .forward(request, response);
    }
}