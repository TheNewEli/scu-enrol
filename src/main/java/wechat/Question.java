package wechat;

public class Question {
    private String _id;
    private String _openid;
    private String content;
    private String status;
    private String submition_time;

    private String sutdent_id;
    private String art_n_sicence;
    private String district;
    private String gender;
    private String name;
    private String phone_number;
    private String ranking;
    private String school;
    private String score;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_openid() {
        return _openid;
    }

    public void set_openid(String _openid) {
        this._openid = _openid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmition_time() {
        return submition_time;
    }

    public void setSubmition_time(String submition_time) {
        this.submition_time = submition_time;
    }

    public String getSutdent_id() {
        return sutdent_id;
    }

    public void setSutdent_id(String sutdent_id) {
        this.sutdent_id = sutdent_id;
    }

    public String getArt_n_sicence() {
        return art_n_sicence;
    }

    public void setArt_n_sicence(String art_n_sicence) {
        this.art_n_sicence = art_n_sicence;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
