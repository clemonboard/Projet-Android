import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.projetoutil.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private View view;

    private FragmentActivity myContext;

    public InfoWindowAdapter(FragmentActivity aContext) {
        this.myContext = aContext;

        LayoutInflater inflater = (LayoutInflater) myContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.layout_inflate_parking_info_window,
                null);
    }

    @Override
    public View getInfoContents(Marker marker) {

        if (marker != null
                && marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
            marker.showInfoWindow();
        }
        return null;
    }

    @Override
    public View getInfoWindow(final Marker marker) {

        final String title = marker.getTitle();
        final TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            titleUi.setText(title);
        } else {
            titleUi.setText("");
            titleUi.setVisibility(View.GONE);
        }

        final String snippet = marker.getSnippet();
        final TextView snippetUi = ((TextView) view
                .findViewById(R.id.snippet));

        if (snippet != null) {





            snippetUi.setText("");
        }

        return view;
    }
}