package com.example.tp7;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class homeFragment extends Fragment { // Changed to proper naming convention

    private RecyclerView recyclerView;
    private TeacherAdapter adapter;
    private int longClickedPosition = -1; // Store position for context menu

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.mRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);

        MainActivity mainActivity = (MainActivity) requireActivity();
        adapter = mainActivity.adapter;
        recyclerView.setAdapter(adapter);

        // Set up item long click listener for context menu
        adapter.setOnItemLongClickListener(position -> {
            longClickedPosition = position;
            return false; // Allow showing the context menu
        });

        registerForContextMenu(recyclerView);

        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.mRecyclerview) {
            menu.setHeaderTitle("Select Action");
            menu.add(0, v.getId(), 0, "Delete Teacher");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Delete Teacher")) {
            if (longClickedPosition != -1) {
                adapter.teacherList.remove(longClickedPosition);
                adapter.notifyItemRemoved(longClickedPosition);
                Toast.makeText(requireContext(), "Teacher deleted", Toast.LENGTH_SHORT).show();
                longClickedPosition = -1; // Reset the position
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    // Interface for the adapter
    public interface OnItemLongClickListener {
        boolean onItemLongClick(int position);
    }
}