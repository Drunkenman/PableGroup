package hankin.pablegroup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import hankin.pablegroup.pable.PableGroup;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PableGroup pableGroup = findViewById(R.id.pg_main);
        pableGroup.needToRefresh(false);
        pableGroup.needToLoadMore(false);
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.tv_main_lv:
                LvActivity.startLvActivity(this);
                break;
            case R.id.tv_main_sv:
                SvActivity.startSvActivity(this);
                break;
        }
    }

}
