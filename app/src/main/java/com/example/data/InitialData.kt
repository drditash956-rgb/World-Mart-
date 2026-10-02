package com.example.data

object InitialData {

    val staffList = listOf(
        StaffEntity(
            employeeId = "WM-EMP-101",
            fullName = "Julian Vance",
            photoAvatarId = 1,
            position = "Chief Executive Officer & Co-Founder",
            division = "Leadership",
            department = "Executive Office",
            location = "World Mart HQ, Metropolis",
            joiningDate = "Jan 15, 2024",
            employmentStatus = "Executive Full-Time",
            professionalBiography = "Julian brings over 14 years of executive leadership in hyper-local commerce and omnichannel retail systems. Prior to founding World Mart, he spearheaded digital marketplace integrations connecting over 12,000 regional merchants.",
            responsibilities = "• Setting corporate vision, long-term capital strategy, and district expansion roadmaps.\n• Aligning cross-division executive teams and overseeing regulatory partnerships.\n• Driving multi-vendor platform scaling across metropolitan economic corridors.",
            qualification = "MBA in Strategic Management (Stanford GSB), B.Sc. Computer Engineering (MIT)",
            officialWorkEmail = "julian.vance@worldmart.com",
            manager = "Board of Directors",
            accountStatus = "Active",
            salaryGrade = "Exec-1",
            bankAccountMasked = "•••• 9182",
            nationalIdMasked = "NAT-•••-1082",
            internalNotes = "Founding Member, Board Executive",
            isLeadership = true
        ),
        StaffEntity(
            employeeId = "WM-EMP-102",
            fullName = "Elena Rostova",
            photoAvatarId = 2,
            position = "Chief Technology Officer & Co-Founder",
            division = "Technology",
            department = "Core Engineering & Infrastructure",
            location = "World Mart HQ, Metropolis",
            joiningDate = "Jan 15, 2024",
            employmentStatus = "Executive Full-Time",
            professionalBiography = "Elena is a distributed systems architect with extensive expertise in real-time multi-tenant catalog indexing, edge microservices, and high-frequency order dispatch algorithms.",
            responsibilities = "• Leading core engineering, cloud infrastructure, and vendor POS platform development.\n• Guaranteeing 99.99% uptime, zero-trust security, and scalable multi-vendor APIs.\n• Spearheading automated merchant routing and catalog AI systems.",
            qualification = "M.Sc. Distributed Computing (ETH Zürich), B.Sc. Software Engineering",
            officialWorkEmail = "elena.rostova@worldmart.com",
            manager = "Julian Vance (CEO)",
            accountStatus = "Active",
            salaryGrade = "Exec-1",
            bankAccountMasked = "•••• 4410",
            nationalIdMasked = "NAT-•••-4921",
            internalNotes = "Founding Member, Chief Technical Architect",
            isLeadership = true
        ),
        StaffEntity(
            employeeId = "WM-EMP-103",
            fullName = "Dr. Tariq Al-Mansoor",
            photoAvatarId = 3,
            position = "Chief Financial Officer",
            division = "Finance",
            department = "Financial Strategy & Payouts",
            location = "World Mart HQ, Metropolis",
            joiningDate = "Apr 01, 2024",
            employmentStatus = "Executive Full-Time",
            professionalBiography = "Dr. Tariq possesses 16+ years overseeing fintech settlements, escrow escrow architecture, and merchant credit facilities for high-growth platform businesses.",
            responsibilities = "• Oversight of platform revenue models, seller payout escrow, and treasury liquidity.\n• Establishing fee structures, merchant growth funds, and compliance audits.\n• Directing district-level fiscal reporting and investor relations.",
            qualification = "Ph.D. Economics (LSE), Chartered Financial Analyst (CFA)",
            officialWorkEmail = "tariq.almansoor@worldmart.com",
            manager = "Julian Vance (CEO)",
            accountStatus = "Active",
            salaryGrade = "Exec-2",
            bankAccountMasked = "•••• 7731",
            nationalIdMasked = "NAT-•••-8820",
            internalNotes = "Authorizes merchant disbursement schedules",
            isLeadership = true
        ),
        StaffEntity(
            employeeId = "WM-EMP-104",
            fullName = "Camila Duarte",
            photoAvatarId = 4,
            position = "VP of Operations & District Logistics",
            division = "Operations",
            department = "District Fulfillment & Field Network",
            location = "Metropolis West Hub",
            joiningDate = "Jun 10, 2024",
            employmentStatus = "Full-Time Permanent",
            professionalBiography = "Camila manages last-mile carrier networks, district fulfillment hubs, and rapid on-boarding field teams across 5 metropolitan territories.",
            responsibilities = "• Coordinating district fulfillment centers, courier fleets, and seller parcel dispatch.\n• Maintaining seller SLA compliance (sub-2-hour packaging and dispatch).\n• Optimizing return handling and localized cross-docking.",
            qualification = "M.Sc. Supply Chain & Operations Management (Georgia Tech)",
            officialWorkEmail = "camila.duarte@worldmart.com",
            manager = "Julian Vance (CEO)",
            accountStatus = "Active",
            salaryGrade = "VP-1",
            bankAccountMasked = "•••• 3329",
            nationalIdMasked = "NAT-•••-3914",
            internalNotes = "Manages 42 field dispatch captains",
            isLeadership = false
        ),
        StaffEntity(
            employeeId = "WM-EMP-105",
            fullName = "Siddharth Rao",
            photoAvatarId = 5,
            position = "Director of Marketing & Merchant Acquisition",
            division = "Marketing",
            department = "Brand & Merchant Growth",
            location = "World Mart HQ, Metropolis",
            joiningDate = "May 20, 2024",
            employmentStatus = "Full-Time Permanent",
            professionalBiography = "Siddharth is a data-driven growth strategist who designed digital outreach programs onboarding over 2,500 brick-and-mortar storefronts to online commerce.",
            responsibilities = "• Directing brand positioning, district awareness campaigns, and public relations.\n• Managing corporate website content, seller success stories, and press releases.\n• Designing consumer acquisition funnels and vendor promotional campaigns.",
            qualification = "MBA in Marketing (Columbia Business School)",
            officialWorkEmail = "siddharth.rao@worldmart.com",
            manager = "Julian Vance (CEO)",
            accountStatus = "Active",
            salaryGrade = "Dir-1",
            bankAccountMasked = "•••• 6201",
            nationalIdMasked = "NAT-•••-6612",
            internalNotes = "Manages digital ad budgets & PR agencies",
            isLeadership = false
        ),
        StaffEntity(
            employeeId = "WM-EMP-106",
            fullName = "Amina Kente",
            photoAvatarId = 6,
            position = "Head of Business Development",
            division = "Business Development",
            department = "Strategic Partnerships & Enterprise Sellers",
            location = "World Mart East District Office",
            joiningDate = "Jul 01, 2024",
            employmentStatus = "Full-Time Permanent",
            professionalBiography = "Amina leads relationships with trade associations, regional chambers of commerce, and multi-location retail cooperatives.",
            responsibilities = "• Negotiating platform partnership agreements with major merchant associations.\n• Onboarding enterprise anchor vendors and wholesale distribution partners.\n• Formulating category expansion strategies for emerging product sectors.",
            qualification = "LL.B. Corporate Law, B.A. Economics",
            officialWorkEmail = "amina.kente@worldmart.com",
            manager = "Julian Vance (CEO)",
            accountStatus = "Active",
            salaryGrade = "Dir-2",
            bankAccountMasked = "•••• 1948",
            nationalIdMasked = "NAT-•••-9031",
            internalNotes = "Enterprise seller partnerships specialist",
            isLeadership = false
        ),
        StaffEntity(
            employeeId = "WM-EMP-107",
            fullName = "Rachel Chen",
            photoAvatarId = 7,
            position = "Head of Customer Support & Merchant Success",
            division = "Customer Support",
            department = "Merchant Care & Buyer Relations",
            location = "Metropolis Support Center",
            joiningDate = "Aug 12, 2024",
            employmentStatus = "Full-Time Permanent",
            professionalBiography = "Rachel runs 24/7 multilingual support operations for both shoppers and sellers, prioritizing fast resolution and seamless dispute handling.",
            responsibilities = "• Leading merchant onboarding helpdesk, seller verification audits, and consumer care.\n• Tracking customer CSAT, first-contact resolution times, and merchant queries.\n• Implementing self-serve seller documentation and dispute resolution workflows.",
            qualification = "B.Sc. Communications & Human Computer Interaction",
            officialWorkEmail = "rachel.chen@worldmart.com",
            manager = "Camila Duarte (Operations)",
            accountStatus = "Active",
            salaryGrade = "Lead-2",
            bankAccountMasked = "•••• 5821",
            nationalIdMasked = "NAT-•••-2849",
            internalNotes = "Oversees tier-1 and tier-2 escalation teams",
            isLeadership = false
        ),
        StaffEntity(
            employeeId = "WM-EMP-108",
            fullName = "Marcus Vance",
            photoAvatarId = 8,
            position = "Senior Field Sales Representative (CBD & West)",
            division = "Operations",
            department = "Field Operations & Merchant Onboarding",
            location = "Central Business District & West Harbor",
            joiningDate = "Sep 01, 2024",
            employmentStatus = "Full-Time Field",
            professionalBiography = "Marcus works directly on the ground with neighborhood store owners, managing onboarding, POS setup, and physical inventory auditing.",
            responsibilities = "• Conducting daily on-site visits to assigned brick-and-mortar storefronts.\n• Assisting local vendors with product photography, digital catalog setup, and packaging.\n• Reporting daily merchant activity and resolving field logistics challenges.",
            qualification = "B.A. Business Administration",
            officialWorkEmail = "m.vance@worldmart.com",
            manager = "Camila Duarte (Operations)",
            accountStatus = "Active",
            salaryGrade = "Field-1",
            bankAccountMasked = "•••• 8190",
            nationalIdMasked = "NAT-•••-7721",
            internalNotes = "Assigned 18 active sellers in Central & West districts",
            isLeadership = false
        )
    )

