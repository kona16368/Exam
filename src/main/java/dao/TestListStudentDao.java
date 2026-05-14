package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.TestStudent;

public class TestListStudentDao
    extends Dao {

    public List<TestStudent> filter(
            String studentNo)
            throws Exception {

        List<TestStudent> list =
                new ArrayList<>();

        Connection con =
                getConnection();

        String sql =
            "select "
            + "sub.name as subject_name, "
            + "t.subject_cd, "
            + "t.no, "
            + "t.point "
            + "from test t "
            + "inner join subject sub "
            + "on t.subject_cd = sub.cd "
            + "where t.student_no = ? "
            + "order by "
            + "t.subject_cd, "
            + "t.no";

        PreparedStatement st =
                con.prepareStatement(sql);

        st.setString(
                1,
                studentNo);

        ResultSet rs =
                st.executeQuery();

        while (rs.next()) {

            TestStudent test =
                    new TestStudent();

            test.setSubjectName(
                    rs.getString(
                            "subject_name"));

            test.setSubjectCd(
                    rs.getString(
                            "subject_cd"));

            test.setNo(
                    rs.getInt(
                            "no"));

            test.setPoint(
                    rs.getInt(
                            "point"));

            list.add(test);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }
}