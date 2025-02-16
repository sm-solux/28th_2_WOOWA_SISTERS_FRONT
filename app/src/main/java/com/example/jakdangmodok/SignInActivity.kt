package com.example.jakdangmodok

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.jakdangmodok.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
    private lateinit var googleAuthLauncher: ActivityResultLauncher<Intent>

    private var tokenId: String? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setGoogleAuthLauncher()
        addClickListeners()
    }

    private fun addClickListeners() {
        binding.llSignIn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                requestGoogleLogin()
            }
        }
    }

    private fun requestGoogleLogin() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(this@SignInActivity, googleSignInOption)
    }

    private fun setGoogleAuthLauncher() {
        googleAuthLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ActivityResultCallback { result ->
                Log.e(TAG, "resultCode : ${result.resultCode}")
                Log.e(TAG, "result : $result")

                if (result.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

                    try {
                        val account = task.getResult(ApiException::class.java)
                        tokenId = account.idToken
                        if (tokenId != null && tokenId != "") {
                            val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                            firebaseAuth.signInWithCredential(credential)
                                .addOnCompleteListener {
                                    if (firebaseAuth.currentUser != null) {
                                        val user: FirebaseUser = firebaseAuth.currentUser!!
                                        email = user.email.toString()
                                        Log.e(TAG, "email : $email")

                                        val googleSignInToken = account.idToken ?: ""
                                        if (googleSignInToken != "") {
                                            Log.e(TAG, "googleSignInToken : $googleSignInToken")
                                        } else {
                                            Log.e(TAG, "googleSignInToken이 null")
                                        }

                                        moveSignUpActivity()
                                    }
                                }
                        }
                    }   catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        )
    }

    private fun moveSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "SignInActivity"
    }
}