package in.shubhamchaudhary.logmein.ui;

import in.shubhamchaudhary.logmein.DatabaseEngine;
import in.shubhamchaudhary.logmein.R;
import in.shubhamchaudhary.logmein.R.id;
import in.shubhamchaudhary.logmein.R.layout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

public class FragmentEdit extends Fragment {

    EditText username,password;
    CheckBox cb_show_password;
    static View v;
    Button button_update;
    DatabaseEngine de;
    UserStructure activity_user;

    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        activity_user = (UserStructure)getArguments().getSerializable("user");
        v = inflater.inflate(
                R.layout.fragment_edit_layout, container, false);
        username = (EditText)v.findViewById(R.id.edit_username);
        password = (EditText)v.findViewById(R.id.edit_password);
        cb_show_password = (CheckBox) v.findViewById(R.id.cb_show_password);
        username.setText(activity_user.getUsername());
        password.setText(activity_user.getPassword());

        // when user clicks on this checkbox, this is the handler.
        cb_show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        button_update = (Button)v.findViewById(R.id.button_update);
        de = new DatabaseEngine(container.getContext());
        username.setText(activity_user.getUsername());
        password.setText(activity_user.getPassword());

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserStructure updated_user = new UserStructure();
                updated_user.setPassword(password.getText().toString());
                updated_user.setUsername(username.getText().toString());
                Log.e("username",username.getText().toString());

                int i=de.updateUser(updated_user,activity_user.getUsername());
                //TODO: show pop ups instead of logs
                Boolean flag = false;
                if(i==1){
                    Log.e("Updated", "Updated user");
                    flag = true;
                }
                else if(i==0){
                    Log.e("Updated", "Error updating");
                }
                else{
                    Log.e("Updated", "Updated more than 1 records");
                    flag=true;
                }

                if(flag){
                    ((UserDatabase)getActivity()).update_spinner_list(activity_user.getUsername(), username.getText().toString());
                }
            }
        });
        //TODO:close fragment at completing update or cancel
        return v;
    }//end of onCreate
//
//  public void show_password_edit_fragment(){
//      CheckBox cb_show_pwd = (CheckBox)v.findViewById(R.id.cb_show_password);
//      if(cb_show_pwd.isChecked()){
//          password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//          return;
//      }
//      password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//  }
}
// vim: set ts=4 sw=4 tw=79 et :
