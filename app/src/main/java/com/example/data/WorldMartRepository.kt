package com.example.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class WorldMartRepository(private val dao: WorldMartDao) {

    val allStaff: Flow<List<StaffEntity>> = dao.getAllStaff()
    val allShops: Flow<List<ShopEntity>> = dao.getAllShops()
    val allSellers: Flow<List<SellerEntity>> = dao.getAllSellers()
    val allProducts: Flow<List<ProductEntity>> = dao.getAllProducts()
    val allProductPhotos: Flow<List<ProductPhotoEntity>> = dao.getAllProductPhotos()
    val allDailyReports: Flow<List<DailyReportEntity>> = dao.getAllDailyReports()
    val allShopVisits: Flow<List<ShopVisitEntity>> = dao.getAllShopVisits()
    val allOrders: Flow<List<OrderEntity>> = dao.getAllOrders()
    val allUsers: Flow<List<UserAccountEntity>> = dao.getAllUsers()
    val allAnnouncements: Flow<List<AnnouncementEntity>> = dao.getAllAnnouncements()
    val publicAnnouncements: Flow<List<AnnouncementEntity>> = dao.getPublicAnnouncements()
    val allTasks: Flow<List<TaskEntity>> = dao.getAllTasks()
    val allContentBlocks: Flow<List<ContentBlockEntity>> = dao.getAllContentBlocks()
    val allJobs: Flow<List<JobEntity>> = dao.getAllJobs()
    val allJobApplications: Flow<List<JobApplicationEntity>> = dao.getAllJobApplications()
    val allInquiries: Flow<List<InquiryEntity>> = dao.getAllInquiries()
    val allFinancials: Flow<List<FinancialRecordEntity>> = dao.getAllFinancials()

    // Real Operations Flows
    val allSellerDocuments: Flow<List<SellerDocumentEntity>> = dao.getAllSellerDocuments()
    val allSellerPayments: Flow<List<SellerPaymentEntity>> = dao.getAllSellerPayments()
    val allServicePricings: Flow<List<ServicePricingEntity>> = dao.getAllServicePricings()
    val allFollowUps: Flow<List<FollowUpEntity>> = dao.getAllFollowUps()
    val allApprovals: Flow<List<ApprovalRecordEntity>> = dao.getAllApprovals()
    val allAuditLogs: Flow<List<AuditLogEntity>> = dao.getAllAuditLogs()
    val allNotifications: Flow<List<AppNotificationEntity>> = dao.getAllNotifications()

    suspend fun ensureDataInitialized() = withContext(Dispatchers.IO) {
        val existingStaff = dao.getAllStaff().first()
        if (existingStaff.isEmpty()) {
            dao.insertAllStaff(InitialData.staffList)
            dao.insertAllShops(InitialData.shopList)
            dao.insertAllSellers(InitialData.sellerList)
            dao.insertAllProducts(InitialData.productList)
            dao.insertAllUsers(InitialData.userAccountsList)
            dao.insertDailyReport(InitialData.dailyReportsList.first())
            dao.insertShopVisit(InitialData.shopVisitsList.first())
            dao.insertOrder(InitialData.ordersList.first())
            dao.insertAllAnnouncements(InitialData.announcementList)
            dao.insertAllTasks(InitialData.taskList)
            dao.insertAllContentBlocks(InitialData.contentBlocks)
            dao.insertAllJobs(InitialData.jobList)
            dao.insertAllFinancials(InitialData.financialRecords)

            // Seed operations tables
            dao.insertAllDocuments(InitialData.sellerDocumentsList)
            dao.insertAllPayments(InitialData.sellerPaymentsList)
            dao.insertAllPricings(InitialData.servicePricingsList)
            dao.insertAllFollowUps(InitialData.followUpsList)
            dao.insertAllApprovals(InitialData.approvalRecordsList)
            dao.insertAllAuditLogs(InitialData.auditLogsList)
            dao.insertAllNotifications(InitialData.notificationsList)
        }
    }

    // --- Authentication ---
    suspend fun authenticate(usernameOrEmail: String, rawPassword: String): UserAccountEntity? = withContext(Dispatchers.IO) {
        val user = dao.getUserByUsername(usernameOrEmail) ?: dao.getUserByEmail(usernameOrEmail)
        if (user != null && SecurityUtils.verifyPassword(rawPassword, user.passwordHash)) {
            user
        } else {
            null
        }
    }

    suspend fun registerUser(
        username: String,
        rawPassword: String,
        email: String,
        fullName: String,
        roleName: String,
        phone: String = "",
        linkedSellerId: String = "",
        linkedShopId: String = "",
        linkedEmployeeId: String = ""
    ): Result<UserAccountEntity> = withContext(Dispatchers.IO) {
        val existingUsername = dao.getUserByUsername(username)
        if (existingUsername != null) {
            return@withContext Result.failure(Exception("Username '$username' is already taken."))
        }
        val existingEmail = dao.getUserByEmail(email)
        if (existingEmail != null) {
            return@withContext Result.failure(Exception("Email '$email' is already registered."))
        }

        val newUser = UserAccountEntity(
            userId = "USER-${System.currentTimeMillis().toString().takeLast(6)}",
            username = username.trim(),
            passwordHash = SecurityUtils.hashPassword(rawPassword),
            email = email.trim(),
            fullName = fullName.trim(),
            roleName = roleName,
            phone = phone.trim(),
            linkedSellerId = linkedSellerId,
            linkedShopId = linkedShopId,
            linkedEmployeeId = linkedEmployeeId,
            status = "Active",
            createdAt = "2026-10-01"
        )
        dao.insertUser(newUser)
        logAudit("System / Auth", roleName, "User Account Registered", "User", newUser.userId, "Created user ${newUser.fullName} ($roleName)")
        Result.success(newUser)
    }

    // Staff
    suspend fun addStaff(staff: StaffEntity) = withContext(Dispatchers.IO) {
        dao.insertStaff(staff)
        logAudit("HR Admin", "Operations", "Employee Added", "Staff", staff.employeeId, "Added ${staff.fullName} to ${staff.division}")
    }
    suspend fun updateStaff(staff: StaffEntity) = withContext(Dispatchers.IO) {
        dao.insertStaff(staff)
        logAudit("HR Admin", "Operations", "Employee Updated", "Staff", staff.employeeId, "Updated profile for ${staff.fullName}")
    }
    suspend fun deleteStaff(staff: StaffEntity) = withContext(Dispatchers.IO) { dao.deleteStaff(staff) }
    suspend fun getStaffById(empId: String): StaffEntity? = withContext(Dispatchers.IO) { dao.getStaffById(empId) }

    // Shops
    suspend fun addShop(shop: ShopEntity) = withContext(Dispatchers.IO) {
        dao.insertShop(shop)
        logAudit(shop.assignedRepresentative, "Sales Rep", "Shop Registered", "Shop", shop.shopId, "Registered ${shop.shopName} in ${shop.district}")
    }
    suspend fun updateShop(shop: ShopEntity) = withContext(Dispatchers.IO) { dao.updateShop(shop) }
    suspend fun deleteShop(shop: ShopEntity) = withContext(Dispatchers.IO) { dao.deleteShop(shop) }
    suspend fun getShopById(shopId: String): ShopEntity? = withContext(Dispatchers.IO) { dao.getShopById(shopId) }
    fun getShopsByRep(repName: String): Flow<List<ShopEntity>> = dao.getShopsByRep(repName)

    // Sellers
    suspend fun addSeller(seller: SellerEntity) = withContext(Dispatchers.IO) {
        dao.insertSeller(seller)
        logAudit(seller.assignedRepresentative, "Sales Rep", "Seller Registered", "Seller", seller.sellerId, "Registered ${seller.businessName}")
        createNotification("New Seller Registered", "${seller.businessName} onboarded in ${seller.district}", "SELLER", "OPERATIONS", seller.sellerId)
    }
    suspend fun updateSeller(seller: SellerEntity) = withContext(Dispatchers.IO) { dao.updateSeller(seller) }
    suspend fun deleteSeller(seller: SellerEntity) = withContext(Dispatchers.IO) { dao.deleteSeller(seller) }
    suspend fun getSellerById(sellerId: String): SellerEntity? = withContext(Dispatchers.IO) { dao.getSellerById(sellerId) }
    fun getSellersByRep(repName: String): Flow<List<SellerEntity>> = dao.getSellersByRep(repName)

    // Seller Documents
    suspend fun addDocument(document: SellerDocumentEntity) = withContext(Dispatchers.IO) {
        dao.insertDocument(document)
        logAudit(document.uploadedBy, "Sales Rep", "Document Uploaded", "Document", document.documentId, "Uploaded ${document.documentType} for ${document.sellerId}")
        createNotification("KYC Document Uploaded", "New ${document.documentType} uploaded for seller ${document.sellerId}", "DOCUMENT", "OPERATIONS", document.documentId)
    }
    suspend fun updateDocument(document: SellerDocumentEntity) = withContext(Dispatchers.IO) { dao.updateDocument(document) }
    suspend fun deleteDocument(document: SellerDocumentEntity) = withContext(Dispatchers.IO) { dao.deleteDocument(document) }
    fun getDocumentsForSeller(sellerId: String): Flow<List<SellerDocumentEntity>> = dao.getDocumentsForSeller(sellerId)

    // Seller Payments
    suspend fun addPayment(payment: SellerPaymentEntity) = withContext(Dispatchers.IO) {
        dao.insertPayment(payment)
        logAudit(payment.collectedBy, "Sales Rep", "Payment Submitted", "Payment", payment.paymentId, "Collected ₹${payment.amount} for ${payment.service}")
        createNotification("Payment Submitted", "Payment of ₹${payment.amount} for ${payment.service} submitted for verification", "PAYMENT", "FINANCE", payment.paymentId)
    }
    suspend fun updatePayment(payment: SellerPaymentEntity) = withContext(Dispatchers.IO) { dao.updatePayment(payment) }
    suspend fun deletePayment(payment: SellerPaymentEntity) = withContext(Dispatchers.IO) { dao.deletePayment(payment) }

    // Service Pricing
    suspend fun addPricing(pricing: ServicePricingEntity) = withContext(Dispatchers.IO) { dao.insertPricing(pricing) }
    suspend fun updatePricing(pricing: ServicePricingEntity) = withContext(Dispatchers.IO) { dao.updatePricing(pricing) }

    // Follow-ups
    suspend fun addFollowUp(followUp: FollowUpEntity) = withContext(Dispatchers.IO) { dao.insertFollowUp(followUp) }
    suspend fun updateFollowUp(followUp: FollowUpEntity) = withContext(Dispatchers.IO) { dao.updateFollowUp(followUp) }
    suspend fun deleteFollowUp(followUp: FollowUpEntity) = withContext(Dispatchers.IO) { dao.deleteFollowUp(followUp) }

    // Approvals
    suspend fun recordApproval(approval: ApprovalRecordEntity) = withContext(Dispatchers.IO) {
        dao.insertApproval(approval)
        logAudit(approval.userName, approval.userRole, "Approval Action: ${approval.action}", approval.entityType, approval.entityId, "${approval.action} - ${approval.comment}")
        createNotification("Approval: ${approval.action}", "${approval.entityTitle} was ${approval.action} by ${approval.userName}", "APPROVAL", "ALL", approval.entityId)
    }

    // Audit Logging
    suspend fun logAudit(
        user: String,
        userRole: String,
        action: String,
        recordType: String,
        recordId: String,
        details: String
    ) = withContext(Dispatchers.IO) {
        val entry = AuditLogEntity(
            user = user,
            userRole = userRole,
            action = action,
            recordType = recordType,
            recordId = recordId,
            timestamp = "2026-10-01 10:00",
            changeDetails = details
        )
        dao.insertAuditLog(entry)
    }

    // Notifications
    suspend fun createNotification(
        title: String,
        message: String,
        category: String,
        recipientRole: String = "ALL",
        linkedId: String = ""
    ) = withContext(Dispatchers.IO) {
        val notif = AppNotificationEntity(
            title = title,
            message = message,
            category = category,
            recipientRole = recipientRole,
            timestamp = "Just Now",
            isRead = false,
            linkedId = linkedId
        )
        dao.insertNotification(notif)
    }
    suspend fun markNotificationRead(id: Int) = withContext(Dispatchers.IO) { dao.markNotificationRead(id) }

    // Products
    suspend fun addProduct(product: ProductEntity) = withContext(Dispatchers.IO) {
        dao.insertProduct(product)
        logAudit(product.createdBy, "Sales / Operations", "Product Created", "Product", product.productId, "Created product ${product.productName}")
    }
    suspend fun updateProduct(product: ProductEntity) = withContext(Dispatchers.IO) { dao.updateProduct(product) }
    suspend fun deleteProduct(product: ProductEntity) = withContext(Dispatchers.IO) { dao.deleteProduct(product) }
    suspend fun getProductById(productId: String): ProductEntity? = withContext(Dispatchers.IO) { dao.getProductById(productId) }
    fun getProductsBySeller(sellerId: String): Flow<List<ProductEntity>> = dao.getProductsBySeller(sellerId)

    // Product Photos
    suspend fun addProductPhoto(photo: ProductPhotoEntity) = withContext(Dispatchers.IO) {
        dao.insertPhoto(photo)
        logAudit(photo.uploader, "Sales Rep", "Product Photo Uploaded", "Photo", photo.photoId.toString(), "Uploaded photo for product ${photo.productId}")
    }
    suspend fun updateProductPhoto(photo: ProductPhotoEntity) = withContext(Dispatchers.IO) { dao.updatePhoto(photo) }
    suspend fun deleteProductPhoto(photo: ProductPhotoEntity) = withContext(Dispatchers.IO) { dao.deletePhoto(photo) }
    fun getPhotosForProduct(productId: String): Flow<List<ProductPhotoEntity>> = dao.getPhotosForProduct(productId)
    fun getPhotosForSeller(sellerId: String): Flow<List<ProductPhotoEntity>> = dao.getPhotosForSeller(sellerId)

    // Daily Reports
    suspend fun addDailyReport(report: DailyReportEntity) = withContext(Dispatchers.IO) {
        dao.insertDailyReport(report)
        logAudit(report.repName, "Sales Rep", "Daily Report Submitted", "DailyReport", report.reportId.toString(), "Submitted report for ${report.district}")
        createNotification("Daily Report Submitted", "${report.repName} submitted daily report for ${report.district}", "REPORT", "OPERATIONS", report.reportId.toString())
    }
    suspend fun updateDailyReport(report: DailyReportEntity) = withContext(Dispatchers.IO) { dao.updateDailyReport(report) }
    fun getDailyReportsByRep(repId: String, repName: String): Flow<List<DailyReportEntity>> = dao.getDailyReportsByRep(repId, repName)

    // Shop Visits
    suspend fun addShopVisit(visit: ShopVisitEntity) = withContext(Dispatchers.IO) {
        dao.insertShopVisit(visit)
        logAudit(visit.repName, "Sales Rep", "Shop Visit Recorded", "ShopVisit", visit.visitId.toString(), "Visited ${visit.shopName}: ${visit.purpose}")
    }
    fun getVisitsForShop(shopId: String): Flow<List<ShopVisitEntity>> = dao.getVisitsForShop(shopId)
    fun getVisitsByRep(repName: String): Flow<List<ShopVisitEntity>> = dao.getVisitsByRep(repName)

    // Orders
    suspend fun addOrder(order: OrderEntity) = withContext(Dispatchers.IO) { dao.insertOrder(order) }
    suspend fun updateOrder(order: OrderEntity) = withContext(Dispatchers.IO) { dao.updateOrder(order) }
    fun getOrdersByCustomer(customerId: String): Flow<List<OrderEntity>> = dao.getOrdersByCustomer(customerId)

    // Cart
    fun getCartItems(customerId: String): Flow<List<CartItemEntity>> = dao.getCartItems(customerId)
    suspend fun addCartItem(item: CartItemEntity) = withContext(Dispatchers.IO) { dao.insertCartItem(item) }
    suspend fun updateCartItem(item: CartItemEntity) = withContext(Dispatchers.IO) { dao.updateCartItem(item) }
    suspend fun deleteCartItem(item: CartItemEntity) = withContext(Dispatchers.IO) { dao.deleteCartItem(item) }
    suspend fun clearCart(customerId: String) = withContext(Dispatchers.IO) { dao.clearCart(customerId) }

    // Tasks
    suspend fun addTask(task: TaskEntity) = withContext(Dispatchers.IO) { dao.insertTask(task) }
    suspend fun updateTask(task: TaskEntity) = withContext(Dispatchers.IO) { dao.updateTask(task) }
    suspend fun deleteTask(task: TaskEntity) = withContext(Dispatchers.IO) { dao.deleteTask(task) }

    // CMS Blocks
    suspend fun updateContentBlock(block: ContentBlockEntity) = withContext(Dispatchers.IO) { dao.insertContentBlock(block) }

    // Announcements
    suspend fun addAnnouncement(announcement: AnnouncementEntity) = withContext(Dispatchers.IO) { dao.insertAnnouncement(announcement) }

    // Inquiries
    suspend fun submitInquiry(inquiry: InquiryEntity) = withContext(Dispatchers.IO) { dao.insertInquiry(inquiry) }

    // Job Applications
    suspend fun submitJobApplication(app: JobApplicationEntity) = withContext(Dispatchers.IO) { dao.insertJobApplication(app) }

    // Production Data Purge (for initial office setup)
    suspend fun purgeOperationalData() = withContext(Dispatchers.IO) {
        dao.deleteAllStaff()
        dao.deleteAllShops()
        dao.deleteAllSellers()
        dao.deleteAllProducts()
        dao.deleteAllDailyReports()
        dao.deleteAllOrders()
        dao.deleteAllTasks()
        dao.deleteAllSellerDocuments()
        dao.deleteAllSellerPayments()
        dao.deleteAllFollowUps()
        dao.deleteAllApprovals()
        dao.deleteAllShopVisits()
        logAudit("System / Admin", "CEO / SuperAdmin", "Database Operational Reset", "System", "DB-PURGE", "Purged sample data to initialize empty operational database")
    }
}