    val shopList = listOf(
        ShopEntity(
            shopId = "WM-SHP-201",
            shopName = "Apex Digital & Mobile Hub",
            owner = "Harrison Croft",
            category = "Electronics",
            address = "142 Grand Avenue, Suite 3B",
            district = "Central Metro",
            block = "Block-4",
            city = "Metropolis",
            contact = "+1 (555) 234-8901",
            assignedRepresentative = "Marcus Vance",
            registrationDate = "2024-02-10",
            status = "Active",
            verificationStatus = "Verified",
            eshopStatus = "Live",
            sellerId = "WM-SLR-1001"
        ),
        ShopEntity(
            shopId = "WM-SHP-202",
            shopName = "Verdant Organic Grocers",
            owner = "Miriam O'Connor",
            category = "Fresh Groceries",
            address = "88 Farmer Market Way",
            district = "West Harbor",
            block = "Pier-7",
            city = "Metropolis",
            contact = "+1 (555) 345-6712",
            assignedRepresentative = "Marcus Vance",
            registrationDate = "2024-03-01",
            status = "Active",
            verificationStatus = "Verified",
            eshopStatus = "Live",
            sellerId = "WM-SLR-1002"
        ),
        ShopEntity(
            shopId = "WM-SHP-203",
            shopName = "Atelier Urban Fashion",
            owner = "Sora Takahashi",
            category = "Fashion",
            address = "77 Boulevard Saint-Honoré",
            district = "Central Metro",
            block = "Avenue District",
            city = "Metropolis",
            contact = "+1 (555) 901-2345",
            assignedRepresentative = "Marcus Vance",
            registrationDate = "2024-03-18",
            status = "Active",
            verificationStatus = "Verified",
            eshopStatus = "Live",
            sellerId = "WM-SLR-1003"
        ),
        ShopEntity(
            shopId = "WM-SHP-204",
            shopName = "Nordic Living Essentials",
            owner = "Astrid Lindgren",
            category = "Home & Living",
            address = "310 Design District",
            district = "East Valley",
            block = "Sector-B",
            city = "Metropolis",
            contact = "+1 (555) 789-0123",
            assignedRepresentative = "Amina Kente",
            registrationDate = "2024-04-05",
            status = "Active",
            verificationStatus = "Verified",
            eshopStatus = "Live",
            sellerId = "WM-SLR-1004"
        ),
        ShopEntity(
            shopId = "WM-SHP-205",
            shopName = "Vitality Botanical Wellness",
            owner = "Dr. Maya Patel",
            category = "Health & Wellness",
            address = "55 Health Park Lane",
            district = "North Park",
            block = "Civic Center",
            city = "Metropolis",
            contact = "+1 (555) 456-7890",
            assignedRepresentative = "Amina Kente",
            registrationDate = "2024-05-12",
            status = "Active",
            verificationStatus = "Verified",
            eshopStatus = "Live",
            sellerId = "WM-SLR-1005"
        ),
        ShopEntity(
            shopId = "WM-SHP-206",
            shopName = "GearHead Performance Auto",
            owner = "Dmitri Volkov",
            category = "Automotive",
            address = "920 Industrial Parkway",
            district = "South Tech Hub",
            block = "Logistics Zone",
            city = "Metropolis",
            contact = "+1 (555) 678-9012",
            assignedRepresentative = "Marcus Vance",
            registrationDate = "2024-06-20",
            status = "Pending",
            verificationStatus = "Pending",
            eshopStatus = "In Setup",
            sellerId = "WM-SLR-1006"
        )
    )

