package vap.go.thue.thuegovap;

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

            if (mobile.equals("Training")) {
                imageView.setImageResource(R.drawable.diadiem);
            } else if (mobile.equals("Testing")) {
                imageView.setImageResource(R.drawable.vanban);
            } else if (mobile.equals("ISTQB")) {
                imageView.setImageResource(R.drawable.hoidap);
            }
            else if (mobile.equals("Findus")) {
                imageView.setImageResource(R.drawable.gopy);
            }
            else if (mobile.equals("GooglePlay")) {
                imageView.setImageResource(R.drawable.danhgia);
            } else {
                imageView.setImageResource(R.drawable.chiase);
            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;



    }

}

