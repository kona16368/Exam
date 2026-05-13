package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.TestSubject;

public class TestListSubjectDao extends Dao {

    public List<TestSubject> filter(
            int entYear,
            String classNum,
            String subjectCd) throws Exception {

        List<TestSubject> list = new ArrayList<>();

        Connection con = getConnection();
        PreparedStatement st = null;

        try {

            String sql =
                "SELECT "
              + "s.ent_year, "
              + "s.class_num, "
              + "s.no, "
              + "s.name, "
              + "MAX(CASE WHEN t.no = 1 THEN t.point END) AS point1, "
              + "MAX(CASE WHEN t.no = 2 THEN t.point END) AS point2 "
              + "FROM student s "
              + "LEFT JOIN test t "
              + "ON s.no = t.student_no "
              + "AND t.subject_cd = ? "
              + "WHERE s.ent_year = ? "
              + "AND s.class_num = ? "
              + "GROUP BY "
              + "s.ent_year, s.class_num, s.no, s.name "
              + "ORDER BY s.no";

            st = con.prepareStatement(sql);

            st.setString(1, subjectCd);
            st.setInt(2, entYear);
            st.setString(3, classNum);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                TestSubject student = new TestSubject();

                student.setEntYear(rs.getInt("ent_year"));
                student.setClassNum(rs.getString("class_num"));
                student.setStudentNo(rs.getString("no"));
                student.setStudentName(rs.getString("name"));
                student.setPoint1((Integer)rs.getObject("point1"));
                student.setPoint2((Integer)rs.getObject("point2"));

                list.add(student);
            }

        } finally {

            if (st != null) {
                st.close();
            }

            if (con != null) {
                con.close();
            }
        }

        return list;
    }
}