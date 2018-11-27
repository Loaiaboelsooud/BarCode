package com.example.loaiaboelsooud.barcodedetector;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class BarcodeAdapter extends RecyclerView.Adapter<BarcodeAdapter.Barcode> {

    Context context;
    ArrayList<String> barcodes;

    public BarcodeAdapter(Context context, ArrayList<String> barcodes) {

        this.context = context;
        this.barcodes = barcodes;
    }

    @Override
    public BarcodeAdapter.Barcode onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.costume_barcode_row, parent, false);
        Barcode barcode = new Barcode(row);
        return barcode;
    }

    @Override
    public void onBindViewHolder(Barcode holder, int position) {
        holder.textView.setText(barcodes.get(position));
    }

    @Override
    public int getItemCount() {
        return barcodes.size();
    }

    public void removeItem(int position) {
        MainActivity.barcodeData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, MainActivity.barcodeData.size());
        notifyDataSetChanged();
    }

    public void addItem(int position) {
        notifyItemInserted(position);
    }

    public void restoreItem(int position, String item) {
        notifyItemInserted(position);
        MainActivity.barcodeData.add(position, item);
    }

    public class Barcode extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView dbutton;
        RelativeLayout viewForeground, viewBackground;

        public Barcode(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.barcode);
            //dbutton = itemView.findViewById(R.id.delete_Icon);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            viewBackground = itemView.findViewById(R.id.view_background);
        }
    }

}
