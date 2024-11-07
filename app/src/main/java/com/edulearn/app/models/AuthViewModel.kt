package com.edulearn.app.models
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel:ViewModel() {
    val db = FirebaseDatabase.getInstance()
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private var _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser
    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
    fun signOut(){
        _currentUser.value = null
        auth.signOut()
    }
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun handleGoogleSignIn(idToken: String?, onSignInSuccess: () -> Unit, onSignInFailure: (Exception?) -> Unit) {
        if (idToken != null) {
            Log.d("DebugAuthViewModel", "ID Token received")
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            viewModelScope.launch {
                auth.signInWithCredential(credential).addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        _currentUser.value = auth.currentUser
                        Log.d("DebugAuthViewModel", "Firebase Sign-In Success, User: ${auth.currentUser?.displayName}")
                        setLoading(false)

                        onSignInSuccess()
                    } else {
                        Log.e("DebugAuthViewModel", "Firebase Sign-In Failed: ${authResult.exception}")
                        _currentUser.value = null
                        setLoading(false)
                        onSignInFailure(authResult.exception)
                    }
                }
            }
        } else {
            Log.e("DebugAuthViewModel", "ID Token is null")
            setLoading(false)
            onSignInFailure(null)
        }
    }
}