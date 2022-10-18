package com.example.waterjarmanagement

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.waterjarmanagement.databinding.ActivityOtpBinding
import com.google.android.gms.common.internal.Objects
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private lateinit var storedVerificationId: String
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var credential: PhoneAuthCredential
    private lateinit var progressDialog: ProgressDialog
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.textView.text = "Enter verification code sent to\n +91" + intent.getStringExtra("phone")

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Validating OTP")
        progressDialog.setMessage("wait...")

        phoneVerification("+91"+intent.getStringExtra("phone").toString())

        binding.verifyButton.setOnClickListener {
            progressDialog.show()
            credential = PhoneAuthProvider.getCredential(storedVerificationId, binding.firstPinView.text.toString())
            signInWithPhoneAuthCredential(credential)
            progressDialog.dismiss()
        }
    }

    private fun phoneVerification(phoneNumber: String){

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Snackbar.make(binding.root, "Verification success", Snackbar.LENGTH_LONG).show()
                Log.e("", "onVerificationCompleted:$credential")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("", "onVerificationFailed", e)

                Snackbar.make(binding.root, "Verification failed", Snackbar.LENGTH_LONG).show()
                val intent = Intent()
                intent.putExtra("result", "F")
                setResult(1, intent)
                finish()
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }
                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Snackbar.make(binding.root, "Code sent", Snackbar.LENGTH_LONG).show()
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("", "onCodeSent:$verificationId")
                storedVerificationId = verificationId
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)


    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.delete()
                    Snackbar.make(binding.root, "Incorrect Otp", Snackbar.LENGTH_LONG).show()
                    progressDialog.dismiss()
                    Handler().postDelayed({
                        val intent = Intent()
                        intent.putExtra("result", "T")
                        setResult(1, intent)
                        finish()
                    }, 2000)
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Snackbar.make(binding.root, "Incorrect Otp", Snackbar.LENGTH_LONG).show()
                        progressDialog.dismiss()
                        Handler().postDelayed({
                            val intent = Intent()
                            intent.putExtra("result", "F")
                            setResult(1, intent)
                            finish()
                        }, 2000)
                    }
                    // Update UI
                }
            }
    }
}