package com.frigerio.library_service.services.impl;

import com.frigerio.library_service.model.Book;
import com.frigerio.library_service.persistance.BookRepositoryMock;
import com.frigerio.library_service.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class BookServiceImpl implements BookService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BookRepositoryMock bookRepositoryMock = new BookRepositoryMock();
    @Override
    public Book getBookByTitle(Authentication auth,String title) {

        Map<String,Object> tokenAttributes = ((JwtAuthenticationToken) auth).getTokenAttributes();
        Map<String,Object> restrictions = (Map<String,Object>) tokenAttributes.get("restrictions");
        List<String> allowedAuthors = (List<String>) restrictions.get("allowedAuthors");

        Book book = bookRepositoryMock.getBookByTitle(title);
        if(admitBook(book,allowedAuthors)){
            return book;
        }
        logger.error("User {} not allowed to get book: {}", tokenAttributes.get("sub"), book.getTitle());
        throw new AuthorizationServiceException("User not allowed to get [book] :".concat(book.getTitle()));

    }

    @Override
    public List<String> getAllTitles() {
        List<String> titles= new ArrayList<>();
        bookRepositoryMock.getBooks().forEach(e -> titles.add(e.getTitle()));
        return titles;
    }

    @Override
    public Book save(Book book) {
        bookRepositoryMock.getBooks().add(book);
        return book;
    }

    private boolean admitBook(Book book,List<String>authors) {
        //query in dbi
        return authors.stream().anyMatch(author ->
                    book.getAuthor().equalsIgnoreCase(author));
    }
}
