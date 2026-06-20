package com.example.nisabgold;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etPrice;
    TextView toggleSavings, toggleWorn;
    TextView btnCalculate, btnReset;
    TextView tvWeightError, tvPriceError, tvUrufHint;
    LinearLayout resultSection, breakdownToggleRow, breakdownContent;
    TextView tvTotalZakat, tvTotalValue, tvZakatPayable;
    TextView tvStep1Label, tvStep1Value, tvStep2Label, tvStep2Value;
    TextView tvStep3Label, tvStep3Value, tvStep4Label, tvStep4Value;
    ImageView ivChevron;
    ImageView ivInfoIcon;
    ImageButton btnShare, btnMenu;

    int selectedUruf = 85; // default: Savings
    String selectedTypeName = "Savings";
    boolean isBreakdownExpanded = false;

    DecimalFormat moneyFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Number formatter: RM 28,000.00 style
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        moneyFormat = new DecimalFormat("#,##0.00", symbols);

        bindViews();
        setupToggle();
        setupTextWatchers();
        setupButtons();
    }

    private void bindViews() {
        etWeight = findViewById(R.id.etWeight);
        etPrice = findViewById(R.id.etPrice);

        toggleSavings = findViewById(R.id.toggleSavings);
        toggleWorn = findViewById(R.id.toggleWorn);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);

        tvWeightError = findViewById(R.id.tvWeightError);
        tvPriceError = findViewById(R.id.tvPriceError);
        tvUrufHint = findViewById(R.id.tvUrufHint);

        resultSection = findViewById(R.id.resultSection);
        breakdownToggleRow = findViewById(R.id.breakdownToggleRow);
        breakdownContent = findViewById(R.id.breakdownContent);

        tvTotalZakat = findViewById(R.id.tvTotalZakat);
        tvTotalValue = findViewById(R.id.tvTotalValue);
        tvZakatPayable = findViewById(R.id.tvZakatPayable);

        tvStep1Label = findViewById(R.id.tvStep1Label);
        tvStep1Value = findViewById(R.id.tvStep1Value);
        tvStep2Label = findViewById(R.id.tvStep2Label);
        tvStep2Value = findViewById(R.id.tvStep2Value);
        tvStep3Label = findViewById(R.id.tvStep3Label);
        tvStep3Value = findViewById(R.id.tvStep3Value);
        tvStep4Label = findViewById(R.id.tvStep4Label);
        tvStep4Value = findViewById(R.id.tvStep4Value);

        ivChevron = findViewById(R.id.ivChevron);

        ivInfoIcon = findViewById(R.id.ivInfoIcon);

        btnShare = findViewById(R.id.btnShare);
        btnMenu = findViewById(R.id.btnMenu);
    }

    private void setupToggle() {
        toggleSavings.setOnClickListener(v -> selectGoldType(true));
        toggleWorn.setOnClickListener(v -> selectGoldType(false));
    }

    private void selectGoldType(boolean isSavings) {
        if (isSavings) {
            selectedUruf = 85;
            selectedTypeName = "Savings";
            toggleSavings.setBackgroundResource(R.drawable.toggle_selected_bg);
            toggleSavings.setTextColor(getColor(R.color.text_on_gold));
            toggleWorn.setBackgroundResource(R.drawable.toggle_unselected_bg);
            toggleWorn.setTextColor(getColor(R.color.text_secondary));
            tvUrufHint.setText("Uruf threshold: 85g for gold kept as savings");
        } else {
            selectedUruf = 200;
            selectedTypeName = "Worn";
            toggleWorn.setBackgroundResource(R.drawable.toggle_selected_bg);
            toggleWorn.setTextColor(getColor(R.color.text_on_gold));
            toggleSavings.setBackgroundResource(R.drawable.toggle_unselected_bg);
            toggleSavings.setTextColor(getColor(R.color.text_secondary));
            tvUrufHint.setText("Uruf threshold: 200g for gold worn as jewellery");
        }
        // Re-validate in case fields are already filled
        validateInputs();
    }

    private void setupTextWatchers() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateInputs();
            }
        };
        etWeight.addTextChangedListener(watcher);
        etPrice.addTextChangedListener(watcher);
    }

    private void validateInputs() {
        String weightStr = etWeight.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();

        boolean weightValid = true;
        boolean priceValid = true;

        // Weight validation
        if (weightStr.isEmpty()) {
            tvWeightError.setVisibility(View.GONE);
            weightValid = false;
        } else {
            double weight = Double.parseDouble(weightStr);
            if (weight <= 0) {
                tvWeightError.setText("Weight must be greater than 0");
                tvWeightError.setVisibility(View.VISIBLE);
                weightValid = false;
            } else {
                tvWeightError.setVisibility(View.GONE);
            }
        }

        // Price validation
        if (priceStr.isEmpty()) {
            tvPriceError.setVisibility(View.GONE);
            priceValid = false;
        } else {
            double price = Double.parseDouble(priceStr);
            if (price <= 0) {
                tvPriceError.setText("Price must be greater than 0");
                tvPriceError.setVisibility(View.VISIBLE);
                priceValid = false;
            } else {
                tvPriceError.setVisibility(View.GONE);
            }
        }

        // Enable/disable button based on validity
        if (weightValid && priceValid) {
            btnCalculate.setBackgroundResource(R.drawable.button_primary_bg);
            btnCalculate.setTextColor(getColor(R.color.text_on_gold));
            btnCalculate.setEnabled(true);
        } else {
            btnCalculate.setBackgroundResource(R.drawable.button_disabled_bg);
            btnCalculate.setTextColor(getColor(R.color.text_disabled));
            btnCalculate.setEnabled(false);
        }
    }

    private void setupButtons() {
        btnCalculate.setOnClickListener(v -> calculateZakat());

        btnReset.setOnClickListener(v -> resetForm());

        breakdownToggleRow.setOnClickListener(v -> toggleBreakdown());

        btnShare.setOnClickListener(v -> shareApp());

        btnMenu.setOnClickListener(v -> showMenu(v));

        ivInfoIcon.setOnClickListener(v -> showUrufInfoDialog());
    }

    private void calculateZakat() {
        double weight = Double.parseDouble(etWeight.getText().toString().trim());
        double price = Double.parseDouble(etPrice.getText().toString().trim());

        double totalValue = weight * price;
        double weightMinusUruf = weight - selectedUruf;
        double zakatableValue = (weightMinusUruf > 0) ? weightMinusUruf * price : 0;
        double totalZakat = zakatableValue * 0.025;

        // Main result card
        tvTotalZakat.setText("RM " + moneyFormat.format(totalZakat));
        tvTotalValue.setText("RM " + moneyFormat.format(totalValue));
        tvZakatPayable.setText("RM " + moneyFormat.format(zakatableValue));

        // Breakdown steps
        tvStep1Label.setText("STEP 1 — TOTAL VALUE");
        tvStep1Value.setText(trimDecimal(weight) + "g × RM" + moneyFormat.format(price)
                + " = RM" + moneyFormat.format(totalValue));

        tvStep2Label.setText("STEP 2 — SUBTRACT URUF (" + selectedTypeName.toUpperCase()
                + ": " + selectedUruf + "g)");
        if (weightMinusUruf > 0) {
            tvStep2Value.setText(trimDecimal(weight) + "g − " + selectedUruf + "g = "
                    + trimDecimal(weightMinusUruf) + "g zakatable");
        } else {
            tvStep2Value.setText(trimDecimal(weight) + "g − " + selectedUruf
                    + "g = below uruf, not zakatable");
        }

        tvStep3Label.setText("STEP 3 — ZAKATABLE VALUE");
        if (weightMinusUruf > 0) {
            tvStep3Value.setText(trimDecimal(weightMinusUruf) + "g × RM" + moneyFormat.format(price)
                    + " = RM" + moneyFormat.format(zakatableValue));
        } else {
            tvStep3Value.setText("RM0.00 (gold weight is within the exempted uruf amount)");
        }

        tvStep4Label.setText("STEP 4 — APPLY 2.5% ZAKAT RATE");
        tvStep4Value.setText("RM" + moneyFormat.format(zakatableValue) + " × 2.5% = RM"
                + moneyFormat.format(totalZakat));

        // Reset breakdown to collapsed state each new calculation
        breakdownContent.setVisibility(View.GONE);
        ivChevron.setRotation(0f);
        isBreakdownExpanded = false;

        // Animate result section into view
        if (resultSection.getVisibility() != View.VISIBLE) {
            resultSection.setVisibility(View.VISIBLE);
            AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
            fadeIn.setDuration(300);
            resultSection.startAnimation(fadeIn);
        }
    }

    private String trimDecimal(double value) {
        if (value == Math.floor(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }

    private void toggleBreakdown() {
        isBreakdownExpanded = !isBreakdownExpanded;
        if (isBreakdownExpanded) {
            breakdownContent.setVisibility(View.VISIBLE);
            ivChevron.setRotation(180f);
        } else {
            breakdownContent.setVisibility(View.GONE);
            ivChevron.setRotation(0f);
        }
    }

    private void resetForm() {
        etWeight.setText("");
        etPrice.setText("");
        selectGoldType(true); // back to default: Savings
        resultSection.setVisibility(View.GONE);
        breakdownContent.setVisibility(View.GONE);
        ivChevron.setRotation(0f);
        isBreakdownExpanded = false;
        tvWeightError.setVisibility(View.GONE);
        tvPriceError.setVisibility(View.GONE);
    }

    private void shareApp() {
        String appUrl = "https://github.com/yourusername/NisabGold"; // replace with your real GitHub link

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "NisabGold - Gold Zakat Calculator");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                "Try NisabGold to calculate your gold zakat!\n" + appUrl);

        startActivity(Intent.createChooser(shareIntent, "Share via..."));
    }

    private void showMenu(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenu().add("About");
        popup.setOnMenuItemClickListener(item -> {
            if (item.getTitle().equals("About")) {
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void showUrufInfoDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("What is Uruf?")
                .setMessage("Uruf is the customary minimum weight of gold exempted from zakat, based on common practice.\n\n" +
                        "• Savings (85g) — gold kept as an investment or stored wealth\n" +
                        "• Worn (200g) — gold actively worn as jewellery\n\n" +
                        "Only the weight above this threshold is subject to the 2.5% zakat rate.")
                .setPositiveButton("Got it", null)
                .show();
    }
}
