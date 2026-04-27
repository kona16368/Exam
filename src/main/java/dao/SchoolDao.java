package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;

public class SchoolDao extends Dao {

    /**
     * getメソッド 学校コードを指定して学校インスタンスを１件取得する
     */
    public School get(String cd) throws Exception {
        School school = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from school where cd = ?"
            );
            statement.setString(1, cd);

            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                school = new School();

                // 学校情報をセット
                school.setCd(rSet.getString("cd"));
                school.setName(rSet.getString("name"));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return school;
    }
}