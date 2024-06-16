package com.example.transactionregisterapp

import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import java.text.SimpleDateFormat
import java.util.*
import com.google.api.services.gmail.GmailScopes
import com.google.api.services.gmail.Gmail
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.services.gmail.model.Message
import java.io.ByteArrayOutputStream
import java.util.Properties

import android.util.Base64
class TransactionEntryActivity : Activity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    private val PREF_ACCOUNT_NAME = "accountName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_entry)

        googleLogin()


        //TODO: Add autoformatting to the amount box
        val editTextAmount: EditText = findViewById(R.id.editTextAmount)
        val editTextVendor: EditText = findViewById(R.id.editTextVendor)
        val buttonSubmit: Button = findViewById(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            val amount = editTextAmount.text.toString()
            val vendor = editTextVendor.text.toString()

            //format data
            val formattedData = formatTransactionData(amount, vendor)

            //send email
            sendEmail(formattedData)
        }
    }

    private fun signIn() {
        Log.d("TransactionEntry", "Starting sign-in intent")
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun googleLogin() {
        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(GmailScopes.GMAIL_SEND))
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Check for an existing signed-in account
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            // User is already signed in, update UI accordingly
            Log.d("TransactionEntry", "Already signed in: ${account.email}")
        } else {
            // No user signed in, prompt for sign-in
            Log.d("TransactionEntry", "No account found, prompting sign-in")
            signIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.d("TransactionEntry", "Signed in successfully: ${account.email}")
        } catch (e: ApiException) {
            Log.w("TransactionEntry", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun formatTransactionData(amount: String, vendor: String): String {
        val date = SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date())
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        return "$amount €|$vendor|$date|$time|mine"
    }

    private fun sendEmail(data: String) {
        val to = "cristianferrari95@gmail.com"
        val subject = "CC Transaction"
        val body = data


    }

}