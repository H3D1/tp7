package com.example.tp7;

import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.example.tp7.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding bind;
    private boolean enseig = false; // Track if the teacher fragment is active
    private boolean about = false; // Track if the about fragment is active
    public TeacherAdapter adapter; // Adapter for RecyclerView (make it public)
    public List<Teacher> teacherList; // List of teachers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        View view = bind.getRoot();
        setContentView(view);

        setTitle("My App");
        setSupportActionBar(bind.toolbar);

        bind.navView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, bind.drawerLayout, bind.toolbar, R.string.open_drawer, R.string.close_drawer);
        bind.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initialize the teacher list and adapter
        teacherList = new ArrayList<>();
        adapter = new TeacherAdapter(teacherList, this); // Initialize your adapter here

        // Load the initial fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homeFragment()).commit();
            bind.navView.setCheckedItem(R.id.nav_home); // Set default checked item
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            bind.toolbar.setTitle("Enseignants");
            enseig = true; // Set to true for teachers fragment
            about = false; // Reset about fragment state
            invalidateOptionsMenu(); // Update options menu
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new homeFragment()) // Ensure HomeFragment is correctly referenced
                    .commit();

        } else if (itemId == R.id.nav_about) {
            bind.toolbar.setTitle("About");
            enseig = false; // Reset teachers fragment state
            about = true; // Set to true for about fragment
            invalidateOptionsMenu(); // Update options menu
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AboutFragment())
                    .commit();

        } else if (itemId == R.id.nav_manage_courses) { // New case for managing courses
            bind.toolbar.setTitle("Gérer Cours");
            enseig = false; // Reset teachers fragment state
            about = false; // Reset about fragment state
            invalidateOptionsMenu(); // Update options menu
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddCourseFragment()) // Reference to your Courses management fragment
                    .commit();

        } else if (itemId == R.id.nav_logout) {
            Log.i("tag", "exit");
            Toast.makeText(this, "Exit", Toast.LENGTH_LONG).show();
            finish();
        }

        bind.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (bind.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            bind.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menus) {
        getMenuInflater().inflate(R.menu.menu_main_about, menus); // Load the default menu
        return super.onCreateOptionsMenu(menus);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear(); // Clear existing items
        if (enseig) {
            getMenuInflater().inflate(R.menu.menu_main_ensg, menu); // Inflate teacher menu
        } else if (about) {
            getMenuInflater().inflate(R.menu.menu_main_about, menu); // Inflate about menu
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void sortTeachersAscending() {
        if (teacherList != null) {
            Collections.sort(teacherList, new Comparator<Teacher>() {
                @Override
                public int compare(Teacher t1, Teacher t2) {
                    return t1.getNom().compareTo(t2.getNom()); // Sort by name A-Z
                }
            });
            adapter.notifyDataSetChanged(); // Notify adapter about data change
        } else {
            Log.e("MainActivity", "Teacher list is null");
        }
    }

    private void sortTeachersDescending() {
        if (teacherList != null) {
            Collections.sort(teacherList, new Comparator<Teacher>() {
                @Override
                public int compare(Teacher t1, Teacher t2) {
                    return t2.getNom().compareTo(t1.getNom()); // Sort by name Z-A
                }
            });
            adapter.notifyDataSetChanged(); // Notify adapter about data change
        } else {
            Log.e("MainActivity", "Teacher list is null");
        }
    }

    private void showAddTeacherDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Teacher");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_teacher, null);
        builder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextEmail = dialogView.findViewById(R.id.editTextEmail);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();

            if (!name.isEmpty() && !email.isEmpty()) {
                Teacher newTeacher = new Teacher(name, email);
                teacherList.add(newTeacher); // Add the new teacher to the list

                adapter.notifyDataSetChanged(); // Notify adapter about data change
                Toast.makeText(MainActivity.this, "Teacher added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please enter both name and email", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        super.startActionMode(callback);
        return null;
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About")
                .setMessage("This app manages a list of teachers.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.a_z) {
            sortTeachersAscending(); // Call sorting method for A-Z
            return true;
        } else if (item.getItemId() == R.id.z_a) {
            sortTeachersDescending(); // Call sorting method for Z-A
            return true;
        } else if (item.getItemId() == R.id.add) {
            showAddTeacherDialog(); // Show dialog for adding a new teacher
            return true;
        } else if (item.getItemId() == R.id.about) {
            showAboutDialog(); // Show about information dialog
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}