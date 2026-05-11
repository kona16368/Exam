package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Test;

public class TestlistsubjectDao extends Dao {

    // =========================
    // 共通：ResultSet → List
    // =========================
    private List<Test> postFilter(ResultSet rs) throws Exception {

        List<Test> list = new ArrayList<>();

        while (rs.next()) {

            Test test = new Test();

            test.setStudent_no(rs.getString("student_no"));
            test.setSubject_cd(rs.getString("subject_cd"));
            test.setSchool_cd(rs.getString("school_cd"));
            test.setNo(rs.getInt("no"));
            test.setPoint(rs.getInt("point"));
            test.setClass_num(rs.getString("class_num"));
            test.setEnt_year(rs.getInt("ent_year"));

            list.add(test);
        }

        return list;
    }

    // =========================
    // 科目別成績一覧
    // =========================
    public List<Test> filter(
            int entYear,
            String classNum,
            String subjectCd,
            String schoolCd) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {

            String sql =
                "SELECT t.*, s.ent_year "
              + "FROM test t "
              + "INNER JOIN student s "
              + "ON t.student_no = s.no "
              + "WHERE s.ent_year = ? "
              + "AND t.class_num = ? "
              + "AND t.subject_cd = ? "
              + "AND t.school_cd = ? "
              + "ORDER BY t.student_no ASC";

            statement = connection.prepareStatement(sql);

            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subjectCd);
            statement.setString(4, schoolCd);

            ResultSet rs = statement.executeQuery();

            list = postFilter(rs);

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return list;
    }
}