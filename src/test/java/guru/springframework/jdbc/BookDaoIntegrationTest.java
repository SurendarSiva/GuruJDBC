package guru.springframework.jdbc;


import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.AuthorDaoImpl;
import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.dao.BookDaoImpl;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
//@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@Import({BookDaoImpl.class,AuthorDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {

    @Autowired
    BookDao bookDao;

    @Autowired
    AuthorDao authorDao;

    @Test
    void testFindBookById(){

        Book book = bookDao.getById(1L);

        assertThat(book).isNotNull();

    }

    @Test
    void testFindBookByTitle(){

        Book book = bookDao.findBookByTitle("Clean Code");

        assertThat(book).isNotNull();

    }

    @Test
    void testSavedBook(){

        Book book = new Book();
        book.setIsbn("6754");
        book.setTitle("test book");
        book.setPublisher("no-one");

        Author author = new Author();
        author.setId(3L);
        book.setAuthor(author);

        Book saved = bookDao.saveBook(book);
        assertThat(saved).isNotNull();

    }

    @Test
    void testUpdateBook(){

        Book book = new Book();
        book.setIsbn("6754");
        book.setTitle("test book");
        book.setPublisher("no-one");

        Author author = new Author();
        author.setId(3L);
        book.setAuthor(author);

        Book saved = bookDao.saveBook(book);
        saved.setTitle("new test book");
        bookDao.updateBook(saved);

        Book updated = bookDao.getById(saved.getId());

        assertThat(updated.getTitle()).isEqualTo("new test book");

    }

    @Test
    void testDeleteById(){

        Book book = new Book();
        book.setTitle("new book");
        book.setPublisher("unknown");
        book.setIsbn("3456");

        Book saved = bookDao.saveBook(book);
        bookDao.deleteBookById(saved.getId());
        Book deleted = bookDao.getById(saved.getId());

        assertThat(deleted).isNull();


    }

}
