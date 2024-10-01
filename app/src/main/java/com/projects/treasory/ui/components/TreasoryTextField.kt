package com.projects.treasory.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TreasoryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onValueSet: () -> Unit,
    enabled: Boolean = true,
    textAlign: TextAlign = TextAlign.Left,
    fontSize: TextUnit = 16.sp,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    var edited by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = value,
        onValueChange = {
            edited = true
            onValueChange(it)
        },
        enabled = enabled,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                if (!it.isFocused && edited) {
                    keyboardController?.hide()
                    edited = false
                    onValueSet()
                }
            },
        textStyle = LocalTextStyle.current.copy(
            textAlign = textAlign,
            fontSize = fontSize
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        )
    )
}