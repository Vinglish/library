public class Identifier {

    private static Long bookId = 1L;
    private static Long readerId = 1L;
    private static Long authorId = 1L;

    private Identifier() {

    }

    public static Long generateBookId() {
        return bookId++;
    }

    public static Long generateReaderId() {
        return readerId++;
    }

    public static Long generateAuthorId() {
        return authorId++;
    }

}
