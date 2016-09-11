package seamolec.org.app.udjseamolec;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private static final String TAG = Register.class.getSimpleName();
    private TextView btnLogin;
    private TextView btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    public EditText inputIP;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    Toolbar toolbar;
    String eko;
    AppConfig ac;
    //public   static String URL_LOGIN = null;

    public String getSIERRA_IP() {
        return SIERRA_IP;
    }

    public String SIERRA_IP;
    public SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login Validation");

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.pass);
        inputIP = (EditText) findViewById(R.id.ip);


        //jadi = inputIP.getText().toString()+"/droid/login.php";
        //URL_LOGIN = "http:/10.10.10.92/droid/login.php";
        
        btnLogin = (TextView) findViewById(R.id.signin);
        btnLinkToRegister = (TextView) findViewById(R.id.Create);
        ac = new AppConfig();


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(this.getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Login.this, MapelController.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                SIERRA_IP = inputIP.getText().toString();

                // nyimpan IP di SP
                sp = getSharedPreferences("",MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("IPnya", SIERRA_IP);
                ed.commit();

                //ngambil
                String wawa = sp.getString("IPnya","");
                Toast.makeText(getApplicationContext(),"IP" + wawa, Toast.LENGTH_LONG).show();

                // Check for empty data in the form
                if (!username.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(username, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
              inputIPRegister();

            }
        });

    }

     String m_text = "";
    public void inputIPRegister() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input IP SIERRA");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
        builder.setView(input);

        builder.setPositiveButton("APPLY IP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_text = input.getText().toString();

                SharedPreferences sp = getSharedPreferences("", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("IPnya", m_text);
                ed.commit();

                //ngambil
                String wawa = sp.getString("IPnya", "");
                if (m_text.length() > 0) {
                    startActivity(new Intent(getApplicationContext(), Register.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Ip Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(getApplicationContext(),"IP Ciat :" + wawa, Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }



    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String username, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();



        String dd = "http://"+SIERRA_IP+"/udj2/login.php";
        StringRequest strReq = new StringRequest(Method.POST,
              dd , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
               // System.out.println("bener gak"+ac.getURL_LOGIN());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String username = user.getString("username");
//                        String password = user.getString("password");
                        String kelas = user.getString("kelas");
                        String created_at = user.getString("created_at");
                        String sekolah = user.getString("sekolah");
                      //  String ipse = inputIP.getText().toString().trim();

                        // Inserting row in users table
                        db.addUser(name, username, uid, kelas, created_at, sekolah);

                        // Launch main activity
                        Intent intent = new Intent(Login.this,
                                MapelController.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}


