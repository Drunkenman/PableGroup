package hankin.pablegroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import hankin.pablegroup.pable.PableGroup;

/**
 *
 * Created by Hankin on 2017/10/15.
 * @email hankin.huan@gmail.com
 */

public class SvActivity extends Activity {

    public static void startSvActivity(Activity activity){
        activity.startActivity(new Intent(activity, SvActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sv);

        PableGroup pableGroup = findViewById(R.id.pg_sv);
        pableGroup.needToRefresh(false);
        pableGroup.needToLoadMore(false);
    }
}
