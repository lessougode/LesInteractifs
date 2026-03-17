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
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var passwordString by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Surface(
        modifier = modifier.fillMaxSize(),
        onClick = { focusManager.clearFocus() }
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

            SecureTextFiel(
                password = passwordString,
                onChanged = { passwordString = it },
                modifier = Modifier,
                focusManager = focusManager
            )
        }
    }
}

@Composable
fun SurnameTextField(surname: String, onChanded: (String) -> Unit) {
    val manager = LocalFocusManager.current
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
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    manager.moveFocus(FocusDirection.Down)
                }
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

@Composable
fun SecureTextFiel(
    password: String,
    onChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    focusManager: androidx.compose.ui.focus.FocusManager,
    onDone: () -> Unit = {}
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val isError = password.isNotEmpty() && password.length < 6

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = onChanged,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Mot de passe") },
            placeholder = { Text("Minimum 6 caractères") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onDone()
                }
            ),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Text(
                        text = if (passwordVisible) "🔓" else "🔒",  // Cadenas ouvert/fermé
                        fontSize = 20.sp
                    )
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            isError = isError
        )

        if (isError) {
            Text(
                text = "Le mot de passe doit contenir au moins 6 caractères",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LesInteractifsTheme {
        Body(modifier = Modifier)
    }
}