package com.example.kevinbui.mygrocerylist.UI;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevinbui.mygrocerylist.Activity.DetailsActivity;
import com.example.kevinbui.mygrocerylist.Data.DatabaseHandler;
import com.example.kevinbui.mygrocerylist.Model.Grocery;
import com.example.kevinbui.mygrocerylist.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private Context context;
    private List<Grocery> groceryItems;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<Grocery> groceryItems) {
        this.context = context;
        this.groceryItems = groceryItems;
    }


    // Create View by inflating list_row and return it and pass in view and context
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // View has list row ready to be set
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view, context);
    }

    // Where everything is bind together
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Grocery grocery = groceryItems.get(position); // Contextualizing grocery item

        holder.groceryItemName.setText(grocery.getName()); // Binding things to our view
        holder.quantity.setText(grocery.getQuantity());
        holder.dateAdded.setText(grocery.getDateItemAdded());
    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView groceryItemName;
        public TextView quantity;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;
        public int id; // Holding ID of each item

        public ViewHolder(@NonNull View view, Context ctx) {

            super(view);

            context = ctx;

            // Set up widgets
            groceryItemName = (TextView) view.findViewById(R.id.name);
            quantity = (TextView) view.findViewById(R.id.quantity);
            dateAdded = (TextView) view.findViewById(R.id.dateAdded);

            editButton = (Button) view.findViewById(R.id.editButton);
            deleteButton = (Button) view.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Go to Next Screen/DetailsActivity
                    int position = getAdapterPosition();

                    Grocery grocery = groceryItems.get(position);
                    Intent intent = new Intent(context, DetailsActivity.class); // Takes us to detail activity
                    intent.putExtra("name", grocery.getName());
                    intent.putExtra("quantity", grocery.getQuantity());
                    intent.putExtra("id", grocery.getId());
                    intent.putExtra("data", grocery.getDateItemAdded());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.editButton:

                    break;
                case R.id.deleteButton:
                    int position = getAdapterPosition();
                    Grocery grocery = groceryItems.get(position);
                    deleteItem(grocery.getId());
                    break;
            }

        }

        public void deleteItem(final int id){

            // Create an AlertDialog
            alertDialogBuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            // This view will have all of the widgets
            View view = inflater.inflate(R.layout.confirmation_dialog, null);

            Button noButton = (Button) view.findViewById(R.id.noButton);
            Button yesButton = (Button) view.findViewById(R.id.yesButton);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Delete the item
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteGrocery(id);
                    groceryItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });

        }
    }
}
