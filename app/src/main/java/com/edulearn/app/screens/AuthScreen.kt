package com.edulearn.app.screens
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.edulearn.app.models.AuthViewModel

@Composable
fun AuthScreen(
    googleSignInClient: GoogleSignInClient,
    onAuthSuccess: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()


    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.result
            val idToken = account?.idToken
            viewModel.handleGoogleSignIn(idToken,
                onSignInSuccess = {
                    onAuthSuccess()
                    Toast.makeText(context, "Singed In using google", Toast.LENGTH_SHORT).show() },
                onSignInFailure = {
                    Toast.makeText(context, "Sign-in failed", Toast.LENGTH_SHORT).show()
                }
            )
        } catch (e: Exception) {
            viewModel.setLoading(false)
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFA726)) // Vibrant background color
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Let's Get Started",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))

                if (currentUser == null &&  !isLoading) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(onClick = {
                            viewModel.setLoading(true)
                            signInLauncher.launch(googleSignInClient.signInIntent)
                        }) {
                            Text("Sign in with Google")
                        }
                    }
                } else if (currentUser!=null){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Welcome, ${currentUser?.displayName}")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            viewModel.auth.signOut()
                            googleSignInClient.signOut()
                            viewModel.signOut()
                        }) {
                            Text("Sign Out")
                        }
                    }
                }
                else {
                    CircularProgressIndicator()
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    val context = LocalContext.current
    val mockGoogleSignInClient = GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN)
    AuthScreen(
        googleSignInClient = mockGoogleSignInClient,
        onAuthSuccess = {}
    )
}
