package com.example.cinemaapp.UserInfor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.TransactionAdapter;
import com.example.cinemaapp.api.InvoiceAPI;
import com.example.cinemaapp.client.APIClient;
import com.example.cinemaapp.dto.InvoiceCommonObject;
import com.example.cinemaapp.dto.InvoiceCommonDTO;
import com.example.cinemaapp.dto.response.InvoiceDetailDTO;
import com.example.cinemaapp.factory.GeneralResponse;
import com.example.cinemaapp.model.TransactionItem;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Response;

public class TransactionHistoryActivity extends AppCompatActivity {

    private RecyclerView rvTransactions;
    private TransactionAdapter transactionAdapter;
    private List<TransactionItem> transactionList;
    private ImageView btnBackGiaoDich;

    private InvoiceCommonObject invoiceCommonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        rvTransactions = findViewById(R.id.rvTransactions);
        btnBackGiaoDich = findViewById(R.id.btnBackGiaoDich);

        // Setup RecyclerView
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));

        // Initialize transaction list
        transactionList = new ArrayList<>();

        // Get data from Intent
        Intent intent = getIntent();
        if (intent.hasExtra("invoiceCommonObject")) {
            invoiceCommonObject = (InvoiceCommonObject) intent.getSerializableExtra("invoiceCommonObject");
            if (invoiceCommonObject != null && invoiceCommonObject.getInvoiceCommonDTOList() != null) {
                // Map InvoiceCommonDTO to TransactionItem
                for (InvoiceCommonDTO dto : invoiceCommonObject.getInvoiceCommonDTOList()) {
                    // Format date and time: "dd/MM/yyyy - HH:mm"
                    // showDate từ API là "yyyy-MM-dd" (VD: "2025-05-14"), cần chuyển thành "dd/MM/yyyy"
                    String formattedDate = reformatDate(dto.getShowDate());
                    // startTime từ API là "HH:mm:ss" (VD: "08:30:00"), cần chuyển thành "HH:mm"
                    String formattedTime = reformatTime(dto.getStartTime());
                    String dateTime = formattedDate + " - " + formattedTime;

                    // Format price: "150.000đ"
                    DecimalFormat decimalFormat = new DecimalFormat("#,###đ");
                    String formattedPrice = decimalFormat.format(dto.getTotalPrice());

                    // Create TransactionItem with dynamic data and static cinemaName
                    TransactionItem item = new TransactionItem(
                            dto.getId(),
                            "2D " + dto.getMovieName(), // Thêm "2D" trước movieName (tĩnh)
                            dateTime,
                            formattedPrice,
                            "BHD Star Garden" // Set tĩnh cho cinemaName
                    );
                    transactionList.add(item);
                }
            }
        }

        // Set adapter for RecyclerView
        transactionAdapter = new TransactionAdapter(transactionList);
        rvTransactions.setAdapter(transactionAdapter);

        // Handle item click
        transactionAdapter.setOnItemClickListener(item -> {

            InvoiceAPI apiService = APIClient.getClient().create(InvoiceAPI.class);
            apiService.getInvoiceDetail(item.getId()).enqueue(new retrofit2.Callback<GeneralResponse<InvoiceDetailDTO>>() {

                  @Override
                  public void onResponse(Call<GeneralResponse<InvoiceDetailDTO>> call, Response<GeneralResponse<InvoiceDetailDTO>> response) {
                      InvoiceDetailDTO invoiceDetailDTO = response.body().getData();
                      Intent detailIntent = new Intent(TransactionHistoryActivity.this, TransactionDetailActivity.class);
                      detailIntent.putExtra("movieTitle", item.getMovieTitle());
                      detailIntent.putExtra("dateTime", item.getDateTime());
                      detailIntent.putExtra("price", item.getPrice());
                      detailIntent.putExtra("cinemaName", item.getCinemaName());
                      detailIntent.putExtra("invoiceDetailDTO", invoiceDetailDTO);


                      // Truyền các thông tin tĩnh khác cho TransactionDetailActivity
                      detailIntent.putExtra("room", "IMAX");
                      detailIntent.putExtra("seats", "M22, M23");
                      detailIntent.putExtra("popcorn", "-/-");
                      detailIntent.putExtra("totalPrice", "336.910 đ");
                      detailIntent.putExtra("ticketPrice", "336.910 đ");
                      detailIntent.putExtra("popcornPrice", "0 đ");
                      detailIntent.putExtra("discount", "0 đ");
                      detailIntent.putExtra("giftCard", "0 đ");
                      detailIntent.putExtra("paymentMethod", "Thẻ quốc tế");
                      detailIntent.putExtra("cinemaLocation", "CGV Aeon Ha Dong\nTầng 3 & 4 – TTTM AEON MALL HÀ ĐÔNG, P. Dương Nội, Q. Hà Đông, Hà Nội");
                      startActivity(detailIntent);

                  }

                  @Override
                  public void onFailure(Call<GeneralResponse<InvoiceDetailDTO>> call, Throwable t) {

                  }

              });

            // Truyền dữ liệu từ TransactionItem


        });

        // Handle back button click
        btnBackGiaoDich.setOnClickListener(v -> finish());
    }

    // Hàm chuyển đổi định dạng ngày từ "yyyy-MM-dd" sang "dd/MM/yyyy"
    private String reformatDate(String date) {
        if (date == null || date.isEmpty()) return "";
        String[] parts = date.split("-");
        if (parts.length != 3) return date;
        return parts[2] + "/" + parts[1] + "/" + parts[0]; // Từ "2025-05-14" thành "14/05/2025"
    }

    // Hàm chuyển đổi định dạng giờ từ "HH:mm:ss" sang "HH:mm"
    private String reformatTime(String time) {
        if (time == null || time.isEmpty()) return "";
        String[] parts = time.split(":");
        if (parts.length != 3) return time;
        return parts[0] + ":" + parts[1]; // Từ "08:30:00" thành "08:30"
    }
}