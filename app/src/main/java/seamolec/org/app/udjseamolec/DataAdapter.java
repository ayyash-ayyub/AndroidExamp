package seamolec.org.app.udjseamolec;

/**
 * Created by Krisnasw on 9/10/2016.
 */

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<JSONResponse> tugas;

    public DataAdapter(ArrayList<JSONResponse> tugas) {
        this.tugas = tugas;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.tv_name.setText(tugas.get(i).getNama_quiz());
        viewHolder.tv_version.setText(tugas.get(i).getTanggal_mulai());
        viewHolder.tv_api_level.setText(tugas.get(i).getDurasi());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),tugas.get(i).getId_quiz(),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(v.getContext(),QuizActivity.class);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tugas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_version,tv_api_level;
        public ViewHolder(View view) {
            super(view);

            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_version = (TextView) view.findViewById(R.id.tv_version);
            tv_api_level = (TextView) view.findViewById(R.id.tv_api_level);
 
        }

    }

}
