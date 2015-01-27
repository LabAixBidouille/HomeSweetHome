package org.labaix.pojo;

/**
 * Created by nicolas on 22/01/15.
 */
public class Contact {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (this.firstname != null ? !this.firstname.equals(contact.firstname) : contact.firstname != null) return false;
        if (this.lastname != null ? !this.lastname.equals(contact.lastname) : contact.lastname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = this.firstname != null ? this.firstname.hashCode() : 0;
        result = 31 * result + (this.lastname != null ? this.lastname.hashCode() : 0);
        return result;
    }

    public Contact(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {

        return this.firstname;

    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    private String firstname;
    private String lastname;
}
