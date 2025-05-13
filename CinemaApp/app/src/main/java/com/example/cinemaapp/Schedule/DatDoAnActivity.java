package com.example.cinemaapp.Schedule;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.cinemaapp.R;

public class DatDoAnActivity extends AppCompatActivity {
    public static class Combo implements Serializable {
        private String tenCombo;
        private int soLuong;
        private int giaCombo;

        public Combo(String tenCombo, int soLuong, int giaCombo) {
            this.tenCombo = tenCombo;
            this.soLuong = soLuong;
            this.giaCombo = giaCombo;
        }

        public String getTenCombo() {
            return tenCombo;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public int getGiaCombo() {
            return giaCombo;
        }
    }
    private String viTriGhe; // Khai báo biến viTriGhe ở cấp độ class
    private ImageView btnBackDatDoAn;
    private TextView tvTenPhimDatDoAn;
    private TextView tvSoLuongGheDatDoAn;
    private TextView tvTongTienDatDoAn;
    private TextView tvGiaTienDatDoAn;
    private Button btnHoanTatChonGhe; // ID này giống với nút ở layout, chúng ta sẽ dùng tạm

    private String tenPhim;
    private int soLuongGhe = 0;
    private int tongTienVe = 0;
    private int tongTienDoAn = 0;
    private int tongTienThanhToan = 0;
    private ImageView btnTangCombo1;
    private ImageView btnGiamCombo1;
    private TextView tvSoLuongCombo1;
    private TextView tvGiaCombo1;
    private int soLuongCombo1 = 0;
    private int giaCombo1 = 69300;
    private ImageView btnTangCombo2;
    private ImageView btnGiamCombo2;
    private TextView tvSoLuongCombo2;
    private TextView tvGiaCombo2;
    private int soLuongCombo2 = 0;
    private int giaCombo2 = 72000;// Giá của combo 1
    private ImageView btnTangCombo3;
    private ImageView btnGiamCombo3;
    private TextView tvSoLuongCombo3;
    private TextView tvGiaCombo3;
    private int soLuongCombo3 = 0;
    private int giaCombo3 = 98100;
    private ImageView btnTangCombo4;
    private ImageView btnGiamCombo4;
    private TextView tvSoLuongCombo4;
    private TextView tvGiaCombo4;
    private int soLuongCombo4 = 0;
    private int giaCombo4 = 102600;
    private ImageView btnTangCombo5;
    private ImageView btnGiamCombo5;
    private TextView tvSoLuongCombo5;
    private TextView tvGiaCombo5;
    private int soLuongCombo5 = 0;
    private int giaCombo5 = 102600;
    private ImageView btnTangCombo6;
    private ImageView btnGiamCombo6;
    private TextView tvSoLuongCombo6;
    private TextView tvGiaCombo6;
    private int soLuongCombo6 = 0;
    private int giaCombo6 = 102600;
    private TextView tvTimerDatDoAn;
    private TextView tvTenRapDatDoAn;
    private TextView tvNgayChieuDatDoAn;
    private TextView tvGioChieuDatDoAn;

    // Khai báo các biến thành viên cho dữ liệu rạp, ngày, giờ
    private String tenRap;
    private String ngayChieu;
    private String gioChieu;

