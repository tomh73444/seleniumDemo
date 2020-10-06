package demo;


public class Person {

private String lastName;
private String firstName;
private String email;
private double dues;
private String website;

public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}

public double getDues() {
	return dues;
}
public void setDues(double dues) {
	this.dues = dues;
}
public String getWebsite() {
	return website;
}
public void setWebsite(String website) {
	this.website = website;
}
@Override
public String toString() {
	return "Person lastName=" + lastName + ", firstName=" + firstName
			+ ", email=" + email + ", dues=" + dues + ", website=" + website + "]";
}
}
