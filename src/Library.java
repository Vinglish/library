import exception.LibraryException;

import java.time.LocalDate;
import java.util.*;

import static exception.ErrorCode.*;

public class Library {

    Map<Long, Book> bookMap = new HashMap<>();
    Map<Long, Reader> readerMap = new HashMap<>();
    Map<Long, List<Book>> authorBookMap = new HashMap<>();
    NavigableMap<LocalDate, List<BorrowRecord>> borrowHistoryMap = new TreeMap<>();

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

    public void addBorrowRecord(long readerId, long bookId, LocalDate borrowedAt) {
        BorrowRecord record = new BorrowRecord(readerId, bookId, borrowedAt);

        borrowHistoryMap
                .computeIfAbsent(borrowedAt, _ -> new ArrayList<>())
                .add(record);
    }

    void markReturn(long readerId, long bookId, LocalDate returnedAt) {

        for (Map.Entry<LocalDate, List<BorrowRecord>> entry : borrowHistoryMap.descendingMap().entrySet()) {

            List<BorrowRecord> daysRecord = entry.getValue();

            for (int i = daysRecord.size() - 1; i >= 0; i--) {

                BorrowRecord record = daysRecord.get(i);

                if (record.getReaderId().equals(readerId)
                        && record.getBookId().equals(bookId)
                        && !record.isReturned()) {

                    record.markReturned(returnedAt);
                    return;
                }
            }
        }

        throw new LibraryException(BOOK_NOT_BORROWED);
    }

    List<BorrowRecord> getBorrowHistoryByRange(LocalDate startDate, LocalDate endDate) {

        return null;
    }


}
