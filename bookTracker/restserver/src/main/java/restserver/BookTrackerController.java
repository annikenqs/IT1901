package restserver;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.Book;
import core.BookShelf;
import core.User;
import core.Users;

/**
 * The service implementation.
 */

@RestController
@RequestMapping("/api")
public class BookTrackerController {

  @Autowired
  private UsersService usersService;

  @Autowired
  private LibraryService libraryService;
  

  @GetMapping("/users")
  public Users getUsers() throws IOException {
    return usersService.getUsers();
  }

  @GetMapping("/users/{username}")
  public User getUser(@PathVariable("username") String username) throws IOException {
    return getUsers().getUser(username);
  }

  @PostMapping("/users/{username}")
  public ResponseEntity<Object> postUser(@PathVariable("username") String username, @RequestBody User user) throws IOException {
    usersService.postUser(user);
    return new ResponseEntity<>("Bruker lagt til", HttpStatus.CREATED);
  }

  @PutMapping("/users/{username}")
  public ResponseEntity<Object> putUser(@PathVariable("username") String username, @RequestBody User user) throws IOException {
    usersService.putUser(user);
    return new ResponseEntity<>("Bruker endret", HttpStatus.OK);
  }

  @GetMapping("/library")
  public BookShelf getLibrary() throws IOException {
    return libraryService.getLibrary();
  }

  @GetMapping("/library/{bookId}")
  public Book getBook(@PathVariable("bookId") String bookId) throws IOException {
    return libraryService.getLibrary().getBook(bookId);
  }


}
