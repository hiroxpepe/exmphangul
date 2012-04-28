
package org.examproject.tweet.util;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author hiroxpepe
 */
public class SentenceToWordsTransformerTest {
    
    public SentenceToWordsTransformerTest() {
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
    public void testTransform() {
        System.out.println("transform");
        SentenceToWordsTransformer instance = new SentenceToWordsTransformer();
        
        String input1 = "hoge";
        String[] expResult1 = new String[1];
        expResult1[0] = "hoge";
        String[] result1 = (String[]) instance.transform(input1);
        assertArrayEquals(expResult1, result1);
        
        String input2 = "hoge piyo";
        String[] expResult2 = new String[2];
        expResult2[0] = "hoge";
        expResult2[1] = "piyo";
        String[] result2 = (String[]) instance.transform(input2);
        assertArrayEquals(expResult2, result2);
        
        String input3 = "hoge  fuga";
        String[] expResult3 = new String[2];
        expResult3[0] = "hoge";
        expResult3[1] = "fuga";
        String[] result3 = (String[]) instance.transform(input3);
        assertArrayEquals(expResult3, result3);
        
        String input4 = " hoge   piyo   fuga ";
        String[] expResult4 = new String[3];
        expResult4[0] = "hoge";
        expResult4[1] = "piyo";
        expResult4[2] = "fuga";
        String[] result4 = (String[]) instance.transform(input4);
        assertArrayEquals(expResult4, result4);
        
        String input5 = " hoge   piyo. ";
        String[] expResult5 = new String[2];
        expResult5[0] = "hoge";
        expResult5[1] = "piyo";
        String[] result5 = (String[]) instance.transform(input5);
        assertArrayEquals(expResult5, result5);
    }
}
