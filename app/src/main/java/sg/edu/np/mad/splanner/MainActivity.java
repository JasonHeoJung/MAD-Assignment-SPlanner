package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public ArrayList<Task> taskList = new ArrayList<>();
    public TaskList taskList1;
    public ArrayList<Score> scoreList = new ArrayList<>();
    public ScoreList scoreList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskList1 = new TaskList(taskList);
        scoreList1 = new ScoreList(scoreList);

        bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

                    Fragment fragment = null;
                    switch (menuitem.getItemId()){
                        case R.id.home:
                            fragment = new HomeFragment();
                            break;

                        case  R.id.schedule:
                            fragment = new ScheduleFragment();
                            break;

                        case  R.id.list:
                            fragment = new ToDoListFragment();
                            break;

                        case  R.id.tracker:
                            fragment = new TrackerFragment();
                            break;

                        case  R.id.timer:
                            fragment = new TimerFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                    return true;
                }
            };
}