    val sellerList = listOf(
        SellerEntity(
            sellerId = "WM-SLR-1001",
            businessName = "Apex Digital & Mobile Hub",
            businessCategory = "Electronics",
            ownerName = "Harrison Croft",
            location = "142 Grand Avenue, Suite 3B",
            district = "Central Metro",
            contactPhone = "+1 (555) 234-8901",
            contactEmail = "support@apexdigital.shop",
            registrationDate = "2024-02-10",
            assignedRepresentative = "Marcus Vance",
            sellerStatus = "Active",
            verificationStatus = "Verified",
            eshopStatus = "Live",
            workflowStep = "Active Seller",
            productCount = 142,
            storeRating = 4.9f,
            monthlyGmv = 38500.0,
            storefrontDescription = "Premier retail dealer for flagship smartphones, noise-canceling audio, and smart home automation.",
            shopId = "WM-SHP-201"
        ),
        SellerEntity(
            sellerId = "WM-SLR-1002",
            businessName = "Verdant Organic Grocers",
            businessCategory = "Fresh Groceries",
            ownerName = "Miriam O'Connor",
            location = "88 Farmer Market Way",
            district = "West Harbor",
            contactPhone = "+1 (555) 345-6712",
            contactEmail = "orders@verdantgrocers.com",
            registrationDate = "2024-03-01",
            assignedRepresentative = "Marcus Vance",
            sellerStatus = "Active",
            verificationStatus = "Verified",
            eshopStatus = "Live",
            workflowStep = "Active Seller",
            productCount = 285,
            storeRating = 4.8f,
            monthlyGmv = 24100.0,
            storefrontDescription = "Farm-to-table seasonal produce, organic dairy, artisanal sourdough, and cold-pressed olive oils.",
            shopId = "WM-SHP-202"
        ),
        SellerEntity(
            sellerId = "WM-SLR-1003",
            businessName = "Atelier Urban Fashion",
            businessCategory = "Fashion",
            ownerName = "Sora Takahashi",
            location = "77 Boulevard Saint-Honoré",
            district = "Central Metro",
            contactPhone = "+1 (555) 901-2345",
            contactEmail = "concierge@atelierfashion.co",
            registrationDate = "2024-03-18",
            assignedRepresentative = "Marcus Vance",
            sellerStatus = "Active",
            verificationStatus = "Verified",
            eshopStatus = "Live",
            workflowStep = "Active Seller",
            productCount = 94,
            storeRating = 4.7f,
            monthlyGmv = 19800.0,
            storefrontDescription = "Contemporary sustainable fashion, tailored outerwear, and minimalist everyday accessories.",
            shopId = "WM-SHP-203"
        ),
        SellerEntity(
            sellerId = "WM-SLR-1004",
            businessName = "Nordic Living Essentials",
            businessCategory = "Home & Living",
            ownerName = "Astrid Lindgren",
            location = "310 Design District",
            district = "East Valley",
            contactPhone = "+1 (555) 789-0123",
            contactEmail = "contact@nordicliving.store",
            registrationDate = "2024-04-05",
            assignedRepresentative = "Amina Kente",
            sellerStatus = "Active",
            verificationStatus = "Verified",
            eshopStatus = "Live",
            workflowStep = "Active Seller",
            productCount = 67,
            storeRating = 4.9f,
            monthlyGmv = 31200.0,
            storefrontDescription = "Scandinavian handcrafted ceramics, ergonomic oak furniture, and ambient smart lighting.",
            shopId = "WM-SHP-204"
        ),
        SellerEntity(
            sellerId = "WM-SLR-1005",
            businessName = "Vitality Botanical Wellness",
            businessCategory = "Health & Wellness",
            ownerName = "Dr. Maya Patel",
            location = "55 Health Park Lane",
            district = "North Park",
            contactPhone = "+1 (555) 456-7890",
            contactEmail = "care@vitalitywellness.org",
            registrationDate = "2024-05-12",
            assignedRepresentative = "Amina Kente",
            sellerStatus = "Active",
            verificationStatus = "Verified",
            eshopStatus = "Live",
            workflowStep = "Active Seller",
            productCount = 53,
            storeRating = 4.9f,
            monthlyGmv = 16400.0,
            storefrontDescription = "Holistic herbal remedies, clean vitamins, adaptogens, and eco-certified skincare lines.",
            shopId = "WM-SHP-205"
        ),
        SellerEntity(
            sellerId = "WM-SLR-1006",
            businessName = "GearHead Performance Auto",
            businessCategory = "Automotive",
            ownerName = "Dmitri Volkov",
            location = "920 Industrial Parkway",
            district = "South Tech Hub",
            contactPhone = "+1 (555) 678-9012",
            contactEmail = "parts@gearheadauto.net",
            registrationDate = "2024-06-20",
            assignedRepresentative = "Marcus Vance",
            sellerStatus = "Active",
            verificationStatus = "Pending Documents",
            eshopStatus = "In Setup",
            workflowStep = "Documents Submitted",
            productCount = 18,
            storeRating = 4.5f,
            monthlyGmv = 5200.0,
            storefrontDescription = "Automotive precision tools, EV charging accessories, and performance detailing kits.",
            shopId = "WM-SHP-206"
        )
    )

