package araikovichinc.ratemeconcept2.Utils;

/**
 * Created by Tigran on 14.12.2017.
 */

public class Pages {
    public static final int SETTINGS = 4;
    public static final int PROFILE = 2;
    public static final int RATE = 0;
    public static final int LogIn = 5;
    public static final int SingUp = 6;
    public static int currentPage = -1;

    public static int getCurrentPage() {
        return currentPage;
    }

    public static void setCurrentPage(int currentPage) {
        Pages.currentPage = currentPage;
    }
}
