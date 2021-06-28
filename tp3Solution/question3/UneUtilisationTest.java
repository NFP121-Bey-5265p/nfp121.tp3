package question3;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class UneUtilisationTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class UneUtilisationTest
{
    /**
     * Default constructor for test class UneUtilisationTest
     */
    public UneUtilisationTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    @Test
    public void trycatch() throws java.lang.Exception
    {
        question3.UneUtilisation.main(new String[]{"PolygoneRegulier(4,100)" });
        question3.UneUtilisation.main(new String[]{"PolygoneRegulier(1,10)" });
        question3.UneUtilisation.main(new String[]{"PolygoneRegulier(10,200)" });
    }
}
