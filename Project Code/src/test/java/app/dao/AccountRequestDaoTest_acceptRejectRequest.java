package app.dao;

import app.Main;
import app.model.dto.AccountRequest;
import app.model.dto.User;
import org.junit.jupiter.api.*;

import static io.javalin.Javalin.log;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountRequestDaoTest_acceptRejectRequest {

    private AccountRequestDao requestDao;

    @BeforeAll
    void initAll() {
        log.info("Test initialisation...");
        //May have to change username and password values manually
        Main.main(new String[]{"root", ""});
    }

    @BeforeEach
    void initEach() {
        log.info("Test initialisation...");
        requestDao = new AccountRequestDao();
        AccountRequest ar = new AccountRequest(99, "abc@gmail.com",
                "critic", "Marvel", "04565656556", "2021-05-22 00:00:00");

        if (requestDao.add(ar)) {
            log.info("request added to db");
        }
        else {
            log.error("Adding request to db failed");
        }
    }

    @DisplayName("Testing... Reject Request From Application")
    @Test
    void rejectRequestFromApplicationTest() {
        log.info("Testing... Reject Request");
        AccountRequest request = requestDao.getRequestByID(99);
        requestDao.remove(request);
        assertNull(requestDao.getRequestByID(99));
        log.info("Success");
    }

    @DisplayName("Testing... Reject Request From Database")
    @Test
    void rejectRequestFromDatabaseTest() {
        log.info("Testing... Reject Request");
        AccountRequest request = requestDao.getRequestByID(99);
        requestDao.remove(request);

        //Using a new DAO object to fetch from DB
        //creating a new instance of a DAO pulls all current information from MySQL database
        AccountRequestDao requestDao2 = new AccountRequestDao();
        assertNull(requestDao2.getRequestByID(99));
        log.info("Success");
    }

    @DisplayName("Testing... Accept Request From Application")
    @Test
    void acceptRequestFromApplicationTest() {
        log.info("Testing... Accept Request");
        AccountRequest request = requestDao.getRequestByID(99);

        UserDao userDao = new UserDao();
        User account = userDao.getUserByEmail(request.getEmail());
        account.setType(request.getType());
        account.setOrgName(request.getOrgName());
        account.setOrgPhone(request.getOrgPhone());

        requestDao.acceptRequest(account);

        assertEquals(request.getType(), account.getType());

        log.info("Success");
    }

    @DisplayName("Testing... Accept Request From Database")
    @Test
    void acceptRequestFromDatabaseTest() {
        log.info("Testing... Accept Request");
        AccountRequest request = requestDao.getRequestByID(99);

        UserDao userDao = new UserDao();
        User account = userDao.getUserByEmail(request.getEmail());
        account.setType(request.getType());
        account.setOrgName(request.getOrgName());
        account.setOrgPhone(request.getOrgPhone());

        requestDao.acceptRequest(account);

        //getting updated user details from db
        //creating a new instance of a DAO pulls all current information from MySQL database
        UserDao userDao1 = new UserDao();
        User account1 = userDao1.getUserByEmail(request.getEmail());

        assertEquals(request.getType(), account1.getType());

        log.info("Success");
    }

    @DisplayName("Cleaning up...")
    @AfterAll
    void done() {
        requestDao.remove(requestDao.getRequestByID(99));
        log.info("Cleaned up residual test data from db");
    }
}
