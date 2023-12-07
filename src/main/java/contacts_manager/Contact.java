package contacts_manager;

public class Contact {
    // note that id is not used in the original Java 2 Contacts Manager
    // we have added it here so that it will transition better to a rel. db
    private long id;
    private String fullName;
    private String phoneNumber;

    public Contact(String fullName, String phoneNumber) {
        this.id = 1;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
