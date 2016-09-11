package seamolec.org.app.udjseamolec;


public class AppConfig {

        //Login l = new Login();

    // Server user register url
    public static String URL_REGISTER = "http://10.10.10.92/droid/register.php";


    //public static String URL_LOGIN = "http://172.16.9.107/droid/login.php";

//    public String getURL_LOGIN() {
//        return "http://172.16.9.107/droid/login.php";
//    }
//
//    public void setURL_LOGIN(String URL_LOGIN) {
//        this.URL_LOGIN = URL_LOGIN;
//    }






    // Server user Input Nilai
    public static String URL_HASIL = "http://10.10.10.92/droid/input.php";

    // Server user Statistik
    public static String URL_NILAI = "http://10.10.10.92/droid/score.php";



    public static String URL_LOGIN= "http://10.10.10.92/droid/login.php";


    //JSON URL
    public static final String DATA_URL = "http://10.10.10.92/droid/kelas.php";


    public static String getUrlMapel() {
        return URL_MAPEL;
    }

    public static final String URL_MAPEL = "http://10.10.10.92/droid/paket.php";

    //Tags used in the JSON String
    public static final String TAG_KELAS = "kelas";

    //JSON array name
    public static final String JSON_ARRAY = "result";



}

