package com.example.prefdatastorepractice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.prefdatastorepractice.core.repositories.PrefRepositoryImpl
import com.example.prefdatastorepractice.core.viewmodels.PrefViewModel
import com.example.prefdatastorepractice.ui.theme.PrefDataStorePracticeTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: PrefViewModel
    private lateinit var prefRepositoryImpl: PrefRepositoryImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        prefRepositoryImpl = PrefRepositoryImpl(this)
        viewModel = PrefViewModel(prefRepositoryImpl)
        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
            PrefDataStorePracticeTheme(darkTheme = isDarkMode) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: PrefViewModel) {

    // Collect the StateFlows from the ViewModel in a lifecycle-aware manner
    val isOnboardingComplete by viewModel.isOnBoardingComplete.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val user by viewModel.userData.collectAsStateWithLifecycle()

    // Local state for the text fields
    var nameInput by remember { mutableStateOf("") }
    var idInput by remember { mutableStateOf("") }
    var tokenInput by remember { mutableStateOf("") }

//    val isdarkmode by viewModel.isDarkMode().collectAsState()
//
//    LaunchedEffect(isdarkmode) {
//        Log.d("45894513", "MainScreen: ${isdarkmode}")
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(32.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ){
//        Text(
//            text = "DataStore Example",
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(bottom = 24.dp)
//        )
//        OutlinedTextField(
//            value = "",
//            onValueChange = { "" },
//            label = { Text("Name") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        OutlinedTextField(
//            value = "",
//            onValueChange = { "" },
//            label = { Text("Age") },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = {
////            if (nameState.isNotBlank() && ageState.isNotBlank()){
////                scope.launch {
////                    storeData(context, nameState, ageState.toInt(), false)
////                    Toast.makeText(context, "Data Saved!", Toast.LENGTH_SHORT).show()
////                }
////            }
////            else{
////                Toast.makeText(context, "Please enter valid data", Toast.LENGTH_SHORT).show()
////            }
//            viewModel.isDarkMode()
//            isdarkmode
//        },
//            modifier = Modifier.fillMaxWidth()
//        ) { Text("Save Data") }
//
//        Spacer(modifier = Modifier.height(48.dp))
//
//        // Display the saved data
//        Text(
//            text = "Saved Name: ${isdarkmode}",
//            style = MaterialTheme.typography.bodyLarge
//        )
//        Text(
//            text = "Saved Age: 22",
//            style = MaterialTheme.typography.bodyLarge
//        )
//    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Make the column scrollable
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("App Preferences", style = MaterialTheme.typography.headlineMedium)

        // --- Section for Onboarding ---
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Onboarding Status", style = MaterialTheme.typography.titleMedium)
                Text("Is Onboarding Complete? $isOnboardingComplete")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.isOnBoardingComplete() }) {
                    Text("Complete Onboarding")
                }
            }
        }

        // --- Section for Theme Settings ---
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Dark Mode", style = MaterialTheme.typography.titleMedium)
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { isChecked ->
                        viewModel.setTheme(isChecked)
                    }
                )
            }
        }

        // --- Section for User Details ---
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("User Details", style = MaterialTheme.typography.titleMedium)
                //Text("Current Saved Name: $username")

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("New Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = idInput,
                    onValueChange = { idInput = it },
                    label = { Text("User ID") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = tokenInput,
                    onValueChange = { tokenInput = it },
                    label = { Text("Auth Token") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Button(
                    onClick = {
                        val id = idInput.toLongOrNull() ?: 0L
                        viewModel.saveUserData(nameInput, id, tokenInput)
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Save User")
                }

            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Card (modifier = Modifier.fillMaxWidth()){
            Column (modifier = Modifier.fillMaxWidth().padding(16.dp)){
                Text("Saved Name: ${user.name}")
                Text("Saved ID: ${user.id}")
                Text("Saved Token: ${user.token}")
            }
        }
    }
}
