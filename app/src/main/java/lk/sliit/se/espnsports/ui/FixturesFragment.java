package lk.sliit.se.espnsports.ui;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import lk.sliit.se.espnsports.R;
import lk.sliit.se.espnsports.core.Callback;
import lk.sliit.se.espnsports.core.SportsService;
import lk.sliit.se.espnsports.data.ScheduleListAdapter;
import lk.sliit.se.espnsports.data.ScheduledMatch;
import lk.sliit.se.espnsports.utils.Constants;
import lk.sliit.se.espnsports.utils.PropertyFileUtils;


public class FixturesFragment extends Fragment implements Callback{

    private ScheduleListAdapter scheduleListAdapter;
    private List<ScheduledMatch> upcomingMatches = new ArrayList<>();
    private String apiKey;
    private SportsService sportsService;
    private ProgressDialog progress;
    final static String TAG = "Match_Fixtures";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        progress = new ProgressDialog(getContext(), 3);
        progress.setMessage("Loading fixtures");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        setAPIKey();

        View view = inflater.inflate(R.layout.fragment_fixtures, container, false);
        scheduleListAdapter = new ScheduleListAdapter(getActivity(), new ArrayList<ScheduledMatch>());

        ListView upcomingMatcheslv = (ListView) view.findViewById(R.id.scheduledList);
        upcomingMatcheslv.setAdapter(scheduleListAdapter);

        scheduleListAdapter = new ScheduleListAdapter(getActivity(), upcomingMatches);
        upcomingMatcheslv.setAdapter(scheduleListAdapter);

        sportsService = new SportsService(this, apiKey);
        sportsService.getFixtures();

        return view;
    }

    @Override
    public void onCallbackCompleted(String data) {

        Log.i(TAG, "FixturesFragment: Recieved callback data - " + data);
        Gson gson = new Gson();

        try{
        JsonObject jsonObject = gson.fromJson(data, JsonElement.class).getAsJsonObject();
        Type listType;

        if (null != jsonObject.get("data")) {
            listType = new TypeToken<ArrayList<ScheduledMatch>>(){}.getType();
            upcomingMatches = gson.fromJson(jsonObject.get("data").toString(), listType);
            scheduleListAdapter.clear();
            scheduleListAdapter.addAll(upcomingMatches);
            scheduleListAdapter.notifyDataSetChanged();
            progress.hide();
        }
        } catch (JsonSyntaxException e){
            Log.e(TAG, "Failed to parse data", e);
        }

    }

    private void setAPIKey() {
        try {
            apiKey = PropertyFileUtils.getPropertyValue(Constants.API_KEY_PROPERTY, getActivity().getAssets().open(Constants.APP_PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}