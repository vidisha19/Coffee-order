package com.example.android.coffeeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Intent.ACTION_SENDTO;
import static android.content.Intent.EXTRA_SUBJECT;
import static android.content.Intent.EXTRA_TEXT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void SubmitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream =whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate =chocolateCheckBox.isChecked();

        EditText nameField=(EditText)findViewById(R.id.name_field) ;
        String name =nameField.getText().toString();

        displayQuantity( quantity );
        int price= calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage=createOrderSummary(price,hasWhippedCream,hasChocolate,name);
        displayOrderSummary(priceMessage);
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayOrderSummary(String priceMessage) {
        TextView orderSummary=(TextView)findViewById(R.id.order_summary_text_view);
        orderSummary.setText(priceMessage);
    }

    private String createOrderSummary(int price,boolean addWhippedCream,boolean addChocolate,String name)
    {
        String priceMessage="Name : "+name+ "\nAdd Whipped Cream ? "+addWhippedCream+"\nAdd Chocolate ? "+addChocolate+" \nQuantity : "+quantity+"\nTotal Amount : ₹"+price+"\nThank You!!";
        return priceMessage;

    }

    private int  calculatePrice(boolean addWhippedCream,boolean addChocolate) {
        int base=100;

        if(addWhippedCream)
        {
            base=base+20;
        }
        if(addChocolate)
        {
            base=base+30;
        }
        return base*quantity;
    }
    int quantity=0;
    public void decrement(View view) {
        if(quantity==1)
        {
            Toast.makeText(this,"You cannot have less than one coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);
    }

    public void increment(View view) {
        if(quantity==100)
        {
            Toast.makeText(this,"You cannot have more than 100 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);
    }

    public void email(View view) {


        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(EXTRA_SUBJECT, "Coffee order for " + name);
        intent.putExtra(EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}