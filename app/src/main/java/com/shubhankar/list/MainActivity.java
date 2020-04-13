package com.shubhankar.list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shubhankar.list.data.ItemsDatabase;
import com.shubhankar.list.model.Item;
import com.shubhankar.list.ui.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText itemName;
    private EditText itemQty;
    private EditText itemPrice;
    private EditText itemNote;
    private ItemsDatabase itemsDatabase;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList=new ArrayList<>();
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemsDatabase= new ItemsDatabase(this);
        itemList=itemsDatabase.getAllItems();
        for(int i=0;i<itemsDatabase.getItemsCount();i++){
            Log.d("TAG", "onCreate: " + itemList.get(i).getItemName());
        }
        Log.d("TAG", "item Count: " + itemsDatabase.getItemsCount());

        recyclerView=findViewById(R.id.recycler_view);
        recyclerViewAdapter=new RecyclerViewAdapter(this,itemList);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(recyclerViewAdapter);


        FloatingActionButton fab = findViewById(R.id.FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createPopupDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });



    }

    private void createPopupDialog() {

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        itemName = view.findViewById(R.id.item_name);
        itemQty = view.findViewById(R.id.item_qty);
        itemPrice = view.findViewById(R.id.item_price);
        itemNote = view.findViewById(R.id.item_note);
        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!itemName.getText().toString().isEmpty()
                        && !itemPrice.getText().toString().isEmpty()
                        && !itemQty.getText().toString().isEmpty()
                        ) {
                    saveItem(v);
                }else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }

            }
        });

        builder.setView(view);
        dialog = builder.create();// creating our dialog object
        dialog.show();// important step!


    }

    private void saveItem(View v) {
        Item item = new Item();

        String newItem = itemName.getText().toString().trim();
        Double newPrice = Double.parseDouble(itemPrice.getText().toString().trim());
        String quantity = itemQty.getText().toString().trim();
        String note = itemNote.getText().toString().trim();

        item.setItemName(newItem);
        item.setItemPrice(newPrice);
        item.setItemQuantity(quantity);
        item.setItemNote(note);

        itemsDatabase.addItem(item);

        Snackbar.make(v, "Item Saved",Snackbar.LENGTH_SHORT)
                .show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                recreate();;

            }
        }, 500);// 1sec



    }
}
