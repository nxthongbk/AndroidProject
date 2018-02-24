package com.tma.test;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by nhokm on 08/28/2017.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int id;
    private List<String> items ;

    public CustomAdapter(Context context, int textViewResourceId , List<String> list )
    {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list ;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        ListView text = (ListView) mView.findViewById(R.id.lvperson);

//        if(items.get(position) != null )
//        {
//            text.setBackgroundColor(Color.CYAN);
//            int color = Color.argb( 200, 255, 64, 64 );
//            text.setBackgroundColor( color );
//
//        }

        return mView;
    }

}
