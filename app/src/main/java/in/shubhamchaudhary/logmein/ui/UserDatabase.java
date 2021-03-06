/**
 *   LogMeIn - Automatically log into Panjab University Wifi Network
 *
 *   Copyright (c) 2014 Tanjot Kaur <tanjot28@gmail.com>
 *   Copyright (c) 2014 Shubham Chaudhary <me@shubhamchaudhary.in>
 *
 *   This file is part of LogMeIn.
 *
 *   LogMeIn is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   LogMeIn is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with LogMeIn.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.shubhamchaudhary.logmein.ui;

import in.shubhamchaudhary.logmein.DatabaseEngine;
import in.shubhamchaudhary.logmein.R;
import in.shubhamchaudhary.logmein.R.id;
import in.shubhamchaudhary.logmein.R.layout;
import in.shubhamchaudhary.logmein.R.menu;

import java.util.ArrayList;
import java.util.zip.Inflater;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class UserDatabase extends FragmentActivity {

    Spinner spinner_user_list;
    ArrayAdapter<String> adapter;
    ArrayList<String> user_list;
    DatabaseEngine databaseEngine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_database);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }

        databaseEngine = new DatabaseEngine(this);
        spinner_user_list =(Spinner)findViewById(R.id.spinner_user_list);
        user_list = databaseEngine.userList();

        adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout,user_list);
        spinner_user_list.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_database, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_user_database,
                    container, false);
            return rootView;
        }
    }

    public void edit_user_profile(View v){
        String username = (String)spinner_user_list.getSelectedItem();
        UserStructure user = databaseEngine.getUsernamePassword(username);
        Bundle bundle = new Bundle();
        Fragment frag = new FragmentEdit();
        FragmentManager fm  = getSupportFragmentManager();
        FragmentTransaction fragment_transaction = fm.beginTransaction();


        bundle.putSerializable("user",user);
        frag.setArguments(bundle);
        fragment_transaction.replace(R.id.fragment_blank, frag);
        fragment_transaction.commit();

    }//end
    public void show_password(View v){
//      FragmentEdit fe = new FragmentEdit();
//      fe.show_password_edit_fragment();
        CheckBox cb_show_pwd = (CheckBox)FragmentEdit.v.findViewById(R.id.cb_show_password);
        EditText pwd = (EditText)FragmentEdit.v.findViewById(R.id.edit_password);
        if(cb_show_pwd.isChecked()){
            pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            return;
        }
        pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }//end of show_password(View)

    public void update_spinner_list(String oldname,String newname){
        adapter.remove(oldname);
        adapter.add(newname);
        adapter.notifyDataSetChanged();
        spinner_user_list.setSelection(adapter.getPosition(newname));
    }//end of update_spinner_list

}//end of class UserDatabase
// vim: set ts=4 sw=4 tw=79 et :
