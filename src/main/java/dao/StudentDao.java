package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {

    private String baseSql = "select * from student where school_cd = ?";

    // =========================
    // ① 1件取得
    // =========================
    public Student get(String no) throws Exception {

        Student student = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "SELECT * FROM student WHERE no = ?";
            statement = connection.prepareStatement(sql);

            statement.setString(1, no);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                student = new Student();

                student.setNo(resultSet.getString("no"));
                student.setName(resultSet.getString("name"));
                student.setEntYear(resultSet.getInt("ent_year"));
                student.setClassNum(resultSet.getString("class_num"));
                student.setAttend(resultSet.getBoolean("is_attend"));

                School school = new School();
                school.setCd(resultSet.getString("school_cd"));
                student.setSchool(school);
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return student;
    }

    // =========================
    // 共通：ResultSet → List
    // =========================
    private List<Student> postFilter(ResultSet rs, School school) throws Exception {

        List<Student> list = new ArrayList<>();

        while (rs.next()) {
            Student student = new Student();

            student.setNo(rs.getString("no"));
            student.setName(rs.getString("name"));
            student.setEntYear(rs.getInt("ent_year"));
            student.setClassNum(rs.getString("class_num"));
            student.setAttend(rs.getBoolean("is_attend"));
            student.setSchool(school);

            list.add(student);
        }

        return list;
    }

    // =========================
    // ② 年度＋クラス
    // =========================
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {

        List<Student> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            String sql = baseSql + " AND ent_year=? AND class_num=?";

            if (isAttend) {
                sql += " AND is_attend=true";
            }

            sql += " ORDER BY no ASC";

            statement = connection.prepareStatement(sql);

            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);

            ResultSet rs = statement.executeQuery();

            list = postFilter(rs, school);

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // =========================
    // ③ 年度のみ
    // =========================
    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {

        List<Student> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            String sql = baseSql + " AND ent_year=?";

            if (isAttend) {
                sql += " AND is_attend=true";
            }

            sql += " ORDER BY no ASC";

            statement = connection.prepareStatement(sql);

            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);

            ResultSet rs = statement.executeQuery();

            list = postFilter(rs, school);

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // =========================
    // ④ 全件
    // =========================
    public List<Student> filter(School school, boolean isAttend) throws Exception {

        List<Student> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            String sql = baseSql;

            if (isAttend) {
                sql += " AND is_attend=true";
            }

            sql += " ORDER BY no ASC";

            statement = connection.prepareStatement(sql);

            statement.setString(1, school.getCd());

            ResultSet rs = statement.executeQuery();

            list = postFilter(rs, school);

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // =========================
    // ⑤ 登録 or 更新
    // =========================
    public boolean save(Student student) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            Student old = get(student.getNo());

            if (old == null) {
                // INSERT
                String sql = "INSERT INTO student VALUES (?, ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(sql);

                statement.setString(1, student.getNo());
                statement.setString(2, student.getName());
                statement.setInt(3, student.getEntYear());
                statement.setString(4, student.getClassNum());
                statement.setBoolean(5, student.isAttend());
                statement.setString(6, student.getSchool().getCd());

            } else {
                // UPDATE
                String sql = "UPDATE student SET name=?, ent_year=?, class_num=?, is_attend=? WHERE no=?";
                statement = connection.prepareStatement(sql);

                statement.setString(1, student.getName());
                statement.setInt(2, student.getEntYear());
                statement.setString(3, student.getClassNum());
                statement.setBoolean(4, student.isAttend());
                statement.setString(5, student.getNo());
            }

            count = statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return count > 0;
    }
}