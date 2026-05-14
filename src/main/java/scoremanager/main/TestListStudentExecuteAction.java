package scoremanager.main;

import java.util.List;

import bean.Teacher;
import bean.TestStudent;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListStudentExecuteAction
    extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

    	String f4 =
    		    request.getParameter("f4");

    		TestListStudentDao dao =
    		    new TestListStudentDao();

    		List<TestStudent> list =
    		    dao.filter(f4);

    		request.setAttribute(
    		    "student_list",
    		    list);

    		request.setAttribute(
    		    "f4",
    		    f4);

        request.setAttribute(
                "student_list",
                list);


        ClassNumDao classNumDao =
                new ClassNumDao();

        SubjectDao subjectDao =
                new SubjectDao();
        Teacher teacher =
        	    (Teacher)request.getSession()
        	        .getAttribute("user");

        request.setAttribute(
                "class_num_set",
                classNumDao.filter(teacher.getSchool()));

        request.setAttribute(
                "subject_set",
                subjectDao.filter(teacher.getSchool()));

        request.getRequestDispatcher(
                "test_list_student.jsp")
                .forward(
                        request,
                        response);
    }
}