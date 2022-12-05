package guru.springframework.jdbc;


import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.AuthorDaoImpl;
import guru.springframework.jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
//@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;



    @Test
    void testGetAuthor() {

        Author author = authorDao.getById(1L);

        assertThat(author).isNotNull();

    }

    @Test
    void testFindAuthorByName() {

        Author author = authorDao.findAuthorByName("Eric", "Evans");

        assertThat(author).isNotNull();

    }
    @Test
    void testSaveNewAuthor(){

        Author author = new Author();
        author.setFirstName("Surendar");
        author.setLastName("Siva");
        Author savedAuthor = authorDao.saveNewAuthor(author);

        assertThat(savedAuthor).isNotNull();

    }

    @Test
    void testUpdateAuthor(){
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Thomson");

        Author saved = authorDao.saveNewAuthor(author);

        saved.setLastName("Thompson");
        Author updated = authorDao.updateAuthor(saved);

        assertThat(updated.getLastName()).isEqualTo("Thompson");

    }

    @Test
    void testDeleteAuthorById(){
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("tom");

        Author saved = authorDao.saveNewAuthor(author);

        authorDao.deleteAuthorById(saved.getId());

        Author deleted = authorDao.getById(saved.getId());
        assertThat(deleted).isNull();



    }
}