    private CountDownTimer countDownTimerDatDoAn;
    private long remainingTimeInMillisDatDoAn = 0;
    private static final long COUNTDOWN_INTERVAL = 1000;
    private TimerService timerService;
    private boolean isServiceBound = false;
    private final BroadcastReceiver timerTickReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TimerService.ACTION_TIMER_TICK.equals(intent.getAction())) {
                long remainingTime = intent.getLongExtra(TimerService.EXTRA_TIME_REMAINING, 0);
                tvTimerDatDoAn.setText(timerService.formatTime(remainingTime / 1000));
            } else if (TimerService.ACTION_TIMER_FINISHED.equals(intent.getAction())) {
                tvTimerDatDoAn.setText("00:00");
                Toast.makeText(DatDoAnActivity.this, "Đã hết thời gian.", Toast.LENGTH_LONG).show();
                finish(); // Hoặc thực hiện hành động phù hợp
            }
        }
    };

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimerService.LocalBinder binder = (TimerService.LocalBinder) service;
            timerService = binder.getService();
            isServiceBound = true;
            // Cập nhật giao diện ngay lập tức nếu service đã có thời gian
            tvTimerDatDoAn.setText(timerService.formatTime(timerService.getRemainingTime() / 1000));
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
        setContentView(R.layout.activity_dat_do_an);
        Intent serviceIntent = new Intent(this, TimerService.class);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        LocalBroadcastManager.getInstance(this).registerReceiver(timerTickReceiver,
                new IntentFilter(TimerService.ACTION_TIMER_TICK));
        LocalBroadcastManager.getInstance(this).registerReceiver(timerTickReceiver,
                new IntentFilter(TimerService.ACTION_TIMER_FINISHED));

        // Ánh xạ các view
        Toolbar toolbarChonGhe = findViewById(R.id.toolbarChonGhe);
        btnBackDatDoAn = findViewById(R.id.btnBackChonGhe);
        tvTenPhimDatDoAn = findViewById(R.id.tvTenPhimChonGhe);
        tvSoLuongGheDatDoAn = findViewById(R.id.tvSoLuongGheChon);
        tvTongTienDatDoAn = findViewById(R.id.tvTongTienChonGhe);
        tvGiaTienDatDoAn = findViewById(R.id.tvGiaTienChonGhe);
        btnHoanTatChonGhe = findViewById(R.id.btnHoanTatChonGhe);
        btnHoanTatChonGhe.setText("Hoàn tất thanh toán (2/3)");// Cập nhật text cho nút
        long thoiGianConLai = 0;
        tvTimerDatDoAn = findViewById(R.id.tvTimerDatDoAn);
        btnTangCombo1 = findViewById(R.id.btnTangCombo1);
        btnGiamCombo1 = findViewById(R.id.btnGiamCombo1);
        tvSoLuongCombo1 = findViewById(R.id.tvSoLuongCombo1);

        btnTangCombo2 = findViewById(R.id.btnTangCombo2);
        btnGiamCombo2 = findViewById(R.id.btnGiamCombo2);
        tvSoLuongCombo2 = findViewById(R.id.tvSoLuongCombo2);
        btnTangCombo3 = findViewById(R.id.btnTangCombo3);
        btnGiamCombo3 = findViewById(R.id.btnGiamCombo3);
        tvSoLuongCombo3 = findViewById(R.id.tvSoLuongCombo3);

        btnTangCombo4 = findViewById(R.id.btnTangCombo4);
        btnGiamCombo4 = findViewById(R.id.btnGiamCombo4);
        tvSoLuongCombo4 = findViewById(R.id.tvSoLuongCombo4);

        btnTangCombo5 = findViewById(R.id.btnTangCombo5);
        btnGiamCombo5 = findViewById(R.id.btnGiamCombo5);
        tvSoLuongCombo5 = findViewById(R.id.tvSoLuongCombo5);

        btnTangCombo6 = findViewById(R.id.btnTangCombo6);
        btnGiamCombo6 = findViewById(R.id.btnGiamCombo6);
        tvSoLuongCombo6 = findViewById(R.id.tvSoLuongCombo6);

        tvTenRapDatDoAn = findViewById(R.id.tvTenRapDatDoAn);
        tvNgayChieuDatDoAn = findViewById(R.id.tvNgayChieuDatDoAn);
        tvGioChieuDatDoAn = findViewById(R.id.tvGioChieuDatDoAn);

        // Nhận dữ liệu từ Intent (từ ChonGheActivity)
        Intent intent = getIntent();
        if (intent != null) {
            tenPhim = intent.getStringExtra("tenPhim");
            Log.d("DEBUG_GHE", "← DatDoAn nhận:"
                    + " SL=" + intent.getIntExtra("soLuongVe", -1)
                    + " | viTri=" + intent.getStringExtra("viTriGhe"));
            Log.d("DEBUG_GHE", "   soLuongGhe key cũ = "
                    + intent.getIntExtra("soLuongGhe", -1));

            soLuongGhe = intent.getIntExtra("soLuongVe", 0);
            tongTienVe = intent.getIntExtra("tongTienVe", 0);
            remainingTimeInMillisDatDoAn = intent.getLongExtra("thoiGianConLai", 0);
            tenRap = intent.getStringExtra("tenRap"); // Nhận tên rạp
            ngayChieu = intent.getStringExtra("ngayChieu"); // Nhận ngày chiếu
            gioChieu = intent.getStringExtra("gioChieu"); // Nhận giờ chiếu
            viTriGhe = intent.getStringExtra("viTriGhe");//            tvTimerDatDoAn.setText(formatTime(remainingTimeInMillisDatDoAn / 1000));
            tvTenPhimDatDoAn.setText(tenPhim);
            tvSoLuongGheDatDoAn.setText(String.format(Locale.getDefault(), "2D SUB %d ghế", soLuongGhe));
            tvTongTienDatDoAn.setText("Tổng tiền:"); // Label
            tvGiaTienDatDoAn.setText(String.format(Locale.getDefault(), "%dđ", tongTienVe));
            tongTienThanhToan = tongTienVe; // Khởi tạo tổng tiền thanh toán
            tvTenRapDatDoAn.setText(tenRap);
            tvNgayChieuDatDoAn.setText(ngayChieu);
            tvGioChieuDatDoAn.setText(String.format("%s - %s", gioChieu, calculateEndTime(gioChieu))); // Hiển thị khoảng giờ chiếu


        }

        // Xử lý nút back
        btnBackDatDoAn.setOnClickListener(v -> finish());

        // Xử lý nút hoàn tất thanh toán (chuyển sang bước 3)
        btnHoanTatChonGhe.setOnClickListener(v -> {
            ArrayList<Combo> danhSachCombo = new ArrayList<>();
            if (soLuongCombo1 > 0) {
                danhSachCombo.add(new Combo("OL Combo Single Sweet 22oz", soLuongCombo1, giaCombo1));
            }
            if (soLuongCombo2 > 0) {
                danhSachCombo.add(new Combo("OL Combo Single Sweet 32oz", soLuongCombo2, giaCombo2));
            }
            if (soLuongCombo3 > 0) {
                danhSachCombo.add(new Combo("OL Combo Couple Sweet 32oz", soLuongCombo3, giaCombo3));
            }
            if (soLuongCombo4 > 0) {
                danhSachCombo.add(new Combo("OL Food CB Ga Vong Sweet 32oz ", soLuongCombo4, giaCombo4));
            }
            if (soLuongCombo5 > 0) {
                danhSachCombo.add(new Combo("OL Food CB KTC Sweet 32oz", soLuongCombo5, giaCombo5));
            }
            if (soLuongCombo6 > 0) {
                danhSachCombo.add(new Combo("OL Food CB Xuc Xich Sweet 32oz", soLuongCombo6, giaCombo6));
            }

            // Thêm dòng này để truyền danh sách combo
            // Tạo Intent để chuyển sang ThanhToanActivity
            Intent intentThanhToan = new Intent(DatDoAnActivity.this, ThanhToanActivity.class);
            intentThanhToan.putExtra("tongTienThanhToan", tongTienThanhToan);
            intentThanhToan.putExtra("tenPhim", tenPhim);
            intentThanhToan.putExtra("soLuongVe", soLuongGhe);
            intentThanhToan.putExtra("viTriGhe", viTriGhe); // Đảm bảo bạn đã lấy 'viTriGhe' ở onCreate
            intentThanhToan.putExtra("tongTienVe", tongTienVe);
            intentThanhToan.putExtra("tongTienDoAn", tongTienDoAn);
            intentThanhToan.putExtra("tenRap", tenRap);
            intentThanhToan.putExtra("ngayChieu", ngayChieu);
            intentThanhToan.putExtra("gioChieu", gioChieu);
            intentThanhToan.putExtra("danhSachCombo", danhSachCombo);
            Log.d("DEBUG_GHE", "→ DatDoAn gửi:"
                    + " SL=" + soLuongGhe
                    + " | viTri=" + viTriGhe);

            // Khởi chạy ThanhToanActivity
            startActivity(intentThanhToan);
        });
        setupComboListeners();



    }
    //    private void startTimerDatDoAn() {
