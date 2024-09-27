package okestro.hjpark.vo;

public class PasswordVerificationRequest {
    private Long id;
    private String password;

    // 기본 생성자
    public PasswordVerificationRequest() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
