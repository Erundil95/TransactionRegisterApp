//package com.example.transactionregisterapp
//
//import android.os.AsyncTask
//import java.util.*
//import javax.mail.*
//import javax.mail.internet.InternetAddress
//import javax.mail.internet.MimeMessage
//
//class EmailSender (
//    private val to: String,
//    private val subject: String,
//    private val body: String
//
//    ) : AsyncTask<Void, Void, Void?>(){
//
//    override fun doInBackground(vararg params: Void?): Void? {
//        try {
//            val props = Properties()
//            props["mail.smtp.auth"] = "true"
//            props["mail.smtp.starttls.enable"] = "true"
//            props["mail.smtp.host"] = "smtp.gmail.com"
//            props["mail.smtp.port"] = "587"
//
////            val session = Session.getInstance(props, object : Authenticator() {
////                override fun getPasswordAuthentication(): PasswordAuthentication {
////                    //TODO: Create a separate account to do this with
////                    return PasswordAuthentication("cristianferrari95@gmail.com", )
////                }
////            }
//        }
//    }
//    }