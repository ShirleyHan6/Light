package ntu.student.a5tar.light.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import ntu.student.a5tar.light.Activities.SettingActivity;
import ntu.student.a5tar.light.R;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    public static int id;
    TextView userNickname,userDescription;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if(bundle!=null){
            id=bundle.getInt("fragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView setting = (ImageView)view.findViewById(R.id.settingIcon);

        //retrieve user info

        SharedPreferences pref=getActivity().getSharedPreferences("account"+id, MODE_PRIVATE);
        String name=pref.getString("username","");
        String des=pref.getString("description","");
        userNickname=(TextView)view.findViewById(R.id.userNickname);
        userDescription=(TextView)view.findViewById(R.id.userDescription);
        userNickname.setText(name);
        userDescription.setText(des);

        //load facebook profile image into profile

        if(pref.getString("password","").equals("")) {
            String facebook_image = pref.getString("image", "");
            ImageView image = (ImageView) view.findViewById(R.id.userAvatar);
            if (facebook_image.isEmpty()) {
                image.setImageResource(R.drawable.avatarpink);
            } else {
                Picasso.get().load(facebook_image).into(image);
            }
        }

        //go to setting page
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSetting = new Intent(getActivity().getApplication(), SettingActivity.class);
                startActivity(toSetting);
            }
        });
        return view;
    }
}
