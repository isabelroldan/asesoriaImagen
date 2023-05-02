package iesFranciscodelosRios.model;

import java.util.Objects;

public class Person {
    protected int id_person;
    protected String name;
    protected String surname;
    protected String telephone;
    protected String email;
    protected String password;

    public Person(int id_person, String name, String surname, String telephone, String email, String password) {
        this.id_person = id_person;
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
    }

    public Person() {
        this(0, "", "", "", "", "");
    }

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id_person == person.id_person;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_person);
    }

    @Override
    public String toString() {
        return "Person " +
                "\n\tid_person=" + id_person +
                "\n\tname='" + name + '\'' +
                "\n\tsurname='" + surname + '\'' +
                "\n\ttelephone='" + telephone + '\'' +
                "\n\temail='" + email + '\'' +
                "\n\tpassword='" + password + '\'';
    }
}
