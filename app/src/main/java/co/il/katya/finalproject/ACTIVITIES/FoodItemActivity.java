package co.il.katya.finalproject.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import co.il.katya.finalproject.R;
import co.il.katya.helper.AlertUtil;
import co.il.katya.helper.DateUtil;
import co.il.katya.helper.inputValidators.DateRule;
import co.il.katya.helper.inputValidators.NameRule;
import co.il.katya.helper.inputValidators.Rule;
import co.il.katya.helper.inputValidators.RuleOperation;
import co.il.katya.helper.inputValidators.TextRule;
import co.il.katya.helper.inputValidators.Validator;
import co.il.katya.model.FoodItem;
import co.il.katya.viewmodel.FoodViewModel;
import co.il.katya.viewmodel.GenericViewModelFactory;

public class FoodItemActivity extends AppCompatActivity {

    private TextView tvAddFoodItem;
    private TextView tvSelectCategory;
    private EditText etFoodName;
    private EditText etDate;
    private EditText etContent;
    private ImageView ivCalendar;
    private Button btnSave;
    private Button btnCancel;

    private FoodViewModel foodViewModel;

    private FoodItem oldItem;

    boolean[] selectedCategory;
    ArrayList<Integer> categoryList = new ArrayList<>();
    String[] categoryArray = {"Drinks", "Fruits", "Meat", "Sauces", "Spices", "Vegetables"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);

        initViews();
        setObservers();
    }

    private void setObservers() {
        GenericViewModelFactory<FoodViewModel> factory = new GenericViewModelFactory<>(getApplication(), FoodViewModel::new);
        foodViewModel = new ViewModelProvider(this, factory).get(FoodViewModel.class);

        foodViewModel.getSuccessOperation().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d("ll", "on change");
                if (aBoolean) {
                    Toast.makeText(FoodItemActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FoodItemActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        tvAddFoodItem = findViewById((R.id.tvAddFoodItem));
        tvSelectCategory = findViewById(R.id.tvSelectCategory);
        etFoodName = findViewById(R.id.etFoodName);
        etDate = findViewById(R.id.etDate);
        etContent = findViewById(R.id.etContent);
        ivCalendar = findViewById(R.id.ivCalendar);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        setListeners();
    }

    private void setListeners() {

        selectedCategory = new boolean[categoryArray.length];
        tvSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FoodItemActivity.this);
                builder.setTitle("Select Category");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(categoryArray, selectedCategory, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            categoryList.add(i);
                            Collections.sort(categoryList);
                        } else {
                            categoryList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < categoryList.size(); j++) {
                            // concat array value
                            stringBuilder.append(categoryArray[categoryList.get(j)]);
                            // check condition
                            if (j != categoryList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        tvSelectCategory.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedCategory.length; j++) {
                            // remove all selection
                            selectedCategory[j] = false;
                            // clear language list
                            categoryList.clear();
                            // clear text view value
                            tvAddFoodItem.setText("");
                        }
                    }
                });

                // show dialog
                builder.show();
            }
        });

        ivCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Select date");
                builder.setTextInputFormat(new SimpleDateFormat("dd/MM/yyyy"));

                CalendarConstraints constraint = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    constraint = DateUtil.buidCalendarConstrains(LocalDate.now(), LocalDate.now().plusDays(1095));
                }

                builder.setCalendarConstraints(constraint);


                if (!etDate.getText().toString().isEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        LocalDateTime date = LocalDate.parse(etDate.getText().toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
                        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
                        builder.setSelection(zdt.toInstant().toEpochMilli());
                    }
                }

                MaterialDatePicker picker = builder.build();

                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            etDate.setText(Instant.ofEpochMilli((long) selection).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                            etDate.setError(null);
                        } else {
                            etDate.setText("ERROR");
                        }
                    }
                });

                picker.addOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        AlertUtil.alertOk(FoodItemActivity.this, "Date", "Please enter expiration date", true, R.drawable.exclamation_mark);
                    }
                });

                picker.setCancelable(true);
                picker.show(getSupportFragmentManager(), "DATE PICKER");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    FoodItem foodItem = new FoodItem();

                    foodItem.setName(etFoodName.getText().toString());
                    foodItem.setCategory(tvSelectCategory.getText().toString());
                    foodItem.setBestUseBefore(DateUtil.stringDateToLong(etDate.getText().toString()));
                    foodItem.setContent(etContent.getText().toString());

                    Log.d("ll", "going to view model");
                    foodViewModel.add(foodItem);
                    Log.d("ll", "after view model");

//                    Toast.makeText(BlogPostActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    FoodItem foodItem = new FoodItem();

                    foodItem.setName(etFoodName.getText().toString());
                    foodItem.setCategory(tvSelectCategory.getText().toString());
                    foodItem.setBestUseBefore(DateUtil.stringDateToLong(etDate.getText().toString()));
                    foodItem.setContent(etContent.getText().toString());

                    if (oldItem != null)
                        foodItem.setIdFs(oldItem.getIdFs());

                    foodViewModel.add(foodItem);

                    Intent intent = new Intent();
                    intent.putExtra("ITEM", foodItem);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish();}
        });
    }


    public void setValidation() {

        Validator.add(new Rule(etFoodName, RuleOperation.REQUIRED, "Please enter item name"));
        Validator.add(new NameRule(etFoodName, RuleOperation.NAME, "Item name doesn't match requirements"));
        Validator.add(new Rule(tvSelectCategory, RuleOperation.REQUIRED, "Please select category"));
        Validator.add(new TextRule(tvSelectCategory, RuleOperation.TEXT, "Category doesn't match requirements", 4, 50, true));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Validator.add(new DateRule(etDate, RuleOperation.DATE, "Date is wrong", LocalDate.now(), LocalDate.now().plusDays(1095)));
        }

        Validator.add(new Rule(etContent, RuleOperation.REQUIRED, "Please enter content"));
        Validator.add(new TextRule(etContent, RuleOperation.TEXT, "Content doesn't match requirements", 1, 1000, true));

    }

    public boolean validate() {
        return Validator.validate();
    }

    private void getExtra() {
        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra("ITEM")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    oldItem = getIntent().getSerializableExtra("ITEM", FoodItem.class);
                    if (oldItem != null) {
                        setData();
                    }
                }
            }
        }
    }

    private void setData() {
        etFoodName.setText(oldItem.getName());
        tvSelectCategory.setText(oldItem.getCategory());
        etContent.setText(oldItem.getContent());
        etDate.setText(DateUtil.longDateToString(oldItem.getBestUseBefore()));
    }

}