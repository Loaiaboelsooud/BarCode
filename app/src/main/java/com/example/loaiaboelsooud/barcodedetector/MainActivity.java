package com.example.loaiaboelsooud.barcodedetector;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelperLisetner {
    TextView Result;
    BarcodeAdapter barcodeAdapter;
    public static ArrayList<String> barcodeData;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1234;
    RecyclerView barcodeRecyclerView;
    private ConstraintLayout rootLayout;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barcodeData = new ArrayList<>();
        barcodeAdapter = new BarcodeAdapter(this, barcodeData);
        Result = (TextView) findViewById(R.id.scanResult);
        rootLayout = (ConstraintLayout) findViewById(R.id.rootLayout);
        barcodeRecyclerView = findViewById(R.id.barcoderecycler);
        barcodeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        barcodeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        barcodeRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        barcodeRecyclerView.setAdapter(barcodeAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(barcodeRecyclerView);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof BarcodeAdapter.Barcode) {
            String name = barcodeData.get(viewHolder.getAdapterPosition());
            final String deletedItem = barcodeData.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            barcodeAdapter.removeItem(deletedIndex);
            Snackbar snackbar = Snackbar.make(rootLayout, name + " is removed from list " + deletedIndex, Snackbar.LENGTH_SHORT);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barcodeAdapter.restoreItem(deletedIndex, deletedItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (flag) {
            barcodeAdapter.addItem(barcodeData.size() - 1);
            flag = false;
        }
    }

    public void scanBarcode(View v) {
        String[] permissions = {Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, scanBarcodeActivity.class);
            startActivityForResult(intent, 0);
            return;
        } else
            ActivityCompat.requestPermissions(MainActivity.this, permissions, CAMERA_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getApplicationContext(), "In order to scan barcode you should accept camera perm ",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Intent intent = new Intent(this, scanBarcodeActivity.class);
                    startActivityForResult(intent, 0);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("barcode");
                    barcodeData.add(barcode.displayValue);
                    flag = true;
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
