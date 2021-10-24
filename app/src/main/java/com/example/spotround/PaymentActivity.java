package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.spotround.databinding.ActivityPaymentBinding;
import com.example.spotround.modle.Application;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    ActivityPaymentBinding binding;
    String paymentEmail, paymentPhoneNo;
    Application application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Checkout.preload(getApplicationContext());

        application = (Application) getIntent().getSerializableExtra("Application");
        FirebaseAuth auth = FirebaseAuth.getInstance();

        binding.Email.setText(auth.getCurrentUser().getEmail());
        binding.phoneNo.setText(application.getPhoneNo());

        binding.makePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentEmail = binding.Email.getText().toString();
                paymentPhoneNo = binding.phoneNo.getText().toString();
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
            options.put("prefill.email", paymentEmail);
            options.put("prefill.contact", paymentPhoneNo);
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
        //startActivity(new Intent(getApplicationContext(),InsideStudentLogin.class));
        //send email
        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference reference = fireStore.collection("Application").document(uid);

        application.setPayment(true);
        reference.set(application);

        Intent intent = new Intent(PaymentActivity.this, SetPreference.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment failed due to"+s, Toast.LENGTH_SHORT).show();
    }
}