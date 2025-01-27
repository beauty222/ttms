package xupt.se.ttms.model;


public class user {
	//private int id=0;
    private String username;//用户名
    private String password;//密码
    private String phonenumber;//电话
    private String question;
    private String answer;
    private String status;//状态
    public user(){
		username="";
	}
	public user(String username, String password,String phonenumber,String question,String answer,String status){
		this.username=username;
		this.password = password;
		this.phonenumber = phonenumber;	
		this.status=status;
		this.question=question;
		this.answer=answer;
	}
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    @Override
    public String toString() {
        return "user{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}