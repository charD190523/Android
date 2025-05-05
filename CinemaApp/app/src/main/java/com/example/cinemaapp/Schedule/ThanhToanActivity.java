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
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ThanhToanActivity extends AppCompatActivity {

    // Khai báo các view
    private TextView movieTitleTextView;
    private TextView theaterInfoTextView;
    private TextView showtimeInfoTextView;
    private TextView seatInfoTextView;
    private TextView ticketDetailsTextView; // Để hiển thị chi tiết vé
    private TextView foodDetailsTextView;   // Để hiển thị chi tiết đồ ăn
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

    // Các biến dữ liệu
    private String tenPhim;
    private int soLuongGhe;
    private int tongTienVe;
    private int tongTienDoAn = 0; // Khởi tạo là 0
    private int tongTienThanhToan;
    private String tenRap;
    private String ngayChieu;
    private String gioChieu;
    private String viTriGhe; // Thêm biến để nhận vị trí ghế

    // Danh sách combo (nếu có)
    private ArrayList<DatDoAnActivity.Combo> danhSachCombo = new ArrayList<>();

    // Các biến liên quan đến TimerService
    private TimerService timerService;
    private boolean isServiceBound = false;
    private ImageView moviePosterImageView;

    // Biến theo dõi trạng thái chọn phương thức thanh toán và điều khoản
    private boolean isPaymentMethodSelected = false;
    private void selectPayment(int radioButtonId) {
        paymentMethodsGroup.check(radioButtonId);
        isPaymentMethodSelected = true;
        updatePaymentButtonState();
    }

    // BroadcastReceiver và ServiceConnection
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


        // Ánh xạ các view
        moviePosterImageView = findViewById(R.id.movie_poster);
        String tenPhim = getIntent().getStringExtra("tenPhim");
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
        ticketDetailsTextView = findViewById(R.id.ticket_details); // Ánh xạ TextView chi tiết vé
        foodDetailsTextView = findViewById(R.id.food_details);// Ánh xạ TextView chi tiết đồ ăn

        // Ánh xạ các LinearLayout phương thức thanh toán
        paymentVnpayLayout = findViewById(R.id.payment_vnpay_layout);
        paymentMomoLayout = findViewById(R.id.payment_momo_layout);
        paymentZaloPayLayout = findViewById(R.id.payment_zalopay_layout);
        paymentShopeePayLayout = findViewById(R.id.payment_shopeepay_layout);

        if (tenPhim != null) {
            if (tenPhim.equals("Địa đạo: Mật trời trong bóng tối")) {
                moviePosterImageView.setImageResource(R.drawable.dia_dao_mat_troi);
            } else if (tenPhim.equals("A Minecraft Movie")) {
                moviePosterImageView.setImageResource(R.drawable.a_minecraft_movie);
            } else if (tenPhim.equals("DROP: Buổi hẹn hò kinh hoàng")) {
                moviePosterImageView.setImageResource(R.drawable.drop_buoi_hen_ho);
            } else if (tenPhim.equals("PANOR: Tà thuật huyết ngải")) {
                moviePosterImageView.setImageResource(R.drawable.panor_ta_thuat);
            } else {
                // Ảnh mặc định nếu không tìm thấy tên phim trùng khớp
                moviePosterImageView.setImageResource(R.drawable.default_poster);
            }
        } else {
            // Ảnh mặc định nếu không nhận được tên phim từ Intent
            moviePosterImageView.setImageResource(R.drawable.default_poster);
        }
        // Ban đầu vô hiệu hóa nút thanh toán
        completePaymentButton.setEnabled(false);
        completePaymentButton.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));

        // Thiết lập SpannableString cho TextView điều khoản
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

        // Lấy dữ liệu từ Intent và cập nhật UI
        Bundle extras = getIntent().getExtras();
        Log.d("DEBUG_GHE", "← ThanhToan nhận:"
                + " SL=" + extras.getInt("soLuongVe", -1)   // đọc key mới
                + " | SL_alt=" + extras.getInt("soLuongGhe", -1)  // key cũ
                + " | viTri=" + extras.getString("viTriGhe"));

        if (extras != null) {
            tenPhim = extras.getString("tenPhim");
            soLuongGhe = extras.getInt("soLuongVe", 0);
            tongTienVe = extras.getInt("tongTienVe", 0);
            tongTienDoAn = extras.getInt("tongTienDoAn", 0);
            tenRap = extras.getString("tenRap");
            ngayChieu = extras.getString("ngayChieu");
            gioChieu = extras.getString("gioChieu");
            viTriGhe = extras.getString("viTriGhe");
            tongTienThanhToan = extras.getInt("tongTienThanhToan", 0);// Lấy thông tin vị trí ghế

            // Nhận danh sách combo (nếu có)
            ArrayList<DatDoAnActivity.Combo> comboList = (ArrayList<DatDoAnActivity.Combo>) extras.getSerializable("danhSachCombo");
            if (comboList != null) {
                danhSachCombo.addAll(comboList);
                for (DatDoAnActivity.Combo combo : danhSachCombo) {
                    tongTienDoAn += combo.getSoLuong() * combo.getGiaCombo();
                }
            }

            movieTitleTextView.setText(tenPhim);
            theaterInfoTextView.setText(tenRap);
            showtimeInfoTextView.setText(String.format("%s - %s", ngayChieu, gioChieu));
            seatInfoTextView.setText(String.format("%d vé", soLuongGhe));
            totalAmountTextView.setText(String.format(Locale.getDefault(), "%dđ", tongTienThanhToan));

            // Hiển thị chi tiết vé
            // Hiển thị chi tiết vé
            if (viTriGhe != null && !viTriGhe.isEmpty()) {
                ticketDetailsTextView.setText(String.format(Locale.getDefault(), "%d vé 2D: %s - %dđ", soLuongGhe, viTriGhe, tongTienVe));
            } else {
                ticketDetailsTextView.setText(String.format(Locale.getDefault(), "%d vé - %dđ", soLuongGhe, tongTienVe));
            }

            // Hiển thị chi tiết đồ ăn (như trước)
            StringBuilder foodDetailsBuilder = new StringBuilder();
            if (!danhSachCombo.isEmpty()) {
                for (DatDoAnActivity.Combo combo : danhSachCombo) {
                    foodDetailsBuilder.append(combo.getSoLuong()).append(" x ").append(combo.getTenCombo()).append(" - ").append(String.format(Locale.getDefault(), "%dđ", combo.getSoLuong() * combo.getGiaCombo())).append("\n");
                }
                foodDetailsTextView.setText(foodDetailsBuilder.toString().trim());
            } else {
                foodDetailsTextView.setText("Không có đồ ăn hoặc nước được chọn.");
            }
        }

        // Thiết lập OnClickListener cho nút hoàn tất thanh toán
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

        // Bind to TimerService và Register BroadcastReceiver
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