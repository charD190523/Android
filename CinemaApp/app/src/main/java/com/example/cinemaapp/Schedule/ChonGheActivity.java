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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.cinemaapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ChonGheActivity extends AppCompatActivity {

    private TextView tvTimerChonGhe;
    private TextView tvTenRapChonGhe;
    private TextView tvThongTinChieuChonGhe;
    private LinearLayout layoutHangGhe;
    private TextView tvTenPhimChonGhe;
    private TextView tvSoLuongGheChon;
    private TextView tvTongTienChonGhe;
    private TextView tvGiaTienChonGhe;
    private Button btnHoanTatChonGhe;
    private ImageView btnBackChonGhe;

    private String tenPhim;
    private String gioChieu;
    private String tenRap;
    private String tenPhong;
    private String ngayChieu;

    private List<View> gheDaChon = new ArrayList<>();
    private int tongTien = 0;
    private final int giaVe = 45000;
    private TimerService timerService;
    private boolean isServiceBound = false;

    private final BroadcastReceiver timerTickReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TimerService.ACTION_TIMER_TICK.equals(intent.getAction())) {
                long remainingTime = intent.getLongExtra(TimerService.EXTRA_TIME_REMAINING, 0);
                tvTimerChonGhe.setText(timerService.formatTime(remainingTime / 1000));
            } else if (TimerService.ACTION_TIMER_FINISHED.equals(intent.getAction())) {
                tvTimerChonGhe.setText("00:00");
                Toast.makeText(ChonGheActivity.this, "Đã hết thời gian đặt vé", Toast.LENGTH_LONG).show();
                new android.os.Handler().postDelayed(() -> finish(), 3000);
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
            tvTimerChonGhe.setText(timerService.formatTime(timerService.getRemainingTime() / 1000));
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
        setContentView(R.layout.activity_chon_ghe);

        // Ánh xạ các view
        Toolbar toolbarChonGhe = findViewById(R.id.toolbarChonGhe);
        btnBackChonGhe = findViewById(R.id.btnBackChonGhe);
        tvTimerChonGhe = findViewById(R.id.tvTimerChonGhe);
        tvTenRapChonGhe = findViewById(R.id.tvTenRapChonGhe);
        tvThongTinChieuChonGhe = findViewById(R.id.tvThongTinChieuChonGhe);
        layoutHangGhe = findViewById(R.id.layoutHangGhe);
        tvTenPhimChonGhe = findViewById(R.id.tvTenPhimChonGhe);
        tvSoLuongGheChon = findViewById(R.id.tvSoLuongGheChon);
        tvTongTienChonGhe = findViewById(R.id.tvTongTienChonGhe);
        tvGiaTienChonGhe = findViewById(R.id.tvGiaTienChonGhe);
        btnHoanTatChonGhe = findViewById(R.id.btnHoanTatChonGhe);

        // Nhận thông tin từ Intent (lịch chiếu)
        Intent intent = getIntent();
        if (intent != null) {
            tenPhim = intent.getStringExtra("tenPhim");
            gioChieu = intent.getStringExtra("gioChieu");
            tenRap = intent.getStringExtra("tenRap");
            tenPhong = "Screen 4";
            ngayChieu = intent.getStringExtra("ngayChieu");

            tvTenPhimChonGhe.setText(tenPhim);
            tvTenRapChonGhe.setText(tenRap);
            tvThongTinChieuChonGhe.setText(String.format("%s - %s %s ~ %s", tenPhong, ngayChieu, gioChieu, calculateEndTime(gioChieu)));
        }

        // Tạo layout ghế
        createSeatLayout();

        // Bắt đầu và bind TimerService
        Intent serviceIntent = new Intent(this, TimerService.class);
        startService(serviceIntent);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        // Đăng ký BroadcastReceiver để nhận cập nhật thời gian
        LocalBroadcastManager.getInstance(this).registerReceiver(timerTickReceiver,
                new IntentFilter(TimerService.ACTION_TIMER_TICK));
        LocalBroadcastManager.getInstance(this).registerReceiver(timerTickReceiver,
                new IntentFilter(TimerService.ACTION_TIMER_FINISHED));

        // Xử lý nút quay lại
        btnBackChonGhe.setOnClickListener(v -> finish());

        // Xử lý nút hoàn tất thanh toán
        btnHoanTatChonGhe.setOnClickListener(v -> {
            if (!gheDaChon.isEmpty()) {
                Intent intentDatDoAn = new Intent(ChonGheActivity.this, DatDoAnActivity.class);
                intentDatDoAn.putExtra("tenPhim", tenPhim);
                intentDatDoAn.putExtra("soLuongGhe", gheDaChon.size());
                intentDatDoAn.putExtra("tongTienVe", tongTien);
                intentDatDoAn.putExtra("tenRap", tenRap);
                intentDatDoAn.putExtra("ngayChieu", ngayChieu);
                intentDatDoAn.putExtra("gioChieu", gioChieu);
                startActivity(intentDatDoAn);
            } else {
                Toast.makeText(this, "Vui lòng chọn ghế trước khi thanh toán.", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void createSeatLayout() {
        char[] hangChars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        int[] soGheGiua = {6, 6, 6, 6, 6, 6, 6, 6, 6, 0};
        int[] soGheBienA_I = {4, 4, 4, 4, 4, 4, 4, 4, 3};
        int soGheDoiJ = 7;
        boolean[][] bookedSeats = generateBookedSeats(hangChars.length, Arrays.stream(soGheGiua).sum() + 2 * Arrays.stream(soGheBienA_I).max().orElse(0) + soGheDoiJ * 2);

        for (int i = 0; i < hangChars.length; i++) {
            char hang = hangChars[i];
            LinearLayout hangWrapperLayout = new LinearLayout(this);
            hangWrapperLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            hangWrapperLayout.setOrientation(LinearLayout.HORIZONTAL);
            hangWrapperLayout.setGravity(Gravity.CENTER_VERTICAL);

            TextView hangLabel = createHangLabel(hang);
            hangWrapperLayout.addView(hangLabel);

            LinearLayout hangLayout = new LinearLayout(this);
            hangLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            hangLayout.setOrientation(LinearLayout.HORIZONTAL);
            hangLayout.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams seatParams = new LinearLayout.LayoutParams(40, 40);
            seatParams.setMargins(8, 8, 8, 8);
            LinearLayout.LayoutParams seatDoiParams = new LinearLayout.LayoutParams(80, 40);
            seatDoiParams.setMargins(8, 8, 8, 8);

            int seatIndex = 0;
            if (hang == 'J') {
                // Xử lý riêng cho hàng J với ghế đôi
                for (int k = 0; k < soGheDoiJ; k++) {
                    boolean isBooked = bookedSeats[i][seatIndex++];
                    ImageView seat = createSeat(hang, k * 2, "Couple", true, isBooked);
                    hangLayout.addView(seat, seatDoiParams);
                }
            } else {
                int soGheBien = soGheBienA_I[i];
                // Ghế bên trái
                for (int j = 0; j < soGheBien; j++) {
                    boolean isBooked = bookedSeats[i][seatIndex++];
                    ImageView seat = createSeat(hang, j, "Stand", false, isBooked);
                    hangLayout.addView(seat, seatParams);
                }

                // Khoảng trống bên trái
                if (soGheBien > 0 && soGheGiua[i] > 0) {
                    View spaceLeft = new View(this);
                    spaceLeft.setLayoutParams(new LinearLayout.LayoutParams(20, 1));
                    hangLayout.addView(spaceLeft);
                }

                // Ghế giữa
                for (int k = 0; k < soGheGiua[i]; k++) {
                    String loaiGhe = (i < 3) ? "Stand" : (i < 9) ? "VIP" : "Couple";
                    boolean isDoi = (loaiGhe.equals("Couple"));
                    boolean isBooked = bookedSeats[i][seatIndex++];
                    ImageView seat = createSeat(hang, (soGheBien > 0 ? soGheBien + k : k), loaiGhe, isDoi, isBooked);
                    hangLayout.addView(seat, isDoi ? seatDoiParams : seatParams);
                    if (isDoi && k < soGheGiua[i] - 1) {
                        k++;
                        seatIndex++;
                    }
                }

                // Khoảng trống bên phải
                if (soGheBien > 0 && soGheGiua[i] > 0) {
                    View spaceRight = new View(this);
                    spaceRight.setLayoutParams(new LinearLayout.LayoutParams(20, 1));
                    hangLayout.addView(spaceRight);
                }

                // Ghế bên phải
                for (int l = 0; l < soGheBien; l++) {
                    boolean isBooked = bookedSeats[i][seatIndex++];
                    ImageView seat = createSeat(hang, (soGheBien > 0 ? soGheBien + soGheGiua[i] + l : soGheGiua[i] + l), "Stand", false, isBooked);
                    hangLayout.addView(seat, seatParams);
                }
            }

            hangWrapperLayout.addView(hangLayout);
            layoutHangGhe.addView(hangWrapperLayout);
        }
    }

    private TextView createHangLabel(char hang) {
        TextView label = new TextView(this);
        label.setText(String.valueOf(hang));
        label.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(0, 8, 16, 8);
        label.setLayoutParams(params);
        return label;
    }

    // Hàm giả định để tạo trạng thái ghế đã đặt (cần implement logic thực tế của bạn)
    private boolean[][] generateBookedSeats(int numRows, int maxSeatsPerRow) {
        boolean[][] booked = new boolean[numRows][maxSeatsPerRow];
        Random random = new Random();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < booked[i].length; j++) {
                booked[i][j] = random.nextBoolean() && random.nextBoolean(); // Tạo ngẫu nhiên trạng thái đã đặt
            }
        }
        return booked;
    }

    private ImageView createSeat(char hang, int soThuTu, String loaiGhe, boolean isDoi, boolean isBooked) {
        ImageView seatView = new ImageView(this);
        float density = getResources().getDisplayMetrics().density;
        int widthPx = (int) (isDoi ? 120 * density : 60 * density);
        int heightPx = (int) (60 * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthPx, heightPx);
        params.setMargins(4, 4, 4, 4); // Giảm margin nhiều hơn
        seatView.setLayoutParams(params);

        int iconResId;
        if (isBooked) {
            iconResId = R.drawable.ic_seat_sold;
        } else if (loaiGhe.equals("Stand")) {
            iconResId = R.drawable.ic_seat_stand;
        } else if (loaiGhe.equals("VIP")) {
            iconResId = R.drawable.ic_seat_vip;
        } else {
            iconResId = R.drawable.ic_seat_couple;
        }
        seatView.setImageResource(iconResId);
        seatView.setTag(hang + String.valueOf(soThuTu) + "-" + loaiGhe + "-" + (isBooked ? "booked" : "available"));

        seatView.setOnClickListener(v -> {
            String tag = (String) v.getTag();
            if (tag.endsWith("available")) {
                if (!gheDaChon.contains(v)) {
                    ((ImageView) v).setImageResource(R.drawable.ic_seat_selected);
                    v.setTag(tag.replace("available", "selected"));
                    gheDaChon.add(v);
                } else {
                    ((ImageView) v).setImageResource(iconResId);
                    v.setTag(tag.replace("selected", "available"));
                    gheDaChon.remove(v);
                }
                updateThongTinThanhToan();
            } else if (tag.endsWith("selected")) {
                ((ImageView) v).setImageResource(iconResId);
                v.setTag(tag.replace("selected", "available"));
                gheDaChon.remove(v);
                updateThongTinThanhToan();
            } else if (tag.endsWith("booked")) {
                Toast.makeText(this, "Ghế này đã được đặt", Toast.LENGTH_SHORT).show();
            }
        });

        return seatView;
    }

    private int getSeatColor(String loaiGhe) {
        if (loaiGhe.equals("Stand")) {
            return Color.GRAY;
        } else if (loaiGhe.equals("VIP")) {
            return Color.parseColor("#FF69B4");
        } else {
            return Color.parseColor("#800080");
        }
    }

    private void updateThongTinThanhToan() {
        int soGhe = gheDaChon.size();
        tongTien = soGhe * giaVe;
        tvSoLuongGheChon.setText(String.format(Locale.getDefault(), "2D SUB %d ghế", soGhe));
        tvGiaTienChonGhe.setText(String.format(Locale.getDefault(), "%dđ", tongTien));
        btnHoanTatChonGhe.setEnabled(soGhe > 0);
        btnHoanTatChonGhe.setBackgroundTintList(getColorStateList(soGhe > 0 ? R.color.green : R.color.gray));
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