package ntu.student.a5tar.light.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ntu.student.a5tar.light.Fragments.ProfileFragment;
import ntu.student.a5tar.light.Fragments.SearchFragment;
import ntu.student.a5tar.light.Fragments.ShelfFragment;
import ntu.student.a5tar.light.Fragments.ThoughtFragment;
import ntu.student.a5tar.light.R;

public class NavigationActivity extends AppCompatActivity {
    int id;
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homepageContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    fragment = new SearchFragment();
                    break;
                case R.id.navigation_shelf:
                    fragment = new ShelfFragment();
                    break;
                case R.id.navigation_thought:
                    fragment = new ThoughtFragment();
                    break;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //pass user id to every fragment
        if(getIntent().getStringExtra("activity").equals("log")) {
            id = getIntent().getIntExtra("navigation", 0);
        }
        if(getIntent().getStringExtra("activity").equals("subject")) {
            id = getIntent().getIntExtra("login", 0);
        }


        Bundle bundle=new Bundle();
        bundle.putInt("fragment", id);

        SearchFragment searchFragment=new SearchFragment();
        searchFragment.setArguments(bundle);

        ThoughtFragment thoughtFragment=new ThoughtFragment();
        thoughtFragment.setArguments(bundle);

        ShelfFragment shelfFragment=new ShelfFragment();
        shelfFragment.setArguments(bundle);

        ProfileFragment profileFragment=new ProfileFragment();
        profileFragment.setArguments(bundle);

        loadFragment(thoughtFragment);
        loadFragment(shelfFragment);
        loadFragment(profileFragment);
        loadFragment(searchFragment);
    }

}
