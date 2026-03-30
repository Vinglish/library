import exception.LibraryException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static exception.ErrorCode.*;

public class Library {

    Map<Long, Book> bookMap = new HashMap<>();
    Map<Long, Reader> readerMap = new HashMap<>();
    Map<Long, List<Book>> authorBookMap = new HashMap<>();
    Map<Long, List<BorrowRecord>> borrowHistoryMap = new HashMap<>();

    void addBook(Book book) {
        bookMap.put(book.getId(), book);
    }

    Book findBook(long bookId) {

        Book book = bookMap.get(bookId);

        if (Objects.isNull(book)) {
            throw new LibraryException(BOOK_NOT_FOUND);
        }

        return book;
    }

    void removeBook(Book book) {

        if (book.isBorrowed()) {
            throw new LibraryException(BOOK_IS_BORROWED);
        }

        bookMap.remove(book.getId());
    }

    void addReader(Reader reader) {
        readerMap.put(reader.getId(), reader);
    }

    Reader findReader(long readerId) {

        Reader reader = readerMap.get(readerId);

        if (Objects.isNull(reader)) {
            throw new LibraryException(READER_NOT_FOUND);
        }

        return reader;
    }

    void removeReader(Reader reader) {

        if (!reader.getBorrowedBooks().isEmpty()) {
            throw new LibraryException(READER_HAS_BOOKS);
        }

        readerMap.remove(reader.getId());
    }

    void borrowBook(long readerId, long bookId) {

        Reader reader = findReader(readerId);
        Book book = findBook(bookId);

        if (book.isBorrowed()) {
            throw new LibraryException(BOOK_IS_BORROWED);
        }

        reader.borrowBook(book);
        book.borrow(reader);

    }

    void returnBook(long readerId, long bookId) {
        Reader reader = findReader(readerId);
        Book book = findBook(bookId);

        if (!book.getBorrowedBy().getId().equals(readerId)) {
            throw new LibraryException(BOOK_NOT_BELONG_TO_READER);
        }

        book.returnBack();
        reader.returnBook(book);
    }

    public List<Reader> getAllReaders() {

        return readerMap.values()
                .stream()
                .toList();
    }

}
