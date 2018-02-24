package com.tma.test;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

/**
 * Created by AutoUsr on 15/08/2017.
 */

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private final String[] mobileValues;

    public ImageAdapter(Context context, String[] mobileValues) {
        this.context = context;
        this.mobileValues = mobileValues;

    }



    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mobileValues.length;
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View gridView;


        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.menu, null);

            // set value into textview


            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            String mobile = mobileValues[position];

            if (mobile.equals("sachkinhdoanh")) {
                imageView.setImageResource(R.drawable.sachkinhdoanh);
            } else if (mobile.equals("truyenngan")) {
                imageView.setImageResource(R.drawable.truyenngan);
            } else if (mobile.equals("tieuthuyet")) {
                imageView.setImageResource(R.drawable.sachtieuthuyet);
            }
            else if (mobile.equals("truyenkiemhiep")) {
                imageView.setImageResource(R.drawable.truyenkiemhiep);
            }
            else if (mobile.equals("truyencotich")) {
                imageView.setImageResource(R.drawable.truyencotich);
            }
            else if (mobile.equals("camnangcuocsong")) {
                imageView.setImageResource(R.drawable.camnangcuocsong);
            }
            else if (mobile.equals("sachlichsu")) {
                imageView.setImageResource(R.drawable.sachlichsu);
            }
            else if (mobile.equals("truyencuoi")) {
                imageView.setImageResource(R.drawable.truyencuoi);
            }
            else if (mobile.equals("phatphap")) {
                imageView.setImageResource(R.drawable.phatphap);
            }
            else {
                imageView.setImageResource(R.drawable.sach6);
            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;



    }

}

