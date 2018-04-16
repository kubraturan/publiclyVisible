package publiclyvisible.publiclyvisible;

import java.util.Date;

public class User {
    public User(String name, String surname, String born, String number) {
        this.name = name;
        this.surname = surname;
        this.born = born;
        this.number = number;
    }

    String name ;
    String surname;
    String born;
    String number;


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBorn() {
        return born;
    }

    public String getNumber() {
        return number;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
