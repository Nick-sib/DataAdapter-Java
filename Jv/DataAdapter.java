package ltd.nickolay.listclick.Jv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ltd.nickolay.listclick.R;

/**Adapter with Interfaces
 * onSelect
 * BeforeDelete*/

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    private ArrayList<DataItem> mList;

    //Interactions
    private int itemSeleted = -1;
    public interface OnItemListClick{
        void onSelectItem(int position, String title);
        boolean beforeDeleteItem(DataItem dataItem);
    }

    private OnItemListClick onItemListClickListener;
    public void setOnItemListClickListener(OnItemListClick onItemListClickListener) {
        this.onItemListClickListener = onItemListClickListener;
    }

    public void addItem(int image, String title, String subtext){
        if (mList.add(new DataItem(image, title, subtext))) {
            notifyItemInserted(mList.size()-1);
            if (itemSeleted != -1) {
                int priorSelected = itemSeleted;
                itemSeleted = -1;
                notifyItemChanged(priorSelected);
            }

        }
    }

    public DataAdapter(){
        this(null);
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_item,
                        parent,
                        false),
                onItemListClickListener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        DataItem dataItem = mList.get(position);
        holder.dvhImage.setImageResource(dataItem.getImage());
        if (itemSeleted != position)
            holder.dvhTitle.setText(dataItem.getTitle());
        else
            holder.dvhTitle.setText("SELECTED");
        holder.dvhSubText.setText(dataItem.getSubText());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public DataAdapter(ArrayList<DataItem> list) {
        if (list != null)
            mList = list;
        else
            mList = new ArrayList<>();
    }

    /////////////////////////////////////////////////////////////////
    public class DataViewHolder extends RecyclerView.ViewHolder {
        private ImageView dvhImage;
        private TextView dvhTitle;
        private TextView dvhSubText;

        public DataViewHolder(@NonNull View itemView, final OnItemListClick onItemListClick) {
            super(itemView);
            dvhImage = itemView.findViewById(R.id.iv_Image);
            dvhTitle = itemView.findViewById(R.id.tv_Title);
            dvhSubText = itemView.findViewById(R.id.tv_SubText);

            itemView.setOnClickListener(new OnDoubleClickListener() {
                @Override
                void onSingleClick(View view) {
                    int priorClick = itemSeleted;
                    itemSeleted = getAdapterPosition();
                    notifyItemChanged(priorClick);
                    notifyItemChanged(itemSeleted);

                    if ((onItemListClick != null) && (itemSeleted != RecyclerView.NO_POSITION))
                        onItemListClick.onSelectItem(itemSeleted, mList.get(itemSeleted).getTitle());
                }

                @Override
                void onDoubleClick(View view) {
                    if (onItemListClick != null){
                        int position = getAdapterPosition();
                        if ((position != RecyclerView.NO_POSITION) &&
                                onItemListClick.beforeDeleteItem(mList.get(position))){
                            mList.remove(position);
                            itemSeleted = -1;
                            notifyItemRemoved(position);
                        }
                    }
                }
            });
        }
    }
}
