package com.bajmo.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText input_field;
    Button button_convert;
    TextView result;
    Spinner from_spinner;
    Spinner to_spinner;
    ImageView from_currency_icon;
    ImageView to_currency_icon;

    ArrayAdapter<CharSequence> model;
    String[] currencies = {"MAD", "USD", "EUR"};
    String[] symbol = {"Dh", "$", "€"};
    CardView result_card;
    int[] currency_icons = {
            R.drawable.morocco,
            R.drawable.united_states,
            R.drawable.european_union
    };
    double[][] rates = {
            {1,0.097,0.091},
            {10.34,1,0.94},
            {11.04,1.06,1}
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_convert = findViewById(R.id.button_convert);
        result = findViewById(R.id.result);
        from_spinner = findViewById(R.id.from_spinner);
        to_spinner = findViewById(R.id.to_spinner);
        input_field = findViewById(R.id.input_field);
        result_card = findViewById(R.id.result_card);
        from_currency_icon = findViewById(R.id.icon_from_currency);
        to_currency_icon = findViewById(R.id.icon_to_currency);

        model = new ArrayAdapter<>(this, R.layout.item_file, currencies);
        model = ArrayAdapter.createFromResource(this, R.array.currencies, R.layout.item_file);
        from_spinner.setAdapter(model);
        from_spinner.setSelection(1);
        to_spinner.setAdapter(model);

        from_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageView fromIcon = findViewById(R.id.icon_from_currency);
                int icon_id = currency_icons[position];
                fromIcon.setImageResource(icon_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        to_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageView toIcon = findViewById(R.id.icon_to_currency);
                int icon_id = currency_icons[position];
                toIcon.setImageResource(icon_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        button_convert.setOnClickListener(v -> {
            if (input_field.getText().toString().isEmpty())
                Toast.makeText(MainActivity.this, "Please enter the amount to convert", Toast.LENGTH_SHORT).show();
            else {
                result_card.setVisibility(View.VISIBLE);

                int amount_input = Integer.parseInt(input_field.getText().toString());
                int currency_from = Integer.parseInt(String.valueOf(from_spinner.getSelectedItemId()));
                int currency_to = Integer.parseInt(String.valueOf(to_spinner.getSelectedItemId()));
                double amount_result = amount_input * rates[currency_from][currency_to];

                result.setText(getString(R.string.amount_result, String.format(Locale.getDefault(), "%.2f", amount_result), symbol[currency_to]));
            }
        });
    }
}
