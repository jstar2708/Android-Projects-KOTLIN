package com.example.firstchat2

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firstchat2.Models.User
import com.example.firstchat2.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private  lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Logging in")
        progressDialog.setMessage("Wait while we log you in")

        binding.siClickForSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) // for the requestIdToken, this is in the values.xml file that
                 // is generated from your google-services.json
                .requestIdToken("688016477426-jh39jehn7nobgr9s06scnlidfma8k2b0.apps.googleusercontent.com")
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://firstchat-86bd2-default-rtdb.asia-southeast1.firebasedatabase.app")

        if(auth.currentUser != null){
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.siGoogleButton.setOnClickListener {
            progressDialog.show()
            signIn()
        }
        binding.siSignInButton.setOnClickListener {
            progressDialog.show()
            if(binding.siEmailEditText.text.isNullOrEmpty() || binding.siPasswordEditText.text.isNullOrEmpty() || binding.siPasswordEditText.text.toString().length < 6){
                Toast.makeText(this, "Ensure all fields are appropriately filled", Toast.LENGTH_SHORT).show()
            }
            if(binding.siPasswordEditText.text.toString().equals("GOOGLESIGNIN")){
                Toast.makeText(this, "Use google sign in option", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(binding.siEmailEditText.text.toString(), binding.siPasswordEditText.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        progressDialog.dismiss()
                        Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, NavigationActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            progressDialog.dismiss()
        }

    }

    private val RC_SIGN_IN = 65


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e)
                progressDialog.dismiss()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser

                    val users = User()
                    users.setUserId(user?.uid.toString())
                    users.setUserName(user?.displayName.toString())
                    users.setProfilePic(user?.photoUrl.toString())
                    users.setEmail(user?.email.toString())
                    users.setLastMessage("No Message")
                    users.setPassword("GOOGLESIGNIN")

                    database.reference.child("Users").child(auth.currentUser?.uid.toString()).setValue(users)
                    progressDialog.dismiss()
                    val intent = Intent(this, NavigationActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Sign in with google", Toast.LENGTH_SHORT).show()

                    //updateUI(user)
                } else {
                    progressDialog.dismiss()
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()

                    //updateUI(null)
                }
            }
    }
}