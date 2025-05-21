package com.frigerio.library_service.services;

import com.frigerio.library_service.model.Book;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface BookService {

    Book getBookByTitle(Authentication auth, String title );

    List<String> getAllTitles();

    Book save(Book book);
}
