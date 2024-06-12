package DigitalLibraryManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private String id;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    @Override
    public String toString() {
        return "Book [ID=" + id + ", Title=" + title + ", Author=" + author + ", Issued=" + isIssued + "]";
    }
}

class Member {
    private String id;
    private String name;
    private String email;

    public Member(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Member [ID=" + id + ", Name=" + name + ", Email=" + email + "]";
    }
}

class Admin {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void addBook(Library library, Book book) {
        library.addBook(book);
    }

    public void removeBook(Library library, String bookId) {
        library.removeBook(bookId);
    }

    public void addMember(Library library, Member member) {
        library.addMember(member);
    }

    public void removeMember(Library library, String memberId) {
        library.removeMember(memberId);
    }

    public void modifyBook(Library library, String bookId, String newTitle, String newAuthor) {
        library.modifyBook(bookId, newTitle, newAuthor);
    }

    public void modifyMember(Library library, String memberId, String newName, String newEmail) {
        library.modifyMember(memberId, newName, newEmail);
    }
}

class User extends Member {

    public User(String id, String name, String email) {
        super(id, name, email);
    }

    public void viewBooks(Library library) {
        List<Book> books = library.getBooks();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void issueBook(Library library, String bookId) {
        Book book = library.getBookById(bookId);
        if (book != null && !book.isIssued()) {
            book.setIssued(true);
            System.out.println("Book issued successfully.");
        } else {
            System.out.println("Book is either not available or already issued.");
        }
    }

    public void returnBook(Library library, String bookId) {
        Book book = library.getBookById(bookId);
        if (book != null && book.isIssued()) {
            book.setIssued(false);
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book is either not available or not issued.");
        }
    }

    public void searchBook(Library library, String title) {
        List<Book> books = library.searchBook(title);
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void sendEmail(String query) {
        System.out.println("Sending email with query: " + query);
    }
}

class Library {
    private List<Book> books;
    private List<Member> members;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(String bookId) {
        books.removeIf(book -> book.getId().equals(bookId));
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void removeMember(String memberId) {
        members.removeIf(member -> member.getId().equals(memberId));
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book getBookById(String bookId) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> searchBook(String title) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public void modifyBook(String bookId, String newTitle, String newAuthor) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                book = new Book(bookId, newTitle, newAuthor);
            }
        }
    }

    public void modifyMember(String memberId, String newName, String newEmail) {
        for (Member member : members) {
            if (member.getId().equals(memberId)) {
                member = new Member(memberId, newName, newEmail);
            }
        }
    }

    public Member getMemberById(String memberId) {
        for (Member member : members) {
            if (member.getId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }
}

public class DigitalLibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Admin admin = new Admin("admin", "admin123");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter admin username: ");
                String username = scanner.nextLine();
                System.out.print("Enter admin password: ");
                String password = scanner.nextLine();

                if (admin.login(username, password)) {
                    System.out.println("Admin login successful!");
                    while (true) {
                        System.out.println("1. Add Book");
                        System.out.println("2. Remove Book");
                        System.out.println("3. Add Member");
                        System.out.println("4. Remove Member");
                        System.out.println("5. Modify Book");
                        System.out.println("6. Modify Member");
                        System.out.println("7. Logout");
                        System.out.print("Enter your choice: ");
                        int adminChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (adminChoice == 1) {
                            System.out.print("Enter book ID: ");
                            String bookId = scanner.nextLine();
                            System.out.print("Enter book title: ");
                            String title = scanner.nextLine();
                            System.out.print("Enter book author: ");
                            String author = scanner.nextLine();
                            Book book = new Book(bookId, title, author);
                            admin.addBook(library, book);
                        } else if (adminChoice == 2) {
                            System.out.print("Enter book ID to remove: ");
                            String bookId = scanner.nextLine();
                            admin.removeBook(library, bookId);
                        } else if (adminChoice == 3) {
                            System.out.print("Enter member ID: ");
                            String memberId = scanner.nextLine();
                            System.out.print("Enter member name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter member email: ");
                            String email = scanner.nextLine();
                            Member member = new Member(memberId, name, email);
                            admin.addMember(library, member);
                        } else if (adminChoice == 4) {
                            System.out.print("Enter member ID to remove: ");
                            String memberId = scanner.nextLine();
                            admin.removeMember(library, memberId);
                        } else if (adminChoice == 5) {
                            System.out.print("Enter book ID to modify: ");
                            String bookId = scanner.nextLine();
                            System.out.print("Enter new book title: ");
                            String newTitle = scanner.nextLine();
                            System.out.print("Enter new book author: ");
                            String newAuthor = scanner.nextLine();
                            admin.modifyBook(library, bookId, newTitle, newAuthor);
                        } else if (adminChoice == 6) {
                            System.out.print("Enter member ID to modify: ");
                            String memberId = scanner.nextLine();
                            System.out.print("Enter new member name: ");
                            String newName = scanner.nextLine();
                            System.out.print("Enter new member email: ");
                            String newEmail = scanner.nextLine();
                            admin.modifyMember(library, memberId, newName, newEmail);
                        } else if (adminChoice == 7) {
                            break;
                        }
                    }
                } else {
                    System.out.println("Invalid admin credentials.");
                }
            } else if (choice == 2) {
                System.out.print("Enter user ID: ");
                String userId = scanner.nextLine();
                System.out.print("Enter user name: ");
                String userName = scanner.nextLine();

                Member member = library.getMemberById(userId);
                if (member != null && member.getName().equals(userName)) {
                    System.out.println("User login successful!");
                    User user = new User(userId, userName, member.getEmail());

                    while (true) {
                        System.out.println("1. View Books");
                        System.out.println("2. Issue Book");
                        System.out.println("3. Return Book");
                        System.out.println("4. Search Book");
                        System.out.println("5. Send Email");
                        System.out.println("6. Logout");
                        System.out.print("Enter your choice: ");
                        int userChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (userChoice == 1) {
                            user.viewBooks(library);
                        } else if (userChoice == 2) {
                            System.out.print("Enter book ID to issue: ");
                            String bookId = scanner.nextLine();
                            user.issueBook(library, bookId);
                        } else if (userChoice == 3) {
                            System.out.print("Enter book ID to return: ");
                            String bookId = scanner.nextLine();
                            user.returnBook(library, bookId);
                        } else if (userChoice == 4) {
                            System.out.print("Enter book title to search: ");
                            String title = scanner.nextLine();
                            user.searchBook(library, title);
                        } else if (userChoice == 5) {
                            System.out.print("Enter your query: ");
                            String query = scanner.nextLine();
                            user.sendEmail(query);
                        } else if (userChoice == 6) {
                            break;
                        }
                    }
                } else {
                    System.out.println("Invalid user credentials.");
                }
            } else if (choice == 3) {
                break;
            }
        }
        scanner.close();
    }
}
