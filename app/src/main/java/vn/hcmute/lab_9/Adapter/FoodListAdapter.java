package vn.hcmute.lab_9.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.hcmute.lab_9.Model.FoodReview;
import vn.hcmute.lab_9.R;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.MyViewHolder> {

    Context context;
    List<FoodReview> listFood;

    public FoodListAdapter(Context context, List<FoodReview> listFood) {
        this.context = context;
        this.listFood = listFood;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foodlist, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FoodReview food = listFood.get(position);
        holder.txtFoodName.setText(food.getStrMeal());
        Glide.with(context).load(food.getStrMealThumb()).into(holder.imgFood);
        // Set onClickListener for each item in the list
    }

    @Override
    public int getItemCount() {
        return listFood.isEmpty()? 0 : listFood.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgFood;
        TextView txtFoodName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.food_list_image);
            txtFoodName = itemView.findViewById(R.id.food_list_name);
        }
    }

    public void updateData(List<FoodReview> newFoodList) {
        this.listFood = newFoodList;
        notifyDataSetChanged();
    }
}
