package com.example.lesinteractifs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lesinteractifs.ui.theme.LesInteractifsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LesInteractifsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Body(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Body(modifier: Modifier = Modifier) {
    var nameString by remember { mutableStateOf("") }
    var surnameString by remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(12.dp)
    ) {
        NameTextFild(
            nameString = nameString,
            onNameChanded = { nameString = it },
            modifier = Modifier
        )

        SurnameTextField(
            surname = surnameString,
            onChanded = { surnameString = it }
        )
    }
}

@Composable
fun SurnameTextField(surname: String, onChanded: (String) -> Unit) {
    OutlinedTextField(
        value = surname,
        onValueChange = onChanded,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        label = { Text("Entrer votre prénom") },
        singleLine = true,
        placeholder = {
            Text(text = "Nom inconnu")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = { print("Click") }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                )
            }
        },
        isError = (surname == ""),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        )
    )
}

@Composable
fun NameTextFild(modifier: Modifier, nameString: String, onNameChanded: (String) -> Unit
) {
    TextField(
        value = nameString,
        onValueChange = onNameChanded,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        label = { Text("Entrer votre nom") },
        singleLine = true,
        placeholder = {
            Text(text = "Nom inconnu")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = { print("Click") }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                )
            }
        },
        isError = (nameString == ""),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        )
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LesInteractifsTheme {
        Body(modifier = Modifier)
    }
}