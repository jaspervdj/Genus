/** A class from which all unit test classes should derive.
 */
public abstract class UnitTest
{
    /** Number of tests performed. */
    int tests;

    /** Number of tests succeeded. */
    int succeeded;

    /** Constructor.
     */
    public UnitTest()
    {
        tests = 0;
        succeeded = 0;
    }

    /** Perform a test.
     *  @param value Value of the test.
     *  @param description Description of the test.
     */
    public void test(boolean value, String description)
    {
        tests++;
        
        if(value) {
            succeeded++;
        } else {
            System.out.println("Test " + tests + " failed: " + description);
        }
    }

    /** Run all tests.
     *  @param args Arguments passed.
     */
    public void runTests(String[] args)
    {
        run(args);
        System.out.println("Performed " + tests + " tests, " +
                succeeded + " succeeded, " + (tests - succeeded) + " failed.");
    }

    /** Should be implemented to run tests.
     *  @param args Arguments passed.
     */
    public abstract void run(String[] args);
}