    val productList = listOf(
        ProductEntity(
            productId = "WM-PRD-8001",
            sellerId = "WM-SLR-1001",
            shopId = "WM-SHP-201",
            productName = "Aura Pro Wireless ANC Headphones",
            category = "Electronics",
            subcategory = "Audio",
            brand = "Aura Acoustics",
            sku = "SKU-AUR-8001",
            photoCode = "headphone",
            description = "Studio-grade active noise cancellation with 40-hour battery life and spatial audio support.",
            price = 249.99,
            stock = 38,
            availability = "In Stock",
            status = "Active",
            createdDate = "2026-08-15",
            updatedDate = "2026-10-01",
            rating = 4.9f
        ),
        ProductEntity(
            productId = "WM-PRD-8002",
            sellerId = "WM-SLR-1001",
            shopId = "WM-SHP-201",
            productName = "HyperCharge 100W GaN Travel Adapter",
            category = "Electronics",
            subcategory = "Accessories",
            brand = "HyperCharge Tech",
            sku = "SKU-HYP-8002",
            photoCode = "charger",
            description = "Ultra-compact 4-port high-speed charger compatible with laptops, tablets, and smartphones.",
            price = 59.50,
            stock = 110,
            availability = "In Stock",
            status = "Active",
            createdDate = "2026-08-20",
            updatedDate = "2026-10-01",
            rating = 4.8f
        ),
        ProductEntity(
            productId = "WM-PRD-8003",
            sellerId = "WM-SLR-1002",
            shopId = "WM-SHP-202",
            productName = "Artisanal Organic Honeycomb Jar (500g)",
            category = "Fresh Groceries",
            subcategory = "Pantry",
            brand = "Verdant Harvest",
            sku = "SKU-VRD-8003",
            photoCode = "honey",
            description = "Raw, unpasteurized wild flower honey harvested sustainably from regional mountain apiaries.",
            price = 18.00,
            stock = 45,
            availability = "In Stock",
            status = "Active",
            createdDate = "2026-08-25",
            updatedDate = "2026-10-01",
            rating = 5.0f
        ),
        ProductEntity(
            productId = "WM-PRD-8004",
            sellerId = "WM-SLR-1002",
            shopId = "WM-SHP-202",
            productName = "Crisp Heritage Apple Box (3kg)",
            category = "Fresh Groceries",
            subcategory = "Fruits",
            brand = "Verdant Harvest",
            sku = "SKU-VRD-8004",
            photoCode = "apple",
            description = "Hand-picked organic Honeycrisp apples delivered within 4 hours of morning harvest.",
            price = 14.50,
            stock = 80,
            availability = "In Stock",
            status = "Active",
            createdDate = "2026-09-01",
            updatedDate = "2026-10-01",
            rating = 4.7f
        ),
        ProductEntity(
            productId = "WM-PRD-8005",
            sellerId = "WM-SLR-1003",
            shopId = "WM-SHP-203",
            productName = "Merino Wool Relaxed Overshirt",
            category = "Fashion",
            subcategory = "Menswear",
            brand = "Atelier Collection",
            sku = "SKU-ATL-8005",
            photoCode = "shirt",
            description = "100% fine extra-merino wool with thermal regulation and water-resistant nano coating.",
            price = 135.00,
            stock = 22,
            availability = "In Stock",
            status = "Active",
            createdDate = "2026-09-05",
            updatedDate = "2026-10-01",
            rating = 4.9f
        ),
        ProductEntity(
            productId = "WM-PRD-8006",
            sellerId = "WM-SLR-1004",
            shopId = "WM-SHP-204",
            productName = "Nordic Matte Ceramic Pour-Over Set",
            category = "Home & Living",
            subcategory = "Kitchenware",
            brand = "Nordic Form",
            sku = "SKU-NOR-8006",
            photoCode = "coffee",
            description = "Hand-thrown stoneware dripper with double-walled carafe for barista-grade coffee brewing.",
            price = 48.00,
            stock = 19,
            availability = "In Stock",
            status = "Active",
            createdDate = "2026-09-10",
            updatedDate = "2026-10-01",
            rating = 4.9f
        ),
        ProductEntity(
            productId = "WM-PRD-8007",
            sellerId = "WM-SLR-1005",
            shopId = "WM-SHP-205",
            productName = "Adaptogenic Ashwagandha & Reishi Elixir",
            category = "Health & Wellness",
            subcategory = "Supplements",
            brand = "Vitality Botanics",
            sku = "SKU-VIT-8007",
            photoCode = "elixir",
            description = "Clinical strength certified organic herbal tincture to support calm focus and deep restorative sleep.",
            price = 32.00,
            stock = 64,
            availability = "In Stock",
            status = "Active",
            createdDate = "2026-09-15",
            updatedDate = "2026-10-01",
            rating = 4.8f
        )
    )

    // Pre-hashed accounts (password: password123)
    val defaultPasswordHash = SecurityUtils.hashPassword("password123")

