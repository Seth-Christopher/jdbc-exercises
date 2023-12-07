package dao;

import contacts_manager.models.Album;

import java.util.List;

public class MySQLAlbumsTest extends MYSQLDAO {
//    public static void main(String[] args) {
//        MySQLAlbumsDAO albumsDao = new MySQLAlbumsDAO();
//
//        try {
//            albumsDao.createConnection();
//
//            System.out.println("Using the connection...");
//            int numAlbums = albumsDao.getTotalAlbums();
//            System.out.println("Total # of album records: " + numAlbums);
//        } catch(MySQLAlbumsException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            albumsDao.closeConnection();
//        }
//
//    }
public static void main(String[] args) {
    MySQLAlbumsDAO albumsDao = new MySQLAlbumsDAO();

    try {
        albumsDao.createConnection();

        System.out.println("Using the connection...");
        int numAlbums = albumsDao.getTotalAlbums();
        System.out.println("Total # of album records: " + numAlbums);

        // fetch all albums and print the size
        // this should equal the above Total # of album records:
        List<Album> albums = albumsDao.fetchAlbums();
        System.out.println("Number of album records: " + albums.size());

        // fetch and print a single album (the album with id 1 in the db)
        Album album = albumsDao.fetchAlbumById(1L);
        System.out.println("Album with id 1: " + album);

        // insert a new album
        Album newAlbum = new Album();
        newAlbum.setName("Wish You Were Hear");
        newAlbum.setArtist("Pink Floyd");
        newAlbum.setReleaseDate(1975);
        newAlbum.setGenre("Progressive Rock");
        newAlbum.setSales(9.2);
        long newId = albumsDao.insertAlbum(newAlbum);
        System.out.println("Id for new album: " + newId);
        newAlbum.setId(newId);

        // fix a mistake in the new album
        newAlbum.setName("Wish You Were Here");
        albumsDao.updateAlbum(newAlbum);

//        // let's confirm that the update worked
        album = albumsDao.fetchAlbumById(newId);
        System.out.println("Fixed album: " + album);
//
//        // delete the album
        albumsDao.deleteAlbumById(newId);
        System.out.println("Album with id " + newId + " deleted");

    } catch(MySQLAlbumsException e) {
        System.out.println(e.getMessage());
    } finally {
        albumsDao.closeConnection();
    }

}
}