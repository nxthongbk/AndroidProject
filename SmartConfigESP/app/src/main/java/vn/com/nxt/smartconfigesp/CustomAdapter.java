package vn.com.nxt.smartconfigesp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CustomAdapter extends ArrayAdapter<Device> {

    private Context context;
    private int resource;
    private String currentName;
    private List<Device> arrDevice;


    public CustomAdapter(Context context, int resource, ArrayList<Device> arrDevice) {
        super(context, resource, arrDevice);
        this.context = context;
        this.resource = resource;
        this.arrDevice = arrDevice;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvNumberPhone = (TextView) convertView.findViewById(R.id.tvPhoneNumber);
            viewHolder.tvAvatar = (TextView) convertView.findViewById(R.id.tvAvatar);
            viewHolder.tvEditDevice = (TextView) convertView.findViewById(R.id.tvEditDevice);



            final Device device = arrDevice.get(position);
            viewHolder.tvAvatar.setBackgroundColor(device.getColor());
            viewHolder.tvAvatar.setText(String.valueOf(position+1));
            viewHolder.tvName.setText(device.getName());
            viewHolder.tvNumberPhone.setText(device.getDescription());

            viewHolder.tvEditDevice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = LayoutInflater.from(context);
                    final View dialogView = inflater.inflate(R.layout.dialoglayout, null);

                    final EditText etComments = (EditText) dialogView.findViewById(R.id.etComments);
                    final String oldName  = device.getName();
                    final String oldID =  device.getDescription();
                    etComments.setText(device.getName());
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Edit device name:");

                    alertDialog.setCancelable(false);


                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            SharedPreferences pre=context.getSharedPreferences("device_list", MODE_PRIVATE);
                            String current_device_list = pre.getString("device_list", "");
                            SharedPreferences.Editor editor = pre.edit();
                            String oldDeviceInfo = oldName+","+oldID;
                            String newDeviceInfo= etComments.getText().toString().trim()+","+oldID;

                            editor.putString("device_list",current_device_list.replace(oldDeviceInfo,newDeviceInfo));
                            editor.apply();

                            Intent mainDevice = new Intent(context, MainActivity.class);
                            context.startActivity(mainDevice);



                        }

                    });


                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            SharedPreferences pre=context.getSharedPreferences("device_list", MODE_PRIVATE);
                            String current_device_list = pre.getString("device_list", "");
                            SharedPreferences.Editor editor = pre.edit();
                            String oldDeviceInfo = oldName+","+oldID+";";

                            editor.putString("device_list",current_device_list.replace(oldDeviceInfo,""));
                            editor.apply();

                            Intent mainDevice = new Intent(context, MainActivity.class);
                            context.startActivity(mainDevice);
                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });


                    alertDialog.setView(dialogView);
                    alertDialog.show();



                    //Toast.makeText(context,"aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            });


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }



    public class ViewHolder {
        TextView tvName, tvNumberPhone, tvAvatar,tvEditDevice;

    }
}