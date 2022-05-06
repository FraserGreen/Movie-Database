package app.dao;

import app.Main;
import app.model.dto.Show;
import org.junit.jupiter.api.*;


import static io.javalin.Javalin.log;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShowDaoTest_editShow {

    private ShowDao showDao;

    @BeforeAll
    void initAll() {
        log.info("Test initialisation...");
        //May have to change username and password values manually
        Main.main(new String[]{"root", ""});
        showDao = new ShowDao();
    }

    @BeforeEach
    void initialise() {
        showDao.add(new Show(99, "Test Show", "Action", 2.0, true, false, 1, "2020", "test.png", "Visible"));
    }

    @Test
    void editApplicationSuccessful() {

        showDao.editShow(99, "Edited Test Show", "Thriller", 2.5, false, true, 2, "2021", "test.jpg", "Suspended");
        Show show = showDao.getShowById(99);
        assertEquals(show.getTitle(), "Edited Test Show");
    }

    @Test
    void editBackendSuccessful() {

        showDao.editShow(99, "Edited Test Show", "Thriller", 2.5, false, true, 2, "2021", "test.jpg", "Suspended");
        //creating new instance of a DAO fetches all current data from database
        ShowDao showDao2 = new ShowDao();
        Show show = showDao2.getShowById(99);
        assertEquals(show.getTitle(), "Edited Test Show");
    }

    @Test
    void editBadProcoID() {
        showDao.editShow(99, "Edited Test Show", "Thriller", 2.5, false, true, 9999, "2021", "test.jpg", "Suspended");
        Show show = showDao.getShowById(99);
        assertEquals(show.getProcoID(), 1);
    }

    @Test
    void editBadLength() {
        showDao.editShow(99, "Edited Test Show", "Thriller", 99999999, false, true, 2, "2021", "test.jpg", "Suspended");
        Show show = showDao.getShowById(99);
        assertEquals(show.getLength(), 2);
    }

    @Test
    void editBadYear() {
        showDao.editShow(99, "Edited Test Show", "Thriller", 2.5, false, true, 2, "9999999999999", "test.jpg", "Suspended");
        Show show = showDao.getShowById(99);
        assertEquals(show.getYear(), "2020");
    }

    @AfterEach
    void done() {
        showDao.deleteShow(99);
        log.info("Cleaned up residual test data from db");
    }
}
