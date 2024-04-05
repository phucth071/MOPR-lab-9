package vn.hcmute.lab_9.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import vn.hcmute.lab_9.Activity.FoodListActivity;
import vn.hcmute.lab_9.Model.Category;
import vn.hcmute.lab_9.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    List<Category> categoryList;


    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category cate = categoryList.get(position);
        holder.txtCategoryName.setText(cate.getName());
        holder.txtCategoryDescription.setText(cate.getDescription());
        Glide.with(context)
                .load(cate.getImages())
                .into(holder.imgCategory);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(v.getContext(), FoodListActivity.class);
                    intent.putExtra("CATEGORY_ID", cate.getId());
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgCategory;
        public TextView txtCategoryName;
        public TextView txtCategoryDescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.cate_image);
            txtCategoryName = itemView.findViewById(R.id.cate_name);
            txtCategoryDescription = itemView.findViewById(R.id.cate_desc);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList == null ? 0 : categoryList.size();
    }
}
