package com.example.ui.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import com.example.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class PublicPage(val title: String, val iconName: String) {
    HOME("Home", "home"),
    ABOUT("About World Mart", "business"),
    VISION_MISSION("Vision & Mission", "flag"),
    OUR_TEAM("Our Team", "groups"),
    CENTRAL_DIVISIONS("Central Divisions", "apartment"),
    SERVICES("Services", "category"),
    BECOME_A_SELLER("Become a Seller", "storefront"),
    SELLER_REGISTRATION("Seller Registration", "app_registration"),
    HOW_IT_WORKS("How It Works", "sync_alt"),
    CAREERS("Careers", "work"),
    NEWS_UPDATES("News & Updates", "newspaper"),
    FAQ("FAQ", "quiz"),
    CONTACT("Contact", "contact_mail")
}

enum class AdminTab(val title: String, val icon: String) {
    DASHBOARD("Control Center", "dashboard"),
    STAFF("Employees & HR", "badge"),
    DIVISIONS("Divisions", "apartment"),
    DISTRICTS("District Ops", "map"),
    SHOPS("Shops Database", "store"),
    SELLERS("Seller Management", "storefront"),
    DOCUMENTS("Seller Documents", "file_present"),
    PAYMENTS("Seller Payments", "payments"),
    PRODUCTS("Product Catalog", "inventory_2"),
    PRODUCT_PHOTOS("Product Photos", "add_a_photo"),
    SALES_REP_PORTAL("Sales Rep Workspace", "directions_walk"),
    FOLLOW_UPS("Follow-ups & Visits", "pin_drop"),
    DAILY_REPORTS("Daily Reports", "description"),
    APPROVALS("Approvals Queue", "fact_check"),
    FINANCE("Finance & Pricing", "account_balance"),
    TASKS("Tasks", "task_alt"),
    NOTIFICATIONS("Notifications", "notifications"),
    AUDIT_LOGS("Audit History", "manage_history"),
    CMS("Communications & CMS", "web")
}

class WorldMartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WorldMartRepository

    init {
        val db = WorldMartDatabase.getDatabase(application)
        repository = WorldMartRepository(db.worldMartDao())
        viewModelScope.launch {
            repository.ensureDataInitialized()
            // Auto login default CEO account
            val defaultCeo = InitialData.userAccountsList.firstOrNull { it.roleName == "FOUNDER_CEO" }
            _authenticatedUser.value = defaultCeo
            _currentUserRole.value = UserRole.FOUNDER_CEO
        }
    }

    // Navigation & Portal Mode
    private val _isAdminMode = MutableStateFlow(true) // Operations App defaults to Internal Operations
    val isAdminMode: StateFlow<Boolean> = _isAdminMode.asStateFlow()

    private val _currentPublicPage = MutableStateFlow(PublicPage.HOME)
    val currentPublicPage: StateFlow<PublicPage> = _currentPublicPage.asStateFlow()

    private val _currentAdminTab = MutableStateFlow(AdminTab.DASHBOARD)
    val currentAdminTab: StateFlow<AdminTab> = _currentAdminTab.asStateFlow()

    // Authentication State
    private val _authenticatedUser = MutableStateFlow<UserAccountEntity?>(null)
    val authenticatedUser: StateFlow<UserAccountEntity?> = _authenticatedUser.asStateFlow()

    private val _currentUserRole = MutableStateFlow(UserRole.FOUNDER_CEO)
    val currentUserRole: StateFlow<UserRole> = _currentUserRole.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    // Database reactive streams
    val staffList: StateFlow<List<StaffEntity>> = repository.allStaff
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val shopList: StateFlow<List<ShopEntity>> = repository.allShops
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sellerList: StateFlow<List<SellerEntity>> = repository.allSellers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val productList: StateFlow<List<ProductEntity>> = repository.allProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val productPhotosList: StateFlow<List<ProductPhotoEntity>> = repository.allProductPhotos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dailyReportsList: StateFlow<List<DailyReportEntity>> = repository.allDailyReports
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val shopVisitsList: StateFlow<List<ShopVisitEntity>> = repository.allShopVisits
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val orderList: StateFlow<List<OrderEntity>> = repository.allOrders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userList: StateFlow<List<UserAccountEntity>> = repository.allUsers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val announcements: StateFlow<List<AnnouncementEntity>> = repository.allAnnouncements
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val publicAnnouncements: StateFlow<List<AnnouncementEntity>> = repository.publicAnnouncements
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val taskList: StateFlow<List<TaskEntity>> = repository.allTasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val contentBlocks: StateFlow<List<ContentBlockEntity>> = repository.allContentBlocks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val jobList: StateFlow<List<JobEntity>> = repository.allJobs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val jobApplications: StateFlow<List<JobApplicationEntity>> = repository.allJobApplications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val financialRecords: StateFlow<List<FinancialRecordEntity>> = repository.allFinancials
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val inquiries: StateFlow<List<InquiryEntity>> = repository.allInquiries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Real Operations Streams
    val sellerDocumentsList: StateFlow<List<SellerDocumentEntity>> = repository.allSellerDocuments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sellerPaymentsList: StateFlow<List<SellerPaymentEntity>> = repository.allSellerPayments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val servicePricingsList: StateFlow<List<ServicePricingEntity>> = repository.allServicePricings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val followUpsList: StateFlow<List<FollowUpEntity>> = repository.allFollowUps
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val approvalRecordsList: StateFlow<List<ApprovalRecordEntity>> = repository.allApprovals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val auditLogsList: StateFlow<List<AuditLogEntity>> = repository.allAuditLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notificationsList: StateFlow<List<AppNotificationEntity>> = repository.allNotifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Cart stream
    val cartItems: StateFlow<List<CartItemEntity>> = _authenticatedUser
        .flatMapLatest { user ->
            val customerId = user?.userId ?: "GUEST-USER"
            repository.getCartItems(customerId)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // UI Interactive States
    private val _selectedEmployee = MutableStateFlow<StaffEntity?>(null)
    val selectedEmployee: StateFlow<StaffEntity?> = _selectedEmployee.asStateFlow()

    private val _selectedSeller = MutableStateFlow<SellerEntity?>(null)
    val selectedSeller: StateFlow<SellerEntity?> = _selectedSeller.asStateFlow()

    private val _selectedShop = MutableStateFlow<ShopEntity?>(null)
    val selectedShop: StateFlow<ShopEntity?> = _selectedShop.asStateFlow()

    private val _selectedProduct = MutableStateFlow<ProductEntity?>(null)
    val selectedProduct: StateFlow<ProductEntity?> = _selectedProduct.asStateFlow()

    private val _teamDivisionFilter = MutableStateFlow<String?>("All")
    val teamDivisionFilter: StateFlow<String?> = _teamDivisionFilter.asStateFlow()

    private val _statusNotification = MutableStateFlow<String?>(null)
    val statusNotification: StateFlow<String?> = _statusNotification.asStateFlow()

    // Navigation triggers
    fun switchToAdminMode(admin: Boolean) {
        _isAdminMode.value = admin
    }

    fun navigateToPublicPage(page: PublicPage) {
        _currentPublicPage.value = page
        _isAdminMode.value = false
    }

    fun selectAdminTab(tab: AdminTab) {
        _currentAdminTab.value = tab
    }

    // Role & Auth Switcher
    fun switchUserRole(role: UserRole) {
        viewModelScope.launch {
            _currentUserRole.value = role
            val matchingAccount = userList.value.find { it.roleName == role.name }
                ?: InitialData.userAccountsList.find { it.roleName == role.name }
            _authenticatedUser.value = matchingAccount
            _statusNotification.value = "Active session switched to ${role.title} (${matchingAccount?.username ?: "guest"})"
        }
    }

    fun login(usernameOrEmail: String, rawPassword: String) {
        viewModelScope.launch {
            _authError.value = null
            val user = repository.authenticate(usernameOrEmail.trim(), rawPassword)
            if (user != null) {
                _authenticatedUser.value = user
                val role = try {
                    UserRole.valueOf(user.roleName)
                } catch (e: Exception) {
                    UserRole.OPERATIONS
                }
                _currentUserRole.value = role
                _statusNotification.value = "Welcome back, ${user.fullName} (${role.title})"
            } else {
                _authError.value = "Invalid credentials. (Hint: Try username with password: password123)"
                _statusNotification.value = "Authentication failed: invalid username or password."
            }
        }
    }

    fun register(
        username: String,
        password: String,
        email: String,
        fullName: String,
        role: UserRole,
        phone: String = ""
    ) {
        viewModelScope.launch {
            _authError.value = null
            val result = repository.registerUser(
                username = username,
                rawPassword = password,
                email = email,
                fullName = fullName,
                roleName = role.name,
                phone = phone
            )
            result.onSuccess { user ->
                _authenticatedUser.value = user
                _currentUserRole.value = role
                _statusNotification.value = "Account created successfully for ${user.fullName} (${role.title})."
            }.onFailure { err ->
                _authError.value = err.message
            }
        }
    }

    fun logout() {
        _authenticatedUser.value = null
        _currentUserRole.value = UserRole.GUEST
        _statusNotification.value = "You have been logged out."
    }

    fun clearAuthError() {
        _authError.value = null
    }

    fun clearNotification() {
        _statusNotification.value = null
    }

    fun setSelectedEmployee(staff: StaffEntity?) {
        _selectedEmployee.value = staff
    }

    fun setSelectedSeller(seller: SellerEntity?) {
        _selectedSeller.value = seller
    }

    fun setSelectedShop(shop: ShopEntity?) {
        _selectedShop.value = shop
    }

    fun setTeamDivisionFilter(division: String?) {
        _teamDivisionFilter.value = division
    }

    // --- STAFF MANAGEMENT ---
    fun addNewStaff(
        name: String,
        position: String,
        division: String,
        department: String,
        location: String,
        joiningDate: String,
        status: String,
        bio: String,
        responsibilities: String,
        qualification: String,
        workEmail: String,
        manager: String = "Julian Vance (CEO)",
        accountStatus: String = "Active",
        salaryGrade: String = "Tier-4 Executive",
        bankAccountMasked: String = "•••• 8923",
        nationalIdMasked: String = "ID-•••-4412",
        internalNotes: String = "Clearance Level 2",
        district: String = "Central Metro",
        area: String = "Commercial Hub",
        phone: String = "+1 (555) 234-5678"
    ) {
        viewModelScope.launch {
            val nextId = "WM-EMP-${(100 + (staffList.value.size + 1))}"
            val newStaff = StaffEntity(
                employeeId = nextId,
                fullName = name,
                photoAvatarId = (staffList.value.size % 8) + 1,
                position = position,
                division = division,
                department = department,
                location = location,
                assignedDistrict = district,
                assignedArea = area,
                joiningDate = joiningDate.ifBlank { "2026-10-01" },
                employmentStatus = status,
                professionalBiography = bio,
                responsibilities = responsibilities,
                qualification = qualification,
                officialWorkEmail = workEmail,
                officialPhone = phone,
                manager = manager,
                accountStatus = accountStatus,
                salaryGrade = salaryGrade,
                bankAccountMasked = bankAccountMasked,
                nationalIdMasked = nationalIdMasked,
                internalNotes = internalNotes
            )
            repository.addStaff(newStaff)
            _statusNotification.value = "Employee '$name' ($nextId) added to $division."
        }
    }

    fun updateStaffStatus(staff: StaffEntity, newStatus: String) {
        viewModelScope.launch {
            repository.updateStaff(staff.copy(accountStatus = newStatus))
            _statusNotification.value = "Employee '${staff.fullName}' status set to $newStatus."
        }
    }

    fun assignEmployeeDistrict(staff: StaffEntity, district: String, area: String, manager: String) {
        viewModelScope.launch {
            repository.updateStaff(staff.copy(assignedDistrict = district, assignedArea = area, manager = manager))
            _statusNotification.value = "Assigned ${staff.fullName} to $district ($area) under $manager."
        }
    }

    fun deleteStaff(staff: StaffEntity) {
        viewModelScope.launch {
            repository.deleteStaff(staff)
            _statusNotification.value = "Staff member '${staff.fullName}' removed."
        }
    }

    // --- SHOP MANAGEMENT ---
    fun addShop(
        shopName: String,
        owner: String,
        category: String,
        address: String,
        district: String,
        block: String,
        city: String,
        contact: String,
        rep: String,
        pin: String = "400001",
        sellerId: String = ""
    ) {
        viewModelScope.launch {
            val nextShopId = "WM-SHP-${(200 + (shopList.value.size + 1))}"
            val shop = ShopEntity(
                shopId = nextShopId,
                shopName = shopName,
                owner = owner,
                category = category,
                address = address,
                district = district,
                block = block.ifBlank { "Block-4" },
                city = city.ifBlank { "Metropolis" },
                pin = pin.ifBlank { "400001" },
                contact = contact,
                assignedRepresentative = rep.ifBlank { "Marcus Vance" },
                registrationDate = "2026-10-01",
                status = "Active",
                verificationStatus = "Verified",
                eshopStatus = "Live",
                sellerId = sellerId
            )
            repository.addShop(shop)
            _statusNotification.value = "Shop '$shopName' ($nextShopId) registered in $district."
        }
    }

    fun updateShop(shop: ShopEntity) {
        viewModelScope.launch {
            repository.updateShop(shop)
            _statusNotification.value = "Shop '${shop.shopName}' updated."
        }
    }

    fun deleteShop(shop: ShopEntity) {
        viewModelScope.launch {
            repository.deleteShop(shop)
            _statusNotification.value = "Shop '${shop.shopName}' deleted from database."
        }
    }

    // --- SELLER MANAGEMENT & 11-STEP WORKFLOW ---
    fun registerSeller(
        businessName: String,
        category: String,
        ownerName: String,
        location: String,
        district: String,
        contactPhone: String,
        contactEmail: String,
        description: String = "",
        assignedRep: String = "Marcus Vance",
        businessType: String = "Sole Proprietorship"
    ) {
        viewModelScope.launch {
            val nextSellerId = "WM-SLR-${(1000 + (sellerList.value.size + 1))}"
            val nextShopId = "WM-SHP-${(200 + (shopList.value.size + 1))}"

            val seller = SellerEntity(
                sellerId = nextSellerId,
                businessName = businessName,
                businessCategory = category,
                businessType = businessType,
                ownerName = ownerName,
                location = location,
                district = district,
                contactPhone = contactPhone,
                contactEmail = contactEmail,
                registrationDate = "2026-10-01",
                assignedRepresentative = assignedRep,
                sellerStatus = "Active",
                verificationStatus = "Pending Documents",
                eshopStatus = "In Setup",
                workflowStep = "Registration Started",
                productCount = 0,
                monthlyGmv = 0.0,
                storefrontDescription = description.ifBlank { "Quality merchant store in $district" },
                shopId = nextShopId
            )
            repository.addSeller(seller)

            // Auto-create initial Shop record
            val initialShop = ShopEntity(
                shopId = nextShopId,
                shopName = businessName,
                owner = ownerName,
                category = category,
                address = location,
                district = district,
                contact = contactPhone,
                assignedRepresentative = assignedRep,
                registrationDate = "2026-10-01",
                status = "Active",
                verificationStatus = "Pending",
                eshopStatus = "In Setup",
                sellerId = nextSellerId
            )
            repository.addShop(initialShop)

            _statusNotification.value = "Seller '$businessName' ($nextSellerId) & Shop ($nextShopId) registered."
        }
    }

    fun updateSellerStatus(
        seller: SellerEntity,
        newSellerStatus: String? = null,
        newVerificationStatus: String? = null,
        newEshopStatus: String? = null,
        newRep: String? = null
    ) {
        viewModelScope.launch {
            val updated = seller.copy(
                sellerStatus = newSellerStatus ?: seller.sellerStatus,
                verificationStatus = newVerificationStatus ?: seller.verificationStatus,
                eshopStatus = newEshopStatus ?: seller.eshopStatus,
                assignedRepresentative = newRep ?: seller.assignedRepresentative
            )
            repository.updateSeller(updated)

            // Sync with associated Shop
            val shop = shopList.value.find { it.sellerId == seller.sellerId || it.shopId == seller.shopId }
            if (shop != null) {
                repository.updateShop(
                    shop.copy(
                        status = newSellerStatus ?: shop.status,
                        verificationStatus = newVerificationStatus ?: shop.verificationStatus,
                        eshopStatus = newEshopStatus ?: shop.eshopStatus,
                        assignedRepresentative = newRep ?: shop.assignedRepresentative
                    )
                )
            }
            _statusNotification.value = "Updated merchant '${seller.businessName}' status."
        }
    }

    fun updateSellerWorkflowStep(seller: SellerEntity, step: SellerWorkflowStep) {
        viewModelScope.launch {
            val isVerified = step.stepNumber >= SellerWorkflowStep.VERIFIED.stepNumber
            val isEshopLive = step == SellerWorkflowStep.ACTIVE_SELLER

            val updated = seller.copy(
                workflowStep = step.label,
                verificationStatus = if (isVerified) "Verified" else seller.verificationStatus,
                eshopStatus = if (isEshopLive) "Live" else if (step.stepNumber >= SellerWorkflowStep.ESHOP_PREPARATION.stepNumber) "In Setup" else "Paused"
            )
            repository.updateSeller(updated)

            val shop = shopList.value.find { it.sellerId == seller.sellerId || it.shopId == seller.shopId }
            if (shop != null) {
                repository.updateShop(
                    shop.copy(
                        verificationStatus = if (isVerified) "Verified" else shop.verificationStatus,
                        eshopStatus = if (isEshopLive) "Live" else shop.eshopStatus
                    )
                )
            }
            _statusNotification.value = "Seller '${seller.businessName}' advanced to Step ${step.stepNumber}: ${step.label}."
        }
    }

    // --- SELLER DOCUMENT MANAGEMENT ---
    fun uploadSellerDocument(
        sellerId: String,
        documentType: String,
        uri: Uri
    ) {
        viewModelScope.launch {
            val persistentPath = SecurityUtils.copyUriToInternalStorage(
                context = getApplication(),
                subDir = "seller_docs",
                uri = uri,
                fileNamePrefix = "doc_${sellerId}"
            )
            val docId = "DOC-WM-${(1000 + (sellerDocumentsList.value.size + 1))}"
            val doc = SellerDocumentEntity(
                documentId = docId,
                sellerId = sellerId,
                documentType = documentType,
                filePath = persistentPath ?: "docs/${documentType.lowercase().replace(" ", "_")}.pdf",
                uploadedBy = authenticatedUser.value?.fullName ?: currentUserRole.value.title,
                uploadDate = "2026-10-01",
                verificationStatus = "PENDING"
            )
            repository.addDocument(doc)
            _statusNotification.value = "Document '$documentType' ($docId) uploaded for seller $sellerId."
        }
    }

    fun verifyDocument(document: SellerDocumentEntity, status: String, reason: String = "") {
        viewModelScope.launch {
            val verifier = authenticatedUser.value?.fullName ?: currentUserRole.value.title
            val updated = document.copy(
                verificationStatus = status,
                verifiedBy = verifier,
                verificationDate = "2026-10-01",
                rejectionReason = reason
            )
            repository.updateDocument(updated)
            // Record approval
            repository.recordApproval(
                ApprovalRecordEntity(
                    entityType = "DOCUMENT",
                    entityId = document.documentId,
                    entityTitle = "${document.documentType} (${document.sellerId})",
                    action = if (status == "VERIFIED") "APPROVE" else "REJECT",
                    previousStatus = document.verificationStatus,
                    newStatus = status,
                    userRole = currentUserRole.value.title,
                    userName = verifier,
                    timestamp = "2026-10-01 11:00",
                    comment = reason.ifBlank { "Document verified against commercial registry." }
                )
            )
            _statusNotification.value = "Document ${document.documentId} set to $status."
        }
    }

    // --- SELLER PAYMENTS & CONFIGURABLE PRICING ---
    fun recordSellerPayment(
        sellerId: String,
        shopId: String,
        service: String,
        amount: Double,
        method: String,
        refNumber: String,
        receiptUri: Uri?,
        notes: String
    ) {
        viewModelScope.launch {
            val receiptPath = receiptUri?.let {
                SecurityUtils.copyUriToInternalStorage(getApplication(), "receipts", it, "rec_${sellerId}")
            } ?: ""
            val payId = "PAY-WM-${(5000 + (sellerPaymentsList.value.size + 1))}"
            val payment = SellerPaymentEntity(
                paymentId = payId,
                sellerId = sellerId,
                shopId = shopId.ifBlank { "WM-SHP-201" },
                service = service,
                amount = amount,
                date = "2026-10-01",
                paymentMethod = method,
                transactionRefNumber = refNumber.ifBlank { "TX-REF-${(100000..999999).random()}" },
                paymentStatus = "SUBMITTED",
                receiptPath = receiptPath,
                collectedBy = authenticatedUser.value?.fullName ?: "Marcus Vance",
                notes = notes
            )
            repository.addPayment(payment)
            _statusNotification.value = "Payment $payId (₹$amount) recorded for $service."
        }
    }

    fun verifyPayment(payment: SellerPaymentEntity, status: String, notes: String = "") {
        viewModelScope.launch {
            val verifier = authenticatedUser.value?.fullName ?: currentUserRole.value.title
            val updated = payment.copy(
                paymentStatus = status,
                verifiedBy = verifier,
                notes = if (notes.isNotBlank()) "${payment.notes} | $notes" else payment.notes
            )
            repository.updatePayment(updated)
            repository.recordApproval(
                ApprovalRecordEntity(
                    entityType = "PAYMENT",
                    entityId = payment.paymentId,
                    entityTitle = "${payment.service} (₹${payment.amount})",
                    action = if (status == "CONFIRMED") "APPROVE" else "REJECT",
                    previousStatus = payment.paymentStatus,
                    newStatus = status,
                    userRole = currentUserRole.value.title,
                    userName = verifier,
                    timestamp = "2026-10-01 11:30",
                    comment = notes.ifBlank { "Payment verified and posted to district ledger." }
                )
            )
            _statusNotification.value = "Payment ${payment.paymentId} status set to $status."
        }
    }

    fun updateServicePricing(pricing: ServicePricingEntity) {
        viewModelScope.launch {
            repository.updatePricing(pricing)
            _statusNotification.value = "Pricing for '${pricing.serviceName}' updated to ₹${pricing.basePrice}."
        }
    }

    fun addServicePricing(name: String, category: String, price: Double, district: String, desc: String) {
        viewModelScope.launch {
            val pId = "SVC-${(100 + (servicePricingsList.value.size + 1))}"
            repository.addPricing(
                ServicePricingEntity(
                    serviceId = pId,
                    serviceName = name,
                    category = category,
                    basePrice = price,
                    district = district.ifBlank { "All Districts" },
                    description = desc
                )
            )
            _statusNotification.value = "Configured service '$name' (₹$price)."
        }
    }

    // --- FOLLOW-UP SYSTEM ---
    fun addFollowUp(
        sellerId: String,
        shopId: String,
        purpose: String,
        dueDate: String,
        priority: String,
        notes: String
    ) {
        viewModelScope.launch {
            val rep = authenticatedUser.value
            val followUp = FollowUpEntity(
                sellerId = sellerId,
                shopId = shopId,
                employeeId = rep?.linkedEmployeeId ?: "WM-EMP-107",
                employeeName = rep?.fullName ?: "Marcus Vance",
                purpose = purpose,
                dueDate = dueDate,
                priority = priority,
                status = "PENDING",
                notes = notes
            )
            repository.addFollowUp(followUp)
            _statusNotification.value = "Follow-up scheduled for $dueDate ($purpose)."
        }
    }

    fun updateFollowUpStatus(followUp: FollowUpEntity, newStatus: String) {
        viewModelScope.launch {
            repository.updateFollowUp(followUp.copy(status = newStatus))
            _statusNotification.value = "Follow-up marked as $newStatus."
        }
    }

    // --- APPROVAL WORKFLOW ---
    fun processApproval(
        entityType: String,
        entityId: String,
        entityTitle: String,
        action: String,
        prevStatus: String,
        newStatus: String,
        comment: String
    ) {
        viewModelScope.launch {
            val reviewer = authenticatedUser.value?.fullName ?: currentUserRole.value.title
            val approval = ApprovalRecordEntity(
                entityType = entityType,
                entityId = entityId,
                entityTitle = entityTitle,
                action = action,
                previousStatus = prevStatus,
                newStatus = newStatus,
                userRole = currentUserRole.value.title,
                userName = reviewer,
                timestamp = "2026-10-01 12:00",
                comment = comment
            )
            repository.recordApproval(approval)

            // Dispatch entity updates
            when (entityType) {
                "SELLER" -> {
                    val seller = sellerList.value.find { it.sellerId == entityId }
                    if (seller != null) {
                        repository.updateSeller(seller.copy(verificationStatus = newStatus))
                    }
                }
                "PRODUCT" -> {
                    val prod = productList.value.find { it.productId == entityId }
                    if (prod != null) {
                        repository.updateProduct(prod.copy(status = newStatus))
                    }
                }
                "DOCUMENT" -> {
                    val doc = sellerDocumentsList.value.find { it.documentId == entityId }
                    if (doc != null) {
                        repository.updateDocument(doc.copy(verificationStatus = newStatus, verifiedBy = reviewer, verificationDate = "2026-10-01"))
                    }
                }
                "PAYMENT" -> {
                    val pay = sellerPaymentsList.value.find { it.paymentId == entityId }
                    if (pay != null) {
                        repository.updatePayment(pay.copy(paymentStatus = newStatus, verifiedBy = reviewer))
                    }
                }
            }
            _statusNotification.value = "Approval '$action' recorded for $entityTitle."
        }
    }

    // --- PRODUCT MANAGEMENT ---
    fun addProduct(
        sellerId: String,
        shopId: String = "",
        productName: String,
        category: String,
        subcategory: String,
        brand: String = "World Mart Partner",
        sku: String = "",
        description: String,
        price: Double,
        stock: Int,
        photoCode: String = "gadget",
        photoPath: String = "",
        unit: String = "pcs",
        discount: Double = 0.0
    ) {
        viewModelScope.launch {
            val nextId = "WM-PRD-${(8000 + (productList.value.size + 1))}"
            val effectiveShopId = shopId.ifBlank {
                shopList.value.find { it.sellerId == sellerId }?.shopId ?: "WM-SHP-201"
            }
            val product = ProductEntity(
                productId = nextId,
                sellerId = sellerId,
                shopId = effectiveShopId,
                productName = productName,
                category = category,
                subcategory = subcategory,
                brand = brand.ifBlank { "World Mart Vendor" },
                sku = sku.ifBlank { "SKU-WM-${(1000..9999).random()}" },
                description = description,
                price = price,
                discount = discount,
                stock = stock,
                stockQuantity = stock,
                unit = unit,
                photos = photoPath,
                photoCode = photoCode,
                status = "PENDING REVIEW",
                availability = if (stock > 0) "In Stock" else "Out of Stock",
                createdBy = authenticatedUser.value?.fullName ?: "Marcus Vance (Sales Rep)",
                createdDate = "2026-10-01",
                updatedDate = "2026-10-01"
            )
            repository.addProduct(product)

            val seller = sellerList.value.find { it.sellerId == sellerId }
            if (seller != null) {
                repository.updateSeller(seller.copy(productCount = seller.productCount + 1))
            }
            _statusNotification.value = "Product '$productName' ($nextId) submitted for review."
        }
    }

    fun updateProductStatus(product: ProductEntity, newStatus: String, availability: String = "In Stock") {
        viewModelScope.launch {
            repository.updateProduct(product.copy(status = newStatus, availability = availability, updatedDate = "2026-10-01"))
            _statusNotification.value = "Product '${product.productName}' status set to $newStatus."
        }
    }

    fun updateProductStock(product: ProductEntity, newStock: Int) {
        viewModelScope.launch {
            val avail = if (newStock > 0) "In Stock" else "Out of Stock"
            repository.updateProduct(product.copy(stock = newStock, stockQuantity = newStock, availability = avail, updatedDate = "2026-10-01"))
            _statusNotification.value = "Product '${product.productName}' stock updated to $newStock."
        }
    }

    // --- PHOTO PERSISTENCE & REVIEW ---
    fun saveProductPhoto(
        sellerId: String,
        shopId: String,
        productId: String,
        uri: Uri,
        caption: String
    ) {
        viewModelScope.launch {
            val persistentPath = SecurityUtils.copyUriToInternalStorage(
                context = getApplication(),
                subDir = "product_photos",
                uri = uri,
                fileNamePrefix = "prod_${productId}"
            )
            if (persistentPath != null) {
                val photoRecord = ProductPhotoEntity(
                    sellerId = sellerId,
                    shopId = shopId,
                    productId = productId,
                    uploader = authenticatedUser.value?.fullName ?: currentUserRole.value.title,
                    date = "2026-10-01",
                    filePath = persistentPath,
                    caption = caption,
                    status = "Approved"
                )
                repository.addProductPhoto(photoRecord)
                val prod = productList.value.find { it.productId == productId }
                if (prod != null) {
                    repository.updateProduct(prod.copy(photos = persistentPath))
                }
                _statusNotification.value = "Product photo saved to catalogue database."
            } else {
                _statusNotification.value = "Failed to copy photo to storage."
            }
        }
    }

    fun updatePhotoStatus(photo: ProductPhotoEntity, status: String, reason: String = "") {
        viewModelScope.launch {
            repository.updateProductPhoto(photo.copy(status = status, rejectionReason = reason))
            _statusNotification.value = "Photo #${photo.photoId} marked as $status."
        }
    }

    // --- DAILY STAFF REPORTS ---
    fun submitDailyReport(
        district: String,
        area: String,
        shopsPlanned: Int,
        shopsVisited: Int,
        newLeads: Int,
        interestedShops: Int,
        newSellers: Int,
        documentsCollected: Int,
        paymentsSubmitted: Double,
        productsCollected: Int,
        photosCollected: Int,
        followUps: String,
        problems: String,
        tomorrowPlan: String,
        notes: String
    ) {
        viewModelScope.launch {
            val rep = authenticatedUser.value
            val report = DailyReportEntity(
                repId = rep?.linkedEmployeeId ?: "WM-EMP-108",
                repName = rep?.fullName ?: "Marcus Vance",
                date = "2026-10-01",
                district = district,
                area = area,
                shopsPlanned = shopsPlanned,
                shopsVisited = shopsVisited,
                newLeads = newLeads,
                interestedShops = interestedShops,
                newSellers = newSellers,
                documentsCollected = documentsCollected,
                paymentsSubmitted = paymentsSubmitted,
                productsCollected = productsCollected,
                photosCollected = photosCollected,
                followUps = followUps,
                problems = problems,
                tomorrowPlan = tomorrowPlan,
                notes = notes,
                reviewStatus = "Submitted"
            )
            repository.addDailyReport(report)
            _statusNotification.value = "Daily field report submitted for $district ($shopsVisited visits recorded)."
        }
    }

    fun reviewDailyReport(report: DailyReportEntity, newStatus: String, feedback: String) {
        viewModelScope.launch {
            val reviewer = authenticatedUser.value?.fullName ?: currentUserRole.value.title
            val updated = report.copy(
                reviewStatus = newStatus,
                managerReviewer = reviewer,
                managerFeedback = feedback
            )
            repository.updateDailyReport(updated)
            _statusNotification.value = "Report #${report.reportId} marked as $newStatus with feedback."
        }
    }

    // --- SHOP VISITS ---
    fun logShopVisit(
        shopId: String,
        sellerId: String,
        shopName: String,
        purpose: String,
        outcome: String,
        notes: String,
        discussion: String = "",
        docsCount: Int = 0,
        prodsCount: Int = 0,
        photosCount: Int = 0,
        followUpNeeded: Boolean = false,
        followUpDate: String = ""
    ) {
        viewModelScope.launch {
            val rep = authenticatedUser.value?.fullName ?: "Marcus Vance"
            val visit = ShopVisitEntity(
                shopId = shopId,
                sellerId = sellerId,
                shopName = shopName,
                repName = rep,
                date = "2026-10-01",
                time = "11:15 AM",
                purpose = purpose,
                discussion = discussion,
                registrationStatus = "Completed",
                documentsCollected = docsCount,
                productsCollected = prodsCount,
                photosCollected = photosCount,
                followUpRequired = followUpNeeded,
                nextFollowUpDate = followUpDate,
                outcome = outcome,
                notes = notes
            )
            repository.addShopVisit(visit)

            if (followUpNeeded && followUpDate.isNotBlank()) {
                addFollowUp(sellerId, shopId, "Visit Follow-up: $outcome", followUpDate, "Normal", notes)
            }
            _statusNotification.value = "Shop visit logged for '$shopName'."
        }
    }

    // --- NOTIFICATIONS ---
    fun markNotificationRead(id: Int) {
        viewModelScope.launch {
            repository.markNotificationRead(id)
        }
    }

    // --- TASKS ---
    fun addTask(
        title: String,
        desc: String,
        division: String,
        priority: String,
        dueDate: String,
        district: String = "Central Metro",
        assignedUser: String = ""
    ) {
        viewModelScope.launch {
            val creator = authenticatedUser.value?.fullName ?: "Operations Management"
            val task = TaskEntity(
                title = title,
                description = desc,
                assignedDivision = division,
                district = district,
                priority = priority,
                dueDate = dueDate,
                assignedBy = creator,
                assignedToUser = assignedUser,
                status = "PENDING"
            )
            repository.addTask(task)
            _statusNotification.value = "Task '$title' created for $division."
        }
    }

    fun toggleTaskCompletion(task: TaskEntity) {
        viewModelScope.launch {
            val updated = task.copy(
                isCompleted = !task.isCompleted,
                status = if (!task.isCompleted) "COMPLETED" else "PENDING"
            )
            repository.updateTask(updated)
            _statusNotification.value = "Task status updated."
        }
    }

    // --- ORDERS & CART (Kept for compatibility) ---
    fun addToCart(product: ProductEntity, quantity: Int = 1) {
        viewModelScope.launch {
            val user = authenticatedUser.value
            val customerId = user?.userId ?: "GUEST-USER"
            val existing = cartItems.value.find { it.productId == product.productId }
            if (existing != null) {
                repository.updateCartItem(existing.copy(quantity = existing.quantity + quantity))
            } else {
                repository.addCartItem(
                    CartItemEntity(
                        customerId = customerId,
                        productId = product.productId,
                        productName = product.productName,
                        price = product.price,
                        quantity = quantity,
                        sellerId = product.sellerId,
                        shopId = product.shopId,
                        photoCode = product.photoCode
                    )
                )
            }
            _statusNotification.value = "Added '${product.productName}' to cart."
        }
    }

    fun updateCartQuantity(item: CartItemEntity, newQty: Int) {
        viewModelScope.launch {
            if (newQty <= 0) {
                repository.deleteCartItem(item)
            } else {
                repository.updateCartItem(item.copy(quantity = newQty))
            }
        }
    }

    fun removeCartItem(item: CartItemEntity) {
        viewModelScope.launch {
            repository.deleteCartItem(item)
        }
    }

    fun placeOrder(address: String, district: String, method: String) {
        viewModelScope.launch {
            val user = authenticatedUser.value
            val currentItems = cartItems.value
            if (currentItems.isEmpty()) return@launch

            val orderId = "WM-ORD-${(5000 + (orderList.value.size + 1))}"
            val total = currentItems.sumOf { it.price * it.quantity }
            val summary = currentItems.joinToString(", ") { "${it.quantity}x ${it.productName}" }

            val order = OrderEntity(
                orderId = orderId,
                customerId = user?.userId ?: "CUST-GUEST",
                customerName = user?.fullName ?: "District Customer",
                customerEmail = user?.email ?: "customer@worldmart.com",
                customerPhone = user?.phone ?: "+1 555-0192",
                shippingAddress = address,
                district = district,
                totalAmount = total,
                paymentMethod = method,
                paymentStatus = "Escrow Held",
                orderStatus = "Confirmed",
                orderDate = "2026-10-01",
                itemsSummary = summary
            )
            repository.addOrder(order)
            repository.clearCart(user?.userId ?: "GUEST-USER")
            _statusNotification.value = "Order #$orderId placed successfully."
        }
    }

    // --- CMS, Announcements & Inquiries ---
    fun updateContentBlock(key: String, title: String, subtitle: String, body: String) {
        viewModelScope.launch {
            val block = ContentBlockEntity(
                key = key,
                title = title,
                subtitle = subtitle,
                body = body,
                lastUpdated = "2026-10-01",
                updatedBy = authenticatedUser.value?.fullName ?: "Admin"
            )
            repository.updateContentBlock(block)
            _statusNotification.value = "Content block '$key' updated."
        }
    }

    fun postAnnouncement(title: String, category: String, summary: String, content: String, isPublic: Boolean) {
        viewModelScope.launch {
            val announcement = AnnouncementEntity(
                title = title,
                category = category,
                date = "2026-10-01",
                summary = summary,
                content = content,
                isPublic = isPublic,
                authorRole = authenticatedUser.value?.fullName ?: currentUserRole.value.title
            )
            repository.addAnnouncement(announcement)
            _statusNotification.value = "Announcement '$title' broadcasted."
        }
    }

    fun submitContactInquiry(name: String, email: String, category: String, subject: String, msg: String) {
        viewModelScope.launch {
            val inquiry = InquiryEntity(
                fullName = name,
                email = email,
                category = category,
                subject = subject,
                message = msg,
                submittedAt = "2026-10-01"
            )
            repository.submitInquiry(inquiry)
            _statusNotification.value = "Inquiry received. A representative will contact you."
        }
    }

    fun submitJobApplication(jobId: String, jobTitle: String, name: String, email: String, phone: String, quals: String, port: String) {
        viewModelScope.launch {
            val app = JobApplicationEntity(
                jobId = jobId,
                jobTitle = jobTitle,
                applicantName = name,
                applicantEmail = email,
                applicantPhone = phone,
                qualifications = quals,
                portfolioUrl = port,
                submittedAt = "2026-10-01"
            )
            repository.submitJobApplication(app)
            _statusNotification.value = "Application for '$jobTitle' submitted."
        }
    }

    // --- First-Time Admin Setup & Database Reset ---
    fun purgeOperationalData() {
        viewModelScope.launch {
            repository.purgeOperationalData()
            _statusNotification.value = "Operational database reset to clean empty state."
        }
    }
}
