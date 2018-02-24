package vap.go.thue.thuegovap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView list;
    String[] web = {
            "Phiếu Báo",
            "Bảng Kê nộp Tiền",
            "Thu Nhập Cá Nhân",
            "Lệ Phí Truớc Bạ",
            "Tiền Sử Dụng Đất",
            "Hạn Nộp",
            "Quản Lý Nợ"

    } ;
    Integer[] imageId = {
            R.drawable.thuenho,
            R.drawable.thuenho,
            R.drawable.thuenho,
            R.drawable.thuenho,
            R.drawable.thuenho,
            R.drawable.thuenho,
            R.drawable.thuenho

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomList adapter = new
                CustomList(MainActivity.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch(position)
                {
                    case 0:
                        Intent PBIntent = new Intent(view.getContext(), PhieuBaoActivity.class);
                        view.getContext().startActivity(PBIntent);
                        break;
                    case 1:
                        Intent BKNTIntent = new Intent(view.getContext(), BangKeNopTienActivity.class);
                        view.getContext().startActivity(BKNTIntent);
                        break;

                    case 2:
                        Intent TNCNIntent = new Intent(view.getContext(), ThuNhapCaNhanActivity.class);
                        view.getContext().startActivity(TNCNIntent);
                        break;

                    case 3:
                        Intent LPTBIntent = new Intent(view.getContext(), LePhiTruocBaActivity.class);
                        view.getContext().startActivity(LPTBIntent);
                        break;

                    case 4:
                        Intent TSDDIntent = new Intent(view.getContext(), ThueSuDungDatActivity.class);
                        view.getContext().startActivity(TSDDIntent);
                        break;
                    case 5:
                        Intent HNIntent = new Intent(view.getContext(), HanNopActivity.class);
                        view.getContext().startActivity(HNIntent);
                        break;

                    case 6:
                        Intent QLNIntent = new Intent(view.getContext(), QuanLyNoActivity.class);
                        view.getContext().startActivity(QLNIntent);
                        break;
                    default:
                        break;

                }

                //Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });


    }
}