    val userAccountsList = listOf(
        UserAccountEntity(
            userId = "USER-001",
            username = "ceo.admin",
            passwordHash = defaultPasswordHash,
            email = "ceo@worldmart.com",
            fullName = "Julian Vance",
            roleName = "FOUNDER_CEO",
            phone = "+1 (555) 100-0001",
            linkedEmployeeId = "WM-EMP-101"
        ),
        UserAccountEntity(
            userId = "USER-002",
            username = "super.admin",
            passwordHash = defaultPasswordHash,
            email = "superadmin@worldmart.com",
            fullName = "Super Administrator",
            roleName = "SUPER_ADMIN",
            phone = "+1 (555) 100-0002"
        ),
        UserAccountEntity(
            userId = "USER-003",
            username = "tech.admin",
            passwordHash = defaultPasswordHash,
            email = "tech@worldmart.com",
            fullName = "Elena Rostova",
            roleName = "TECHNOLOGY",
            phone = "+1 (555) 100-0003",
            linkedEmployeeId = "WM-EMP-102"
        ),
        UserAccountEntity(
            userId = "USER-004",
            username = "finance.lead",
            passwordHash = defaultPasswordHash,
            email = "finance@worldmart.com",
            fullName = "Dr. Tariq Al-Mansoor",
            roleName = "FINANCE",
            phone = "+1 (555) 100-0004",
            linkedEmployeeId = "WM-EMP-103"
        ),
        UserAccountEntity(
            userId = "USER-005",
            username = "ops.manager",
            passwordHash = defaultPasswordHash,
            email = "operations@worldmart.com",
            fullName = "Camila Duarte",
            roleName = "OPERATIONS",
            phone = "+1 (555) 100-0005",
            linkedEmployeeId = "WM-EMP-104"
        ),
        UserAccountEntity(
            userId = "USER-006",
            username = "marketing.lead",
            passwordHash = defaultPasswordHash,
            email = "marketing@worldmart.com",
            fullName = "Siddharth Rao",
            roleName = "MARKETING",
            phone = "+1 (555) 100-0006",
            linkedEmployeeId = "WM-EMP-105"
        ),
        UserAccountEntity(
            userId = "USER-007",
            username = "bizdev.lead",
            passwordHash = defaultPasswordHash,
            email = "bizdev@worldmart.com",
            fullName = "Amina Kente",
            roleName = "BUSINESS_DEVELOPMENT",
            phone = "+1 (555) 100-0007",
            linkedEmployeeId = "WM-EMP-106"
        ),
        UserAccountEntity(
            userId = "USER-008",
            username = "support.lead",
            passwordHash = defaultPasswordHash,
            email = "support@worldmart.com",
            fullName = "Rachel Chen",
            roleName = "CUSTOMER_SUPPORT",
            phone = "+1 (555) 100-0008",
            linkedEmployeeId = "WM-EMP-107"
        ),
        UserAccountEntity(
            userId = "USER-009",
            username = "rep.marcus",
            passwordHash = defaultPasswordHash,
            email = "m.vance@worldmart.com",
            fullName = "Marcus Vance",
            roleName = "SALES_REP",
            phone = "+1 (555) 100-0009",
            linkedEmployeeId = "WM-EMP-108"
        ),
        UserAccountEntity(
            userId = "USER-010",
            username = "seller.apex",
            passwordHash = defaultPasswordHash,
            email = "seller@apexdigital.shop",
            fullName = "Harrison Croft",
            roleName = "SELLER",
            phone = "+1 (555) 234-8901",
            linkedSellerId = "WM-SLR-1001",
            linkedShopId = "WM-SHP-201"
        ),
        UserAccountEntity(
            userId = "USER-011",
            username = "customer.alex",
            passwordHash = defaultPasswordHash,
            email = "alex.shopper@gmail.com",
            fullName = "Alex Mercer",
            roleName = "CUSTOMER",
            phone = "+1 (555) 987-6543"
        )
    )

    val dailyReportsList = listOf(
        DailyReportEntity(
            repId = "WM-EMP-108",
            repName = "Marcus Vance",
            date = "2026-10-01",
            district = "Central Metro",
            area = "Grand Avenue Corridor",
            shopsPlanned = 6,
            shopsVisited = 5,
            newLeads = 3,
            interestedShops = 2,
            newSellers = 1,
            documentsCollected = 2,
            productsCollected = 14,
            photosCollected = 28,
            followUps = "Follow up with Vintage Vinyl Lounge regarding municipal permit renewal.",
            problems = "Slight delay in QR barcode scanning for small item packages.",
            tomorrowPlan = "Inspect 4 storefronts along Pier-7 West Harbor and audit cold-chain containers.",
            notes = "Merchants very responsive to consolidated 2-hour delivery program.",
            reviewStatus = "Reviewed",
            managerReviewer = "Camila Duarte (VP Operations)",
            managerFeedback = "Excellent field momentum. Keep targeting specialty electronics."
        )
    )

    val shopVisitsList = listOf(
        ShopVisitEntity(
            shopId = "WM-SHP-201",
            sellerId = "WM-SLR-1001",
            shopName = "Apex Digital & Mobile Hub",
            repName = "Marcus Vance",
            date = "2026-10-01",
            time = "10:30 AM",
            purpose = "Catalog Photo Session",
            outcome = "Uploaded 12 high-res SKU photos and updated ANC headphone stock count.",
            notes = "Store owner was satisfied with fast catalog sync."
        )
    )

    val ordersList = listOf(
        OrderEntity(
            orderId = "WM-ORD-9021",
            customerId = "USER-011",
            customerName = "Alex Mercer",
            customerEmail = "alex.shopper@gmail.com",
            customerPhone = "+1 (555) 987-6543",
            shippingAddress = "45 Willow Street, Apt 4B",
            district = "Central Metro",
            totalAmount = 309.49,
            paymentMethod = "WorldMart Pay Escrow",
            paymentStatus = "Escrow Secured",
            orderStatus = "In Transit",
            orderDate = "2026-10-01",
            estimatedDelivery = "Within 45 Minutes",
            itemsSummary = "1x Aura Pro ANC Headphones ($249.99), 1x 100W GaN Travel Adapter ($59.50)"
        )
    )

    val announcementList = listOf(
        AnnouncementEntity(
            title = "World Mart Surpasses 500 Verified Local Merchants in Metro Corridor",
            category = "Milestone",
            date = "Oct 01, 2026",
            summary = "World Mart's hyper-local marketplace network celebrated a landmark milestone today, now hosting over 500 verified independent businesses across five metropolitan districts.",
            content = "Through our proprietary district logistics routing and instant digital e-shop deployment, regional merchants experienced an average 42% revenue increase within their first 60 days of onboarding. Founder & CEO Julian Vance commented: 'Our mission has always been connecting neighborhood craftsmanship with modern digital convenience.'",
            isPublic = true,
            authorRole = "Executive Communications"
        ),
        AnnouncementEntity(
            title = "Launch of World Mart Express: 2-Hour District Delivery Fleet",
            category = "Platform Update",
            date = "Sep 22, 2026",
            summary = "Customers across Central Metro and West Harbor can now bundle purchases from multiple local sellers into a single unified checkout with 2-hour consolidated delivery.",
            content = "Our engineering and operations teams rolled out the World Mart Consolidated Cart, allowing shoppers to buy fresh bread from the bakery, electronics from the gadget hub, and vitamins from the pharmacy in one simple transaction.",
            isPublic = true,
            authorRole = "Operations Leadership"
        ),
        AnnouncementEntity(
            title = "Quarterly Merchant Grant Fund: $250,000 Allocated for Storefront Digitization",
            category = "Expansion",
            date = "Sep 10, 2026",
            summary = "World Mart partners with municipal economic development funds to provide hardware POS terminals and photography grants to small family shops.",
            content = "Selected merchants will receive free high-speed barcode scanners, tablet point-of-sale stations, and complimentary product photography sessions to build high-converting online e-shops.",
            isPublic = true,
            authorRole = "Business Development"
        )
    )

