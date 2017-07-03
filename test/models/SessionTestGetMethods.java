import org.junit.*;
import models.Session;
import models.TaskTrial;

import static org.junit.Assert.*;
/**
 * @author gabrielahlers
 */

public class SessionTestGetMethods {

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

       assertEquals("GetUsername", "testUser", testSession.getUsername());
   }

   @Test
   public void testGetConnectionInfo() {
     Session testSession = new Session();
     testSession.setConnectionInfo(1101);

     assertEquals("getConnectionInfo", 1101, testSession.getConnectionInfo());
   }

   @Test
   public void testGetTastTrial() {

    Session testSession = new Session();
    TaskTrial testTaskTrial = new TaskTrial();
    testSession.setTaskTrial(testTaskTrial);

    assertEquals("TaskTrial", testTaskTrial, testSession.getTaskTrial());
   }

   @Test
   public void testIsValid() {
     Session testSession = new Session();

     assertTrue("SessionIsValid", testSession.isValid());
   }







 }
