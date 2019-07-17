package hris.enseval.samuelsep.paadmin.Pagination;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import hris.enseval.samuelsep.paadmin.Model.EmployeeModel;
import hris.enseval.samuelsep.paadmin.R;

public class PostRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

Context context;
    private List<EmployeeModel> mPostItems;
    private ArrayList<EmployeeModel> mPostItemsList = new ArrayList<>();


    public interface EventListener {
        void getCheckedData(List<EmployeeModel> EmployeeList);
        void test();
    }

    EventListener listener;

    public PostRecyclerAdapter(List<EmployeeModel> postItems, Context context, EventListener listener) {
        this.context = context;
        this.mPostItems = postItems;
        this.listener = listener;
        mPostItemsList.addAll(postItems);
        //Toast.makeText(context,Integer.toString(postItems.size()),Toast.LENGTH_LONG).show();

    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list, parent, false));
            case VIEW_TYPE_LOADING:
                return new FooterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
        //Toast.makeText(context,Integer.toString(mPostItems.size()),Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == mPostItems.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        }
        if(position == mPostItems.size()){
            return 0;


        }
        else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mPostItems == null ? 0 : mPostItems.size();
    }

    public void add(EmployeeModel response) {
        mPostItems.add(response);
        notifyItemInserted(mPostItems.size() - 1);
    }

    public void addAll(List<EmployeeModel> postItems) {
        for (EmployeeModel response : postItems) {
            add(response);
        }
        mPostItemsList.addAll(postItems);

    }


    private void remove(EmployeeModel postItems) {
        int position = mPostItems.indexOf(postItems);
        if (position > -1) {
            mPostItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoading() {
        isLoaderVisible = true;
        add(new EmployeeModel());
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = mPostItems.size() - 1;
        EmployeeModel item = getItem(position);
        if (item != null) {
            mPostItems.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void removeLoadingLast() {
        isLoaderVisible = false;
        //getItemViewType(40);
       // Toast.makeText(context,Integer.toString(mPostItems.size()),Toast.LENGTH_LONG).show();

    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    EmployeeModel getItem(int position) {
        return mPostItems.get(position);
    }


    public class ViewHolder extends BaseViewHolder {
        public TextView tvName, tvNIK, tvDept, tvjobtitle, tvStatus,tvStartJoin,tvLastJoin;
        LinearLayout lnEmployeeList;
        CheckBox cbEmployee;



        ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_employeeName);
            tvNIK = (TextView) itemView.findViewById(R.id.tv_nik);
            tvDept = (TextView) itemView.findViewById(R.id.tv_dept);
            tvjobtitle = (TextView) itemView.findViewById(R.id.tv_job_title);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_status);
            lnEmployeeList = (LinearLayout) itemView.findViewById(R.id.lnEmployeeList);
            tvLastJoin = (TextView) itemView.findViewById(R.id.tv_last_join);
            tvStartJoin = (TextView) itemView.findViewById(R.id.tv_start_join);
            cbEmployee = itemView.findViewById(R.id.cbEmployee);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            EmployeeModel item = mPostItems.get(position);
            //Toast.makeText(context,Integer.toString(mPostItems.size()),Toast.LENGTH_LONG).show();

            if(mPostItems.get(position).isChecked()){
                cbEmployee.setChecked(true);
                //Toast.makeText(context,Boolean.toString(EmployeeList.get(position).isChecked()),Toast.LENGTH_LONG).show();
            }
            else{
                cbEmployee.setChecked(false);
                //Toast.makeText(context,Boolean.toString(EmployeeList.get(position).isChecked()),Toast.LENGTH_LONG).show();
            }

            tvName.setText(item.getNama());

//        if(klist.getStatus().equals("Approved")){
            tvStatus.setText(item.getStatus());
//            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
//        }
//        else {
//            holder.tvStatus.setText(klist.getStatus());
//            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
//
//        }
            tvStartJoin.setText("Periode: "+item.getJoinDate());
            tvLastJoin.setText(item.getFinalDate());
            tvjobtitle.setText(item.getJobTitleName());
            tvDept.setText(item.getDept());
            tvNIK.setText(item.getNIK());


            lnEmployeeList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //employee detail
                }
            });

            cbEmployee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        mPostItems.get(position).setChecked(true);
                    }
                    else{
                        mPostItems.get(position).setChecked(false);
                    }

                    try {
                        listener.getCheckedData(mPostItems);
                    }
                    catch (Exception e){
                        Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });

            lnEmployeeList.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    PopupMenu popup = new PopupMenu(context,v);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.employee_list_popup, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {

                            if(item.getTitle().equals("delete")){
                                mPostItems.remove(position);
                                notifyDataSetChanged();
                            }
                            Toast.makeText(context,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });

                    popup.show();//showing popup menu




                    return false;
                }
            });




        }
    }

    public class FooterHolder extends BaseViewHolder {

        @BindView(R.id.progressBar)
        ProgressBar mProgressBar;


        FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void hideProgressBar(){
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        protected void clear() {

        }

    }


    public void filter(String charText) {
        //Toast.makeText(context,"here!!",Toast.LENGTH_SHORT).show();
        charText = charText.toLowerCase(Locale.getDefault());
        mPostItems.clear();
        if (charText.length() == 0) {
//            mPostItems.addAll(homeListFilter);
            addAll(mPostItemsList);
        }
        else
        {
            for (EmployeeModel wp : mPostItemsList) {
                if (wp.getNIK().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    mPostItems.add(wp);
                    add(wp);
                }
            }
        }
        //Toast.makeText(context,mPostItemsList.get(3).getNama(),Toast.LENGTH_LONG).show();
        Toast.makeText(context,Integer.toString(mPostItemsList.size()),Toast.LENGTH_LONG).show();
        notifyDataSetChanged();
    }


}
