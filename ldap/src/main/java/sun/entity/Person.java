package sun.entity;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;

/**
 * @author sumu
 * @date 12/31/2021 4:50 PM
 */
public class Person {
    private String username;
    private String password;
    private String userEmail;
    private String age;
    private String phone;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", age='" + age + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
