package dao;

import config.Config;
import com.mysql.cj.jdbc.Driver;
import contacts_manager.models.Album;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLAlbumsDAO {
    // initialize the connection to null, so we know whether to close it when done or not
    private Connection connection = null;




    public List<Album> fetchAlbums() {
        List<Album> albums = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM albums");
            while (resultSet.next()){
                albums.add(new Album(
                    resultSet.getLong("id"),
                    resultSet.getString("artist"),
                    resultSet.getString("name"),
                    resultSet.getInt("release_date"),
                    resultSet.getDouble("sales"),
                    resultSet.getString("genre")
                ));
            }
        } catch (SQLException sqlx) {
            System.out.println(sqlx.getMessage());
        }
        return albums;
    }

    public Album fetchAlbumById(long id) {
       Album album = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM albums WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            album = new Album();
            album.setId(resultSet.getLong("id"));
            album.setArtist(resultSet.getString("artist"));
            album.setName(resultSet.getString("name"));
            album.setReleaseDate(resultSet.getInt("release_date"));
            album.setSales(resultSet.getDouble("sales"));
            album.setGenre(resultSet.getString("genre"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       return album;
    }

    // Note that insertAlbum should return the id that MySQL creates for the new inserted album record
    public long insertAlbum(Album album) throws MySQLAlbumsException {
        long id = 0;
        PreparedStatement statement = null;

        try {
             statement = connection.prepareStatement("INSERT INTO albums(artist, name, release_date, genre, sales) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, album.getArtist());
            statement.setString(2, album.getName());
            statement.setLong(3, album.getReleaseDate());
            statement.setString(4, album.getGenre());
            statement.setDouble(5, album.getSales());

            int numInserted = statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();

            id = keys.getLong(1);
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAlbum(Album album) throws MySQLAlbumsException {
        try {
            PreparedStatement st = connection.prepareStatement("UPDATE albums" +
                    " set artist = ? " +
                    " , name = ? " +
                    ", release_date = ?" +
                    ", sales = ?" +
                    ", genre = ?" +
                    " where id = ? ");
            st.setString(1, album.getArtist());
            st.setString(2, album.getName());
            st.setLong(3, album.getReleaseDate());
            st.setDouble(4, album.getSales());
            st.setString(5, album.getGenre());
            st.setLong(6, album.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAlbumById(long id) throws MySQLAlbumsException {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("DELETE FROM albums where ID = ?");
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }





    public void createConnection() throws MySQLAlbumsException {
        System.out.print("Trying to connect... ");
        try {
            //TODO: create the connection and assign it to the instance variable
            DriverManager.registerDriver(new Driver());

            // establish connection
            connection = DriverManager.getConnection(
                    Config.getUrl(),
                    Config.getUser(),
                    Config.getPassword()
            );
            System.out.println("connection created.");
        } catch (SQLException e) {
            throw new MySQLAlbumsException("connection failed!!!");
        }
    }

    public int getTotalAlbums() throws MySQLAlbumsException {
        int count = 0;
        try {
            //TODO: fetch the total number of albums from the albums table and assign it to the local variable

            Statement statement = connection.createStatement();

            // OPTION 1: LOOP OVER RESULTSET AND INCREMENT COUNT
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM albums");
            resultSet.next();
            // resultset get... methods accept either a column name
            // or a column index
            count = resultSet.getInt(1);


        } catch (SQLException e) {
            throw new MySQLAlbumsException("Error executing query: " + e.getMessage() + "!!!");
        }
        return count;
    }

    public void closeConnection() {
        if(connection == null) {
            System.out.println("Connection aborted.");
            return;
        }
        try {
            //TODO: close the connection
            connection.close();

            System.out.println("Connection closed.");
        } catch(SQLException e) {
            // ignore this
        }
    }

}