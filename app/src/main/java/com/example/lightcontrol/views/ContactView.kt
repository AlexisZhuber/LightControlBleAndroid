package com.example.lightcontrol.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lightcontrol.R
import com.example.lightcontrol.components.MainTopBar
import com.example.lightcontrol.components.SendWhatsApp
import com.example.lightcontrol.components.StaticSectionTitle
import com.example.lightcontrol.ui.theme.selectedColorDrawer

/**
 * Composable function for the Contact view.
 *
 * @param drawerState The state of the navigation drawer.
 */
@Composable
fun ContactView(drawerState: DrawerState) {
    Scaffold(
        topBar = {
            MainTopBar(
                title = stringResource(R.string.contact_topbar_title),
                onClickBackButton = { /*TODO*/ },
                drawerState = drawerState
            )
        }
    ) {
        ContentContactView(it)
    }
}

/**
 * Content for the Contact view.
 *
 * @param paddingValues Padding values from the Scaffold.
 */
@Composable
fun ContentContactView(paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        item {
            // Section title for headquarters
            StaticSectionTitle(stringResource(R.string.headquarters_text))

            // Row of flags
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mexico),
                    contentDescription = "Mexico",
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ecuador),
                    contentDescription = "Ecuador",
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.chile),
                    contentDescription = "Chile",
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.spain),
                    contentDescription = "Spain",
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.canada),
                    contentDescription = "Canada",
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.brazil),
                    contentDescription = "Brazil",
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                )
            }

            // Section title for "Contact us"
            StaticSectionTitle(stringResource(R.string.contact_us_title))

            // Part 1 text
            Text(
                text = stringResource(R.string.main_text_part_one),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )

            // Section title for "We develop your customized project"
            StaticSectionTitle(stringResource(R.string.we_develop_title))

            // Part 2 text
            Text(
                text = stringResource(R.string.main_text_part_two),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contact Info Card
            ContactCard()

            Spacer(modifier = Modifier.height(16.dp))

            // WhatsApp button with default message
            SendWhatsApp(stringResource(R.string.whatsapp_message))

            Divider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}

/**
 * Composable function for the Contact Info Card.
 */
@Composable
fun ContactCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = selectedColorDrawer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.contact_information_title),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(text = stringResource(R.string.contact_information_email))
            Text(text = stringResource(R.string.contact_information_phone))
        }
    }
}
