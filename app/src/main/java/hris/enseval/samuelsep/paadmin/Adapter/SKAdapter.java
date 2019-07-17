package hris.enseval.samuelsep.paadmin.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hris.enseval.samuelsep.paadmin.Model.SKModel;
import hris.enseval.samuelsep.paadmin.R;

/**
 * Created by Samuel Septiano on 28,June,2019
 */
public class SKAdapter extends RecyclerView.Adapter<SKAdapter.MyViewHolder> {

    private List<SKModel> pdfModels;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pdf_creation, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SKModel model = pdfModels.get(position);
        holder.tvNIK.setText(model.getNik());
        holder.tvNama.setText(model.getNama());
    }

    @Override
    public int getItemCount() {
        return pdfModels.size();
    }

    /**
     * This is set from PDFCreateByXML class
     * This is my own model. This model have to set data from api
     *
     * @param pdfModels
     */
    public void setListData(List<SKModel> pdfModels) {
        this.pdfModels = pdfModels;
        notifyDataSetChanged();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNama;
        private final TextView tvNIK;


        public MyViewHolder(View view) {
            super(view);
            tvNama = (TextView) view.findViewById(R.id.tvEmpName);
            tvNIK = (TextView) view.findViewById(R.id.tvNik);

    }
    }
}
