
package com.example.firstchat2

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firstchat2.Models.User
import com.example.firstchat2.databinding.ActivitySignInBinding
import com.example.firstchat2.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog
    private  lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Signing Up")
        progressDialog.setMessage("We are creating your account")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) // for the requestIdToken, this is in the values.xml file that
            // is generated from your google-services.json
            .requestIdToken("688016477426-jh39jehn7nobgr9s06scnlidfma8k2b0.apps.googleusercontent.com")
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://firstchat-86bd2-default-rtdb.asia-southeast1.firebasedatabase.app")

        binding.suSignUpButton.setOnClickListener {

            if(binding.suEmailEditText.text.isNullOrEmpty() || binding.suNameEditText.text.toString().isNullOrEmpty()
                || binding.suPasswordEditText.text.isNullOrEmpty() || binding.suPasswordEditText.text.toString().length < 6){
                Toast.makeText(this, "Ensure all fields are appropriately filled", Toast.LENGTH_SHORT).show()
            }
            else{

                progressDialog.show()
                auth.createUserWithEmailAndPassword(binding.suEmailEditText.text.toString(),
                    binding.suPasswordEditText.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, "Signed Up successfully", Toast.LENGTH_SHORT).show()
                        val user: User = User()
                        user.setEmail(auth.currentUser?.email.toString())
                        user.setLastMessage("No message")
                        user.setProfilePic("")
                        user.setPassword(binding.suPasswordEditText.text.toString())
                        user.setUserName(binding.suNameEditText.text.toString())
                        user.setUserId(auth.currentUser?.uid.toString())
                        database.reference.child("Users").child(auth.currentUser?.uid.toString()).setValue(user)

                        progressDialog.dismiss()

                        val intent = Intent(this, NavigationActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                progressDialog.dismiss()
            }
        }

        binding.suGoogleButton.setOnClickListener {
            progressDialog.show()
            signIn()
        }

        binding.suAlreadyHaveAnAcc.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
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

                    database.reference.child("Users").child(auth.currentUser?.uid.toString())
                        .setValue(users)
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