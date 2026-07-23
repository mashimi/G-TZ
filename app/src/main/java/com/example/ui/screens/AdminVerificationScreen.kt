package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PaymentReceiptEntity
import com.example.data.model.PaymentStatus
import com.example.ui.theme.GermanCrimson
import com.example.ui.theme.MpesaGreen
import com.example.ui.theme.PrimaryNavy

@Composable
fun AdminVerificationScreen(
    receipts: List<PaymentReceiptEntity>,
    onBack: () -> Unit,
    onVerifyReceipt: (receiptId: Long, approve: Boolean, notes: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("screen_admin_verification")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("btn_back_admin")
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Rudi")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin",
                        tint = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Jopo la Msimamizi (Admin Verification)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = PrimaryNavy
                    )
                }
                Text(
                    text = "Kagua na kuthibitisha risiti za M-Pesa papo hapo",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (receipts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Hakuna risiti za malipo kwenye mfumo.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(receipts) { receipt ->
                    var noteText by remember { mutableStateOf("") }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("card_admin_receipt_${receipt.id}"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Risiti #${receipt.id} - ${receipt.planRequested}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = PrimaryNavy
                                )

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = when (receipt.status) {
                                        PaymentStatus.APPROVED.name -> MpesaGreen.copy(alpha = 0.2f)
                                        PaymentStatus.REJECTED.name -> GermanCrimson.copy(alpha = 0.2f)
                                        else -> Color.LightGray.copy(alpha = 0.3f)
                                    }
                                ) {
                                    Text(
                                        text = receipt.status,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = when (receipt.status) {
                                            PaymentStatus.APPROVED.name -> MpesaGreen
                                            PaymentStatus.REJECTED.name -> GermanCrimson
                                            else -> PrimaryNavy
                                        }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(text = "Kodi ya M-Pesa: ${receipt.mpesaRef}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text(text = "Kiasi: TZS ${receipt.amountTzs}", fontSize = 13.sp, color = MpesaGreen)
                            Text(text = "Namba ya Simu: ${receipt.phoneNumber}", fontSize = 12.sp, color = Color.Gray)

                            Spacer(modifier = Modifier.height(12.dp))

                            if (receipt.status == PaymentStatus.PENDING.name) {
                                OutlinedTextField(
                                    value = noteText,
                                    onValueChange = { noteText = it },
                                    placeholder = { Text("Maelezo ya Msimamizi (hiari)...") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("input_admin_note_${receipt.id}"),
                                    shape = RoundedCornerShape(8.dp)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(
                                        onClick = { onVerifyReceipt(receipt.id, false, noteText) },
                                        colors = ButtonDefaults.buttonColors(containerColor = GermanCrimson),
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("btn_reject_${receipt.id}"),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Kataa")
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Button(
                                        onClick = { onVerifyReceipt(receipt.id, true, noteText) },
                                        colors = ButtonDefaults.buttonColors(containerColor = MpesaGreen),
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("btn_approve_${receipt.id}"),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Thibitisha ✅")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
