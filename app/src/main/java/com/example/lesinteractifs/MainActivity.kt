package com.example.lesinteractifs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
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
@OptIn(ExperimentalStdlibApi::class)
@Composable
fun Body(modifier: Modifier = Modifier) {
    var nameString by remember { mutableStateOf("") }
    var surnameString by remember { mutableStateOf("") }

    // Gestionnaire de focus - correction ici
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = modifier.fillMaxSize(),
        onClick = { focusManager.clearFocus() }  // Correction ici
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
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
}

@Composable
fun SurnameTextField(surname: String, onChanded: (String) -> Unit) {
    val error = (surname.isEmpty() || surname.length < 3)
    val errorMessage = when {
        surname.isEmpty() -> "Le prénom ne peut pas être vide"
        surname.length < 3 -> "Le prénom est trop court (minimum 3 caractères)"
        else -> ""
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        OutlinedTextField(
            value = surname,
            onValueChange = onChanded,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Votre prénom") },
            singleLine = true,
            placeholder = { Text("Prénom inconnu") },
            isError = error,
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        if (error) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun NameTextFild(
    modifier: Modifier,
    nameString: String,
    onNameChanded: (String) -> Unit
) {
    val manager = LocalFocusManager.current

    TextField(
        value = nameString,
        onValueChange = onNameChanded,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        label = { Text("Entrer votre nom") },
        singleLine = true,
        placeholder = { Text("Nom inconnu") },
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
        isError = nameString.isEmpty(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                manager.moveFocus(FocusDirection.Down)
            }
        )
    )

    if (nameString.isEmpty()) {
        Text(
            text = "Le nom ne peut pas être vide",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(start = 24.dp, top = 4.dp)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LesInteractifsTheme {
        Body(modifier = Modifier)
    }
}