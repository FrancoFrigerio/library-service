package com.frigerio.library_service.persistance;

import com.frigerio.library_service.model.Book;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryMock {

    public List<Book> getBooks(){
        //db
        List<Book> booksMock = new ArrayList<>();
        Book book1 = new Book();
        book1.setTitle("Better call Soul");
        book1.setCategory("NOVELA");
        book1.setAuthor("Josefina");
        Book book2 = new Book();
        book2.setTitle("El ethernauta");
        book2.setCategory("SUSPENSO");
        book2.setAuthor("Josefina");
        Book book3 = new Book();
        book3.setTitle("Spiderman");
        book3.setCategory("FICCION");
        book3.setAuthor("Augusto");

        booksMock.add(book1);
        booksMock.add(book2);
        booksMock.add(book3);


        return booksMock;
    }
    public Book getBookByTitle(String title){
        return getBooks().stream(). filter(bookElement ->
            bookElement.getTitle().equalsIgnoreCase(title))
            .findFirst().orElse(null);
    }

}
