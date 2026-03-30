package exception;

public enum ErrorCode {

    BOOK_IS_BORROWED("Book is borrowed", 400),
    BOOK_LIMIT("Reader has already 3 books", 400),
    READER_HAS_BOOKS("Reader has books", 400),
    READER_NOT_FOUND("Reader not found", 404),
    BOOK_NOT_FOUND("Book not found", 404),
    BOOK_NOT_BORROWED("Book is not borrowed", 400),
    BOOK_NOT_BELONG_TO_READER("Book is not belong to reader", 400);

    private String message;
    private int code;

    ErrorCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
