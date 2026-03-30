import exception.ErrorCode;
import exception.LibraryException;

import static exception.ErrorCode.BOOK_NOT_BORROWED;

public class Book {

    private Long id;
    private String title;
    private Author author;
    private Reader borrowedBy;
    private boolean isBorrowed;
    private int borrowedCount;
    private int year;

    public Book(String title, Author author, int year) {
        this.id = Identifier.generateBookId();
        this.title = title;
        this.author = author;
        this.year = year;
    }

    void borrow(Reader reader) {

        if (isBorrowed) {
            throw new LibraryException(ErrorCode.BOOK_IS_BORROWED);
        }

        isBorrowed = true;
        borrowedBy = reader;
        borrowedCount++;
    }

    void returnBack() {

        if (!isBorrowed) {
            throw new LibraryException(BOOK_NOT_BORROWED);
        }

        isBorrowed = false;
        borrowedBy = null;
    }

    void getInfo() {
        System.out.println("Book name:" + title);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Reader getBorrowedBy() {
        return borrowedBy;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public int getYear() {
        return year;
    }
}
