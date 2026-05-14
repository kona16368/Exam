package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.TestSubject;

public class TestListSubjectDao extends Dao {

    // 成績検索
    public List<TestSubject> filter(
            int entYear,
            String classNum,
            String subjectCd) throws Exception {

        List<TestSubject> list =
                new ArrayList<>();

        Connection con =
                getConnection();

        String sql =
        	    "select "
        	    + "s.ent_year, "
        	    + "s.class_num, "
        	    + "s.no as student_no, "
        	    + "s.name as student_name, "

        	    + "max(case when t.no = 1 then t.point end) as point1, "

        	    + "max(case when t.no = 2 then t.point end) as point2 "

        	    + "from student s "

        	    + "left join test t "
        	    + "on s.no = t.student_no "

        	    + "where s.ent_year = ? "
        	    + "and s.class_num = ? "
        	    + "and t.subject_cd = ? "

        	    + "group by "
        	    + "s.ent_year, "
        	    + "s.class_num, "
        	    + "s.no, "
        	    + "s.name "

        	    + "order by s.no";

        PreparedStatement st =
                con.prepareStatement(sql);

        st.setInt(1, entYear);
        st.setString(2, classNum);
        st.setString(3, subjectCd);

        ResultSet rs =
                st.executeQuery();

        while (rs.next()) {

            TestSubject test =
                    new TestSubject();

            test.setEntYear(
                    rs.getInt("ent_year"));

            test.setClassNum(
                    rs.getString("class_num"));

            test.setStudentNo(
                    rs.getString("student_no"));

            test.setStudentName(
                    rs.getString("student_name"));

            test.setPoint1(
                    (Integer) rs.getObject("point1"));

            test.setPoint2(
                    (Integer) rs.getObject("point2"));

            list.add(test);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

    // 入学年度取得
    public List<Integer> filterEntYear()
            throws Exception {

        List<Integer> list =
                new ArrayList<>();

        Connection con =
                getConnection();

        String sql =
                "select distinct ent_year "
                + "from student "
                + "order by ent_year";

        PreparedStatement st =
                con.prepareStatement(sql);

        ResultSet rs =
                st.executeQuery();

        while (rs.next()) {

            list.add(
                    rs.getInt("ent_year"));
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }
}