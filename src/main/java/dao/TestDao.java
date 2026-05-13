package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Test;

public class TestDao extends Dao {

    // =========================
    // ① 1件取得
    // =========================
    public Test get(
            String studentNo,
            String subjectCd,
            int no) throws Exception {

        Test test = null;

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {

            String sql =
                "SELECT * FROM test "
              + "WHERE student_no = ? "
              + "AND subject_cd = ? "
              + "AND no = ?";

            statement = connection.prepareStatement(sql);

            statement.setString(1, studentNo);
            statement.setString(2, subjectCd);
            statement.setInt(3, no);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                test = new Test();

                test.setStudent_no(rs.getString("student_no"));
                test.setSubject_cd(rs.getString("subject_cd"));
                test.setSchool_cd(rs.getString("school_cd"));
                test.setNo(rs.getInt("no"));
                test.setPoint(rs.getInt("point"));
                test.setClass_num(rs.getString("class_num"));
            }

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return test;
    }

    // =========================
    // ② 登録 or 更新
    // =========================
    public boolean save(Test test) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;

        int count = 0;

        try {

            Test old = get(
                    test.getStudent_no(),
                    test.getSubject_cd(),
                    test.getNo());

            if (old == null) {

                // INSERT
                String sql =
                    "INSERT INTO test "
                  + "(student_no, subject_cd, school_cd, no, point, class_num) "
                  + "VALUES (?, ?, ?, ?, ?, ?)";

                statement = connection.prepareStatement(sql);

                statement.setString(1, test.getStudent_no());
                statement.setString(2, test.getSubject_cd());
                statement.setString(3, test.getSchool_cd());
                statement.setInt(4, test.getNo());
                statement.setInt(5, test.getPoint());
                statement.setString(6, test.getClass_num());

            } else {

                // UPDATE
                String sql =
                    "UPDATE test "
                  + "SET point = ? "
                  + "WHERE student_no = ? "
                  + "AND subject_cd = ? "
                  + "AND no = ?";

                statement = connection.prepareStatement(sql);

                statement.setInt(1, test.getPoint());
                statement.setString(2, test.getStudent_no());
                statement.setString(3, test.getSubject_cd());
                statement.setInt(4, test.getNo());
            }

            count = statement.executeUpdate();

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return count > 0;
    }
    
 // =========================
    // ③ 削除
 // =========================
    public boolean delete(
            String studentNo,
         String subjectCd,
            int no) throws Exception {

     Connection connection = getConnection();
     PreparedStatement statement = null;

        int count = 0;

     try {

         String sql =
                "DELETE FROM test "
              + "WHERE student_no = ? "
              + "AND subject_cd = ? "
              + "AND no = ?";

         statement = connection.prepareStatement(sql);

            statement.setString(1, studentNo);
            statement.setString(2, subjectCd);
            statement.setInt(3, no);

            count = statement.executeUpdate();

     } finally {

         if (statement != null) {
             statement.close();
         }

         if (connection != null) {
             connection.close();
         }
     }

        return count > 0;
 }
}