    val taskList = listOf(
        TaskEntity(
            title = "Verify GearHead Auto trade license & tax certificate",
            description = "Review submitted commercial registration docs and confirm warehouse inspection for hazardous fluids safety.",
            assignedDivision = "Operations",
            priority = "Urgent",
            dueDate = "Oct 03, 2026",
            isCompleted = false,
            assignedBy = "Camila Duarte"
        ),
        TaskEntity(
            title = "Deploy v2.4 Microservice for Consolidated Shopping Cart",
            description = "Optimize database indexes on multi-vendor order line items and test edge caching for instant cart recalculations.",
            assignedDivision = "Technology",
            priority = "High",
            dueDate = "Oct 05, 2026",
            isCompleted = false,
            assignedBy = "Elena Rostova"
        ),
        TaskEntity(
            title = "Audit Q3 Merchant Escrow Payout Balances",
            description = "Reconcile payment gateway settlement ledgers against seller bank transfers across all 5 operational districts.",
            assignedDivision = "Finance",
            priority = "High",
            dueDate = "Oct 07, 2026",
            isCompleted = false,
            assignedBy = "Dr. Tariq Al-Mansoor"
        ),
        TaskEntity(
            title = "Launch Autumn Artisan Food & Wine Spotlight Campaign",
            description = "Publish featured seller video banners on homepage and send push updates for local cheese and honey vendors.",
            assignedDivision = "Marketing",
            priority = "Normal",
            dueDate = "Oct 10, 2026",
            isCompleted = true,
            assignedBy = "Siddharth Rao"
        )
    )

    val contentBlocks = listOf(
        ContentBlockEntity(
            key = "hero_headline",
            title = "Main Hero Headline",
            subtitle = "Displayed at top of Home Screen",
            body = "Building a Connected Marketplace for Businesses and Customers."
        ),
        ContentBlockEntity(
            key = "hero_subtext",
            title = "Hero Supporting Tagline",
            subtitle = "Explaining World Mart's core vision",
            body = "World Mart empowers local businesses and sellers with enterprise-grade e-commerce tools, hyper-local logistics, and unified multi-vendor storefronts."
        ),
        ContentBlockEntity(
            key = "about_mission",
            title = "Corporate Mission Statement",
            subtitle = "Core Company Philosophy",
            body = "To decentralize commerce by giving independent regional sellers the same technology, logistics, and capital power as multinational retail giants while preserving community character."
        ),
        ContentBlockEntity(
            key = "about_vision",
            title = "Long-Term 2030 Vision",
            subtitle = "Strategic Target",
            body = "A seamless national network of 50+ connected metropolitan districts where any customer can receive authentic local goods within hours, and any merchant can sell digitally with zero technical friction."
        )
    )

    val jobList = listOf(
        JobEntity(
            jobId = "WM-JOB-01",
            title = "Senior Distributed Backend Engineer (Kotlin / Go)",
            division = "Technology",
            location = "Metropolis HQ / Hybrid",
            employmentType = "Full-Time",
            experienceLevel = "5+ Years",
            overview = "Design high-concurrency order routing microservices and multi-tenant inventory syncing across 50,000+ local SKUs.",
            requirements = "• Strong background in Kotlin/Go, distributed databases (PostgreSQL, Redis), gRPC, and Kafka.\n• Experience with transaction rollbacks and event-driven architecture."
        ),
        JobEntity(
            jobId = "WM-JOB-02",
            title = "District Merchant Success Lead",
            division = "Operations",
            location = "West Harbor District Office",
            employmentType = "Full-Time On-Site",
            experienceLevel = "3+ Years",
            overview = "Lead the physical onboarding, training, and operational success of 100+ local food and apparel vendors.",
            requirements = "• Proven track record in retail operations, merchant relationship management, or local field sales.\n• Passion for small businesses and regional economic growth."
        ),
        JobEntity(
            jobId = "WM-JOB-03",
            title = "Product Marketing Manager (Seller Growth)",
            division = "Marketing",
            location = "Metropolis HQ / Remote Optional",
            employmentType = "Full-Time",
            experienceLevel = "4+ Years",
            overview = "Drive merchant acquisition campaigns, create case studies, and organize community merchant masterclasses.",
            requirements = "• Experience in B2B SaaS or marketplace growth marketing.\n• Excellent storytelling and cross-channel campaign orchestration."
        ),
        JobEntity(
            jobId = "WM-JOB-04",
            title = "Financial Operations Analyst (Payouts & Escrow)",
            division = "Finance",
            location = "Metropolis HQ",
            employmentType = "Full-Time",
            experienceLevel = "2+ Years",
            overview = "Monitor daily seller settlement reconciliations, fee calculations, and automated payout execution.",
            requirements = "• Bachelor's degree in Finance, Accounting, or Economics.\n• Expertise in advanced financial modeling and payment gateway operations."
        )
    )

    val financialRecords = listOf(
        FinancialRecordEntity(
            transactionRef = "TX-2026-9041",
            title = "District Escrow Settlement Batch #38",
            category = "Payout to Sellers",
            amount = 142850.00,
            type = "Payout",
            date = "Oct 01, 2026",
            district = "Central Metro"
        ),
        FinancialRecordEntity(
            transactionRef = "TX-2026-9040",
            title = "Platform Technology Service Fees (Sep)",
            category = "Platform Commission",
            amount = 38400.00,
            type = "Income",
            date = "Sep 30, 2026",
            district = "Metropolis All"
        ),
        FinancialRecordEntity(
            transactionRef = "TX-2026-9039",
            title = "Consolidated District Logistics Fleet Recovery",
            category = "Logistics Fee",
            amount = 19200.00,
            type = "Income",
            date = "Sep 29, 2026",
            district = "West Harbor & East Valley"
        )
    )

