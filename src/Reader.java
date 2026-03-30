import exception.ErrorCode;
import exception.LibraryException;

import java.util.List;

public class Reader {

    private Long id;
    private String name;
    private List<Book> borrowedBooks;
    private int totalBooks;

    public Reader(String name, List<Book> borrowedBooks) {
        this.id = Identifier.generateReaderId();
        this.name = name;
        this.borrowedBooks = borrowedBooks;
    }

    void borrowBook(Book book) {

        if(borrowedBooks.size() >= 3) {
            throw new LibraryException(ErrorCode.BOOK_LIMIT);
        }

        borrowedBooks.add(book);
        totalBooks++;
    }

    void returnBook(Book book) {

        borrowedBooks.remove(book);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public int getTotalBooks() {
        return totalBooks;
    }
}
