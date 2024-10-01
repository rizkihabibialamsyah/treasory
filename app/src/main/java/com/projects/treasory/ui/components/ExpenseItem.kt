package com.projects.treasory.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseItem(
    name: String,
    spent: String,
    budget: String,
    onNameChange: (String) -> Unit,
    onNameSet: () -> Unit,
    onSpentChange: (String) -> Unit,
    onSpentSet: () -> Unit,
    onBudgetChange: (String) -> Unit,
    onBudgetSet: () -> Unit,
    onDeleted: () -> Unit
) {
    var frozen by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .height(40.dp)
            .background(
                if (frozen) Color.LightGray else Color.White,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .weight(3f)
                .padding(5.dp)) {
            TreasoryTextField(
                value = name,
                onValueChange = onNameChange,
                onValueSet = onNameSet,
                enabled = !frozen,
            )
        }
        Box(
            Modifier
                .weight(1f)
                .padding(5.dp)) {
            TreasoryTextField(
                value = spent,
                onValueChange = onSpentChange,
                onValueSet = onSpentSet,
                enabled = !frozen,
                textAlign = TextAlign.Center,
                keyboardType = KeyboardType.Number,
            )
        }
        Box(
            Modifier
                .weight(1f)
                .padding(5.dp)) {
            TreasoryTextField(
                value = budget,
                onValueChange = onBudgetChange,
                onValueSet = onBudgetSet,
                enabled = !frozen,
                textAlign = TextAlign.Center,
                keyboardType = KeyboardType.Number
            )
        }
        ExpenseOptionButton(
            frozenState = frozen,
            onFreezeClick = { frozen = !frozen },
            onDeleteClick = onDeleted
        )
    }
}

@Composable
fun ExpenseOptionButton(
    frozenState: Boolean,
    onFreezeClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Box() {
        var expanded by remember { mutableStateOf(false) }

        IconButton(onClick = { expanded = true }) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "Localized description"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { if (frozenState) Text("Unfreeze") else Text("Freeze") },
                onClick = {
                    expanded = false
                    onFreezeClick()
                }
            )
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = {
                    expanded = false
                    onDeleteClick()
                }
            )
        }
    }
}
