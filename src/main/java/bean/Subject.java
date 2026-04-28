package bean;

import java.io.Serializable;

public class Subject implements Serializable {

    // クラス番号
    private String cd;
    
    //科目名
    private String name;
    // 学校
    private School school;

    // ===== getter / setter =====

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
    public String getname() {
    	return name;
	}
    public void setName(String name) {
    	this.name=name;
		
	}
}