package com.wiprojobsearch.joblisting.model;

import org.springframework.data.annotation.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class User {
    @Id
    private String id;
    @NotEmpty(message="Fullname is required")
    private String fullName;
    @Email(message="Invalid email format")
    @NotEmpty(message="email is required")
    private String email;
    @NotEmpty(message="Password is required")
    private String password;
    @NotEmpty(message="Mobile number is required")
    private String mobileNumber;
    @NotEmpty(message="workStatus is required")
    private String workStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", workStatus='" + workStatus + '\'' +
                '}';
    }
}
