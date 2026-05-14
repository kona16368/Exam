package bean;

import java.io.Serializable;

public class TestStudent
    implements Serializable {

    private String subjectName;
    private String subjectCd;
    private Integer no;
    private Integer point;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(
            String subjectName) {

        this.subjectName =
                subjectName;
    }

    public String getSubjectCd() {
        return subjectCd;
    }

    public void setSubjectCd(
            String subjectCd) {

        this.subjectCd =
                subjectCd;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(
            Integer no) {

        this.no = no;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(
            Integer point) {

        this.point = point;
    }
}