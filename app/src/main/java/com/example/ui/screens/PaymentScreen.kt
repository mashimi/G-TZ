package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.model.PaymentReceiptEntity
import com.example.data.model.PaymentStatus
import com.example.data.model.SubscriptionPlan
import com.example.data.model.UserEntity
import com.example.ui.theme.AmberGold
import com.example.ui.theme.GermanCrimson
import com.example.ui.theme.MpesaGreen
import com.example.ui.theme.MpesaGreenLight
import com.example.ui.theme.PrimaryNavy
import com.example.util.ReceiptImagePicker

@Composable
fun PaymentScreen(
    user: UserEntity?,
    receipts: List<PaymentReceiptEntity>,
    submissionNotice: String?,
    onSubmitPayment: (plan: SubscriptionPlan, mpesaRef: String, phone: String, imageUri: String?) -> Unit,
    onResetNotice: () -> Unit,
    onOpenAdminPanel: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var selectedPlan by remember { mutableStateOf(SubscriptionPlan.STANDARD) }
    var mpesaCodeText by remember { mutableStateOf("") }
    var phoneText by remember { mutableStateOf(user?.phone ?: "+255712345678") }
    var attachedImageUri by remember { mutableStateOf<Uri?>(null) }
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    // ===== REAL IMAGE PICKER (Gallery) =====
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            attachedImageUri = uri
        }
    }

    // ===== REAL CAMERA CAPTURE =====
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success && cameraImageUri != null) {
            attachedImageUri = cameraImageUri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("screen_payment")
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
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

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                onClick = onOpenAdminPanel,
                shape = RoundedCornerShape(12.dp),
                color = PrimaryNavy.copy(alpha = 0.1f),
                modifier = Modifier.testTag("btn_open_admin")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin",
                        tint = PrimaryNavy,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Admin", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryNavy)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // STEP 1: Select Plan
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
                    containerColor = if (isSelected) MpesaGreen.copy(alpha = 0.08f)
                    else MaterialTheme.colorScheme.surface
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

        // STEP 2: M-Pesa Instructions
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

        // STEP 3: Form
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
        Spacer(modifier = Modifier.height(14.dp))

        // STEP 4: Real Image Attachment Options
        Text(
            text = "4. Weka Picha ya Risiti ya M-Pesa",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = PrimaryNavy
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Gallery Picker
            Surface(
                onClick = {
                    pickImageLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                color = PrimaryNavy.copy(alpha = 0.08f),
                modifier = Modifier
                    .weight(1f)
                    .testTag("btn_pick_gallery")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Gallery",
                        tint = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Gallery", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = PrimaryNavy)
                }
            }

            // Camera Capture
            Surface(
                onClick = {
                    val uri = ReceiptImagePicker.createImageUri(context)
                    cameraImageUri = uri
                    takePictureLauncher.launch(uri)
                },
                shape = RoundedCornerShape(12.dp),
                color = MpesaGreen.copy(alpha = 0.12f),
                modifier = Modifier
                    .weight(1f)
                    .testTag("btn_take_photo")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Piga Picha",
                        tint = MpesaGreen
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Piga Picha", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MpesaGreen)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Image Preview Box
        if (attachedImageUri != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MpesaGreenLight)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "✅ Risiti Imeambatanishwa!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MpesaGreen
                        )
                        Surface(
                            onClick = { attachedImageUri = null },
                            shape = RoundedCornerShape(8.dp),
                            color = GermanCrimson.copy(alpha = 0.1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Ondoa Picha",
                                tint = GermanCrimson,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = attachedImageUri),
                        contentDescription = "Risiti ya M-Pesa",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.15f))
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Bado hujaweka picha ya risiti",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Bonyeza 'Gallery' au 'Piga Picha' hapo juu",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submission Notice
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

        // Submit Button
        Button(
            onClick = {
                if (mpesaCodeText.isNotBlank()) {
                    onSubmitPayment(
                        selectedPlan,
                        mpesaCodeText,
                        phoneText,
                        attachedImageUri?.toString() ?: "sample_mpesa_receipt_demo.jpg"
                    )
                    mpesaCodeText = ""
                    attachedImageUri = null
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
