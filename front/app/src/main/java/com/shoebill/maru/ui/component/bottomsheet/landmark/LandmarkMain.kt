package com.shoebill.maru.ui.component.bottomsheet.landmark

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.shoebill.maru.R
import com.shoebill.maru.viewmodel.BottomSheetNavigatorViewModel
import com.shoebill.maru.viewmodel.LandmarkLandingViewModel

@Composable
fun LandmarkMain(
    landmarkLandingViewModel: LandmarkLandingViewModel = hiltViewModel(),
    bottomSheetNavigatorViewModel: BottomSheetNavigatorViewModel = hiltViewModel()
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .clickable { bottomSheetNavigatorViewModel.navController!!.navigate("landmark/picture") },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        Text(
            text = landmarkLandingViewModel.coloredLandmarkName,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Box(Modifier.height(267.dp)) {
                AsyncImage(
                    model = landmarkLandingViewModel.landmark.occupantProfileImageUrl,
                    contentDescription = "occupant profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(1.dp)
                        .border(4.dp, Color.White, CircleShape)
                        .size(200.dp)
                        .clip(CircleShape)
                        .align(Alignment.BottomCenter)
                        .shadow(14.dp, CircleShape)
                )
                Image(
                    painter = painterResource(id = R.drawable.crown_image),
                    contentDescription = "crown",
                    modifier = Modifier
                        .size(92.dp)
                        .align(Alignment.TopCenter)
                )
            }
            Text(
                text = landmarkLandingViewModel.landmark.occupantNickname,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = landmarkLandingViewModel.landmark.sentence,
            Modifier.padding(horizontal = 50.dp),
            textAlign = TextAlign.Center
        )
    }
}