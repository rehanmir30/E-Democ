package com.example.e_democ.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.e_democ.Fragments.Login_as_Candidate;
import com.example.e_democ.Fragments.Login_as_Voter;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new Login_as_Voter();
            case 1:
                return new Login_as_Candidate();

        }

        return new Login_as_Voter();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