    val servicePricingsList = listOf(
        ServicePricingEntity(
            serviceId = "SVC-ONBOARD",
            serviceName = "Merchant Onboarding & KYC Verification",
            category = "Onboarding",
            basePrice = 1500.0,
            district = "All Districts",
            description = "Physical address verification, commercial document review, merchant registration certificate."
        ),
        ServicePricingEntity(
            serviceId = "SVC-ESHOP-SETUP",
            serviceName = "Digital E-Shop Setup & Storefront Customization",
            category = "Digital Setup",
            basePrice = 2500.0,
            district = "All Districts",
            description = "Customized branded storefront, opening hours, banner artwork, SEO tags, district indexing."
        ),
        ServicePricingEntity(
            serviceId = "SVC-CATALOG-50",
            serviceName = "Product Cataloging & Photography (50 SKUs)",
            category = "Cataloging",
            basePrice = 1800.0,
            district = "Central Metro",
            description = "Professional in-store photography, standardized White-background edits, SKU descriptions."
        ),
        ServicePricingEntity(
            serviceId = "SVC-FAST-VERIFY",
            serviceName = "Express 24-Hour Expedited Inspection",
            category = "Verification",
            basePrice = 800.0,
            district = "All Districts",
            description = "Dedicated priority field representative dispatched for instant premises inspection."
        ),
        ServicePricingEntity(
            serviceId = "SVC-ANNUAL-TECH",
            serviceName = "Annual Marketplace Cloud & Order Maintenance",
            category = "Maintenance",
            basePrice = 3600.0,
            district = "All Districts",
            description = "Annual SLA, real-time inventory sync, automated weekly payout settlement, merchant support."
        )
    )

    val sellerDocumentsList = listOf(
        SellerDocumentEntity(
            documentId = "DOC-WM-1001",
            sellerId = "WM-SLR-1001",
            documentType = "Commercial Trade License",
            filePath = "/storage/emulated/0/WorldMart/docs/apex_trade_license.pdf",
            uploadedBy = "Marcus Vance (Sales Rep)",
            uploadDate = "2026-09-12",
            verificationStatus = "VERIFIED",
            verifiedBy = "Elena Rostov (Operations)",
            verificationDate = "2026-09-14",
            rejectionReason = ""
        ),
        SellerDocumentEntity(
            documentId = "DOC-WM-1002",
            sellerId = "WM-SLR-1001",
            documentType = "GST / Business Tax ID",
            filePath = "/storage/emulated/0/WorldMart/docs/apex_tax_cert.pdf",
            uploadedBy = "Marcus Vance (Sales Rep)",
            uploadDate = "2026-09-12",
            verificationStatus = "VERIFIED",
            verifiedBy = "Harrison Croft (Finance)",
            verificationDate = "2026-09-15",
            rejectionReason = ""
        ),
        SellerDocumentEntity(
            documentId = "DOC-WM-1003",
            sellerId = "WM-SLR-1002",
            documentType = "Food Safety & Quality Certificate",
            filePath = "/storage/emulated/0/WorldMart/docs/greenvalley_food_safety.pdf",
            uploadedBy = "Marcus Vance (Sales Rep)",
            uploadDate = "2026-09-20",
            verificationStatus = "VERIFIED",
            verifiedBy = "Elena Rostov (Operations)",
            verificationDate = "2026-09-22",
            rejectionReason = ""
        ),
        SellerDocumentEntity(
            documentId = "DOC-WM-1004",
            sellerId = "WM-SLR-1003",
            documentType = "Commercial Lease Agreement",
            filePath = "/storage/emulated/0/WorldMart/docs/metro_hardware_lease.pdf",
            uploadedBy = "Marcus Vance (Sales Rep)",
            uploadDate = "2026-09-28",
            verificationStatus = "UNDER REVIEW",
            verifiedBy = "",
            verificationDate = "",
            rejectionReason = ""
        ),
        SellerDocumentEntity(
            documentId = "DOC-WM-1005",
            sellerId = "WM-SLR-1004",
            documentType = "Proprietor Government National ID",
            filePath = "/storage/emulated/0/WorldMart/docs/artisan_leather_nid.jpg",
            uploadedBy = "Marcus Vance (Sales Rep)",
            uploadDate = "2026-10-01",
            verificationStatus = "PENDING",
            verifiedBy = "",
            verificationDate = "",
            rejectionReason = ""
        )
    )

    val sellerPaymentsList = listOf(
        SellerPaymentEntity(
            paymentId = "PAY-WM-5001",
            sellerId = "WM-SLR-1001",
            shopId = "WM-SHP-201",
            service = "Merchant Onboarding & KYC Verification",
            amount = 1500.0,
            date = "2026-09-12",
            paymentMethod = "UPI",
            transactionRefNumber = "UPI-REF-90218491",
            paymentStatus = "CONFIRMED",
            receiptPath = "/storage/emulated/0/WorldMart/receipts/rec_5001.pdf",
            collectedBy = "Marcus Vance (Sales Rep)",
            verifiedBy = "Harrison Croft (Finance)",
            notes = "Onboarding fee paid in full upon premises inspection."
        ),
        SellerPaymentEntity(
            paymentId = "PAY-WM-5002",
            sellerId = "WM-SLR-1001",
            shopId = "WM-SHP-201",
            service = "Digital E-Shop Setup & Storefront Customization",
            amount = 2500.0,
            date = "2026-09-15",
            paymentMethod = "Bank Transfer",
            transactionRefNumber = "NEFT-WM-4819204",
            paymentStatus = "CONFIRMED",
            receiptPath = "/storage/emulated/0/WorldMart/receipts/rec_5002.pdf",
            collectedBy = "Marcus Vance (Sales Rep)",
            verifiedBy = "Harrison Croft (Finance)",
            notes = "Storefront customized and published live."
        ),
        SellerPaymentEntity(
            paymentId = "PAY-WM-5003",
            sellerId = "WM-SLR-1002",
            shopId = "WM-SHP-202",
            service = "Merchant Onboarding & KYC Verification",
            amount = 1500.0,
            date = "2026-09-20",
            paymentMethod = "Cash",
            transactionRefNumber = "CASH-REC-00381",
            paymentStatus = "CONFIRMED",
            receiptPath = "/storage/emulated/0/WorldMart/receipts/rec_5003.pdf",
            collectedBy = "Marcus Vance (Sales Rep)",
            verifiedBy = "Harrison Croft (Finance)",
            notes = "Physical receipt issued to store owner."
        ),
        SellerPaymentEntity(
            paymentId = "PAY-WM-5004",
            sellerId = "WM-SLR-1003",
            shopId = "WM-SHP-203",
            service = "Product Cataloging & Photography (50 SKUs)",
            amount = 1800.0,
            date = "2026-09-29",
            paymentMethod = "UPI",
            transactionRefNumber = "UPI-REF-99214418",
            paymentStatus = "UNDER REVIEW",
            receiptPath = "/storage/emulated/0/WorldMart/receipts/rec_5004.pdf",
            collectedBy = "Marcus Vance (Sales Rep)",
            verifiedBy = "",
            notes = "Awaiting bank statement confirmation from Finance ledger."
        ),
        SellerPaymentEntity(
            paymentId = "PAY-WM-5005",
            sellerId = "WM-SLR-1004",
            shopId = "WM-SHP-204",
            service = "Merchant Onboarding & KYC Verification",
            amount = 1500.0,
            date = "2026-10-01",
            paymentMethod = "UPI",
            transactionRefNumber = "UPI-REF-1094812",
            paymentStatus = "SUBMITTED",
            receiptPath = "",
            collectedBy = "Marcus Vance (Sales Rep)",
            verifiedBy = "",
            notes = "Submitted with initial lead registration."
        )
    )

