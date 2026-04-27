package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Teacher;

public class TeacherDao extends Dao {

    /**
     * getメソッド 教員IDを指定して教員インスタンスを1件取得する
     */
    public Teacher get(String id) throws Exception {
        Teacher teacher = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from teacher where id=?"
            );
            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();

            // 学校Dao
            SchoolDao schoolDao = new SchoolDao();

            if (resultSet.next()) {
                teacher = new Teacher();

                // 教員情報セット
                teacher.setId(resultSet.getString("id"));
                teacher.setName(resultSet.getString("name"));
                teacher.setPassword(resultSet.getString("password"));

                // 学校情報セット
                String schoolCd = resultSet.getString("school_cd");
                teacher.setSchool(schoolDao.get(schoolCd));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return teacher;
    }

    /**
     * loginメソッド 教員IDとパスワードで認証する
     */
    public Teacher login(String id, String password) throws Exception {
        Teacher teacher = get(id);

        if (teacher == null || !teacher.getPassword().equals(password)) {
            return null;
        }

        return teacher;
    }
}