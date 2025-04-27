package com.example.cinemaapp.Schedule;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.cinemaapp.R;

public class ThanhToanActivity extends AppCompatActivity {

    // Khai báo các view (giữ nguyên)
    private TextView movieTitleTextView;
    private TextView theaterInfoTextView;
    private TextView showtimeInfoTextView;
    private TextView seatInfoTextView;
    private TextView totalAmountTextView;
    private Button completePaymentButton;
    private TextView tvTimerThanhToan;
    private ImageView btnBackThanhToan;
    private CheckBox termsConditionsCheckbox;
    private TextView termsLinkTextView;
    private RadioGroup paymentMethodsGroup;
    private RadioButton paymentVnpayRadioButton;
    private RadioButton paymentMomoRadioButton;
    private RadioButton paymentZaloPayRadioButton;
    private RadioButton paymentShopeePayRadioButton;
    private LinearLayout paymentVnpayLayout;
    private LinearLayout paymentMomoLayout;
    private LinearLayout paymentZaloPayLayout;
    private LinearLayout paymentShopeePayLayout;

    // Các biến dữ liệu (giữ nguyên)
    private String tenPhim;
    private int soLuongGhe;
    private int tongTienVe;
    private int tongTienDoAn;
    private int tongTienThanhToan;
    private String tenRap;
    private String ngayChieu;
    private String gioChieu;

    // Các biến liên quan đến TimerService (giữ nguyên)
    private TimerService timerService;
    private boolean isServiceBound = false;

    // Biến theo dõi trạng thái chọn phương thức thanh toán và điều khoản
    private boolean isPaymentMethodSelected = false;
    private void selectPayment(int radioButtonId) {
        paymentMethodsGroup.check(radioButtonId);
        isPaymentMethodSelected = true;
        updatePaymentButtonState();
    }

