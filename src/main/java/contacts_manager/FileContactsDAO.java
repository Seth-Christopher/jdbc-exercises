package contacts_manager;

import java.util.List;

public class FileContactsDAO implements ContactsDAO {
    @Override
    public List<Contact> fetchContacts() {
        return null;
    }

    @Override
    public long insertContact(Contact contact) {
        return 0;
    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public List<Contact> searchContacts(String searchTerm) {
        return null;
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }
}
