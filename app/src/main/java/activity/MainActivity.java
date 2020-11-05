package activity;



import activity.Login;
import helper.SQLiteHandler;
import helper.SessionManager;


import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.shoppy.R;

public class MainActivity extends Activity {
    private ImageView imageview;
    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    private Button btncrnt;
    private ImageView imageView;
    private  TextView textView ;
    private SQLiteHandler db;
    private SessionManager session;
    private Typeface typeface ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
textView=(TextView)findViewById(R.id.textView);
Typeface typeface = ResourcesCompat.getFont(this,R.font.titi);
textView.setTypeface(typeface);




        txtName = (TextView) findViewById(R.id.textView2);
        //txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btncrnt = (Button) findViewById(R.id.crnt);

        String uri = "@drawable/imma";  // where myresource (without the extension) is the file

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

        imageview= (ImageView)findViewById(R.id.imageView2);
        Drawable res = getResources().getDrawable(imageResource);
        imageview.setImageDrawable(res);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText(name);
        //txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        btncrnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        MapsActivity.class);
             startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }
}