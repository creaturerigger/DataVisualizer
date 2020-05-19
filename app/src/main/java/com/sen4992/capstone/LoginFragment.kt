package com.sen4992.capstone

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException


private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {

    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPassword: TextView
    private lateinit var auth: FirebaseAuth

    inline fun FragmentManager.doTransaction(func: FragmentTransaction.() ->
    FragmentTransaction) {
        beginTransaction().func().commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        userEmail = view.findViewById(R.id.txtedit_email)
        userPassword = view.findViewById(R.id.txtedit_password)
        forgotPassword = view.findViewById(R.id.txt_notRegistered)
        loginButton = view.findViewById(R.id.btn_login)
        loginButton.setOnClickListener {
            val userEmailText = userEmail.text.toString()
            val userPasswordText = userPassword.text.toString()
            val isInfoValid = validateFields(userEmailText, userPasswordText)
            if (isInfoValid) {
                val isSignedIn: Boolean = signIn(userEmailText, userPasswordText)
                Log.d(TAG, "isSignedIn = $isSignedIn")
            }
        }
        return view
    }

    private fun loadDataFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun signIn(email: String, password: String): Boolean {
        var isSignedIn = false
        val parentActivity = MainActivity.newInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(parentActivity) { task ->
                isSignedIn = if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d(TAG, "${user?.displayName} has signed in")
                    user?.uid?.let {
                        loadDataFragment(DataFragment.newInstance())
                    }
                    true
                } else {
                    try {
                        throw task.exception!!
                    }
                    catch (invalidMail: FirebaseAuthInvalidUserException) {
                        Toast
                            .makeText(context, "You have entered a wrong e-mail address.", Toast.LENGTH_SHORT)
                            .show()
                    }
                    catch (wrongPass: FirebaseAuthInvalidCredentialsException) {
                        Toast
                            .makeText(context, "You have entered a wrong password", Toast.LENGTH_SHORT)
                            .show()
                    }
                    Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        return isSignedIn
    }



    private fun validateFields(userEmail: String, password: String): Boolean {
        return if (userEmail.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, R.string.empty_fields, Toast.LENGTH_SHORT).show()
            false
        } else {
            if(!isEmailValid(userEmail)) {
                Toast.makeText(context, R.string.is_not_valid_email, Toast.LENGTH_SHORT).show()
                false
            } else {
                Toast.makeText(context, R.string.logging_in, Toast.LENGTH_SHORT).show()
                true
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}