    val followUpsList = listOf(
        FollowUpEntity(
            sellerId = "WM-SLR-1003",
            shopId = "WM-SHP-203",
            employeeId = "WM-EMP-107",
            employeeName = "Marcus Vance",
            purpose = "Verify Updated Commercial Lease & Trade License",
            dueDate = "2026-10-03",
            priority = "High",
            status = "PENDING",
            notes = "Owner requested rep visit at 11:30 AM to collect signed paper lease copy."
        ),
        FollowUpEntity(
            sellerId = "WM-SLR-1004",
            shopId = "WM-SHP-204",
            employeeId = "WM-EMP-107",
            employeeName = "Marcus Vance",
            purpose = "Collect Balance Catalog Photos & Tax ID Copy",
            dueDate = "2026-10-04",
            priority = "Normal",
            status = "PENDING",
            notes = "Owner ready with 15 new handmade leather bag models for photo session."
        ),
        FollowUpEntity(
            sellerId = "WM-SLR-1001",
            shopId = "WM-SHP-201",
            employeeId = "WM-EMP-107",
            employeeName = "Marcus Vance",
            purpose = "Review Q4 Inventory Expansion & Top Sellers",
            dueDate = "2026-09-25",
            priority = "Normal",
            status = "COMPLETED",
            notes = "Added 12 new laptop accessories and gaming peripherals to live catalogue."
        )
    )

    val approvalRecordsList = listOf(
        ApprovalRecordEntity(
            entityType = "SELLER",
            entityId = "WM-SLR-1001",
            entityTitle = "Apex Digital Retailers",
            action = "APPROVE",
            previousStatus = "Under Review",
            newStatus = "Verified",
            userRole = "Operations Manager",
            userName = "Elena Rostov",
            timestamp = "2026-09-14 14:30",
            comment = "Trade license verified with municipal registrar. Approved for live trading."
        ),
        ApprovalRecordEntity(
            entityType = "PAYMENT",
            entityId = "PAY-WM-5001",
            entityTitle = "Apex Digital - Onboarding Fee",
            action = "APPROVE",
            previousStatus = "Under Review",
            newStatus = "CONFIRMED",
            userRole = "Finance Manager",
            userName = "Harrison Croft",
            timestamp = "2026-09-13 10:15",
            comment = "₹1,500 credited to World Mart corporate bank account."
        ),
        ApprovalRecordEntity(
            entityType = "DOCUMENT",
            entityId = "DOC-WM-1002",
            entityTitle = "Apex Digital - GST Certificate",
            action = "APPROVE",
            previousStatus = "PENDING",
            newStatus = "VERIFIED",
            userRole = "Finance Manager",
            userName = "Harrison Croft",
            timestamp = "2026-09-15 16:00",
            comment = "Active GST status confirmed on tax authority portal."
        )
    )

    val auditLogsList = listOf(
        AuditLogEntity(
            user = "Julian Vance (CEO)",
            userRole = "Founder/CEO",
            action = "Enterprise System Initialized",
            recordType = "System",
            recordId = "SYS-WM-001",
            timestamp = "2026-10-01 08:00",
            changeDetails = "Internal operations engine activated across 7 central divisions and 4 districts."
        ),
        AuditLogEntity(
            user = "Marcus Vance",
            userRole = "Sales Representative",
            action = "New Merchant Registered",
            recordType = "Seller",
            recordId = "WM-SLR-1004",
            timestamp = "2026-10-01 09:45",
            changeDetails = "Artisan Leather Crafts registered in West Harbor district with 1 shop record."
        ),
        AuditLogEntity(
            user = "Elena Rostov",
            userRole = "Operations Manager",
            action = "Seller Status Verified",
            recordType = "Seller",
            recordId = "WM-SLR-1001",
            timestamp = "2026-09-14 14:30",
            changeDetails = "Apex Digital Retailers marked as Verified and live storefront enabled."
        ),
        AuditLogEntity(
            user = "Harrison Croft",
            userRole = "Finance Manager",
            action = "Payment Confirmed",
            recordType = "Payment",
            recordId = "PAY-WM-5001",
            timestamp = "2026-09-13 10:15",
            changeDetails = "Onboarding fee ₹1,500 confirmed via UPI-REF-90218491."
        )
    )

    val notificationsList = listOf(
        AppNotificationEntity(
            title = "New Merchant Registration Pending",
            message = "Artisan Leather Crafts submitted registration in West Harbor. Awaiting document verification.",
            category = "SELLER",
            recipientRole = "OPERATIONS",
            timestamp = "2026-10-01 09:45",
            isRead = false,
            linkedId = "WM-SLR-1004"
        ),
        AppNotificationEntity(
            title = "Payment Verification Required",
            message = "Payment PAY-WM-5004 (₹1,800) for Metro Hardware cataloging awaits Finance confirmation.",
            category = "PAYMENT",
            recipientRole = "FINANCE",
            timestamp = "2026-09-29 16:30",
            isRead = false,
            linkedId = "PAY-WM-5004"
        ),
        AppNotificationEntity(
            title = "Daily Field Report Submitted",
            message = "Marcus Vance submitted daily field activity report for Central Metro (6 shops visited).",
            category = "REPORT",
            recipientRole = "OPERATIONS",
            timestamp = "2026-10-01 17:30",
            isRead = true,
            linkedId = "REP-1"
        ),
        AppNotificationEntity(
            title = "High Priority Follow-Up Due",
            message = "Follow-up due tomorrow: Collect signed commercial lease from Metro Hardware & Tools.",
            category = "TASK",
            recipientRole = "SALES_REP",
            timestamp = "2026-10-02 08:00",
            isRead = false,
            linkedId = "WM-SLR-1003"
        )
    )
}