    // BroadcastReceiver và ServiceConnection (giữ nguyên)
    private final BroadcastReceiver timerTickReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TimerService.ACTION_TIMER_TICK.equals(intent.getAction())) {
                long remainingTime = intent.getLongExtra(TimerService.EXTRA_TIME_REMAINING, 0);
                tvTimerThanhToan.setText(timerService.formatTime(remainingTime / 1000));
            } else if (TimerService.ACTION_TIMER_FINISHED.equals(intent.getAction())) {
                tvTimerThanhToan.setText("00:00");
                Toast.makeText(ThanhToanActivity.this, "Đã hết thời gian.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    };
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimerService.LocalBinder binder = (TimerService.LocalBinder) service;
            timerService = binder.getService();
            isServiceBound = true;
            tvTimerThanhToan.setText(timerService.formatTime(timerService.getRemainingTime() / 1000));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            timerService = null;
            isServiceBound = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        // Ánh xạ các view (giữ nguyên)
        tvTimerThanhToan = findViewById(R.id.tvTimerThanhToan);
        btnBackThanhToan = findViewById(R.id.btnBackThanhToan);
        termsConditionsCheckbox = findViewById(R.id.terms_conditions);
        termsLinkTextView = findViewById(R.id.terms_link);
        completePaymentButton = findViewById(R.id.complete_payment);
        paymentMethodsGroup = findViewById(R.id.payment_methods_group);
        paymentVnpayRadioButton = findViewById(R.id.payment_vnpay);
        paymentMomoRadioButton = findViewById(R.id.payment_momo);
        paymentZaloPayRadioButton = findViewById(R.id.payment_zalopay);
        paymentShopeePayRadioButton = findViewById(R.id.payment_shopeepay);
        movieTitleTextView = findViewById(R.id.movie_title);
        theaterInfoTextView = findViewById(R.id.theater_info);
        showtimeInfoTextView = findViewById(R.id.showtime_info);
        seatInfoTextView = findViewById(R.id.seat_info);
        totalAmountTextView = findViewById(R.id.total_amount);

        // Ánh xạ các LinearLayout phương thức thanh toán
        paymentVnpayLayout = findViewById(R.id.payment_vnpay_layout);
        paymentMomoLayout = findViewById(R.id.payment_momo_layout);
        paymentZaloPayLayout = findViewById(R.id.payment_zalopay_layout);
        paymentShopeePayLayout = findViewById(R.id.payment_shopeepay_layout);

        // Ban đầu vô hiệu hóa nút thanh toán
        completePaymentButton.setEnabled(false);
        completePaymentButton.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));

        // Thiết lập SpannableString cho TextView điều khoản (giữ nguyên)
        String prefixText = "Tôi đã đọc, hiểu và đồng ý với ";
        String linkText = "điều khoản";
        String fullText = prefixText + linkText;
        SpannableString spannableString = new SpannableString(fullText);
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, fullText.length(), 0);
        int startIndex = fullText.indexOf(linkText);
        int endIndex = startIndex + linkText.length();
        if (startIndex != -1) {
            spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, 0);
            spannableString.setSpan(new ForegroundColorSpan(Color.GREEN), startIndex, endIndex, 0);
        }
        termsLinkTextView.setText(spannableString);
        termsLinkTextView.setOnClickListener(v -> {
            Intent intent = new Intent(ThanhToanActivity.this, DieuKhoanActivity.class);
            startActivity(intent);
        });

        // Thiết lập OnClickListener cho các LinearLayout phương thức thanh toán
        paymentVnpayLayout.setOnClickListener(v -> selectPayment(R.id.payment_vnpay));
        paymentMomoLayout.setOnClickListener(v -> selectPayment(R.id.payment_momo));
        paymentZaloPayLayout.setOnClickListener(v -> selectPayment(R.id.payment_zalopay));
        paymentShopeePayLayout.setOnClickListener(v -> selectPayment(R.id.payment_shopeepay));

        paymentVnpayRadioButton.setClickable(false);
        paymentShopeePayRadioButton.setClickable(false);
        paymentZaloPayRadioButton.setClickable(false);
        paymentMomoRadioButton.setClickable(false);

        // Thiết lập OnCheckedChangeListener cho RadioGroup
        paymentMethodsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isPaymentMethodSelected = checkedId != -1;
                updatePaymentButtonState();
            }
        });

        // Thiết lập OnCheckedChangeListener cho CheckBox điều khoản
        termsConditionsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updatePaymentButtonState();
            }
        });

        // Lấy dữ liệu từ Intent và cập nhật UI (giữ nguyên)
        Bundle extras = getIntent().getExtras();
        if (extras != null) { /* ... */ }

        // Thiết lập OnClickListener cho nút hoàn tất thanh toán (giữ nguyên logic kiểm tra cả điều khoản và phương thức thanh toán)
        completePaymentButton.setOnClickListener(v -> {
            if (termsConditionsCheckbox.isChecked() && isPaymentMethodSelected) {
                String selectedPaymentMethod = "";
                int checkedRadioButtonId = paymentMethodsGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.payment_vnpay) {
                    selectedPaymentMethod = "VNPAY";
                } else if (checkedRadioButtonId == R.id.payment_momo) {
                    selectedPaymentMethod = "Momo";
                } else if (checkedRadioButtonId == R.id.payment_zalopay) {
                    selectedPaymentMethod = "ZaloPay";
                } else if (checkedRadioButtonId == R.id.payment_shopeepay) {
                    selectedPaymentMethod = "ShopeePay";
                }
                Toast.makeText(ThanhToanActivity.this, "Thanh toán bằng " + selectedPaymentMethod + " hoàn tất!", Toast.LENGTH_SHORT).show();
                // Thêm logic thanh toán thực tế dựa trên phương thức đã chọn ở đây
                // Sau khi thanh toán thành công, bạn có thể chuyển sang màn hình xác nhận
            } else {
                if (!termsConditionsCheckbox.isChecked()) {
                    Toast.makeText(ThanhToanActivity.this, "Vui lòng đồng ý với các điều khoản.", Toast.LENGTH_SHORT).show();
                } else if (!isPaymentMethodSelected) {
                    Toast.makeText(ThanhToanActivity.this, "Vui lòng chọn một phương thức thanh toán.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ThanhToanActivity.this, "Vui lòng chọn phương thức thanh toán và đồng ý với điều khoản.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Bind to TimerService và Register BroadcastReceiver (giữ nguyên)
        Intent serviceIntent = new Intent(this, TimerService.class);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(timerTickReceiver, new IntentFilter(TimerService.ACTION_TIMER_TICK));
        LocalBroadcastManager.getInstance(this).registerReceiver(timerTickReceiver, new IntentFilter(TimerService.ACTION_TIMER_FINISHED));
        btnBackThanhToan.setOnClickListener(v -> finish());
    }

    private void updatePaymentButtonState() {
        boolean isTermsChecked = termsConditionsCheckbox.isChecked();
        completePaymentButton.setEnabled(isTermsChecked && isPaymentMethodSelected);
        if (completePaymentButton.isEnabled()) {
            completePaymentButton.setBackgroundTintList(getResources().getColorStateList(R.color.green));
        } else {
            completePaymentButton.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(timerTickReceiver);
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }
}