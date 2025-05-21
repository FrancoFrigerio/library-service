package com.frigerio.library_service.rest;

import com.frigerio.library_service.model.Book;
import com.frigerio.library_service.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{title}")
    @PostAuthorize("@bookValidation.hasBookPermission(returnObject.body.category, authentication)")
    public ResponseEntity<Book> getBook( Authentication auth, @PathVariable String title){
        return ResponseEntity.ok(bookService.getBookByTitle(auth,title));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<Book>saveBook(@RequestBody Book book){
        return ResponseEntity.ok(bookService.save(book));
    }

    @GetMapping("/")
    public ResponseEntity<List<String>>getAllBooks(){
        return ResponseEntity.ok(bookService.getAllTitles());
    }
}
