package hankin.pablegroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import hankin.pablegroup.pable.PableGroup;
import hankin.pablegroup.pable.pview.PListView;

/**
 *
 * Created by Hankin on 2017/10/15.
 * @email hankin.huan@gmail.com
 */

public class LvActivity extends Activity {

    public static void startLvActivity(Activity activity){
        activity.startActivity(new Intent(activity, LvActivity.class));
    }

    private PableGroup mPableGroup;
    private PListView mListView;
    private ArrayList<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv);

        mPableGroup = findViewById(R.id.pg_lv);
        mListView = findViewById(R.id.lv_lv);
        for (int i=0;i<30;i++){
            mList.add("item"+i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(adapter);
        mListView.setAutoPullUp(mPableGroup, true);

        mPableGroup.setOnRefreshListener(new PableGroup.OnRefreshListener() {
            @Override
            public void onRefresh(PableGroup pableGroup) {
                Log.d("mydebug---", "onRefresh");
            }

            @Override
            public void onLoadMore(PableGroup pableGroup) {
                Log.d("mydebug---", "onLoadMore");
            }
        });
    }

}
