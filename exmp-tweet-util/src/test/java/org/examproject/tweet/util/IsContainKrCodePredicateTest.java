
package org.examproject.tweet.util;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author hiroxpepe
 */
public class IsContainKrCodePredicateTest {
    
    public IsContainKrCodePredicateTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        IsContainKrCodePredicate instance = new IsContainKrCodePredicate();
        
        String str1 = "hoge";
        boolean expResult1 = false;
        boolean result1 = instance.evaluate(str1);
        assertEquals(expResult1, result1);
        
        String str2 = "가";
        boolean expResult2 = true;
        boolean result2 = instance.evaluate(str2);
        assertEquals(expResult2, result2);
        
        String str3 = "This is ほげ";
        boolean expResult3 = false;
        boolean result3 = instance.evaluate(str3);
        assertEquals(expResult3, result3);
        
        String str4 = "これは通す 안녕하세요";
        boolean expResult4 = true;
        boolean result4 = instance.evaluate(str4);
        assertEquals(expResult4, result4);
    }
}
