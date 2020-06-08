package com.bookit.units;

import com.bookit.utilities.APIUtilities;
import com.bookit.utilities.Environment;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class with unit tests for APIUtilities class
 * Here we ensure that utilities work fine before using them in action
 */
public class APIUtilitiesUnitTests {

    @Test
    public void getTokenTest(){
        String token = APIUtilities.getToken();
        String tokenForStudent = APIUtilities.getToken("student");
        String tokenForTeacher = APIUtilities.getToken("teacher");
        System.out.println(token);
        System.out.println(tokenForStudent);
        System.out.println(tokenForTeacher);
        Assert.assertNotNull(token);
        Assert.assertNotNull(tokenForStudent);
        Assert.assertNotNull(tokenForTeacher);

    }

    @Test
    public void testIfUserExists(){
        int actual = APIUtilities.getUserID("THSADAemail@email.com","123456");
        Assert.assertEquals(-1 ,actual);

        // if ID is positive - user exists indeed, otherwise is will return -1
        int actual2 = APIUtilities.getUserID(Environment.MEMBER_USERNAME,Environment.MEMBER_PASSWORD);
        Assert.assertTrue(actual2>0);
    }
}
