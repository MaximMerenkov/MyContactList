package com.example.mycontactlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private ArrayList<Contact> items;
    private Context adapterContext;

    public ContactAdapter(Context context, ArrayList<Contact> items){
        super(context,R.layout.list_item,items);
        adapterContext = context;
        this.items= items;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        int red = ContextCompat.getColor(getContext(),R.color.system_red);
        int blue =  ContextCompat.getColor(getContext(),R.color.system_blue);

        try{
            Contact contact = items.get(position);

            if(v == null){
                LayoutInflater vi = (LayoutInflater)adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item,null);
            }
            TextView contactName = (TextView)v.findViewById(R.id.textContactName);
            TextView contactStreet = (TextView)v.findViewById(R.id.textStreet);
            TextView contactCity = (TextView)v.findViewById(R.id.textCity);
            TextView contactState = (TextView)v.findViewById(R.id.textState);
            TextView contactZip = (TextView)v.findViewById(R.id.textZipcode);
            TextView contactNumber =(TextView)v.findViewById(R.id.textPhoneNumber);
            TextView contactCell = (TextView)v.findViewById(R.id.textCellNumber);

            if(position % 2 == 0) {
                contactName.setTextColor(red);
            }
            else{
                contactName.setTextColor(blue);
            }
            Button b =(Button)v.findViewById(R.id.buttonDeleteContact);
            contactStreet.setText(contact.getStreetAddress());
            contactCity.setText(contact.getCity());
            contactState.setText(contact.getState());
            contactZip.setText(contact.getZipCode());
            contactName.setText(contact.getContactName());
            contactNumber.setText(contact.getPhoneNumber());
            contactCell.setText(contact.getCellNumber());
            b.setVisibility(View.INVISIBLE);
        }
        catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return v;

    }
    public void showDelete(final int position, final View convertView,
                           final Context context, final Contact contact){
        View v = convertView;
        final Button b = (Button)v.findViewById(R.id.buttonDeleteContact);
        if(b.getVisibility()==View.INVISIBLE){
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    hideDelete(position, convertView, context);
                    items.remove(contact);
                    deleteOption(contact.getContactId(), context);

                }
            });


        }
        else{
            hideDelete(position, convertView, context);
        }
    }
    private void deleteOption(int contactToDelete, Context context){
        ContactDataSource db = new ContactDataSource(context);
        try{
            db.open();
            db.deleteContact(contactToDelete);
            db.close();
        }
        catch(Exception e){
            Toast.makeText(adapterContext, "Delete Contact Failed", Toast.LENGTH_LONG).show();
        }
        this.notifyDataSetChanged();
    }
    public void hideDelete(int position, View convertView, Context context){
        View v = convertView;
        final Button b = (Button)v.findViewById(R.id.buttonDeleteContact);
        b.setVisibility(View.INVISIBLE);
        b.setOnClickListener(null);

    }





}
