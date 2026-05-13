package bean;

import java.io.Serializable;

public class Test extends User implements Serializable {

    private String student_no;

    private String subject_cd;

    private String school_cd;

    private int no;

    private int point;

    private String class_num;

	// 入学年度
    private int ent_year;

	public Test() {
	}

    public String getStudent_no() {
        return student_no;
    }

    public void setStudent_no(String student_no) {
        this.student_no = student_no;
    }

    public String getSubject_cd() {
        return subject_cd;
    }

    public void setSubject_cd(String subject_cd) {
        this.subject_cd = subject_cd;
    }

    public String getSchool_cd() {
        return school_cd;
    }

    public void setSchool_cd(String school_cd) {
        this.school_cd = school_cd;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

	public int getPoint() {
        return point;
    }

	public void setPoint(int point) {
        this.point = point;
    }

    public String getClass_num() {
        return class_num;
    }

    public void setClass_num(String class_num) {
        this.class_num = class_num;
    }

    public int getEnt_year() {
        return ent_year;
    }

    public void setEnt_year(int ent_year) {
        this.ent_year = ent_year;
    }
}