//        countDownTimerDatDoAn = new CountDownTimer(remainingTimeInMillisDatDoAn, COUNTDOWN_INTERVAL) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                remainingTimeInMillisDatDoAn = millisUntilFinished;
//                tvTimerDatDoAn.setText(formatTime(millisUntilFinished / 1000));
//            }
//
//            @Override
//            public void onFinish() {
//                tvTimerDatDoAn.setText("00:00");
//                Toast.makeText(DatDoAnActivity.this, "Đã hết thời gian.", Toast.LENGTH_LONG).show();
//                // Xử lý khi hết thời gian ở màn hình đặt đồ ăn (ví dụ: quay lại màn hình chọn ghế)
//                finish();
//            }
//        }.start();
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(timerTickReceiver);
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }
    private void setupComboListeners() {
        // Combo 1
        btnTangCombo1.setOnClickListener(v -> {
            soLuongCombo1++;
            tvSoLuongCombo1.setText(String.valueOf(soLuongCombo1));
            tongTienDoAn += giaCombo1;
            updateTongTienThanhToan();
        });

        btnGiamCombo1.setOnClickListener(v -> {
            if (soLuongCombo1 > 0) {
                soLuongCombo1--;
                tvSoLuongCombo1.setText(String.valueOf(soLuongCombo1));
                tongTienDoAn -= giaCombo1;
                updateTongTienThanhToan();
            }
        });

        // Combo 2
        btnTangCombo2.setOnClickListener(v -> {
            soLuongCombo2++;
            tvSoLuongCombo2.setText(String.valueOf(soLuongCombo2));
            tongTienDoAn += giaCombo2;
            updateTongTienThanhToan();
        });

        btnGiamCombo2.setOnClickListener(v -> {
            if (soLuongCombo2 > 0) {
                soLuongCombo2--;
                tvSoLuongCombo2.setText(String.valueOf(soLuongCombo2));
                tongTienDoAn -= giaCombo2;
                updateTongTienThanhToan();
            }
        });

        // Combo 3
        btnTangCombo3.setOnClickListener(v -> {
            soLuongCombo3++;
            tvSoLuongCombo3.setText(String.valueOf(soLuongCombo3));
            tongTienDoAn += giaCombo3;
            updateTongTienThanhToan();
        });

        btnGiamCombo3.setOnClickListener(v -> {
            if (soLuongCombo3 > 0) {
                soLuongCombo3--;
                tvSoLuongCombo3.setText(String.valueOf(soLuongCombo3));
                tongTienDoAn -= giaCombo3;
                updateTongTienThanhToan();
            }
        });

        // Combo 4
        btnTangCombo4.setOnClickListener(v -> {
            soLuongCombo4++;
            tvSoLuongCombo4.setText(String.valueOf(soLuongCombo4));
            tongTienDoAn += giaCombo4;
            updateTongTienThanhToan();
        });

        btnGiamCombo4.setOnClickListener(v -> {
            if (soLuongCombo4 > 0) {
                soLuongCombo4--;
                tvSoLuongCombo4.setText(String.valueOf(soLuongCombo4));
                tongTienDoAn -= giaCombo4;
                updateTongTienThanhToan();
            }
        });

        // Combo 5
        btnTangCombo5.setOnClickListener(v -> {
            soLuongCombo5++;
            tvSoLuongCombo5.setText(String.valueOf(soLuongCombo5));
            tongTienDoAn += giaCombo5;
            updateTongTienThanhToan();
        });

        btnGiamCombo5.setOnClickListener(v -> {
            if (soLuongCombo5 > 0) {
                soLuongCombo5--;
                tvSoLuongCombo5.setText(String.valueOf(soLuongCombo5));
                tongTienDoAn -= giaCombo5;
                updateTongTienThanhToan();
            }
        });

        // Combo 6
        btnTangCombo6.setOnClickListener(v -> {
            soLuongCombo6++;
            tvSoLuongCombo6.setText(String.valueOf(soLuongCombo6));
            tongTienDoAn += giaCombo6;
            updateTongTienThanhToan();
        });

        btnGiamCombo6.setOnClickListener(v -> {
            if (soLuongCombo6 > 0) {
                soLuongCombo6--;
                tvSoLuongCombo6.setText(String.valueOf(soLuongCombo6));
                tongTienDoAn -= giaCombo6;
                updateTongTienThanhToan();
            }
        });
    }

    // Hàm để cập nhật tổng tiền thanh toán
    private void updateTongTienThanhToan() {
        tongTienThanhToan = tongTienVe + tongTienDoAn;
        tvGiaTienDatDoAn.setText(String.format(Locale.getDefault(), "%dđ", tongTienThanhToan));
    }

    private String calculateEndTime(String startTime) {
        String[] parts = startTime.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        minute += 120; // Cộng thêm 2 tiếng (120 phút)
        hour += minute / 60;
        minute %= 60;
        hour %= 24;
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
    }
}
