package pl.knap.libsma.database.models;

public class Book {
    private int id, userID, status;
    private String authorName, authorSurname, category, title, releaseDate, isbn, userName;

    public Book(String authorName, String authorSurname, String category, String title, String releaseDate, String isbn) {
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.category = category;
        this.title = title;
        this.releaseDate = releaseDate;
        this.isbn = isbn;
    }

    public Book(int id, String title, String authorName, String authorSurname, String category, String releaseDate, String isbn, int status, int userID) {
        this.id = id;
        this.userID = userID;
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.category = category;
        this.title = title;
        this.releaseDate = releaseDate;
        this.status = status;
        this.isbn = isbn;
    }

    public Book() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
