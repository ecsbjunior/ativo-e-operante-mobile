package operante.ativo.mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import operante.ativo.mobile.R;
import operante.ativo.mobile.model.Complaint;

public class ComplaintAdapter extends ArrayAdapter<Complaint> {
    private int layout;

    public ComplaintAdapter(@NonNull Context context, int resource, List<Complaint> complaints) {
        super(context, resource, complaints);
        layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.layout, parent, false);
        }

        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        TextView txtDescription = convertView.findViewById(R.id.txtDescription);
        TextView txtProblemType = convertView.findViewById(R.id.txtProblemType);
        TextView txtCompetentOrgan = convertView.findViewById(R.id.txtCompetentOrgan);

        Complaint complaint = this.getItem(position);

        txtTitle.setText(complaint.getTitle());
        txtDescription.setText(complaint.getDescription());
        txtProblemType.setText(complaint.getProblemType().getName());
        txtCompetentOrgan.setText(complaint.getCompetentOrgan().getName());

        return convertView;
    }


}
