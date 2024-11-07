package com.edulearn.app
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edulearn.app.models.AuthViewModel
import com.edulearn.app.screens.AuthScreen
import com.edulearn.app.screens.SplashScreen
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                val authViewModel = AuthViewModel()
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                val googleSignInClient = GoogleSignIn.getClient(this, gso)

                setContent {
                    MyApp(googleSignInClient, authViewModel)
                }
            }
}


            @Composable
            fun MyApp(googleSignInClient: GoogleSignInClient, authViewModel: AuthViewModel) {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "splash") {
                    composable(
                        "splash",
                    ){ SplashScreen {
                        navController.navigate("auth")
                     } }
                    composable("auth"){
                        AuthScreen(
                            googleSignInClient = googleSignInClient,
                            onAuthSuccess = {},
                            viewModel = authViewModel
                        )
                    }
                }

            }





