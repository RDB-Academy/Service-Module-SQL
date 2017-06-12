import org.junit.*;
import models.Session;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;
/**
 * @author gabrielahlers
 */

 public class SessionTestGetMethods {

   @Before
   public void setup() {
   }

   @Test
   public void testGetSessionId() {
     Session testSession = new Session();

     assertTrue(testSession.getId() != null);
   }

   @Test
   public void testGetAmbiguousSessionId() {
     Session testSession1 = new Session();
     Session testSession2 = new Session();
     
     assertTrue(testSession1.getId() != testSession2.getId());
   }

   @Test
   public void testGetUserId() {
     Session testSession = new Session();
     Long id = 1l;
     testSession.setUserId(id);

     assertEquals("GetUserId", Long.valueOf(1), testSession.getUserId());
   }

   @Test
   public void testGetUsername() {
       Session testSession = new Session();
       testSession.setUsername("testUser");

       assertEquals("GetUserId", "testUser", testSession.getUsername());
   }







 }
