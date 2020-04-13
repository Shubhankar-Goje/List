package com.shubhankar.list.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.snackbar.Snackbar;
import com.shubhankar.list.R;
import com.shubhankar.list.data.ItemsDatabase;
import com.shubhankar.list.model.Item;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        Log.d("TAG", "RecyclerViewAdapter: ");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);
        Log.d("TAG", "onCreateViewHolder: ");


        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {


        Item item = itemList.get(position); // object Item
        YoYo.with(Techniques.FadeIn).playOn(viewHolder.cardView);

        viewHolder.itemName.setText(MessageFormat.format("{0}", item.getItemName()));
        viewHolder.itemPrice.setText(MessageFormat.format("Price: ₹{0}", item.getItemPrice()));
        viewHolder.quantity.setText(MessageFormat.format("Quantity: {0}", String.valueOf(item.getItemQuantity())));
        viewHolder.note.setText(MessageFormat.format("Note: {0}", String.valueOf(item.getItemNote())));
        viewHolder.dateAdded.setText(MessageFormat.format("Added on: {0}", item.getDateItemAdded()));


    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "getItemCount: "+itemList.size());
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView itemName;
        public TextView itemPrice;
        public TextView quantity;
        public TextView note;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;
        public CardView cardView;
        public int i=0;
        public int id;



        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            quantity = itemView.findViewById(R.id.item_quantity);
            note = itemView.findViewById(R.id.item_note);
            dateAdded = itemView.findViewById(R.id.item_date);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            cardView=itemView.findViewById(R.id.card_view);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position;
            position = getAdapterPosition();
            Item item = itemList.get(position);

            switch (v.getId()) {
                case R.id.editButton:
                    //edit item
                    editItem(item);
                    break;
                case R.id.deleteButton:
                    //delete item
                    deleteItem(item.getId());
                    break;
                case R.id.card_view:
                    i++;
                    if(i==18){
                        Toast.makeText(context,"Dev by Shubhu",Toast.LENGTH_SHORT).show();
                        i=0;
                    }
                    break;

            }
        }

        private void deleteItem(final int id) {
            builder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confimation_pop, null);

            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();


            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemsDatabase db = new ItemsDatabase(context);
                    db.deleteItem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        private void editItem(final Item item) {



            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.popup, null);

            Button saveButton;
            final EditText itemName;
            final EditText itemQuantity;
            final EditText itemPrice;
            final EditText itemNote;
            TextView title;

            itemName = view.findViewById(R.id.item_name);
            itemQuantity = view.findViewById(R.id.item_qty);
            itemPrice = view.findViewById(R.id.item_price);
            itemNote = view.findViewById(R.id.item_note);
            saveButton = view.findViewById(R.id.saveButton);
            saveButton.setText(R.string.update_text);
            title = view.findViewById(R.id.title);

            title.setText(R.string.edit_time);
            itemName.setText(item.getItemName());
            itemQuantity.setText(String.valueOf(item.getItemQuantity()));
            itemPrice.setText(item.getItemPrice().toString());
            itemNote.setText(String.valueOf(item.getItemNote()));


            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //update our item
                    ItemsDatabase databaseHandler = new ItemsDatabase(context);

                    //update items
                    item.setItemName(itemName.getText().toString());
                    item.setItemPrice(Double.parseDouble(itemPrice.getText().toString()));
                    item.setItemQuantity(itemQuantity.getText().toString());
                    item.setItemNote(itemNote.getText().toString());

                    if (!itemName.getText().toString().isEmpty()
                            && !itemPrice.getText().toString().isEmpty()
                            && !itemQuantity.getText().toString().isEmpty()
                            ) {

                        databaseHandler.updateItem(item);
                        notifyItemChanged(getAdapterPosition(),item); //important!


                    }else {
                        Snackbar.make(view, "Fields Empty",
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    dialog.dismiss();

                }
            });

        }
    }
}