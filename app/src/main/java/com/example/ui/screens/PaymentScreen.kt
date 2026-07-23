package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.PaymentReceiptEntity
import com.example.data.model.PaymentStatus
import com.example.data.model.SubscriptionPlan
import com.example.data.model.UserEntity
import com.example.ui.theme.AmberGold
import com.example.ui.theme.GermanCrimson
import com.example.ui.theme.MpesaGreen
import com.example.ui.theme.MpesaGreenLight
import com.example.ui.theme.PrimaryNavy

@Composable
fun PaymentScreen(
    user: UserEntity?,
    receipts: List<PaymentReceiptEntity>,
    submissionNotice: String?,
    onSubmitPayment: (plan: SubscriptionPlan, mpesaRef: String, phone: String, imageUri: String?) -> Unit,
    onResetNotice: () -> Unit,
    onOpenAdminPanel: () -> Unit
) {
    val scrollState = rememberScrollState()
    var selectedPlan by remember { mutableStateOf(SubscriptionPlan.STANDARD) }
    var mpesaCodeText by remember { mutableStateOf("") }
    var phoneText by remember { mutableStateOf(user?.phone ?: "+255712345678") }
    var attachedImageUri by remember { mutableStateOf<String?>("sample_mpesa_receipt_demo.jpg") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("screen_payment")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "📲 Lipa kwa M-Pesa / Mobile Money",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PrimaryNavy
                )
                Text(
                    text = "Lipa kupitia M-Pesa kisha pakia risiti yako kupata masomo B1/B2",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Surface(
                onClick = onOpenAdminPanel,
                shape = RoundedCornerShape(12.dp),
                color = PrimaryNavy.copy(alpha = 0.1f),
                modifier = Modifier.testTag("btn_open_admin")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin",
                        tint = PrimaryNavy,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Admin", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryNavy)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Subscription Plans Carousel
        Text(
            text = "1. Chagua Mpango wa Masomo (Subscription Plan)",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(8.dp))

        val plans = listOf(
            SubscriptionPlan.BASIC,
            SubscriptionPlan.STANDARD,
            SubscriptionPlan.PREMIUM
        )

        plans.forEach { plan ->
            val isSelected = selectedPlan == plan
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { selectedPlan = plan }
                    .testTag("card_plan_${plan.name}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MpesaGreen.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
                ),
                border = androidx.compose.foundation.BorderStroke(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) MpesaGreen else Color.LightGray.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (isSelected) MpesaGreen else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = plan.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PrimaryNavy
                        )
                        Text(
                            text = "Unapata Masomo Mipaka Kiwango ${plan.maxLevelUnlocked}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Text(
                        text = "TZS ${String.format("%,d", plan.priceTzs)}/mwezi",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = MpesaGreen
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // M-Pesa Instructions Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MpesaGreen)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "2. Maelekezo ya Kutuma Pesa kwa M-Pesa",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "• Piga *150*00# (M-Pesa) au Tumia Vodacom App\n" +
                            "• Chagua Lipa kwa M-Pesa (Namba ya Simu)\n" +
                            "• Namba: 0755 123 456 (Jifunze Kijerumani TZ)\n" +
                            "• Kiasi: TZS ${String.format("%,d", selectedPlan.priceTzs)}\n" +
                            "• Kumbukumbu: GERMAN-${user?.id ?: 1}",
                    color = Color.White,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Receipt Upload Form
        Text(
            text = "3. Pakia Risiti & Namba ya Muamala (Transaction Ref)",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = mpesaCodeText,
            onValueChange = { mpesaCodeText = it },
            label = { Text("Kodi ya Muamala ya M-Pesa (mfano: QK9102X88)") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_mpesa_ref"),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = phoneText,
            onValueChange = { phoneText = it },
            label = { Text("Namba yako ya Simu iliyolipa") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_mpesa_phone"),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Image Attachment Button
        Surface(
            onClick = { attachedImageUri = "sample_mpesa_receipt_demo.jpg" },
            shape = RoundedCornerShape(12.dp),
            color = PrimaryNavy.copy(alpha = 0.08f),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("btn_attach_receipt")
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = "Picha",
                    tint = PrimaryNavy
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (attachedImageUri != null) "📸 Risiti ya M-Pesa Imeambatanishwa" else "Weka Picha / Screenshot ya Risiti",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = PrimaryNavy
                    )
                    Text(
                        text = "Picha ya muamala kutoka kwenye simu yako",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (submissionNotice != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = MpesaGreenLight)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = submissionNotice,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MpesaGreen
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        Button(
            onClick = {
                if (mpesaCodeText.isNotBlank()) {
                    onSubmitPayment(selectedPlan, mpesaCodeText, phoneText, attachedImageUri)
                    mpesaCodeText = ""
                }
            },
            enabled = mpesaCodeText.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("btn_submit_payment"),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Wasilisha Risiti kwa Uhakiki", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Payment History
        Text(
            text = "📋 Historia ya Malipo Yako",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (receipts.isEmpty()) {
            Text(
                text = "Hujawasilisha risiti bado.",
                fontSize = 13.sp,
                color = Color.Gray
            )
        } else {
            receipts.forEach { receipt ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Mpango: ${receipt.planRequested} (TZS ${receipt.amountTzs})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = PrimaryNavy
                            )
                            Text(
                                text = "Kodi ya M-Pesa: ${receipt.mpesaRef}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                        val (statusText, statusBg, statusColor) = when (receipt.status) {
                            PaymentStatus.APPROVED.name -> Triple("WAZI ✅", MpesaGreenLight, MpesaGreen)
                            PaymentStatus.REJECTED.name -> Triple("IMEKATALIWA ❌", GermanCrimson.copy(alpha = 0.15f), GermanCrimson)
                            else -> Triple("INASUBIRI ⏳", AmberGold.copy(alpha = 0.2f), PrimaryNavy)
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = statusBg
                        ) {
                            Text(
                                text = statusText,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = statusColor
                            )
                        }
                    }
                }
            }
        }
    }
}
