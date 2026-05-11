package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Test;

public class TestliststudentDao extends Dao {

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

            list.add(test);
        }

        return list;
    }

    // =========================
    // 学生別成績一覧
    // =========================
    public List<Test> filter(String studentNo)
            throws Exception {

        List<Test> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {

            String sql =
                "SELECT * FROM test "
              + "WHERE student_no = ? "
              + "ORDER BY subject_cd ASC";

            statement = connection.prepareStatement(sql);

            statement.setString(1, studentNo);

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