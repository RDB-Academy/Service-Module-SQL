import org.junit.Test;
import repository.SessionRepository;

import static org.mockito.Mockito.mock;

/**
 * @author fabiomazzone
 */
public class SessionServiceTest {

    @Test
    public void testIsAdmin() {
        System.out.println("Test");
        SessionRepository sessionRepository = mock(SessionRepository.class);
    }
}
