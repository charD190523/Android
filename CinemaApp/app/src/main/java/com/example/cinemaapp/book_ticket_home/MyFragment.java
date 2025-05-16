package com.example.cinemaapp.book_ticket_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MyFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String ARG_TEN_PHIM = "tenPhim"; // Thêm key để truyền tên phim
    private static final String ARG_ANH_BIA = "anhBiaPhim"; // Thêm key để truyền ảnh bìa

    private int position;
    private RecyclerView lichChieuRecyclerView;
    private LichChieuPhimAdapter lichChieuAdapter;
    private List<LichChieuPhimAdapter.LichChieuPhimItem> lichChieuList;
    private LocalDate selectedDate;
    private String regionName;
    private String tenPhim;  // Thêm biến thành viên để lưu tên phim
    private int anhBiaPhim;    // Thêm biến thành viên để lưu ảnh bìa

    public MyFragment() {
        // Required empty public constructor
    }

    // Static factory method to create instances of the fragment.
    public static MyFragment newInstance(int position, String tenPhim, int anhBiaPhim) { // Thêm tham số
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putString(ARG_TEN_PHIM, tenPhim); // Truyền tên phim
        args.putInt(ARG_ANH_BIA, anhBiaPhim); // Truyền ảnh bìa
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
            tenPhim = getArguments().getString(ARG_TEN_PHIM); // Lấy tên phim
            anhBiaPhim = getArguments().getInt(ARG_ANH_BIA); // Lấy ảnh bìa
        }
        // Initialize the selectedDate.  It's important to have a default.
        selectedDate = LocalDate.now();
        // Set the region name based on the position
        switch (position) {
            case 0:
                regionName = "TP.HCM";
                break;
            case 1:
                regionName = "Hà Nội";
                break;
            case 2:
                regionName = "Huế";
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.item_lich_chieu_theo_phim, container, false);
        lichChieuRecyclerView = view.findViewById(R.id.lichChieuRecyclerView); // Sửa lỗi ở đây
        lichChieuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initial data load.
        loadData();
    }

    // Method to update data based on the selected date
    public void updateData(LocalDate date) {
        this.selectedDate = date;
        loadData();
    }

    private void loadData() {
        // Generate data based on the selectedDate and region.
        lichChieuList = generateLichChieuPhim(selectedDate, regionName);
        //IMPORTANT: Pass getContext(), and the selectedDate
        // Tìm tên rạp và ngày chiếu để truyền vào Adapter
        String tenRap = getTenRap(regionName); // Hàm này lấy tên rạp dựa trên regionName
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String ngayChieu = selectedDate.format(dateFormatter);

        lichChieuAdapter = new LichChieuPhimAdapter(getContext(), lichChieuList, tenRap, ngayChieu);
        lichChieuRecyclerView.setAdapter(lichChieuAdapter);
        lichChieuAdapter.notifyDataSetChanged();
    }

    private String getTenRap(String regionName) {
        // Logic để lấy tên rạp dựa trên khu vực.  Bạn có thể cần điều chỉnh logic này.
        if ("TP.HCM".equals(regionName)) {
            return "Rạp chiếu phim TP.HCM"; // Thay bằng tên rạp cụ thể
        } else if ("Hà Nội".equals(regionName)) {
            return "Rạp chiếu phim Hà Nội"; // Thay bằng tên rạp cụ thể
        } else if ("Huế".equals(regionName)) {
            return "Rạp chiếu phim Huế"; // Thay bằng tên rạp cụ thể
        }
        return ""; // Giá trị mặc định
    }

    private List<LichChieuPhimAdapter.LichChieuPhimItem> generateLichChieuPhim(LocalDate date, String region) {
        List<LichChieuPhimAdapter.LichChieuPhimItem> items = new ArrayList<>();
        Random random = new Random();

        // Xác định danh sách rạp dựa trên khu vực
        List<String> danhSachRap = getDanhSachRap(region);

        // Lặp qua từng rạp để tạo lịch chiếu
        for (String tenRap : danhSachRap) {
            // Tạo một số lượng phim ngẫu nhiên cho mỗi rạp
            int numMovies = random.nextInt(3) + 1;
            for (int i = 0; i < numMovies; i++) {
                //String tenPhim = getRandomTenPhim();
                List<String> gioChieu = generateGioChieu2D(random);
                items.add(new LichChieuPhimAdapter.LichChieuPhimItem(tenRap, tenPhim,gioChieu)); // Sửa lỗi ở đây
            }
        }
        return items;
    }

    private List<String> getDanhSachRap(String region) {
        // Phương thức này trả về danh sách các rạp dựa trên khu vực.
        // Đảm bảo rằng mỗi khu vực có đúng 3 rạp.
        if (region.equals("TP.HCM")) {
            return Arrays.asList("Rạp 1 - HCM", "Rạp 2 - HCM", "Rạp 3 - HCM");
        } else if (region.equals("Hà Nội")) {
            return Arrays.asList("Rạp 1 - HN", "Rạp 2 - HN", "Rạp 3 - HN");
        } else if (region.equals("Huế")) {
            return Arrays.asList("Rạp 1 - Huế", "Rạp 2 - Huế", "Rạp 3 - Huế");
        } else {
            return new ArrayList<>(); // Trả về danh sách trống nếu không có khu vực nào khớp
        }
    }

    private String getRandomTenPhim() {
        String[] tenPhims = {"Địa đạo: Mật trời trong bóng tối", "A Minecraft Movie", "DROP: Buổi hẹn hò kinh hoàng", "PANOR: Tà thuật huyết ngải"};
        Random random = new Random();
        return tenPhims[random.nextInt(tenPhims.length)];
    }

    private List<String> generateGioChieu2D(Random random) {
        List<String> gioChieu = new ArrayList<>();
        int soSuat = random.nextInt(3) + 1; // Số suất chiếu ngẫu nhiên
        int gioBatDau = 10 + random.nextInt(10);
        for (int i = 0; i < soSuat; i++) {
            int phut = random.nextInt(60);
            gioChieu.add(String.format("%02d:%02d", gioBatDau + i * 2, phut));
        }
        return gioChieu;
    }
}

