package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Test;

public class TestDao extends Dao {

    // =========================
    // ベースSQL
    // =========================
    private String baseSql = "SELECT * FROM test WHERE school_cd = ?";

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
                test.setEnt_year(rs.getInt("ent_year"));
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
    // ② 科目・クラス別検索
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
                baseSql
              + " AND ent_year = ?"
              + " AND class_num = ?"
              + " AND subject_cd = ?"
              + " ORDER BY student_no ASC";

            statement = connection.prepareStatement(sql);

            statement.setString(1, schoolCd);
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            statement.setString(4, subjectCd);

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

    // =========================
    // ③ 学生別検索
    // =========================
    public List<Test> filterByStudent(
            String studentNo) throws Exception {

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

    // =========================
    // ④ 登録 or 更新
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
                  + "(student_no, subject_cd, school_cd, no, point, class_num, ent_year) "
                  + "VALUES (?, ?, ?, ?, ?, ?, ?)";

                statement = connection.prepareStatement(sql);

                statement.setString(1, test.getStudent_no());
                statement.setString(2, test.getSubject_cd());
                statement.setString(3, test.getSchool_cd());
                statement.setInt(4, test.getNo());
                statement.setInt(5, test.getPoint());
                statement.setString(6, test.getClass_num());
                statement.setInt(7, test.getEnt_year());

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
    // ⑤ 削除
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