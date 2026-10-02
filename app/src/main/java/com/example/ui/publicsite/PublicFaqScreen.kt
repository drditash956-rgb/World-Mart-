package com.example.ui.publicsite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class FaqItem(
    val category: String,
    val question: String,
    val answer: String
)

@Composable
fun PublicFaqScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "General", "Sellers & Onboarding", "Customers & Orders", "Logistics & Delivery", "Technical & E-Shop")

    val faqList = remember {
        listOf(
            FaqItem(
                category = "General",
                question = "What makes World Mart different from Amazon or generic delivery apps?",
                answer = "World Mart is built specifically around the neighborhood retail ecosystem. We do not operate competing private-label brands. Instead, we give local merchants the technology, single-basket multi-store cart, and consolidated 2-hour courier fleet so that buying from 4 distinct neighborhood stores feels as effortless as ordering from a central warehouse."
            ),
            FaqItem(
                category = "General",
                question = "In which districts is World Mart currently operational?",
                answer = "We currently support active merchant networks and 2-hour delivery fleets across 5 metropolitan territories: Central Metro, West Harbor, East Valley, North Park, and South Tech Hub. Cross-district delivery corridors are launching in Q1 2027."
            ),
            FaqItem(
                category = "Sellers & Onboarding",
                question = "How much does it cost to register and sell on World Mart?",
                answer = "Joining World Mart involves zero setup fees, zero monthly listing fees, and zero software subscriptions. We charge a simple, transparent standard 8% commission on successfully completed customer orders. If you don't sell, you don't pay anything."
            ),
            FaqItem(
                category = "Sellers & Onboarding",
                question = "What is the physical field verification process?",
                answer = "Within 24 hours of submitting your online seller registration, a designated World Mart field representative visits your physical storefront or warehouse. They verify your commercial license, inspect packaging standards, setup your free mobile POS, and assist with catalog photography."
            ),
            FaqItem(
                category = "Customers & Orders",
                question = "Can I combine items from multiple shops in one order?",
                answer = "Yes! The World Mart Consolidated Cart allows you to add fresh bread from a local bakery, a phone charger from an electronics store, and vitamins from a pharmacy into a single basket. You pay once, and our drivers deliver everything in one consolidated drop-off."
            ),
            FaqItem(
                category = "Logistics & Delivery",
                question = "How fast is district delivery handled?",
                answer = "Orders placed within operating district hours are fulfilled in an average of 95 minutes, with our 2-hour guarantee. Local merchants package orders upon ping, and our dedicated electric micro-fleet handles collection and consolidated routing."
            ),
            FaqItem(
                category = "Technical & E-Shop",
                question = "Do I need coding or technical skills to manage my E-Shop?",
                answer = "Not at all. Every verified merchant receives an intuitive, zero-code World Mart Merchant Dashboard. Adding products, changing prices, marking stock availability, and toggling holiday hours can be completed on any smartphone in seconds."
            ),
            FaqItem(
                category = "Technical & E-Shop",
                question = "How do seller payouts and escrow work?",
                answer = "All customer payments are secured via WorldMart Pay escrow. Upon successful customer delivery confirmation, funds are automatically cleared and deposited directly into the merchant's linked commercial bank account on the next business morning."
            )
        )
    }

    val filteredFaq = faqList.filter { item ->
        val matchesCategory = selectedCategory == "All" || item.category.equals(selectedCategory, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
                item.question.contains(searchQuery, ignoreCase = true) ||
                item.answer.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("public_faq_screen")
    ) {
        // Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(CorporateNavyDark, CorporateNavySurface)
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "HELP & ANSWERS",
                    color = EmeraldTealLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Frequently Asked Questions",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Everything you need to know about the World Mart ecosystem.",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
            }
        }

        // Search & Category Chips
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search FAQ topics, payments, delivery...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldTeal) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("faq_search_input"),
                shape = RoundedCornerShape(10.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    val isSel = selectedCategory == cat
                    FilterChip(
                        selected = isSel,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EmeraldTeal,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Accordion FAQ list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(filteredFaq) { faq ->
                FaqAccordionCard(faq = faq)
            }
        }
    }
}

@Composable
fun FaqAccordionCard(faq: FaqItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.HelpOutline,
                        contentDescription = null,
                        tint = EmeraldTeal,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = faq.question,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = faq.answer,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Category: ${faq.category}",
                        fontSize = 10.sp,
                        color = EmeraldTeal,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
