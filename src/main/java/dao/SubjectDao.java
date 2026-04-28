package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import bean.School;

public class SubjectDao extends Dao {

    // =========================
    // ベースSQL
    // =========================
    private String baseSql = "SELECT * FROM subject WHERE school_cd = ?";

    // =========================
    // ① 1件取得
    // =========================
    public Subject get(String cd) throws Exception {

        Subject subject = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "SELECT * FROM subject WHERE cd = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, cd);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                subject = new Subject();

                subject.setCd(rs.getString("cd"));
                subject.setName(rs.getString("name"));
                subject.setCredit(rs.getInt("credit"));

                School school = new School();
                school.setCd(rs.getString("school_cd"));
                subject.setSchool(school);
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return subject;
    }

    // =========================
    // 共通：ResultSet → List
    // =========================
    private List<Subject> postFilter(ResultSet rs, School school) throws Exception {

        List<Subject> list = new ArrayList<>();

        while (rs.next()) {
            Subject subject = new Subject();

            subject.setCd(rs.getString("cd"));
            subject.setName(rs.getString("name"));
            subject.setCredit(rs.getInt("credit"));
            subject.setSchool(school);

            list.add(subject);
        }

        return list;
    }

    // =========================
    // ② 一覧取得（全件）
    // =========================
    public List<Subject> filter(School school) throws Exception {

        List<Subject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            String sql = baseSql + " ORDER BY cd ASC";

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
    // ③ 登録 or 更新
    // =========================
    public boolean save(Subject subject) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            Subject old = get(subject.getCd());

            if (old == null) {
                // INSERT
                String sql = "INSERT INTO subject (cd, name, credit, school_cd) VALUES (?, ?, ?, ?)";
                statement = connection.prepareStatement(sql);

                statement.setString(1, subject.getCd());
                statement.setString(2, subject.getName());
                statement.setInt(3, subject.getCredit());
                statement.setString(4, subject.getSchool().getCd());

            } else {
                // UPDATE
                String sql = "UPDATE subject SET name=?, credit=? WHERE cd=?";
                statement = connection.prepareStatement(sql);

                statement.setString(1, subject.getName());
                statement.setInt(2, subject.getCredit());
                statement.setString(3, subject.getCd());
            }

            count = statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return count > 0;
    }

    // =========================
    // ④ 削除
    // =========================
    public boolean delete(String cd) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            String sql = "DELETE FROM subject WHERE cd = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, cd);

            count = statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return count > 0;
    }
}