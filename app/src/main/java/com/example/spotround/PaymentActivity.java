package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    EditText pymtemail;
    EditText pymtphone;
    Button makepymtbtn;
    String paymentemail,paymentphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Checkout.preload(getApplicationContext());
        pymtemail=findViewById(R.id.RegisterActivityEmail);
        pymtphone=findViewById(R.id.RegisterActivityPassword);
        makepymtbtn=findViewById(R.id.StartActivitybtnLogin);
        makepymtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentemail=pymtemail.getText().toString().trim();
                paymentphone=pymtphone.getText().toString().trim();
                makePayment();
            }
        });
    }

    private void makePayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_rdm1PP8ynqRQvd");

        checkout.setImage(R.drawable.walchand);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Walchand College of Engineering, Sangli");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits
            options.put("prefill.email", paymentemail);
            options.put("prefill.contact",paymentphone);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful & Payment ID is "+s, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),InsideStudentlogin.class));
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment failed due to"+s, Toast.LENGTH_SHORT).show();
    }
}